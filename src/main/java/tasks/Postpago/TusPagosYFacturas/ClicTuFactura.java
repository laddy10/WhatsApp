package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

public class ClicTuFactura implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Seleccionar la opción "Tu factura"
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(TU_FACTURA));

    CapturaDePantallaMovil.tomarCapturaPantalla("Selección de 'Tu factura'");
    ReportHooks.registrarPaso("Selección de 'Tu factura'");

    // Clic en Enviar para confirmar la selección
    actor.attemptsTo(
        Click.on(BTN_ENVIAR_2),
        WaitForTextContains.withAnyTextContains(
            TEXTO_CODIGO_SEGURIDAD, TEXTO_NECESITO_CONFIRMAR_IDENTIDAD));
  }

  public static Performable clicTuFactura() {
    return instrumented(ClicTuFactura.class);
  }
}
