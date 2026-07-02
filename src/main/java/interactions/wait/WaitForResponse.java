package interactions.wait;

import io.appium.java_client.MobileBy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebElement;

public class WaitForResponse implements Interaction {

  private final List<String> expectedTexts;
  private final List<String> failFastTexts;
  private final int timeout;
  private static final int DEFAULT_TIMEOUT = 20;
  private static final long POLL_MILLIS = 500;

  public WaitForResponse(List<String> expectedTexts, int timeout) {
    this(expectedTexts, Collections.emptyList(), timeout);
  }

  public WaitForResponse(List<String> expectedTexts, List<String> failFastTexts, int timeout) {
    this.expectedTexts = expectedTexts;
    this.failFastTexts = failFastTexts;
    this.timeout = timeout;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    boolean found = false;
    long startTime = System.currentTimeMillis();

    while ((System.currentTimeMillis() - startTime) < timeout * 1000L && !found) {
      for (String text : expectedTexts) {
        if (isVisibleInText(actor, text) || isVisibleInDescription(actor, text)) {
          found = true;
          break;
        }
      }

      if (!found) {
        String blockingText = visibleFailFastText(actor);
        if (blockingText != null) {
          throw new RuntimeException(
              String.format(
                  "Se encontro una respuesta de bloqueo antes de los textos esperados. Bloqueo: '%s'. Esperados: %s",
                  blockingText, expectedTexts));
        }

        try {
          Thread.sleep(POLL_MILLIS);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }

    if (!found) {
      throw new RuntimeException(
          String.format(
              "Ninguno de los textos esperados fue encontrado en el tiempo dado (%d segundos): %s",
              timeout, expectedTexts));
    }
  }

  private <T extends Actor> boolean isVisibleInText(T actor, String text) {
    return findElements(actor, "textContains", text);
  }

  private <T extends Actor> boolean isVisibleInDescription(T actor, String text) {
    return findElements(actor, "descriptionContains", text);
  }

  private <T extends Actor> String visibleFailFastText(T actor) {
    for (String text : failFastTexts) {
      if (isVisibleInText(actor, text) || isVisibleInDescription(actor, text)) {
        return text;
      }
    }
    return null;
  }

  private <T extends Actor> boolean findElements(T actor, String selectorMethod, String text) {
    try {
      String query =
          String.format(
              "new UiSelector().%s(\"%s\")", selectorMethod, escapeUiAutomatorText(text));
      List<WebElement> elements =
          BrowseTheWeb.as(actor).getDriver().findElements(MobileBy.AndroidUIAutomator(query));
      return !elements.isEmpty();
    } catch (Exception ignored) {
      return false;
    }
  }

  private String escapeUiAutomatorText(String text) {
    return text.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  public static WaitForResponse withText(String text, int timeoutSeconds) {
    return new WaitForResponse(Arrays.asList(text), timeoutSeconds);
  }

  public static WaitForResponse withAnyText(List<String> texts, int timeoutSeconds) {
    return new WaitForResponse(texts, timeoutSeconds);
  }

  public static WaitForResponse withAnyText(int timeoutSeconds, String... texts) {
    return new WaitForResponse(Arrays.asList(texts), timeoutSeconds);
  }

  public static WaitForResponse withAnyTextFailingOn(
      int timeoutSeconds, List<String> failFastTexts, String... texts) {
    return new WaitForResponse(Arrays.asList(texts), failFastTexts, timeoutSeconds);
  }

  public static WaitForResponse withText(String text) {
    return new WaitForResponse(Arrays.asList(text), DEFAULT_TIMEOUT);
  }

  public static WaitForResponse withAnyText(String... texts) {
    return new WaitForResponse(Arrays.asList(texts), DEFAULT_TIMEOUT);
  }

  public static WaitForResponse withAnyText(List<String> texts) {
    return new WaitForResponse(texts, DEFAULT_TIMEOUT);
  }
}
