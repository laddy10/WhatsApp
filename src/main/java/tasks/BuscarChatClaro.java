package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.CLARO_COLOMBIA;

import interactions.wait.WaitFor;
import io.appium.java_client.MobileBy;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;

public class BuscarChatClaro implements Task {

  private static final int TIMEOUT_ENTRADA_CHAT_SEGUNDOS =
      Integer.getInteger("whatsapp.buscarChat.timeout.seconds", 20);

  @Override
  public <T extends Actor> void performAs(T actor) {
    if (cajaMensajeVisible(actor)) {
      return;
    }

    abrirChatDesdeLista(actor);
    esperarEntradaRealAlChat(actor);
  }

  private <T extends Actor> void abrirChatDesdeLista(T actor) {
    WebElement chat = AndroidObject.androidDriver(actor).findElement(
        MobileBy.xpath("//*[contains(@text, '" + CLARO_COLOMBIA
            + "')]/ancestor-or-self::*[@clickable='true'][1]"));
    chat.click();
  }

  private <T extends Actor> void esperarEntradaRealAlChat(T actor) {
    long limite = System.currentTimeMillis() + (TIMEOUT_ENTRADA_CHAT_SEGUNDOS * 1000L);

    while (System.currentTimeMillis() < limite) {
      if (cajaMensajeVisible(actor)) {
        return;
      }
      actor.attemptsTo(WaitFor.aTime(500));
    }

    throw new IllegalStateException(
        "Se encontró el chat de Claro Colombia en la lista, pero no se abrió la conversación. "
            + "No se continúa para evitar monitorear asesor desde fuera del chat.");
  }

  private <T extends Actor> boolean cajaMensajeVisible(T actor) {
    try {
      return TXT_ENVIAR_MENSAJE.resolveFor(actor).isVisible();
    } catch (NoSuchElementException | AssertionError e) {
      return false;
    }
  }

  public static Performable buscarChatClaro() {
    return instrumented(BuscarChatClaro.class);
  }
}