package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.SELECCIONA;
import static utils.ConstantesPost.*;

public class IngresarTusPagosYFacturas implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TUS_PAGOS_FACTURAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opción 'Tus pagos y facturas'");
        ReportHooks.registrarPaso("Seleccionar opción 'Tus pagos y facturas'");


        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(FACTURA_POSTPAGO_LISTA, OTRAS_OPCIONES_FACTURAS, SELECCIONA)
        );
    }

    public static Performable ingresarTusPagosYFacturas() {
        return instrumented(IngresarTusPagosYFacturas.class);
    }
}