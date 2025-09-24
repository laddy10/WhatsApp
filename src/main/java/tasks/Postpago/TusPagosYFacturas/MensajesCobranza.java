package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.FINALIZAR_CHAT;
import static utils.ConstantesPost.MENSAJES_COBRANZA;

public class MensajesCobranza implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar la opción "Tu factura"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(MENSAJES_COBRANZA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Mensajes de cobranza");
        ReportHooks.registrarPaso("Mensajes de cobranza");

        // Clic en Enviar para confirmar la selección
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(FINALIZAR_CHAT)
        );

    }

    public static Performable mensajesCobranza() {
        return instrumented(MensajesCobranza.class);
    }
}