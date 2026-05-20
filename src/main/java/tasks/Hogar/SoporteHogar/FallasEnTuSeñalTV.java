package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.*;

public class FallasEnTuSeñalTV implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TELEVISION)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Fallas en tu señal de TV");
        ReportHooks.registrarPaso("Seleccionar Fallas en tu señal de TV");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(RECOMENDACIONES),
                ValidarTextoQueContengaX.elTextoContiene(RECOMENDACIONES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje de recomendaciones");
        ReportHooks.registrarPaso("Validar mensaje de recomendaciones");
    }

    public static Performable fallasEnTuSeñalTV() {
        return instrumented(FallasEnTuSeñalTV.class);
    }
}
