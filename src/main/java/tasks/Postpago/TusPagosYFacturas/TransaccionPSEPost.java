package tasks.Postpago.TusPagosYFacturas;

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

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

public class TransaccionPSEPost extends AndroidObject implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        Boolean alDia = actor.recall("alDia");
        if (alDia != null && alDia) {
            System.out.println("La cuenta está al día, omitiendo transacción PSE.");
            return;
        }

        // Seleccionar Débito Bancario PSE
        actor.attemptsTo(
                Click.on(CHECK_SELECCIONAR_MEDIO_PAGO),
                WaitForTextContains.withAnyTextContains(TARJETA_CREDITO),
                Click.on(BTN_DEBITO_BANCARIO_PSE),
                WaitForResponse.withText(CONTINUAR_BUTTON)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar PSE y continuar Hogar");
        ReportHooks.registrarPaso("Seleccionar PSE y continuar Hogar");

        actor.attemptsTo(
                ScrollGradual.bajar(0.20),
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR_BUTTON)
        );

        // Esperar a que cargue el formulario PSE
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(PAGA_TU_FACTURA_POSTPAGO),
                ValidarTextoQueContengaX.elTextoContiene(PORTAL_PAGOS_Y_RECARGAS)

        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Formulario PSE cargado Hogar");
        ReportHooks.registrarPaso("Formulario PSE cargado Hogar");

        // Seleccionar Banco o Billetera digital
        actor.attemptsTo(
                Click.on(SELECT_BANCO),
                WaitFor.aTime(2000)
        );

        // Hacer scroll hasta NEQUI y seleccionarlo
        scrollToText(actor, NEQUI);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(NEQUI),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("NEQUI seleccionado como medio PSE");
        ReportHooks.registrarPaso("NEQUI seleccionado como medio PSE");

        // Llenar datos del titular y cliente
        actor.attemptsTo(
                ScrollGradual.bajar(0.25),
                Enter.theValue(NOMBRE_FICTICIO).into(TXT_NOMBRE_TITULAR_PSE),
                WaitFor.aTime(1000),
                Click.on(SELECT_TIPO_CLIENTE),
                WaitFor.aTime(1000),
                Click.on(SELECT_PERSONA_NATURAL),
                WaitFor.aTime(1000)
        );

        // Llenar tipo de documento y número
        actor.attemptsTo(
                Click.on(SELECT_TIPO_DOCUMENTO),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene(CEDULA_CIUDADANIA),
                WaitFor.aTime(1000),
                Enter.theValue(NUMERO_CEDULA_FICTICIO).into(TXT_NUMERO_DOCUMENTO),
                WaitFor.aTime(1000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Diligenciado de datos personales en formulario PSE");
        ReportHooks.registrarPaso("Diligenciado de datos personales en formulario PSE");

        // Scroll para llenar campos restantes
        actor.attemptsTo(
                ScrollGradual.bajar(0.25),
                Enter.theValue(CELULAR_FICTICIO).into(TXT_NUMERO_CELULAR),
                WaitFor.aTime(1000),
                Enter.theValue("Calle 100 # 15-20").into(TXT_DIRECCION),
                WaitFor.aTime(1000),
                Enter.theValue(CORREO_FICTICIO).into(TXT_CORREO_ELECTRONICO),
                WaitFor.aTime(1000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Formulario PSE completamente diligenciado");
        ReportHooks.registrarPaso("Formulario PSE completamente diligenciado");

        // Clic en Confirmar para ir a PSE / Nequi
        actor.attemptsTo(
                Click.on(BTN_CONFIRMAR_FINAL),
                WaitFor.aTime(5000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Redirección a pasarela de pagos / Nequi");
        ReportHooks.registrarPaso("Redirección a pasarela de pagos / Nequi");

        // Validar que se redireccionó exitosamente al portal de PSE/Nequi
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(
                        "NEQUI", "nequi", "Ingresa a tu Nequi", "PSE", "pse")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Redirección a Nequi validada correctamente");
        ReportHooks.registrarPaso("Redirección a Nequi validada correctamente");


    }

    public static Performable transaccionPSEPost() {
        return instrumented(TransaccionPSEPost.class);
    }
}
