package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.scroll.ScrollHastaTexto;
import interactions.scroll.ScrollInicio;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

public class TransaccionTarjetaCreditoPostpago implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar Tarjeta de Crédito
        actor.attemptsTo(
                Click.on(BTN_TARJETA_CREDITO),
                WaitForResponse.withText(CONTINUAR_BUTTON));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Tarjeta de Crédito y continuar");
        ReportHooks.registrarPaso("Seleccionar Tarjeta de Crédito y continuar");

        actor.attemptsTo(
                Click.on(BTN_CONTINUAR_PAGO)
        );


        // Esperar a que cargue el formulario
        actor.attemptsTo(
                WaitForResponse.withText(PORTAL_PAGOS_Y_RECARGAS),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_TOTAL),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_FACTURA),
                ValidarTextoQueContengaX.elTextoContiene(DESCRIPCION_COMPRA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Formulario de tarjeta de crédito cargado");
        ReportHooks.registrarPaso("Formulario de tarjeta de crédito cargado");

        // Llenar datos ficticios de la tarjeta
        actor.attemptsTo(
                WaitFor.aTime(2000),
                ScrollHastaTexto.conTexto(NUMERO_CELULAR),
                Enter.theValue(NUMERO_TARJETA_FICTICIO).into(TXT_NUMERO_TARJETA),
                WaitFor.aTime(1000),
                Enter.theValue(NOMBRE_FICTICIO).into(TXT_NOMBRE_APELLIDO),
                WaitFor.aTime(1000),
                ValidarTiposDocumento.validarTiposDocumento(),
                WaitFor.aTime(1000),
                Enter.theValue(NUMERO_CEDULA_FICTICIO).into(TXT_NUMERO_DOCUMENTO),
                WaitFor.aTime(1000),
                Click.on(SELECT_MES_EXPIRACION),
                ClickTextoQueContengaX.elTextoContiene("05"),
                WaitFor.aTime(1000),
                Click.on(SELECT_ANIO_EXPIRACION),
                ClickTextoQueContengaX.elTextoContiene("2028"),
                WaitFor.aTime(1000),
                Enter.theValue(CVC_FICTICIO).into(TXT_CVC)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Datos básicos de tarjeta ingresados");
        ReportHooks.registrarPaso("Datos básicos de tarjeta ingresados");

        // Continuar con datos adicionales
        actor.attemptsTo(
                WaitFor.aTime(2000),
                ScrollInicio.scrollUnaVista(),
                ScrollHastaTexto.conTexto(CANCELAR_BUTTON),
                Enter.theValue(CORREO_FICTICIO).into(TXT_CORREO_ELECTRONICO),
                WaitFor.aTime(1000),
                Enter.theValue(CELULAR_FICTICIO).into(TXT_NUMERO_CELULAR),
                WaitFor.aTime(1000),
                Click.on(SELECT_NUMERO_CUOTAS),
                ClickTextoQueContengaX.elTextoContiene("9"),
                WaitFor.aTime(1000),
                Click.on(TOGGLE_GUARDAR_TARJETA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Datos adicionales completados");
        ReportHooks.registrarPaso("Datos adicionales completados");

        // Validar botones de acción
        actor.attemptsTo(
                ValidarTexto.validarTexto(CONFIRMAR_BUTTON),
                ValidarTexto.validarTexto(CANCELAR_BUTTON)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Botones Confirmar y Cancelar validados");
        ReportHooks.registrarPaso("Botones Confirmar y Cancelar validados correctamente");
    }

    public static Performable transaccionTarjetaCreditoPostpago() {
        return instrumented(TransaccionTarjetaCreditoPostpago.class);
    }
}