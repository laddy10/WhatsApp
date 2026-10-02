package utils;

import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import org.openqa.selenium.WebElement;

public class UtilidadesAndroid {

    private static final String CHROME_PACKAGE = "com.android.chrome";
    private static final String CHROME_URL_BAR = "com.android.chrome:id/url_bar";

    public static void abrirLinkEnNavegador(String url) {

        // Asegurar protocolo
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        // Obtener el driver y desenvolver el WebDriverFacade
        var webDriver =
                SerenityWebdriverManager.inThisTestThread().getCurrentDriver();

        AndroidDriver driver;

        if (webDriver instanceof net.thucydides.core.webdriver.WebDriverFacade) {

            driver =
                    (AndroidDriver)
                            ((net.thucydides.core.webdriver.WebDriverFacade) webDriver)
                                    .getProxiedDriver();

        } else {

            driver = (AndroidDriver) webDriver;
        }

        Map<String, Object> intentArgs = new HashMap<>();

        intentArgs.put(
                "command",
                "am"
        );

        intentArgs.put(
                "args",
                List.of(
                        "start",
                        "-a",
                        "android.intent.action.VIEW",
                        "-d",
                        url
                )
        );

        driver.executeScript(
                "mobile: shell",
                intentArgs
        );
    }


    public static void esperarRedireccionamientoWeb(
            Actor actor,
            String dominioEsperado,
            int timeoutSegundos) {

        AndroidDriver driver =
                AndroidObject.androidDriver(actor);

        long limite =
                System.currentTimeMillis()
                        + timeoutSegundos * 1000L;

        String paqueteActual = "";
        String urlActual = "";

        while (System.currentTimeMillis() < limite) {

            try {

                paqueteActual =
                        driver.getCurrentPackage();

                List<WebElement> barrasUrl =
                        driver.findElementsById(
                                CHROME_URL_BAR
                        );

                if (!barrasUrl.isEmpty()) {

                    urlActual =
                            barrasUrl.get(0).getText();

                    if (CHROME_PACKAGE.equals(paqueteActual)
                            && urlActual != null
                            && urlActual.toLowerCase()
                            .contains(
                                    dominioEsperado.toLowerCase()
                            )) {

                        return;
                    }
                }

                Thread.sleep(500);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;

            } catch (Exception ignored) {

                // Chrome puede recrear brevemente su Activity
                // mientras resuelve una URL acortada.
            }
        }

        throw new RuntimeException(
                String.format(
                        "No se confirmo el redireccionamiento a '%s'. "
                                + "Paquete actual: '%s'. "
                                + "URL visible: '%s'.",
                        dominioEsperado,
                        paqueteActual,
                        urlActual
                )
        );
    }


    /**
     * Confirma si Chrome es actualmente la aplicación activa.
     */
    public static boolean estaChromeActivo(
            Actor actor) {

        try {

            AndroidDriver driver =
                    AndroidObject.androidDriver(actor);

            return CHROME_PACKAGE.equals(
                    driver.getCurrentPackage()
            );

        } catch (Exception e) {

            return false;
        }
    }


    /**
     * Obtiene la Activity actual del dispositivo.
     */
    public static String obtenerActividadActual(
            Actor actor) {

        try {

            AndroidDriver driver =
                    AndroidObject.androidDriver(actor);

            return driver.currentActivity();

        } catch (Exception e) {

            return "";
        }
    }


    /**
     * Detecta el caso observado en ST:
     * <p>
     * Chrome sí abrió, pero quedó sin contenido visible.
     * <p>
     * Importante:
     * este estado se considera direccionamiento válido.
     */
    public static boolean chromeActivoSinContenido(
            Actor actor) {

        try {

            AndroidDriver driver =
                    AndroidObject.androidDriver(actor);

            String paqueteActual =
                    driver.getCurrentPackage();

            if (!CHROME_PACKAGE.equals(paqueteActual)) {
                return false;
            }

            /*
             * Primero revisamos si Chrome muestra su barra URL.
             *
             * En la pantalla totalmente negra observada en ST
             * no aparece la interfaz normal de Chrome.
             */
            List<WebElement> barrasUrl =
                    driver.findElementsById(
                            CHROME_URL_BAR
                    );

            /*
             * Si la barra existe y tiene contenido,
             * NO asumimos que sea pantalla negra.
             */
            if (!barrasUrl.isEmpty()) {

                String urlVisible =
                        barrasUrl.get(0).getText();

                if (urlVisible != null
                        && !urlVisible.trim().isEmpty()) {

                    return false;
                }
            }

            /*
             * Buscamos textos visibles con contenido.
             *
             * Si no encontramos ninguno y Chrome sigue activo,
             * tratamos el estado como pantalla sin contenido.
             */
            List<WebElement> textos =
                    driver.findElementsByClassName(
                            "android.widget.TextView"
                    );

            for (WebElement texto : textos) {

                try {

                    String valor =
                            texto.getText();

                    if (valor != null
                            && !valor.trim().isEmpty()
                            && texto.isDisplayed()) {

                        return false;
                    }

                } catch (Exception ignored) {
                    // Ignorar elementos que desaparecen durante la carga
                }
            }

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}