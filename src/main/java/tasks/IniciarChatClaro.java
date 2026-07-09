package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollInicio;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import interactions.wait.WaitForTextContainsWithTimeout;
import models.EstadoConversacion;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;
import utils.ClasificarRespuestaBot;
import utils.EstadoAtencionHumana;
import utils.TestDataProvider;

public class IniciarChatClaro implements Task {

    private final User user = TestDataProvider.getRealUser();
    private static final int MAX_REINTENTOS = 2;
    private static final int TIMEOUT_RESPUESTA_SALUDO =
            Integer.getInteger("whatsapp.saludo.timeout.seconds", 30);

    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean chatIniciadoCorrectamente = false;
        boolean saludoYaEnviado = false;
        int intentos = 0;

        // Un escenario anterior puede haber dejado el caso en cola sin mensajes visibles.
        if (EstadoAtencionHumana.requiereRecuperacion()
                || ClasificarRespuestaBot.obtenerEstado(actor) == EstadoConversacion.ESPERANDO_ASESOR) {
            EstadoAtencionHumana.marcarEnCola();
            actor.attemptsTo(ManejarConversacionConAsesor.ejecutar());
        }
        while (!chatIniciadoCorrectamente && intentos < MAX_REINTENTOS) {
            intentos++;

            try {
                // Enviar un solo saludo y esperar una respuesta real, no el "Hola" propio.
                if (!saludoYaEnviado) {
                    actor.attemptsTo(
                            Enter.theValue(user.getSaludo()).into(TXT_ENVIAR_MENSAJE),
                            Click.on(BTN_ENVIAR)
                    );
                    saludoYaEnviado = true;

                    boolean respuestaRecibida = WaitForTextContainsWithTimeout.esperar(
                            TIMEOUT_RESPUESTA_SALUDO, obtenerTextosParaWait()
                    ).answeredBy(actor);

                    if (!respuestaRecibida) {
                        EstadoAtencionHumana.marcarEnCola();
                        ReportHooks.registrarPaso(
                                "Sin respuesta al saludo; posible espera en cola de asesor");
                        CapturaDePantallaMovil.tomarCapturaPantalla(
                                "Posible cola de asesor detectada por silencio");
                        actor.attemptsTo(ManejarConversacionConAsesor.ejecutar());
                        saludoYaEnviado = false;
                        continue;
                    }
                }
                // 2️⃣ Clasificar respuesta del bot
                EstadoConversacion estado = ClasificarRespuestaBot.obtenerEstado(actor);

                switch (estado) {

                    case PANTALLA_INICIAL:
                        actor.attemptsTo(SalirYReiniciarChat.ejecutar());
                        continue;

                    case ERROR:
                        actor.attemptsTo(ValidarTextoErrorYLimpiarChat.validarYLimpiar());
                        continue;

                    case ESPERANDO_ASESOR:
                        EstadoAtencionHumana.marcarEnCola();
                        actor.attemptsTo(ManejarConversacionConAsesor.ejecutar());
                        saludoYaEnviado = false;
                        continue;

                    case FLUJO_NORMAL:
                        break;
                }

                // 3️⃣ Flujo normal
                actor.attemptsTo(
                        WaitForTextContains.withAnyTextContains(
                                SALUDO, SALUDO_PARA_AYUDARTE, LINEAS_POSTPAGO, LINEAS_PREPAGO),
                        ScrollInicio.scrollUnaVista()
                );

                CapturaDePantallaMovil.tomarCapturaPantalla("Chat iniciado correctamente");
                ReportHooks.registrarPaso("Chat iniciado correctamente");

                actor.attemptsTo(
                        ValidarTextoQueContengaX.elTextoContiene(LINEAS_POSTPAGO),
                        ValidarTextoQueContengaX.elTextoContiene(LINEAS_PREPAGO),
                        ValidarTextoQueContengaX.elTextoContiene(CUENTA)
                );

                chatIniciadoCorrectamente = true;

            } catch (Exception e) {
                if (e instanceof IllegalStateException) {
                    throw (IllegalStateException) e;
                }
                if (intentos == MAX_REINTENTOS) {
                    throw new RuntimeException(
                            "No se pudo iniciar el chat correctamente después de "
                                    + MAX_REINTENTOS + " intentos", e);
                }

                actor.attemptsTo(WaitFor.aTime(2000));
            }
        }
    }

    public static String[] obtenerTextosParaWait() {
        return new String[]{
                "Líneas postpago",
                "No entendí",
                "Menú principal",
                "Ideas de regalo",
                "¿Qué quieres hacer hoy?",
                "respuesta no es válida",
                "opciones mostradas anteriormente",
                "ingrese el número de opción",
                "comunicarte con uno de nuestros asesores",
                "mi nombre es",
                "Mi nombre es",
                "me encargare de tu solicitud",
                "me encargaré de tu solicitud",
                "Asesor de Claro",
                "asesor de Claro",
                "Gracias por comunicarte",
                "como te encuentras",
                "en que te puedo colaborar",
                "finalizó",
                "finalizo"
        };
    }

    public static Performable iniciarChatClaro() {
        return instrumented(IniciarChatClaro.class);
    }
}
