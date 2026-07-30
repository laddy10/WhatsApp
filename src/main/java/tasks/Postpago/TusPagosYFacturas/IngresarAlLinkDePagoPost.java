package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.ConstantesPost.*;
import static utils.ConstantesPost.SELECCIONA_MEDIO_PAGO;

public class IngresarAlLinkDePagoPost extends AndroidObject implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        Boolean alDia = actor.recall("alDia");
        if (alDia != null && alDia) {
            System.out.println("La cuenta está al día, omitiendo ingreso al link de pago.");
            return;
        }

        String textoMensaje = "";
        try {
            textoMensaje = androidDriver(actor)
                    .findElementByAndroidUIAutomator("new UiSelector().textContains(\"https://yoiz.me/\")")
                    .getText();
        } catch (Exception e) {
            System.out.println("No se pudo obtener el elemento con el link de pago: " + e.getMessage());
        }

        String urlExtraida = "https://yoiz.me/"; // fallback en caso de error
        if (textoMensaje != null && !textoMensaje.isEmpty()) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("https://yoiz\\.me/\\S+");
            java.util.regex.Matcher matcher = pattern.matcher(textoMensaje);
            if (matcher.find()) {
                urlExtraida = matcher.group();
                System.out.println("URL de pago extraída exitosamente: " + urlExtraida);
            }
        }

        // Abrir la URL dinámica extraída
        UtilidadesAndroid.abrirLinkEnNavegador(urlExtraida);

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en el link de pago");
        ReportHooks.registrarPaso("Clic en el link de pago");

        // Esperar a que cargue la página de pagos
        actor.attemptsTo(
                WaitForResponse.withAnyText(PAGA_TU_FACTURA_POSTPAGO)
        );

        // Validar que estamos en la página correcta
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(PAGA_TU_FACTURA_POSTPAGO),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_TOTAL_SALDO),
                ValidarTextoQueContengaX.elTextoContiene(PAGUE_ANTES_DE),
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_MEDIO_PAGO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación de ingreso al portal de pagos");
        ReportHooks.registrarPaso("Validación de ingreso al portal de pagos");
    }

    public static Performable ingresarAlLinkDePagoPost() {
        return instrumented(IngresarAlLinkDePagoPost.class);
    }
}
