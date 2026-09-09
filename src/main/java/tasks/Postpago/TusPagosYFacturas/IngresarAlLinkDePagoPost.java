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
                    .findElementByAndroidUIAutomator(
                            "new UiSelector().textContains(\"https://portalpagos.claro.com.co\")"
                    )
                    .getText();

        } catch (Exception ePortalPagos) {

            try {

                textoMensaje = androidDriver(actor)
                        .findElementByAndroidUIAutomator(
                                "new UiSelector().textContains(\"https://yoiz.me/\")"
                        )
                        .getText();

            } catch (Exception eYoiz) {

                throw new IllegalStateException(
                        "No se encontro un link de pago valido en el mensaje de WhatsApp"
                );
            }
        }

        String urlExtraida = "";

        if (textoMensaje != null && !textoMensaje.isEmpty()) {

            java.util.regex.Pattern pattern =
                    java.util.regex.Pattern.compile(
                            "https://(?:yoiz\\.me|portalpagos\\.claro\\.com\\.co)/\\S+"
                    );

            java.util.regex.Matcher matcher =
                    pattern.matcher(textoMensaje);

            if (matcher.find()) {

                urlExtraida = matcher.group();

                System.out.println(
                        "URL de pago extraida exitosamente: " + urlExtraida
                );
            }
        }

        if (urlExtraida.isEmpty()) {

            throw new IllegalStateException(
                    "Se encontro el mensaje de pago, pero no fue posible extraer una URL valida"
            );
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
