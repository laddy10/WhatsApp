package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class TransaccionBancolombiaHogar extends AndroidObject implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar Botón Bancolombia
        actor.attemptsTo(
                Click.on(CHECK_SELECCIONAR_MEDIO_PAGO),
                WaitForTextContains.withAnyTextContains(BOTON_BANCOLOMBIA),
                Click.on(BTN_BOTON_BANCOLOMBIA),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Botón Bancolombia Hogar");
        ReportHooks.registrarPaso("Seleccionar Botón Bancolombia Hogar");

        // Scroll suave y clic en Continuar
        actor.attemptsTo(
                ScrollGradual.bajar(0.20),
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR_BUTTON),
                WaitForTextContains.withAnyTextContains(BANCOLOMBIA_BIENVENIDA)
        );


        // Validar ingreso al portal de bienvenida de Bancolombia
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(BANCOLOMBIA_BIENVENIDA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Portal de Bancolombia cargado");
        ReportHooks.registrarPaso("Portal de Bancolombia cargado");

        // Diligenciar campo Usuario
        actor.attemptsTo(
                Enter.theValue("Pruebas15").into(TXT_USUARIO_BANCOLOMBIA),
                interactions.wait.WaitFor.aTime(1500)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Usuario ingresado en Bancolombia");
        ReportHooks.registrarPaso("Usuario ingresado en Bancolombia");

        // Hacer clic en Continuar en el portal de Bancolombia
        actor.attemptsTo(
                Click.on(BTN_CONTINUAR_BANCOLOMBIA),
                interactions.wait.WaitFor.aTime(5000)
        );

        // Validar que se redireccionó a la pantalla de Clave Principal
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLAVE_PRINCIPAL)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla de Clave Principal Bancolombia validada");
        ReportHooks.registrarPaso("Pantalla de Clave Principal Bancolombia validada");
    }

    public static Performable transaccionBancolombiaHogar() {
        return instrumented(TransaccionBancolombiaHogar.class);
    }
}
