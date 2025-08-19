package tasks;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.WaitForResponse;
import interactions.scroll.Scroll;
import interactions.scroll.ScrollInicio;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.*;

public class IniciarChatClaro implements Task {

    User addCredentials;

    public IniciarChatClaro(User addCredentials) {
        this.addCredentials = addCredentials;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(addCredentials.getSaludo()).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withAnyText(
                        SALUDO, INGRESAR_NUMERO_OPCION, INGRESAR_OPCION_VALIDA),
                ValidarTextoErrorYLimpiarChat.validarYLimpiar(addCredentials),
                WaitForResponse.withText(SALUDO),
                ScrollInicio.scrollUnaVista()
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Iniciar el chat con Claro Colombia");
        ReportHooks.registrarPaso("Iniciar el chat con Claro Colombia");

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(SALUDO),
                ValidarTextoQueContengaX.elTextoContiene(GESTIONAR),
                ValidarTextoQueContengaX.elTextoContiene(LINEAS_POSTPAGO),
                ValidarTextoQueContengaX.elTextoContiene(LINEAS_PREPAGO),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_OPCION),
                Scroll.scrollUnaVista()
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar saludo");
        ReportHooks.registrarPaso("Validar saludo");
    }

    public static Performable iniciarChatClaro(User addCredentials) {
        return instrumented(IniciarChatClaro.class, addCredentials);
    }
}