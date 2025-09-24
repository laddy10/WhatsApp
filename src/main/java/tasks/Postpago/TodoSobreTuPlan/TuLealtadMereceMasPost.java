package tasks.Postpago.TodoSobreTuPlan;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class TuLealtadMereceMasPost implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TU_LEALTAD_MERECE_MAS));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opcion Tu lealtad merece mas");
        ReportHooks.registrarPaso("Seleccionar opcion Tu lealtad merece mas");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitFor.aTime(4000),
                WaitForResponse.withText(SELECCIONA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar botón Selecciona de tu lealtad merece más");
        ReportHooks.registrarPaso("Validar botón Selecciona de tu lealtad merece más");


        actor.attemptsTo(
                ClickElementByText.clickElementByText(SELECCIONA),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_MUSICA),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_DRIVE),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_CLUB),
                ValidarTextoQueContengaX.elTextoContiene(SORPRESA_DE_CUMPLEAÑOS)
        );
    }

    public static Performable tuLealtadMereceMasPost() {
        return instrumented(TuLealtadMereceMasPost.class);
    }
}