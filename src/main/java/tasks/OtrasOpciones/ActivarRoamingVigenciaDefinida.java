package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.BTN_FECHA_DESACTIVACION_ROAMING;
import static userinterfaces.WhatsAppPage.BTN_FINALIZAR_CHAT_ROAMING;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Validaciones.ValidarTextoQueContengaX;
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

public class ActivarRoamingVigenciaDefinida implements Task {

    private final String fecha;

    public ActivarRoamingVigenciaDefinida() {
        this(null);
    }

    public ActivarRoamingVigenciaDefinida(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        AndroidObject android = new AndroidObject();

        // 1. Validar mensaje de que el Roaming no está activo
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ROAMING_NO_ESTA_ACTIVO, "no está activo", "no esta activo"),
                ValidarTextoQueContengaX.elTextoContiene(ROAMING_NO_ESTA_ACTIVO));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion mensaje Roaming no está activo");
        ReportHooks.registrarPaso("Validacion mensaje Roaming no está activo");

        // 2. Clic en botón Fecha desactivación (usando Target para evitar texto del bot)
        List<WebElementFacade> botonesFecha = BTN_FECHA_DESACTIVACION_ROAMING.resolveAllFor(actor);
        if (!botonesFecha.isEmpty()) {
            botonesFecha.get(botonesFecha.size() - 1).click();
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText(FECHA_DESACTIVACION));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Dar clic Fecha desactivación");
        ReportHooks.registrarPaso("Dar clic Fecha desactivación");

        // 3. Esperar solicitud de fecha
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ESCRIBE_FECHA_DESACTIVAR, "DD/MM/AAAA", "Escribe la fecha", "desactivar el servicio"),
                ValidarTextoQueContengaX.elTextoContiene("DD/MM/AAAA"));

        // 4. Calcular y enviar fecha (día siguiente a la fecha actual)
        String fechaAEnviar = (fecha != null && !fecha.isEmpty()) ? fecha : obtenerFechaManana();

        actor.attemptsTo(
                Enter.theValue(fechaAEnviar).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitFor.aTime(2000));

        CapturaDePantallaMovil.tomarCapturaPantalla("Fecha de desactivación ingresada: " + fechaAEnviar);
        ReportHooks.registrarPaso("Fecha de desactivación ingresada: " + fechaAEnviar);

        // 5. Validar confirmación de vigencia definida y fechas inicio y fin
        String fechaFin_DMY = fechaAEnviar;
        String fechaFin_YMD = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains("¡Felicidades!", "Felicidades", "estará activo desde el", "estara activo desde el"),
                ValidarTextoQueContengaX.elTextoContiene("estará activo desde el"),
                ValidarTextoQueContengaX.elTextoContiene("hasta el"));

        if (android.textoContiene(actor, fechaFin_DMY)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fechaFin_DMY));
        } else if (android.textoContiene(actor, fechaFin_YMD)) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(fechaFin_YMD));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Confirmación activación con vigencia definida");
        ReportHooks.registrarPaso("Confirmación activación con vigencia definida");

        // 6. Clic en Finalizar chat y validar cierre
        if (android.textoContiene(actor, FINALIZAR_CHAT) || android.textoContiene(actor, "Finalizar")) {
            List<WebElementFacade> botonesFinalizar = BTN_FINALIZAR_CHAT_ROAMING.resolveAllFor(actor);
            if (!botonesFinalizar.isEmpty()) {
                botonesFinalizar.get(botonesFinalizar.size() - 1).click();
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

    private String obtenerFechaManana() {
        return LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static Performable activarRoamingVigenciaDefinida() {
        return instrumented(ActivarRoamingVigenciaDefinida.class);
    }

    public static Performable conFecha(String fecha) {
        return instrumented(ActivarRoamingVigenciaDefinida.class, fecha);
    }
}
