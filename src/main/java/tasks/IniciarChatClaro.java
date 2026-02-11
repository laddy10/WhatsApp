package tasks;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.Scroll;
import interactions.scroll.ScrollInicio;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import models.EstadoConversacion;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;
import utils.ClasificarRespuestaBot;
import utils.TestDataProvider;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class IniciarChatClaro implements Task {

    private final User user = TestDataProvider.getRealUser();
    private static final int MAX_REINTENTOS = 3;


    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean chatIniciadoCorrectamente = false;
        int intentos = 0;

        while (!chatIniciadoCorrectamente && intentos < MAX_REINTENTOS) {
            intentos++;

            try {
                // 1️⃣ Enviar saludo (OBLIGATORIO)
                actor.attemptsTo(
                        Enter.theValue(user.getSaludo()).into(TXT_ENVIAR_MENSAJE),
                        Click.on(BTN_ENVIAR),
                        WaitFor.aTime(3000)
                );

                // 2️⃣ Clasificar respuesta del bot
                EstadoConversacion estado = ClasificarRespuestaBot.obtenerEstado(actor);

                switch (estado) {

                    case PANTALLA_INICIAL:
                        actor.attemptsTo(SalirYReiniciarChat.ejecutar());
                        continue;

                    case ERROR:
                        actor.attemptsTo(ValidarTextoErrorYLimpiarChat.validarYLimpiar());
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
                if (intentos == MAX_REINTENTOS) {
                    throw new RuntimeException(
                            "No se pudo iniciar el chat correctamente después de "
                                    + MAX_REINTENTOS + " intentos", e);
                }

                actor.attemptsTo(WaitFor.aTime(2000));
            }
        }

    }

    public static Performable iniciarChatClaro() {
        return instrumented(IniciarChatClaro.class);
    }
}