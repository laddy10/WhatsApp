package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.*;

public class FallasEnTuNavegacion implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(INTERNET)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Fallas en tu navegación");
        ReportHooks.registrarPaso("Seleccionar Fallas en tu navegación");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(REINICIAR_TU_MODEM)

        );
    }

    public static Performable fallasEnTuNavegacion() {
        return instrumented(FallasEnTuNavegacion.class);
    }
}
