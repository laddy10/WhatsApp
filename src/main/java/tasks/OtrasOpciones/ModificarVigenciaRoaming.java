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
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ModificarVigenciaRoaming implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        AndroidObject android = new AndroidObject();

        // 1. Clic en "Fecha desactivación" (primer clic)
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("Fecha desactivación", "fecha de desactivación"),
                ScrollHastaTexto.conTexto(FECHA_DESACTIVACION));

        List<WebElementFacade> botonesPrimerClic = BTN_FECHA_DESACTIVACION_ROAMING.resolveAllFor(actor);
        if (!botonesPrimerClic.isEmpty()) {
            botonesPrimerClic.get(botonesPrimerClic.size() - 1).click();
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText(FECHA_DESACTIVACION));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Primer clic en Fecha desactivación");
        ReportHooks.registrarPaso("Primer clic en Fecha desactivación");

        // 2. Esperar respuesta del bot: o bien solicita fecha directamente ("DD/MM/AAAA"), o bien muestra submenú ("Vigencia indefinida")
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("DD/MM/AAAA", "Escribe la fecha", "Vigencia indefinida", "fecha de desactivación"),
                WaitFor.aTime(1500));

        // 3. Si mostró submenú con "Vigencia indefinida" (caso prepago), hacer el segundo clic en "Fecha desactivación"
        if (android.textoContiene(actor, "Vigencia indefinida") && !android.textoContiene(actor, "DD/MM/AAAA")) {
            List<WebElementFacade> botonesSegundoClic = BTN_FECHA_DESACTIVACION_ROAMING.resolveAllFor(actor);
            if (!botonesSegundoClic.isEmpty()) {
                botonesSegundoClic.get(botonesSegundoClic.size() - 1).click();
            } else {
                actor.attemptsTo(ClickElementByText.clickElementByText(FECHA_DESACTIVACION));
            }

            CapturaDePantallaMovil.tomarCapturaPantalla("Segundo clic en Fecha desactivación");
            ReportHooks.registrarPaso("Segundo clic en Fecha desactivación");
        }

        // 4. Esperar solicitud de fecha
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ESCRIBE_FECHA_DESACTIVAR, "DD/MM/AAAA", "Escribe la fecha", "desactivar el servicio"),
                ValidarTextoQueContengaX.elTextoContiene("DD/MM/AAAA"));

        // 5. Calcular fecha de 2 días siguientes (Día actual + 2)
        String fecha2Dias = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String fecha2Dias_YMD = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fechaManana = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        actor.attemptsTo(
                Enter.theValue(fecha2Dias).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR));

        CapturaDePantallaMovil.tomarCapturaPantalla("Fecha enviada (+2 días): " + fecha2Dias);
        ReportHooks.registrarPaso("Fecha enviada (+2 días): " + fecha2Dias);

        // 6. Validar respuesta del bot en cuanto a las fechas de inicio y fin
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("¡Felicidades!", "Felicidades", ROAMING_ESTARA_ACTIVO, "estará activo desde el", "estara activo desde el"),
                ValidarTextoQueContengaX.elTextoContiene("estará activo desde el"),
                ValidarTextoQueContengaX.elTextoContiene("hasta el"));

        if (android.textoContiene(actor, fecha2Dias_YMD)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fecha2Dias_YMD));
        } else if (android.textoContiene(actor, fecha2Dias)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fecha2Dias));
        }

        if (android.textoContiene(actor, fechaManana)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fechaManana));
        } else if (android.textoContiene(actor, fechaHoy)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fechaHoy));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Confirmación fechas inicio y fin vigencia");
        ReportHooks.registrarPaso("Confirmación fechas inicio y fin vigencia");

        // 7. Clic en Finalizar chat y validar cierre
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

    public static Performable conDosDiasDespues() {
        return instrumented(ModificarVigenciaRoaming.class);
    }
}
