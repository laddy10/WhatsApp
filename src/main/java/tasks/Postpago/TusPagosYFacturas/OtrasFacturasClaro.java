package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.LBL_PORTAL_PAGOS_RECARGAS;
import static userinterfaces.WhatsAppPostpagoPage.LBL_PRIVACIDAD;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitForResponse;

import java.util.List;

import interactions.wait.WaitForTextContains;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class OtrasFacturasClaro implements Task {

    private static final String MENSAJE_CAPTURA_1 = "Seleccionar 'Otras facturas Claro'";
    private static final String MENSAJE_CAPTURA_2 = "Clic en 'Pagar otras facturas'";
    private static final String MENSAJE_CAPTURA_3 = "Validar direccionamiento portal de pagos";
    private static final String MENSAJE_CAPTURA_4 =
            "Desplegar opciones y seleccionar 'Pago de Facturas'";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Paso 1: Seleccionar "Otras facturas Claro"
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(OTRAS_FACTURAS_CLARO));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_1);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_1);

        actor.attemptsTo(Click.on(BTN_ENVIAR_2), WaitForResponse.withText(PAGAR_OTRAS_FACTURAS));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        // Paso 2: Dar clic en "Pagar otras facturas"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGAR_OTRAS_FACTURAS),
                WaitForTextContains.withAnyTextContains(PORTAL_PAGOS_Y_RECARGAS)
        );


        List<WebElementFacade> lblprivacidad = LBL_PRIVACIDAD.resolveAllFor(actor);
        if (!lblprivacidad.isEmpty()) {
            actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ACEPTAR));
        }

        // Paso 3: Validar direccionamiento al portal de pagos
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(PORTAL_PAGOS_Y_RECARGAS));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_3);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_3);

        // Paso 4: Desplegar "Selecciona la opción de tu interés" y seleccionar "Pago de Facturas"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGO_DE_FACTURAS),
                ClickTextoQueContengaX.elTextoContiene(PAGO_DE_FACTURAS));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_4);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_4);

        // Validar selección de Postpago
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_TIPO_SERVICIO),
                ScrollHastaTexto.conTexto(POSTPAGO_OPCION));

        actor.attemptsTo(Atras.irAtras());

        List<WebElementFacade> linktratamientodatos = LBL_PORTAL_PAGOS_RECARGAS.resolveAllFor(actor);
        if (!linktratamientodatos.isEmpty()) {

            actor.attemptsTo(Atras.irAtras());
        }

        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }

    public static Performable otrasFacturasClaro() {
        return instrumented(OtrasFacturasClaro.class);
    }
}
