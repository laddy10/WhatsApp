package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollGradual;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ValidarDireccionamientoProgramarPagosHogar extends AndroidObject implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Clic en el botón interactive de WhatsApp "Programar pagos"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PROGRAMAR_PAGOS),
                WaitFor.aTime(8000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en Programar pagos");
        ReportHooks.registrarPaso("Clic en Programar pagos");

        // Validar redirección y que estamos en el portal de pagos de Claro
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(PORTAL_PAGOS_Y_RECARGAS),
                ValidarTextoQueContengaX.elTextoContiene(PORTAL_PAGOS_Y_RECARGAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Ingreso exitoso al Portal de Pagos y Recargas Claro");
        ReportHooks.registrarPaso("Ingreso exitoso al Portal de Pagos y Recargas Claro");

        // Desplazamiento gradual para revelar el formulario de validación de identidad
        actor.attemptsTo(
                ScrollGradual.bajar(0.25),
                WaitFor.aTime(2000)
        );

        // Validar la presencia de los campos clave del formulario
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(TIPO_DOCUMENTO),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_DOCUMENTO),
                ValidarTextoQueContengaX.elTextoContiene(FECHA_EXPEDICION),
                ValidarTextoQueContengaX.elTextoContiene(PRIMER_APELLIDO),
                ScrollGradual.bajar(0.25),
                ValidarTextoQueContengaX.elTextoContiene(NO_SOY_UN_ROBOT),
                ValidarTextoQueContengaX.elTextoContiene(CONTINUAR_BUTTON),
                Atras.irAtras(),
                SalirConversacion.salir()
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación de formulario de Programación de Pagos");
        ReportHooks.registrarPaso("Validación de formulario de Programación de Pagos");
    }

    public static Performable validarDireccionamientoProgramarPagosHogar() {
        return instrumented(ValidarDireccionamientoProgramarPagosHogar.class);
    }
}
