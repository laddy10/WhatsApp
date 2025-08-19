package tasks;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.WaitFor;
import interactions.comunes.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.TextoQueContengaX;
import utils.Constantes;

import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.ABANDONAR_CONVERSACION;
import static utils.Constantes.SALUDO;

public class ValidarTextoErrorYLimpiarChat implements Task {

    private final User addCredentials;

    public ValidarTextoErrorYLimpiarChat(User addCredentials) {
        this.addCredentials = addCredentials;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        List<String> mensajesError = Arrays.asList(
                "Por favor ingrese el número de opción",
                "¡Lo siento! No entendí lo que escribiste"
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
                    Enter.theValue(addCredentials.getSaludo()).into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );
        }
    }

    public static ValidarTextoErrorYLimpiarChat validarYLimpiar(User addCredentials) {
        return instrumented(ValidarTextoErrorYLimpiarChat.class, addCredentials);
    }
}
