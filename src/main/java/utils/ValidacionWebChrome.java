package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class ValidacionWebChrome {

    private static final String NATIVE_CONTEXT = "NATIVE_APP";
    private static final String WEBVIEW_PREFIX = "WEBVIEW";

    public static void esperarTextoEnChrome(
            String textoEsperado,
            int timeoutSegundos) {

        AndroidDriver driver = obtenerDriver();

        long limite =
                System.currentTimeMillis()
                        + (timeoutSegundos * 1000L);

        String contextoWebEncontrado = null;

        try {

            /*
             * 1. Esperar a que aparezca un contexto WEBVIEW.
             */
            while (System.currentTimeMillis() < limite) {

                Set<String> contextos =
                        driver.getContextHandles();

                for (String contexto : contextos) {

                    if (contexto != null
                            && contexto.startsWith(WEBVIEW_PREFIX)) {

                        contextoWebEncontrado = contexto;
                        break;
                    }
                }

                if (contextoWebEncontrado != null) {
                    break;
                }

                Thread.sleep(500);
            }

            if (contextoWebEncontrado == null) {

                throw new RuntimeException(
                        "Chrome está abierto, pero Appium no expuso ningún contexto WEBVIEW. "
                                + "No fue posible validar contenido web."
                );
            }

            /*
             * 2. Cambiar al contenido web.
             */
            driver.context(contextoWebEncontrado);

            /*
             * 3. Esperar el texto directamente en el DOM.
             */
            boolean encontrado = false;

            while (System.currentTimeMillis() < limite) {

                try {

                    WebElement elemento =
                            driver.findElement(
                                    By.xpath(
                                            "//*[contains(normalize-space(.), \""
                                                    + textoEsperado
                                                    + "\")]"
                                    )
                            );

                    if (elemento != null
                            && elemento.isDisplayed()) {

                        encontrado = true;
                        break;
                    }

                } catch (Exception ignored) {
                    // Todavía no aparece.
                }

                Thread.sleep(500);
            }

            if (!encontrado) {

                throw new RuntimeException(
                        "No se encontró el texto web esperado en Chrome: ["
                                + textoEsperado
                                + "] después de "
                                + timeoutSegundos
                                + " segundos."
                );
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "La espera de contenido web fue interrumpida.",
                    e
            );

        } finally {

            /*
             * 4. Siempre regresar a Android nativo.
             */
            try {

                driver.context(NATIVE_CONTEXT);

            } catch (Exception ignored) {
            }
        }
    }

    private static AndroidDriver obtenerDriver() {

        var webDriver =
                net.thucydides.core.webdriver
                        .SerenityWebdriverManager
                        .inThisTestThread()
                        .getCurrentDriver();

        if (webDriver
                instanceof net.thucydides.core.webdriver.WebDriverFacade) {

            return (AndroidDriver)
                    ((net.thucydides.core.webdriver.WebDriverFacade) webDriver)
                            .getProxiedDriver();
        }

        return (AndroidDriver) webDriver;
    }
}