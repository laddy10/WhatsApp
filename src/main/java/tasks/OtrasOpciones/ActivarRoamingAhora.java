package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ACTIVAR_AHORA_ROAMING;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class ActivarRoamingAhora implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Clic en "Activar ahora"
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("Activar ahora", "Activa el servicio de Roaming"),
                ScrollHastaTexto.conTexto(BTN_ACTIVAR_AHORA));

        if (!BTN_ACTIVAR_AHORA_ROAMING.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Click.on(BTN_ACTIVAR_AHORA_ROAMING));
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText(BTN_ACTIVAR_AHORA));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en Activar ahora");
        ReportHooks.registrarPaso("Clic en Activar ahora");

        // 2. Validar que quedó activo
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("quedó activo", "quedo activo", "Tu servicio de Roaming quedó activo"),
                ValidarTextoQueContengaX.elTextoContiene(ROAMING_QUEDO_ACTIVO));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación Roaming quedó activo exitosamente");
        ReportHooks.registrarPaso("Validación Roaming quedó activo exitosamente");
    }

    public static Performable activarRoamingAhora() {
        return instrumented(ActivarRoamingAhora.class);
    }
}
