package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import interactions.wait.WaitForTextContains;

public class TransaccionMisTarjetasRegistradasHogar extends AndroidObject implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar Mis tarjetas registradas de la lista desplegable
        actor.attemptsTo(
                Click.on(CHECK_SELECCIONAR_MEDIO_PAGO),
                WaitForTextContains.withAnyTextContains(TARJETA_CREDITO),
                ScrollGradual.bajar(0.20),
                Click.on(BTN_MIS_TARJETAS_REGISTRADAS),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Mis tarjetas registradas Hogar");
        ReportHooks.registrarPaso("Seleccionar Mis tarjetas registradas Hogar");

        // Scroll suave y clic en el botón Continuar
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR_BUTTON),
                WaitFor.aTime(5000)
        );

        // Validar ingreso al portal de Mis tarjetas registradas
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(MIS_TARJETAS_REGISTRADAS),
                ValidarTextoQueContengaX.elTextoContiene(MIS_TARJETAS_REGISTRADAS),
                ValidarTextoQueContengaX.elTextoContiene(PORTAL_PAGOS_Y_RECARGAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Portal de Mis tarjetas registradas cargado");
        ReportHooks.registrarPaso("Portal de Mis tarjetas registradas cargado");

        // Scroll suave para revelar el botón Iniciar Sesión en la parte inferior del viewport
        actor.attemptsTo(
                ScrollGradual.bajar(0.30),
                WaitForTextContains.withAnyTextContains(INICIAR_SESION_TARJ_REGISTRADAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Botón Iniciar Sesión visible");
        ReportHooks.registrarPaso("Botón Iniciar Sesión visible");

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(INICIAR_SESION_TARJ_REGISTRADAS),
                ValidarTextoQueContengaX.elTextoContiene(REGISTRATE)
        );

        // Hacer clic en Iniciar Sesión
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(INICIAR_SESION_TARJ_REGISTRADAS),
                WaitFor.aTime(5000)
        );

        // Validar pantalla de inicio de sesión donde se ingresa usuario y contraseña
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(INICIAR_SESION_MI_CLARO),
                ValidarTextoQueContengaX.elTextoContiene(INICIAR_SESION_MI_CLARO),
                ValidarTextoQueContengaX.elTextoContiene(TEXTO_INFORMATIVO_LOGIN),
                ValidarTextoQueContengaX.elTextoContiene(ETIQUETA_USUARIO),
                ValidarTextoQueContengaX.elTextoContiene(ETIQUETA_CONTRASENA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Redirección a pantalla de inicio de sesión de Mi Claro validada");
        ReportHooks.registrarPaso("Redirección a pantalla de inicio de sesión de Mi Claro validada");
    }

    public static Performable transaccionMisTarjetasRegistradasHogar() {
        return instrumented(TransaccionMisTarjetasRegistradasHogar.class);
    }
}
