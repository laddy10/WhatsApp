package tasks.TodoSobreTuLinea;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.Scroll;
import interactions.wait.WaitForResponse;
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

public class ConsultaOtraLinea implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTA_OTRA_LINEA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opcion 'Consulta otra línea'");
        ReportHooks.registrarPaso("Seleccionar opcion 'Consulta otra línea'");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withText(LINEAS_PREPAGO));

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                ValidarTextoQueContengaX.elTextoContiene(MAS_SERVICIOS_CLARO),
                ValidarTextoQueContengaX.elTextoContiene(LINEAS_POSTPAGO),
                ValidarTextoQueContengaX.elTextoContiene(LINEAS_PREPAGO));

        CapturaDePantallaMovil.tomarCapturaPantalla("Se valida redireccionamiento consulta otra linea");
        ReportHooks.registrarPaso("Se valida redireccionamiento consulta otra linea");

        actor.attemptsTo(
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable consultaOtraLinea() {
        return instrumented(ConsultaOtraLinea.class);
    }
}