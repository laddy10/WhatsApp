package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class TransaccionDaviplataHogar extends AndroidObject implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        Boolean alDia = actor.recall("alDia");
        if (alDia != null && alDia) {
            System.out.println("La cuenta está al día, omitiendo transacción Daviplata.");
            return;
        }

        // Seleccionar Daviplata
        actor.attemptsTo(
                Click.on(CHECK_SELECCIONAR_MEDIO_PAGO),
                ScrollGradual.bajar(0.20),
                WaitForTextContains.withAnyTextContains(DAVIPLATA),
                Click.on(BTN_DAVIPLATA),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Daviplata Hogar");
        ReportHooks.registrarPaso("Seleccionar Daviplata Hogar");

        // Scroll suave y clic en Continuar
        actor.attemptsTo(
                ScrollGradual.bajar(0.20),
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR_BUTTON),
                WaitFor.aTime(5000)
        );

        // Validar ingreso al portal de Daviplata
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(PAGO_FACTURAS_HOGAR),
                ValidarTextoQueContengaX.elTextoContiene(PAGO_FACTURAS_HOGAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Formulario Daviplata cargado");
        ReportHooks.registrarPaso("Formulario Daviplata cargado");

        // Diligenciar tipo de documento
        actor.attemptsTo(
                Click.on(SELECT_TIPO_DOCUMENTO),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene(CEDULA_CIUDADANIA_2),
                WaitFor.aTime(1000)
        );

        // Diligenciar número de documento 99321987
        actor.attemptsTo(
                ScrollGradual.bajar(0.20),
                Enter.theValue("99321987").into(TXT_NUMERO_DOCUMENTO),
                interactions.wait.WaitFor.aTime(1500)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Número de documento Daviplata ingresado");
        ReportHooks.registrarPaso("Número de documento Daviplata ingresado");

        // Clic en Confirmar
        actor.attemptsTo(
                Click.on(BTN_CONFIRMAR_DAVIPLATA),
                WaitFor.aTime(5000)
        );

        // Validar que se ha enviado el mensaje de texto / código
        actor.attemptsTo(
                WaitForResponse.withAnyText("Daviplata ha enviado", "mensaje de texto", "código", "codigo")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Redirección a verificación Daviplata validada correctamente");
        ReportHooks.registrarPaso("Redirección a verificación Daviplata validada correctamente");
    }

    public static Performable transaccionDaviplataHogar() {
        return instrumented(TransaccionDaviplataHogar.class);
    }
}
