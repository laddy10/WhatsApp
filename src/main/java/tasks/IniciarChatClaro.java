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
            Integer.getInteger("whatsapp.saludo.timeout.seconds", 40);

    @Override
    public <T extends Actor> void performAs(T actor) {

        boolean chatIniciadoCorrectamente = false;
        boolean saludoYaEnviado = false;
        int intentos = 0;

        /*
         * 1. PRIORIDAD ASESOR
         *
         * Si al iniciar la ejecución ya está visible un mensaje
         * indicando escalamiento o atención por asesor,
         * NO se debe enviar un nuevo Hola.
         */
        if (asesorVisible(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto una conversacion en espera o atencion de asesor humano"
            );

            actor.attemptsTo(
                    ManejarConversacionConAsesor.ejecutar()
            );
        }

        /*
         * 2. LIMPIAR CONVERSACIONES RESIDUALES
         *
         * Si no quedó manejándose con asesor, revisar estados
         * conocidos de una ejecución anterior:
         *
         * - Política de tratamiento
         * - Selección de líneas
         * - Menú principal
         * - Pagar factura
         * - No entendí
         *
         * Esta Task se encarga de cerrar y vaciar el chat.
         */
        actor.attemptsTo(
                ValidarYLimpiarChatPendiente.ejecutar()
        );

        /*
         * 3. RECUPERAR ASESOR ENTRE EJECUCIONES
         *
         * Si una ejecución anterior quedó realmente EN_COLA,
         * ASESOR_ACTIVO o CIERRE_PENDIENTE, continuar desde allí
         * sin enviar un nuevo saludo.
         */
        if (EstadoAtencionHumana.requiereRecuperacion()) {

            ReportHooks.registrarPaso(
                    "Recuperando conversacion con asesor en estado: "
                            + EstadoAtencionHumana.leerEstado()
            );

            actor.attemptsTo(
                    ManejarConversacionConAsesor.ejecutar()
            );
        }

        /*
         * 4. FLUJO NORMAL
         */
        while (!chatIniciadoCorrectamente && intentos < MAX_REINTENTOS) {

            intentos++;

            try {

                /*
                 * Enviar un solo saludo y esperar una respuesta real,
                 * no solamente el Hola enviado por nosotros.
                 */
                if (!saludoYaEnviado) {

                    actor.attemptsTo(
                            Enter.theValue(user.getSaludo())
                                    .into(TXT_ENVIAR_MENSAJE),
                            Click.on(BTN_ENVIAR)
                    );

                    saludoYaEnviado = true;

                    boolean respuestaRecibida =
                            WaitForTextContainsWithTimeout.esperar(
                                    TIMEOUT_RESPUESTA_SALUDO,
                                    obtenerTextosParaWait()
                            ).answeredBy(actor);

                    /*
                     * Si no hubo respuesta dentro del tiempo,
                     * conservar el comportamiento actual.
                     */
                    if (!respuestaRecibida) {

                        EstadoAtencionHumana.marcarEnCola();

                        ReportHooks.registrarPaso(
                                "Sin respuesta al saludo; posible espera en cola de asesor"
                        );

                        CapturaDePantallaMovil.tomarCapturaPantalla(
                                "Posible cola de asesor detectada por silencio"
                        );

                        actor.attemptsTo(
                                ManejarConversacionConAsesor.ejecutar()
                        );

                        saludoYaEnviado = false;
                        continue;
                    }
                }

                /*
                 * Clasificar la respuesta que realmente dejó el bot.
                 */
                EstadoConversacion estado =
                        ClasificarRespuestaBot.obtenerEstado(actor);

                switch (estado) {

                    case PANTALLA_INICIAL:

                        actor.attemptsTo(
                                SalirYReiniciarChat.ejecutar()
                        );

                        continue;

                    case ERROR:

                        actor.attemptsTo(
                                ValidarTextoErrorYLimpiarChat.validarYLimpiar()
                        );

                        continue;

                    case ESPERANDO_ASESOR:

                        /*
                         * Si el Hola provocó el mensaje:
                         * "Voy a comunicarte con uno de nuestros asesores",
                         * desde aquí se deja de enviar saludos.
                         */
                        EstadoAtencionHumana.marcarEnCola();

                        actor.attemptsTo(
                                ManejarConversacionConAsesor.ejecutar()
                        );

                        saludoYaEnviado = false;
                        continue;

                    case FLUJO_NORMAL:
                        break;
                }

                /*
                 * Flujo normal existente.
                 * No se modifica.
                 */
                actor.attemptsTo(
                        WaitForTextContains.withAnyTextContains(
                                SALUDO,
                                SALUDO_PARA_AYUDARTE,
                                LINEAS_POSTPAGO,
                                LINEAS_PREPAGO
                        ),
                        ScrollInicio.scrollUnaVista()
                );

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Chat iniciado correctamente"
                );

                ReportHooks.registrarPaso(
                        "Chat iniciado correctamente"
                );

                actor.attemptsTo(
                        WaitFor.aTime(5000),
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
                                    + MAX_REINTENTOS
                                    + " intentos",
                            e
                    );
                }

                actor.attemptsTo(
                        WaitFor.aTime(2000)
                );
            }
        }
    }

    /*
     * Se usa únicamente para saber si la pantalla actual
     * muestra un estado de asesor.
     *
     * ClasificarRespuestaBot ya da prioridad a ESPERANDO_ASESOR
     * sobre menú principal, error y flujo normal.
     */
    private boolean asesorVisible(Actor actor) {

        return ClasificarRespuestaBot.obtenerEstado(actor)
                == EstadoConversacion.ESPERANDO_ASESOR;
    }

    public static String[] obtenerTextosParaWait() {

        return new String[]{
                SALUDO,
                SALUDO_PARA_AYUDARTE,
                LINEAS_POSTPAGO,
                LINEAS_PREPAGO,
                "No entendí",
                "Menú principal",
                "Ideas de regalo",
                "¿Qué quieres hacer hoy?",
                "respuesta no es válida",
                "opciones mostradas anteriormente",
                "ingrese el número de opción",
                "Escribe el número de la opción",
                "asistente virtual",
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