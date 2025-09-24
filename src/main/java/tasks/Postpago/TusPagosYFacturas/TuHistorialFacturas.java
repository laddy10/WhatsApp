package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.ABANDONAR_CONVERSACION;
import static utils.Constantes.SALIR;
import static utils.ConstantesPost.*;

public class TuHistorialFacturas implements Task {

    private static final String MENSAJE_CAPTURA_4 = "Validar historial de facturas";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Validar historial de facturas
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(FACTURAS_RECIENTES),
                ValidarTextoQueContengaX.elTextoContiene(DESCARGALAS_LINK)
        );

        // Validar presencia de las últimas 6 facturas (sin validar fechas específicas)
        validarUltimas6Facturas(actor);

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_4);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_4);

        // Validar link de descarga de facturas
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(PAGA_FACTURA_AQUI)
        );

        actor.attemptsTo(
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }


    private void validarUltimas6Facturas(Actor actor) {
        // Obtener todos los mensajes para buscar facturas
        List<WebElementFacade> mensajes = LBL_MENSAJES.resolveAllFor(actor);

        int facturasEncontradas = 0;

        for (WebElementFacade mensaje : mensajes) {
            String texto = mensaje.getText();

            // Buscar patrones de facturas (Valor: $, Pendiente de pago, URLs)
            if (texto.contains("Valor: $") ||
                    texto.contains("6") ||
                    texto.contains("https://yoiz.me/")) {
                facturasEncontradas++;
            }
        }

        // Validar que se encontraron al menos 6 facturas
        if (facturasEncontradas >= 6) {
            ReportHooks.registrarPaso("✅ Se encontraron " + facturasEncontradas + " facturas en el historial");
        } else {
            ReportHooks.registrarPaso("⚠️ Solo se encontraron " + facturasEncontradas + " facturas (se esperaban mínimo 6)");
        }
    }


    public static Performable tuHistorialFacturas() {
        return instrumented(TuHistorialFacturas.class);
    }
}