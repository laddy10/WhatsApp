package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.*;

public class FallasEnTuSeñalTelefonia implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TELEFONIA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Fallas al hacer o recibir llamadas");
        ReportHooks.registrarPaso("Seleccionar Fallas al hacer o recibir llamadas");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(MEJORAR_SERVICIO_LLAMADA),
                ValidarTextoQueContengaX.elTextoContiene(MEJORAR_SERVICIO_LLAMADA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje de recomendaciones servicio de llamadas");
        ReportHooks.registrarPaso("Validar mensaje de recomendaciones servicio de llamadas");
    }

    public static Performable fallasEnTuSeñalTelefonia() {
        return instrumented(FallasEnTuSeñalTelefonia.class);
    }
}
