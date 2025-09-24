package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.LBL_FACTURA_POSTPAGO_LISTA;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class TusPagosFacturasPostpago implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TUS_PAGOS_FACTURAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opción 'Tus pagos y facturas'");
        ReportHooks.registrarPaso("Seleccionar opción 'Tus pagos y facturas'");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForTextContains.withAnyTextContains(FACTURA_POSTPAGO_LISTA, OTRAS_OPCIONES_FACTURAS)
        );

        List<WebElementFacade> facturaLista = LBL_FACTURA_POSTPAGO_LISTA.resolveAllFor(actor);

        if (!facturaLista.isEmpty()) {
            CapturaDePantallaMovil.tomarCapturaPantalla("Factura postpago disponible - abrir URL portal de pagos");
            ReportHooks.registrarPaso("Factura postpago disponible - abrir URL portal de pagos");

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(FACTURA_POSTPAGO_LISTA),
                    ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
                    ValidarTextoQueContengaX.elTextoContiene(FECHA_LIMITE_PAGO),
                    ValidarTextoQueContengaX.elTextoContiene(REFERENCIA_PAGO),
                    ValidarTextoQueContengaX.elTextoContiene(PAGA_TU_FACTURA_AQUI),

                    // Ejecutar las siguientes tareas en secuencia
                    DireccionamientoPortalPagoPostpago.direccionamientoPortalPagoPostpago(),
                    ValidarMediosPagoPostpago.validarMediosPagoPostpago(),
                    TransaccionTarjetaCreditoPostpago.transaccionTarjetaCreditoPostpago()
            );

            CapturaDePantallaMovil.tomarCapturaPantalla("Flujo completo de pago ejecutado");
            ReportHooks.registrarPaso("Flujo completo de pago ejecutado correctamente");

        } else {
            CapturaDePantallaMovil.tomarCapturaPantalla("Otras opciones de facturas disponibles");
            ReportHooks.registrarPaso("Factura no disponible - Continuando con otras opciones");

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(OTRAS_OPCIONES_FACTURAS),
                    ClickTextoQueContengaX.elTextoContiene(SELECCIONA)
                    // Aquí puedes agregar la lógica para el flujo alternativo
            );
        }
    }

    public static Performable tusPagosFacturasPostpago() {
        return instrumented(TusPagosFacturasPostpago.class);
    }
}