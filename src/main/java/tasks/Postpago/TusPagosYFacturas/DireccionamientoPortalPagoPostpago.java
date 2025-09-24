package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.ExtraerURL;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.ConstantesPost.*;

public class DireccionamientoPortalPagoPostpago implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Extraer URL y abrir en navegador
        String mensaje = LBL_MENSAJES.resolveAllFor(actor).stream()
                .map(WebElementFacade::getText)
                .filter(text -> text.contains("yoiz.me") || text.contains("clro.co"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró URL de pago."));

        String urlExtraida = ExtraerURL.desdeTexto(mensaje);
        UtilidadesAndroid.abrirLinkEnNavegador("http://" + urlExtraida);

        // Validar redireccionamiento al portal de pagos
        actor.attemptsTo(
                WaitForResponse.withText(PAGA_TU_FACTURA_POSTPAGO),
                ValidarTexto.validarTexto(PAGA_TU_FACTURA_POSTPAGO),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_TOTAL_SALDO),
                ValidarTextoQueContengaX.elTextoContiene(PAGUE_ANTES_DE),
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_MEDIO_PAGO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Portal de pagos cargado correctamente");
        ReportHooks.registrarPaso("Portal de pagos cargado correctamente");
    }

    public static Performable direccionamientoPortalPagoPostpago() {
        return instrumented(DireccionamientoPortalPagoPostpago.class);
    }
}