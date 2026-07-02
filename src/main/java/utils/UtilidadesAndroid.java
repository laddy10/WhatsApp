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
    var webDriver = SerenityWebdriverManager.inThisTestThread().getCurrentDriver();
    AndroidDriver driver;

    if (webDriver instanceof net.thucydides.core.webdriver.WebDriverFacade) {
      driver =
          (AndroidDriver)
              ((net.thucydides.core.webdriver.WebDriverFacade) webDriver).getProxiedDriver();
    } else {
      driver = (AndroidDriver) webDriver;
    }

    Map<String, Object> intentArgs = new HashMap<>();
    intentArgs.put("command", "am");
    intentArgs.put("args", List.of("start", "-a", "android.intent.action.VIEW", "-d", url));

    driver.executeScript("mobile: shell", intentArgs);
  }

  public static void esperarRedireccionamientoWeb(
      Actor actor, String dominioEsperado, int timeoutSegundos) {
    AndroidDriver driver = AndroidObject.androidDriver(actor);
    long limite = System.currentTimeMillis() + timeoutSegundos * 1000L;
    String paqueteActual = "";
    String urlActual = "";

    while (System.currentTimeMillis() < limite) {
      try {
        paqueteActual = driver.getCurrentPackage();
        List<WebElement> barrasUrl = driver.findElementsById(CHROME_URL_BAR);
        if (!barrasUrl.isEmpty()) {
          urlActual = barrasUrl.get(0).getText();
          if (CHROME_PACKAGE.equals(paqueteActual)
              && urlActual.toLowerCase().contains(dominioEsperado.toLowerCase())) {
            return;
          }
        }
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      } catch (Exception ignored) {
        // Chrome can briefly recreate its activity while resolving a shortened URL.
      }
    }

    throw new RuntimeException(
        String.format(
            "No se confirmo el redireccionamiento a '%s'. Paquete actual: '%s'. URL visible: '%s'.",
            dominioEsperado, paqueteActual, urlActual));
  }
}
