package tasks.OtrasOpciones;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;


public class CambiateaPost implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CAMBIATE_A_POSTPAGO),
                ValidarTexto.validarTexto(CAMBIATE_A_POSTPAGO));


        CapturaDePantallaMovil.tomarCapturaPantalla("cambiate a postpago");
        ReportHooks.registrarPaso("cambiate a postpago");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withAnyText(MENSAJE_CAMBIATE_A_POST)
        );
        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion mensaje asesor");
        ReportHooks.registrarPaso("Validacion mensaje asesor");

    }

    public static Performable cambiateaPost() {
        return instrumented(CambiateaPost.class);
    }
}

