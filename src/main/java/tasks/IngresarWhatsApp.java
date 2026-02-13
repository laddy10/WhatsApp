package tasks;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;
import questions.ValidarElemento;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class IngresarWhatsApp implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        if (!Presence.of(TXT_COPIA_SEGURIDAD).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(AHORA_NO)
            );
        }

        if (!Presence.of(TXT_BIENVENIDA_WHATSAPP).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(ACEPTAR_Y_CONTINUAR_WH)
            );
        }

        actor.attemptsTo(
                WaitForResponse.withText(PREGUNTAR_META)
                );
        actor.attemptsTo(
                ValidarTexto.validarTexto(PREGUNTAR_META)
        );

        actor.should(seeThat(
                ValidarElemento.esVisible(LBL_WHATSAPP))
        );
    }

    public static IngresarWhatsApp ingresar() {
        return instrumented(IngresarWhatsApp.class);
    }
}