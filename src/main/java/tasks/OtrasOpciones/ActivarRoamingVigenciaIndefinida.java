package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_FECHA_DESACTIVACION_ROAMING;
import static userinterfaces.WhatsAppPage.BTN_FINALIZAR_CHAT_ROAMING;
import static userinterfaces.WhatsAppPage.BTN_VIGENCIA_INDEFINIDA_ROAMING;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitForTextContains;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ActivarRoamingVigenciaIndefinida implements Task {

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

        // 2. Validar respuesta con opción de Vigencia indefinida
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("Vigencia indefinida", "vigencia indefinida"),
                ValidarTextoQueContengaX.elTextoContiene(BTN_VIGENCIA_INDEFINIDA));

        // 3. Clic en "Vigencia indefinida"
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(BTN_VIGENCIA_INDEFINIDA));

        if (!BTN_VIGENCIA_INDEFINIDA_ROAMING.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Click.on(BTN_VIGENCIA_INDEFINIDA_ROAMING));
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText(BTN_VIGENCIA_INDEFINIDA));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en Vigencia indefinida");
        ReportHooks.registrarPaso("Clic en Vigencia indefinida");

        // 4. Validar respuesta del bot de activación indefinida con fecha
        String fechaManana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ROAMING_FORMA_INDEFINIDA, "forma indefinida", "estará activo desde el", "estara activo desde el"),
                ValidarTextoQueContengaX.elTextoContiene(ROAMING_FORMA_INDEFINIDA));

        if (android.textoContiene(actor, fechaManana)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fechaManana));
        } else if (android.textoContiene(actor, fechaHoy)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fechaHoy));
        } else {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("estará activo desde el"));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación Roaming activado de forma indefinida");
        ReportHooks.registrarPaso("Validación Roaming activado de forma indefinida");

        // 5. Clic en Finalizar chat y validar cierre
        if (android.textoContiene(actor, FINALIZAR_CHAT) || android.textoContiene(actor, "Finalizar")) {
            actor.attemptsTo(
                    ScrollHastaTexto.conTexto(FINALIZAR_CHAT));

            if (!BTN_FINALIZAR_CHAT_ROAMING.resolveAllFor(actor).isEmpty()) {
                actor.attemptsTo(Click.on(BTN_FINALIZAR_CHAT_ROAMING));
            } else {
                actor.attemptsTo(ClickElementByText.clickElementByText(FINALIZAR_CHAT));
            }

            CapturaDePantallaMovil.tomarCapturaPantalla("Clic en Finalizar chat");
            ReportHooks.registrarPaso("Clic en Finalizar chat");

            // Validar respuesta del bot al finalizar
            actor.attemptsTo(
                    WaitForTextContains.withAnyTextContains(MENSAJE_DESPEDIDA_BOT, "Siempre que me necesites", "Hola", "nuestro chat finalizó"),
                    ValidarTextoQueContengaX.elTextoContiene(MENSAJE_DESPEDIDA_BOT));

            CapturaDePantallaMovil.tomarCapturaPantalla("Validación cierre natural de chat");
            ReportHooks.registrarPaso("Validación cierre natural de chat");
        }
    }

    public static Performable activarRoamingVigenciaIndefinida() {
        return instrumented(ActivarRoamingVigenciaIndefinida.class);
    }
}
