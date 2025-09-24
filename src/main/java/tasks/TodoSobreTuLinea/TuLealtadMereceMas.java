package tasks.TodoSobreTuLinea;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_SELECCIONA;
import static utils.Constantes.*;

public class TuLealtadMereceMas implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TU_LEALTAD_MERECE_MAS));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opcion Tu lealtad merece mas");
        ReportHooks.registrarPaso("Seleccionar opcion Tu lealtad merece mas");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitFor.aTime(2000),
                WaitForResponse.withText(SELECCIONA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar botón Selecciona de tu lealtad merece más");
        ReportHooks.registrarPaso("Validar botón Selecciona de tu lealtad merece más");

        actor.attemptsTo(
                Click.on(BTN_SELECCIONA),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_MUSICA),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_DRIVE));
    }

    public static Performable tuLealtadMereceMas() {
        return instrumented(TuLealtadMereceMas.class);
    }
}