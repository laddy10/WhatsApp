package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.ConstantesPost.ESCRIBE_FECHA_DESACTIVAR;
import static utils.ConstantesPost.FECHA_DESACTIVACION;
import static utils.ConstantesPost.ROAMING_NO_ESTA_ACTIVO;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
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

        actor.attemptsTo(
                WaitForTextContains.withTextContains(ROAMING_NO_ESTA_ACTIVO),
                ValidarTextoQueContengaX.elTextoContiene(ROAMING_NO_ESTA_ACTIVO));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion mensaje Roaming no está activo");
        ReportHooks.registrarPaso("Validacion mensaje Roaming no está activo");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(FECHA_DESACTIVACION));

        CapturaDePantallaMovil.tomarCapturaPantalla("Dar clic Fecha desactivación");
        ReportHooks.registrarPaso("Dar clic Fecha desactivación");

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ESCRIBE_FECHA_DESACTIVAR, "DD/MM/AAAA"),
                ValidarTextoQueContengaX.elTextoContiene("DD/MM/AAAA"));

        String fechaAEnviar = (fecha != null && !fecha.isEmpty()) ? fecha : obtenerFechaManana();

        actor.attemptsTo(
                Enter.theValue(fechaAEnviar).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitFor.aTime(3000));

        CapturaDePantallaMovil.tomarCapturaPantalla("Fecha de desactivación ingresada: " + fechaAEnviar);
        ReportHooks.registrarPaso("Fecha de desactivación ingresada: " + fechaAEnviar);
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
