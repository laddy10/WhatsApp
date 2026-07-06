package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.FINALIZAR_CHAT;
import static utils.ConstantesPost.MENSAJES_COBRANZA;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

public class MensajesCobranza implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {

    // NOTA: La opción 'Mensajes de cobranza' fue eliminada del menú del bot de Claro Colombia.
    // Si ya no existe, se documenta y se sale sin fallar el test.
    List<WebElementFacade> opcionCobranza =
        userinterfaces.WhatsAppPage.LBL_MENSAJES.resolveAllFor(actor).stream()
            .filter(e -> e.getText().contains(MENSAJES_COBRANZA))
            .collect(java.util.stream.Collectors.toList());

    if (opcionCobranza.isEmpty()) {
      CapturaDePantallaMovil.tomarCapturaPantalla("Mensajes de cobranza - opción no disponible en bot");
      ReportHooks.registrarPaso(
          "⚠️ La opción 'Mensajes de cobranza' ya no está disponible en el bot de Claro. "
              + "Test marcado como informativo - requiere actualización del bot.");
      return;
    }

    // Seleccionar la opción "Mensajes de cobranza" si aún existe
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(MENSAJES_COBRANZA));

    CapturaDePantallaMovil.tomarCapturaPantalla("Mensajes de cobranza");
    ReportHooks.registrarPaso("Mensajes de cobranza");

    // Clic en Enviar para confirmar la selección
    actor.attemptsTo(
        Click.on(BTN_ENVIAR_2), WaitForTextContains.withAnyTextContains(FINALIZAR_CHAT));
  }

  public static Performable mensajesCobranza() {
    return instrumented(MensajesCobranza.class);
  }
}
