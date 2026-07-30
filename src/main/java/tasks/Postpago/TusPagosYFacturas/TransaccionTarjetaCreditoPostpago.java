package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.Scroll;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

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
                Click.on(BTN_CONTINUAR_PAGO));

        // VALIDAR FORMULARIO INICIAL
        actor.attemptsTo(
                WaitForResponse.withText(TARJETAS_NACIONALES),
                ValidarTextoQueContengaX.elTextoContiene(TARJETAS_NACIONALES),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_TARJETA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Formulario de tarjeta de crédito cargado");
        ReportHooks.registrarPaso("Formulario de tarjeta de crédito cargado");


        // LLENAR DATOS DE LA TARJETA
        actor.attemptsTo(
                Enter.theValue("5306 9156 7890 1234").into(TXT_NUMERO_TARJETA),
                Enter.theValue("Pepito Perez").into(TXT_NOMBRE_TARJETA),
                WaitFor.aTime(1000)
        );

        // VALIDAR TIPOS DE DOCUMENTO (los 4 disponibles)
        validarTiposDocumento(actor);

        // COMPLETAR RESTO DEL FORMULARIO
        actor.attemptsTo(
                Enter.theValue("12345674").into(TXT_NUMERO_DOCUMENTO),
                Enter.theValue("12/30").into(TXT_FECHA_EXPIRACION),
                WaitFor.aTime(3000),
                Enter.theValue("123").into(TXT_CVV),
                Enter.theValue("pepitoperez@gmail.com").into(TXT_EMAIL));

        CapturaDePantallaMovil.tomarCapturaPantalla("Datos básicos de tarjeta ingresados");
        ReportHooks.registrarPaso("Datos básicos de tarjeta ingresados");


        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                Enter.theValue("3109871234").into(TXT_TELEFONO),
                WaitFor.aTime(1000)
        );

        // AJUSTAR NÚMERO DE CUOTAS
        actor.attemptsTo(
                Click.on(BTN_MAS_CUOTAS),
                Click.on(BTN_MAS_CUOTAS),
                WaitFor.aTime(500)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar numero de cuotas +");
        ReportHooks.registrarPaso("Validar numero de cuotas +");

        actor.attemptsTo(
                Click.on(BTN_MENOS_CUOTAS),
                WaitFor.aTime(500)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar numero de cuotas -");
        ReportHooks.registrarPaso("Validar numero de cuotas -");

        // VALIDAR QUE EL BOTÓN PAGAR ESTÉ HABILITADO
        actor.attemptsTo(
                ValidarTexto.validarTexto(GUARDAR_DATOS_TARJETA),
                ValidarTexto.validarTexto(PAGAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Formulario completado");
        ReportHooks.registrarPaso("Formulario completado");

    }

    private <T extends Actor> void validarTiposDocumento(T actor) {
        String[] tiposDocumento = {
                CEDULA_CIUDADANIA_3,
                CEDULA_EXTRANJERIA_2,
                PASAPORTE_2,
                CEDULA_CIUDADANIA_3,
        };

        for (String tipoDoc : tiposDocumento) {
            actor.attemptsTo(
                    Click.on(DROPDOWN_TIPO_DOCUMENTO),
                    WaitFor.aTime(1000),
                    ValidarTexto.validarTexto(CEDULA_CIUDADANIA),
                    ValidarTexto.validarTexto(CEDULA_EXTRANJERIA),
                    ValidarTexto.validarTexto(PASAPORTE),
                    ValidarTexto.validarTexto(CEDULA_CIUDADANIA),
                    ClickTextoQueContengaX.elTextoContiene(tipoDoc),
                    WaitFor.aTime(1000)
            );

            CapturaDePantallaMovil.tomarCapturaPantalla("Tipo documento seleccionado: " + tipoDoc);
            ReportHooks.registrarPaso("Tipo documento seleccionado: " + tipoDoc);

        }

    }

    public static Performable transaccionTarjetaCreditoPostpago() {
        return instrumented(TransaccionTarjetaCreditoPostpago.class);
    }
}
