package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_DESACTIVAR_ROAMING_OPCION;
import static userinterfaces.WhatsAppPage.BTN_FINALIZAR_CHAT_ROAMING;
import static userinterfaces.WhatsAppPage.BTN_SI;
import static userinterfaces.WhatsAppPage.BTN_SI2;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class DesactivarRoaming implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        AndroidObject android = new AndroidObject();

        // 1. Clic en "Desactivar roaming"
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("Desactivar roaming", "desactivar la fecha", "programar la fecha"),
                ScrollHastaTexto.conTexto(BTN_DESACTIVAR_ROAMING));

        if (!BTN_DESACTIVAR_ROAMING_OPCION.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Click.on(BTN_DESACTIVAR_ROAMING_OPCION));
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText(BTN_DESACTIVAR_ROAMING));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en Desactivar roaming");
        ReportHooks.registrarPaso("Clic en Desactivar roaming");

        // 2. Esperar pregunta de confirmación y clic en "Si"
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(PREGUNTA_DESACTIVAR_ROAMING, "¿Estás seguro de desactivar", "desactivar tu Roaming"),
                ValidarTextoQueContengaX.elTextoContiene(PREGUNTA_DESACTIVAR_ROAMING));

        if (!BTN_SI2.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Click.on(BTN_SI2));
        } else if (!BTN_SI.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Click.on(BTN_SI));
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText("Si"));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Confirmar desactivación con Si");
        ReportHooks.registrarPaso("Confirmar desactivación con Si");

        // 3. Validar mensaje de éxito
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ROAMING_DESACTIVADO_EXITO, "ha sido desactivado", "desactivado", "éxito", "exito"),
                ValidarTextoQueContengaX.elTextoContiene(ROAMING_DESACTIVADO_EXITO));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación Roaming desactivado con éxito");
        ReportHooks.registrarPaso("Validación Roaming desactivado con éxito");

        // 4. Finalizar chat si está presente
        if (android.textoContiene(actor, FINALIZAR_CHAT) || android.textoContiene(actor, "Finalizar")) {
            actor.attemptsTo(
                    ScrollHastaTexto.conTexto(FINALIZAR_CHAT));

            if (!BTN_FINALIZAR_CHAT_ROAMING.resolveAllFor(actor).isEmpty()) {
                actor.attemptsTo(Click.on(BTN_FINALIZAR_CHAT_ROAMING));
            } else {
                actor.attemptsTo(ClickElementByText.clickElementByText(FINALIZAR_CHAT));
            }

            actor.attemptsTo(WaitFor.aTime(2000));
            CapturaDePantallaMovil.tomarCapturaPantalla("Boton Finalizar chat presionado");
            ReportHooks.registrarPaso("Boton Finalizar chat presionado");
        }

        // Validar respuesta del bot al finalizar
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(MENSAJE_DESPEDIDA_BOT, "Siempre que me necesites", "Hola", "nuestro chat finalizó"),
                ValidarTextoQueContengaX.elTextoContiene(MENSAJE_DESPEDIDA_BOT));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación cierre natural de chat");
        ReportHooks.registrarPaso("Validación cierre natural de chat");
    }

    public static Performable desactivarRoaming() {
        return instrumented(DesactivarRoaming.class);
    }
}
