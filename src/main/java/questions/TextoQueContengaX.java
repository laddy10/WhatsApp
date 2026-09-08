package questions;

import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.StaleElementReferenceException;
import userinterfaces.WhatsAppPage;
import utils.diag.WaitProbe;

public class TextoQueContengaX {

  private static final int MAX_REINTENTOS_LECTURA = 3;

  public static Question<Boolean> verificarTexto(String texto) {
    return actor -> {
      WaitProbe.begin("TextoQueContengaX", "verificarTexto", 0, texto);
      try {
        for (int intento = 1; intento <= MAX_REINTENTOS_LECTURA; intento++) {
          try {
            WaitProbe.iter();
            WaitProbe.searchStart();
            List<WebElementFacade> mensajes = WhatsAppPage.LBL_MENSAJES.resolveAllFor(actor);
            WaitProbe.searchEnd(mensajes.size());
            boolean encontrado =
                mensajes.stream().anyMatch(element -> element.getText().contains(texto));
            if (encontrado) {
              WaitProbe.foundNow(texto);
              WaitProbe.outcome("FOUND");
            } else {
              WaitProbe.outcome("NOT_FOUND");
            }
            return encontrado;
          } catch (StaleElementReferenceException e) {
            if (intento == MAX_REINTENTOS_LECTURA) {
              WaitProbe.outcome("STALE");
              throw e;
            }
            esperarActualizacionDelChat();
          }
        }
        WaitProbe.outcome("NOT_FOUND");
        return false;
      } finally {
        WaitProbe.end();
      }
    };
  }

  private static void esperarActualizacionDelChat() {
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
