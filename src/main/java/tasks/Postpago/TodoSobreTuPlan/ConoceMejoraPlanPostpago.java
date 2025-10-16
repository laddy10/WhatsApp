package tasks.Postpago.TodoSobreTuPlan;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;
import static utils.ConstantesPost.WHATSAPP;

public class ConoceMejoraPlanPostpago implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONOCE_MEJORA_TU_PLAN)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opción Conoce/mejora tu plan postpago");
        ReportHooks.registrarPaso("Seleccionar opción Conoce/mejora tu plan postpago");


        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForTextContains.withTextContains(NOMBRE_DE_PLAN)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar información del plan postpago");
        ReportHooks.registrarPaso("Validar información del plan postpago");

        // Validar detalles del plan postpago mostrados en las imágenes
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(NOMBRE_PLAN_POST),
                ValidarTextoQueContengaX.elTextoContiene(PLAN_INCLUYE),
               // ValidarTextoQueContengaX.elTextoContiene(INSTAGRAM),
               // ValidarTextoQueContengaX.elTextoContiene(FACEBOOK),
               // ValidarTextoQueContengaX.elTextoContiene(TWITTER),
               // ValidarTextoQueContengaX.elTextoContiene(WHATSAPP),
               // ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO),
                ValidarTextoQueContengaX.elTextoContiene(CARGO_FIJO_MENSUAL)
        );


        actor.attemptsTo(
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable conoceMejoraPlanPostpago() {
        return instrumented(ConoceMejoraPlanPostpago.class);
    }
}