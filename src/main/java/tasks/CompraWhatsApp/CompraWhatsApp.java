package tasks.CompraWhatsApp;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

public class CompraWhatsApp implements Task {

    private static final String MENSAJE_CAPTURA = "Seleccionar opción ¡Compra por WhatsApp!";

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Seleccionar opción de compra por WhatsApp
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRA_POR_WHATSAPP));

        // Capturar evidencia del paso realizado
        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        // Enviar solicitud y esperar respuesta
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withText(SELECCIONA));
    }

    public static Performable compraWhatsApp() {
        return instrumented(CompraWhatsApp.class);
    }
}
