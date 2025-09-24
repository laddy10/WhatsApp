package tasks;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.TextoQueContengaX;
import utils.Constantes;
import utils.TestDataProvider;

import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.ABANDONAR_CONVERSACION;

public class ValidarTextoErrorYLimpiarChat implements Task {

    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
        List<String> mensajesError = Arrays.asList(
                "Por favor ingrese el número de opción",
                "¡Lo siento! No entendí lo que escribiste",
                "No entendí tu mensaje."
        );

        boolean textoErrorDetectado = mensajesError.stream()
                .anyMatch(msg -> TextoQueContengaX.verificarTexto(msg).answeredBy(actor));

        if (textoErrorDetectado) {
            actor.attemptsTo(
                    Enter.theValue(Constantes.SALIR).into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR),
                    WaitForResponse.withText(ABANDONAR_CONVERSACION),
                    Click.on(BTN_MAS_OPCIONES),
                    ClickTextoQueContengaX.elTextoContiene("Más"),
                    ClickTextoQueContengaX.elTextoContiene("Vaciar chat"),
                    ClickTextoQueContengaX.elTextoContiene("Vaciar chat"),
                    Enter.theValue(user.getSaludo()).into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );
        }
    }

    public static ValidarTextoErrorYLimpiarChat validarYLimpiar() {
        return instrumented(ValidarTextoErrorYLimpiarChat.class);
    }
}
