package interactions.wait;

import io.appium.java_client.MobileBy;
import java.util.Arrays;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebElement;
import utils.diag.WaitProbe;

public class WaitForTextContains implements Interaction {

  private final List<String> partialTexts;
  private final int timeout;
  private static final int DEFAULT_TIMEOUT = 58;

  public WaitForTextContains(List<String> partialTexts, int timeout) {
    this.partialTexts = partialTexts;
    this.timeout = timeout;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    WaitProbe.begin("WaitForTextContains", "performAs", timeout, partialTexts);
    try {
      long startTime = System.currentTimeMillis();
      boolean found = false;

      while ((System.currentTimeMillis() - startTime) < timeout * 1000 && !found) {
        WaitProbe.iter();
        try {
          for (String partialText : partialTexts) {
            String uiAutomatorQuery =
                String.format("new UiSelector().textContains(\"%s\")", partialText);
            WaitProbe.searchStart();
            List<WebElement> elements =
                BrowseTheWeb.as(actor)
                    .getDriver()
                    .findElements(MobileBy.AndroidUIAutomator(uiAutomatorQuery));
            WaitProbe.searchEnd(elements.size());

            if (!elements.isEmpty()) {
              found = true;
              WaitProbe.foundNow(partialText);
              break;
            }
          }

          if (!found) {
            WaitProbe.sleepStart();
            try {
              Thread.sleep(500);
            } finally {
              WaitProbe.sleepEnd();
            }
          }
        } catch (Exception ignored) {
          // Continúa intentando
        }
      }

      if (!found) {
        WaitProbe.outcome("TIMEOUT");
        throw new RuntimeException(
            String.format(
                "No se encontró ningún texto que contenga alguno de estos valores: %s en el tiempo dado (%d segundos)",
                partialTexts, timeout));
      }
    } finally {
      WaitProbe.end();
    }
  }

  // Método para un solo texto con timeout personalizado
  public static WaitForTextContains withTextContains(String partialText, int timeoutSeconds) {
    return new WaitForTextContains(Arrays.asList(partialText), timeoutSeconds);
  }

  // Método para un solo texto con timeout por defecto
  public static WaitForTextContains withTextContains(String partialText) {
    return new WaitForTextContains(Arrays.asList(partialText), DEFAULT_TIMEOUT);
  }

  // Método para múltiples textos con timeout personalizado
  public static WaitForTextContains withAnyTextContains(
      List<String> partialTexts, int timeoutSeconds) {
    return new WaitForTextContains(partialTexts, timeoutSeconds);
  }

  // Método para múltiples textos con timeout por defecto
  public static WaitForTextContains withAnyTextContains(List<String> partialTexts) {
    return new WaitForTextContains(partialTexts, DEFAULT_TIMEOUT);
  }

  // Método para múltiples textos como parámetros variables con timeout por defecto
  public static WaitForTextContains withAnyTextContains(String... partialTexts) {
    return new WaitForTextContains(Arrays.asList(partialTexts), DEFAULT_TIMEOUT);
  }

  // Método para múltiples textos como parámetros variables con timeout personalizado
  public static WaitForTextContains withAnyTextContains(
      int timeoutSeconds, String... partialTexts) {
    return new WaitForTextContains(Arrays.asList(partialTexts), timeoutSeconds);
  }
}
