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

  private static final String WHATSAPP_PACKAGE = "com.whatsapp";

  @Override
  public <T extends Actor> void performAs(T actor) {
    AndroidObject androidObject = new AndroidObject();

    activarWhatsAppSiEsNecesario(actor);

    if (!Presence.of(TXT_COPIA_SEGURIDAD).viewedBy(actor).resolveAll().isEmpty()) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(AHORA_NO));
    }

    if (!Presence.of(TXT_BIENVENIDA_WHATSAPP).viewedBy(actor).resolveAll().isEmpty()) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ACEPTAR_Y_CONTINUAR_WH));
    }

    int attempts = 0;
    while (!androidObject.textoContiene(actor, PREGUNTAR_META) && attempts < 4) {
      try {
        activarWhatsAppSiEsNecesario(actor);
        androidObject.Atras(actor);
        actor.attemptsTo(WaitFor.aTime(1500));
      } catch (Exception e) {
        activarWhatsAppSiEsNecesario(actor);
      }
      attempts++;
    }

    activarWhatsAppSiEsNecesario(actor);
    actor.attemptsTo(WaitForResponse.withText(PREGUNTAR_META, 30));
    actor.attemptsTo(ValidarTexto.validarTexto(PREGUNTAR_META));
  }

  private <T extends Actor> void activarWhatsAppSiEsNecesario(T actor) {
    try {
      String currentPackage = AndroidObject.androidDriver(actor).getCurrentPackage();
      if (!WHATSAPP_PACKAGE.equals(currentPackage)) {
        AndroidObject.androidDriver(actor).activateApp(WHATSAPP_PACKAGE);
        actor.attemptsTo(WaitFor.aTime(3000));
      }
    } catch (Exception ignored) {
      try {
        AndroidObject.androidDriver(actor).activateApp(WHATSAPP_PACKAGE);
        actor.attemptsTo(WaitFor.aTime(3000));
      } catch (Exception ignoredAgain) {
        // The following explicit wait will report the failure with context.
      }
    }
  }

  public static IngresarWhatsApp ingresar() {
    return instrumented(IngresarWhatsApp.class);
  }
}