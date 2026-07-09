package questions;

import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.StaleElementReferenceException;
import userinterfaces.WhatsAppPage;

public class TextoQueContengaX {

  private static final int MAX_REINTENTOS_LECTURA = 3;

  public static Question<Boolean> verificarTexto(String texto) {
    return actor -> {
      for (int intento = 1; intento <= MAX_REINTENTOS_LECTURA; intento++) {
        try {
          return WhatsAppPage.LBL_MENSAJES.resolveAllFor(actor).stream()
              .anyMatch(element -> element.getText().contains(texto));
        } catch (StaleElementReferenceException e) {
          if (intento == MAX_REINTENTOS_LECTURA) {
            throw e;
          }
          esperarActualizacionDelChat();
        }
      }
      return false;
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
