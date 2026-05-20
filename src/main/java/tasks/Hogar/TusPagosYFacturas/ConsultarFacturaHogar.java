package tasks.Hogar.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.FACTURA_POSTPAGO_LISTA;
import static utils.ConstantesPost.VALOR_A_PAGAR;

public class ConsultarFacturaHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Seleccionar la opción en el menú de lista
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTA_FACTURA_PAGA_TUYA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Consulta tu factura y paga la tuya o la de otros'");
        ReportHooks.registrarPaso("Seleccionar 'Consulta tu factura y paga la tuya o la de otros'");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withAnyText(VALOR_A_PAGAR, FACTURA_POSTPAGO_LISTA)
        );

        // Validar información de la factura
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar información de la factura");
        ReportHooks.registrarPaso("Validar información de la factura");

        // Seleccionar la opción "Conoce y descarga tu factura"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONOCE_Y_DESCARGA_TU_FACTURA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Conoce y descarga tu factura'");
        ReportHooks.registrarPaso("Seleccionar 'Conoce y descarga tu factura'");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withAnyText(TEXTO_NECESITO_CONFIRMAR_IDENTIDAD)
        );
    }

    public static Performable consultarFacturaHogar() {
        return instrumented(ConsultarFacturaHogar.class);
    }
}
