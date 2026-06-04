package tasks.Hogar.TodoSobrePlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForTextContains;

public class ConsultaConsumosHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // 1. Seleccionar la opción "Consulta tus consumos"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTA_TUS_CONSUMOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Consulta tus consumos hogar");
        ReportHooks.registrarPaso("Seleccionar Consulta tus consumos hogar");

        // 2. Hacer clic en "Enviar"
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2)
        );

        // 3. Esperar y Validar la información de consumos del plan hogar
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(CONSUMOS_PLAN_HOGAR),
                ValidarTextoQueContengaX.elTextoContiene(CONSUMOS_PLAN_HOGAR),
                ValidarTextoQueContengaX.elTextoContiene(TELEVISION_MAYUS),
                ValidarTextoQueContengaX.elTextoContiene(TELEFONIA_MAYUS),
                ValidarTextoQueContengaX.elTextoContiene(INTERNET_MAYUS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Se validan los consumos del plan hogar");
        ReportHooks.registrarPaso("Se validan los consumos del plan hogar");

        // 4. Salir de la conversación
        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }

    public static Performable consultaConsumosHogar() {
        return instrumented(ConsultaConsumosHogar.class);
    }
}
