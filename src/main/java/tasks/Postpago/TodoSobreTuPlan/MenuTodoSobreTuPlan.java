package tasks.Postpago.TodoSobreTuPlan;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class MenuTodoSobreTuPlan implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TODO_SOBRE_TU_PLAN)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar el menú Todo sobre tu plan");
        ReportHooks.registrarPaso("Seleccionar el menú Todo sobre tu plan");


        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withText(SELECCIONA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Dar clic boton Selecciona");
        ReportHooks.registrarPaso("Dar clic boton Selecciona");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SELECCIONA),
                WaitForResponse.withText(CONOCE_MEJORA_TU_PLAN)
        );
    }

    public static Performable menuTodoSobreTuPlan() {
        return instrumented(MenuTodoSobreTuPlan.class);
    }
}