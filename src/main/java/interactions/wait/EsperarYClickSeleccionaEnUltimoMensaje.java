package interactions.wait;

import static userinterfaces.WhatsAppPage.LBL_MENSAJES;

import java.util.List;
import java.util.stream.Collectors;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EsperarYClickSeleccionaEnUltimoMensaje implements Interaction {

  private final int timeoutSeg;
  private final int pollMs;

  public EsperarYClickSeleccionaEnUltimoMensaje(int timeoutSeg, int pollMs) {
    this.timeoutSeg = timeoutSeg;
    this.pollMs = pollMs;
  }

  public static EsperarYClickSeleccionaEnUltimoMensaje conTimeout(int timeoutSegundos) {
    return Tasks.instrumented(EsperarYClickSeleccionaEnUltimoMensaje.class, timeoutSegundos, 100);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    long fin = System.currentTimeMillis() + (timeoutSeg * 1000L);
    final String label = "Selecciona";
    WebDriver driver = BrowseTheWeb.as(actor).getDriver();
    while (true) {
      if (System.currentTimeMillis() > fin) {
        throw new RuntimeException("No aparecio el boton 'Selecciona' dentro del tiempo.");
      }

      try {
        WebElementFacade ultimoMsg = ultimoMensaje(actor);
        if (ultimoMsg != null) {
          List<WebElement> dentro =
              ultimoMsg.findElements(
                  By.xpath(
                      ".//*[@resource-id='com.whatsapp:id/button' and .//*[@text='"
                          + label
                          + "' or @content-desc='"
                          + label
                          + "']] | .//*[@resource-id='com.whatsapp:id/button_content' and (@text='"
                          + label
                          + "' or @content-desc='"
                          + label
                          + "')] | .//*[@text='"
                          + label
                          + "' or @content-desc='"
                          + label
                          + "']"));
          if (!dentro.isEmpty()) {
            dentro.get(dentro.size() - 1).click();
            return;
          }

          List<WebElement> hermanos =
              ultimoMsg.findElements(
                  By.xpath(
                      "../following-sibling::*//*[(@resource-id='com.whatsapp:id/button' and .//*[@text='"
                          + label
                          + "' or @content-desc='"
                          + label
                          + "']) or (@resource-id='com.whatsapp:id/button_content' and (@text='"
                          + label
                          + "' or @content-desc='"
                          + label
                          + "')) or @text='"
                          + label
                          + "' or @content-desc='"
                          + label
                          + "']"));
          if (!hermanos.isEmpty()) {
            hermanos.get(hermanos.size() - 1).click();
            return;
          }
        }

        WebElement chipGlobal = ultimoChipSeleccionaVisibleDespuesDeMasOpciones(driver, label);
        if (chipGlobal != null) {
          chipGlobal.click();
          return;
        }
      } catch (StaleElementReferenceException e) {
        dormir(pollMs);
        continue;
      }

      dormir(pollMs);
    }
  }

  private static WebElement ultimoChipSeleccionaVisibleDespuesDeMasOpciones(WebDriver driver, String label) {
    List<WebElement> visibles = chipsSeleccionaVisibles(driver, label);
    if (visibles.isEmpty()) {
      return null;
    }

    int yMasOpciones = yUltimoTexto(driver, "Más opciones");
    WebElement debajoDeMasOpciones = visibles.stream()
        .filter(el -> yMasOpciones == Integer.MIN_VALUE || yElemento(el) > yMasOpciones)
        .max(java.util.Comparator.comparingInt(EsperarYClickSeleccionaEnUltimoMensaje::yElemento))
        .orElse(null);

    if (debajoDeMasOpciones != null) {
      return debajoDeMasOpciones;
    }

    return visibles.stream()
        .max(java.util.Comparator.comparingInt(EsperarYClickSeleccionaEnUltimoMensaje::yElemento))
        .orElse(null);
  }
  private static List<WebElement> chipsSeleccionaVisibles(WebDriver driver, String label) {
    List<WebElement> cand =
        driver.findElements(
            By.xpath(
                "//*[@resource-id='com.whatsapp:id/button' and .//*[@text='"
                    + label
                    + "' or @content-desc='"
                    + label
                    + "']]"));
    if (cand.isEmpty()) {
      cand = driver.findElements(By.xpath("//*[@text='" + label + "' or @content-desc='" + label + "']"));
    }

    return cand.stream()
        .filter(
            el -> {
              try {
                return el.isDisplayed() && el.isEnabled();
              } catch (Exception e) {
                return false;
              }
            })
        .collect(Collectors.toList());
  }

  private static int yUltimoTexto(WebDriver driver, String texto) {
    return driver.findElements(By.xpath("//*[@text='" + texto + "' or @content-desc='" + texto + "']")).stream()
        .filter(
            el -> {
              try {
                return el.isDisplayed();
              } catch (Exception e) {
                return false;
              }
            })
        .mapToInt(EsperarYClickSeleccionaEnUltimoMensaje::yElemento)
        .max()
        .orElse(Integer.MIN_VALUE);
  }
  private static int yElemento(WebElement elemento) {
    try {
      return elemento.getRect().y;
    } catch (Exception e) {
      return Integer.MIN_VALUE;
    }
  }

  private static WebElementFacade ultimoMensaje(Actor actor) {
    List<WebElementFacade> mensajes = LBL_MENSAJES.resolveAllFor(actor);
    return mensajes.isEmpty() ? null : mensajes.get(mensajes.size() - 1);
  }

  private static void dormir(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ignored) {
    }
  }
}
