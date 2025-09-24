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
import static utils.ConstantesPost.TU_HISTORIAL;

public class ClicTuHistorial implements Task {

    private static final String MENSAJE_CAPTURA_1 = "Seleccionar 'Tu historial'";


    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar la opción "Tu historial"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TU_HISTORIAL)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_1);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_1);

        // Clic en Enviar para confirmar la selección
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(TEXTO_CODIGO_SEGURIDAD, TEXTO_NECESITO_CONFIRMAR_IDENTIDAD)
        );

    }

    public static Performable clicTuHistorial() {
        return instrumented(ClicTuHistorial.class);
    }
}