package tasks;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class BuscarChatClaro implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
               // Click.on(BTN_ENVIAR_MENSAJE),
               // Click.on(BTN_LUPA),
                Click.on(BTN_LUPA_PRINCIPAL),
                Enter.theValue(CLARO).into(TXT_BUSCAR_TEXTO_PRINCIPAL),
                ClickTextoQueContengaX.elTextoContiene(CLARO_COLOMBIA),
                WaitForResponse.withText((CLARO_COLOMBIA)));
    }

    public static Performable buscarChatClaro() {
        return instrumented(BuscarChatClaro.class);
    }
}