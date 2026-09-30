package utils;

import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ValidacionChromeNativo {

    private static final String CHROME_PACKAGE = "com.android.chrome";
    private static final String CHROME_URL_BAR = "com.android.chrome:id/url_bar";

    public static void validarPagina(
            Actor actor,
            String textoUrlEsperado,
            int timeoutSegundos) {

        AndroidDriver driver = AndroidObject.androidDriver(actor);

        long limite = System.currentTimeMillis() + timeoutSegundos * 1000L;

        String paqueteActual = "";
        String urlActual = "";

        while (System.currentTimeMillis() < limite) {

            try {

                paqueteActual = driver.getCurrentPackage();

                if (!CHROME_PACKAGE.equals(paqueteActual)) {
                    esperar();
                    continue;
                }

                List<WebElement> barrasUrl =
                        driver.findElementsById(CHROME_URL_BAR);

                if (barrasUrl.isEmpty()) {
                    esperar();
                    continue;
                }

                WebElement barraUrl = barrasUrl.get(0);

                /*
                 * Chrome puede no exponer el valor mientras
                 * la barra está desenfocada.
                 */
                barraUrl.click();

                esperarCorto();

                /*
                 * Intentamos varias propiedades nativas.
                 */
                urlActual = barraUrl.getText();

                if (urlActual == null || urlActual.trim().isEmpty()) {
                    urlActual = barraUrl.getAttribute("text");
                }

                if (urlActual == null || urlActual.trim().isEmpty()) {
                    urlActual = barraUrl.getAttribute("contentDescription");
                }

                if (urlActual == null) {
                    urlActual = "";
                }

                /*
                 * Quitamos el foco de la barra sin navegar.
                 */
                driver.navigate().back();

                if (urlActual.toLowerCase()
                        .contains(textoUrlEsperado.toLowerCase())) {

                    return;
                }

                esperar();

            } catch (Exception e) {

                /*
                 * Intentamos quitar cualquier foco/modal
                 * que haya quedado abierto.
                 */
                try {
                    driver.navigate().back();
                } catch (Exception ignored) {
                }

                esperar();
            }
        }

        throw new RuntimeException(
                "No se pudo validar la pagina web en Chrome. "
                        + "Destino esperado: ["
                        + textoUrlEsperado
                        + "]. Paquete actual: ["
                        + paqueteActual
                        + "]. URL obtenida: ["
                        + urlActual
                        + "]"
        );
    }

    private static void esperar() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "La espera de Chrome fue interrumpida",
                    e
            );
        }
    }

    private static void esperarCorto() {

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "La espera de Chrome fue interrumpida",
                    e
            );
        }
    }
}