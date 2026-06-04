package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;
import utils.AndroidObject;

public class IngresarWhatsApp implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {

    if (!Presence.of(TXT_COPIA_SEGURIDAD).viewedBy(actor).resolveAll().isEmpty()) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(AHORA_NO));
    }

    if (!Presence.of(TXT_BIENVENIDA_WHATSAPP).viewedBy(actor).resolveAll().isEmpty()) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ACEPTAR_Y_CONTINUAR_WH));
    }

    AndroidObject androidObject = new AndroidObject();
    int attempts = 0;
    while (!androidObject.textoContiene(actor, PREGUNTAR_META) && attempts < 4) {
      try {
        androidObject.Atras(actor);
        actor.attemptsTo(WaitFor.aTime(1500));
      } catch (Exception e) {
        // Ignore navigation failures
      }
      attempts++;
    }

    actor.attemptsTo(WaitForResponse.withText(PREGUNTAR_META));
    actor.attemptsTo(ValidarTexto.validarTexto(PREGUNTAR_META));
  }

  public static IngresarWhatsApp ingresar() {
    return instrumented(IngresarWhatsApp.class);
  }
}
