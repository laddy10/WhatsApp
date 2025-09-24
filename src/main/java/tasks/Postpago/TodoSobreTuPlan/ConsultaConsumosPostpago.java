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

public class ConsultaConsumosPostpago implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTA_TUS_CONSUMOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opción Consulta tus consumos postpago");
        ReportHooks.registrarPaso("Seleccionar opción Consulta tus consumos postpago");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForTextContains.withTextContains(NOMBRE_DE_PLAN)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar información de consumos del plan postpago");
        ReportHooks.registrarPaso("Validar información de consumos del plan postpago");

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(NOMBRE_PLAN_POST),
                ValidarTextoQueContengaX.elTextoContiene(CONSUMO_A_LA_FECHA),
                ValidarTextoQueContengaX.elTextoContiene(MOVILES_CLARO),
                ValidarTextoQueContengaX.elTextoContiene(OTROS_OPERADORES),
                ValidarTextoQueContengaX.elTextoContiene(FIJOS),
                ValidarTextoQueContengaX.elTextoContiene(DATOS_INCLUIDOS_PLAN),
                ValidarTextoQueContengaX.elTextoContiene(DATOS_DISPONIBLES_PLAN),
                ValidarTextoQueContengaX.elTextoContiene(FECHA_INICIO),
                ValidarTextoQueContengaX.elTextoContiene(FECHA_CORTE),
                ValidarTextoQueContengaX.elTextoContiene(MENSAJES_TEXTO_PLAN),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable consultaConsumosPostpago() {
        return instrumented(ConsultaConsumosPostpago.class);
    }
}