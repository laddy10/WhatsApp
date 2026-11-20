package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.BTN_FECHA_DESACTIVACION_ROAMING;
import static userinterfaces.WhatsAppPage.BTN_FINALIZAR_CHAT_ROAMING;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ProgramarFechaDesactivacionRoaming implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        AndroidObject android = new AndroidObject();

        // 1. Clic en "Fecha desactivación"
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("Fecha desactivación", "fecha de desactivación"),
                ScrollHastaTexto.conTexto(FECHA_DESACTIVACION));

        if (!BTN_FECHA_DESACTIVACION_ROAMING.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Click.on(BTN_FECHA_DESACTIVACION_ROAMING));
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText(FECHA_DESACTIVACION));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en Fecha desactivación");
        ReportHooks.registrarPaso("Clic en Fecha desactivación");

        // 2. Esperar solicitud de fecha
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ESCRIBE_FECHA_DESACTIVAR, "DD/MM/AAAA"),
                ValidarTextoQueContengaX.elTextoContiene("DD/MM/AAAA"));

        // 3. Calcular fecha de mañana (Día actual + 1)
        String fechaManana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        actor.attemptsTo(
                Enter.theValue(fechaManana).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR));

        CapturaDePantallaMovil.tomarCapturaPantalla("Fecha de desactivación enviada: " + fechaManana);
        ReportHooks.registrarPaso("Fecha de desactivación enviada: " + fechaManana);

        // 4. Validar confirmación de vigencia y fecha programada
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ROAMING_ESTARA_ACTIVO, "estará activo desde el", "estara activo desde el"),
                ValidarTextoQueContengaX.elTextoContiene("estará activo"),
                ValidarTextoQueContengaX.elTextoContiene(fechaManana));

        CapturaDePantallaMovil.tomarCapturaPantalla("Confirmación de vigencia programada");
        ReportHooks.registrarPaso("Confirmación de vigencia programada");

        // 5. Clic en Finalizar chat
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

    public static Performable conFechaManana() {
        return instrumented(ProgramarFechaDesactivacionRoaming.class);
    }
}
