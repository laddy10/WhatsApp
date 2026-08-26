package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollGradual;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.TestDataProvider;

public class TransaccionTarjetaCreditoHogar extends AndroidObject implements Task {

    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
        Boolean alDia = actor.recall("alDia");
        if (alDia != null && alDia) {
            System.out.println("La cuenta está al día, omitiendo transacción con tarjeta de crédito.");
            return;
        }

        // Seleccionar Tarjeta de Crédito
        actor.attemptsTo(
                Click.on(CHECK_SELECCIONAR_MEDIO_PAGO),
                WaitForTextContains.withAnyTextContains(TARJETA_CREDITO),
                Click.on(BTN_TARJETA_CREDITO),
                WaitForResponse.withText(CONTINUAR_BUTTON)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Tarjeta de Crédito y continuar Hogar");
        ReportHooks.registrarPaso("Seleccionar Tarjeta de Crédito y continuar Hogar");

        actor.attemptsTo(
                ScrollHastaTexto.conTexto(SERVICIOS_PORTAL_PAGOS),
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR_BUTTON)
        );

        // Esperar a que cargue el formulario
        actor.attemptsTo(
                WaitFor.aTime(5000),
                ValidarTextoQueContengaX.elTextoContiene("Agregar tarjeta")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Formulario de tarjeta de crédito cargado Hogar");
        ReportHooks.registrarPaso("Formulario de tarjeta de crédito cargado Hogar");


        // Llenar datos ficticios de la tarjeta
        actor.attemptsTo(
                WaitFor.aTime(2000),
                Click.on(TXT_NUMERO_TARJETA)
        );

        // Digitar el número de tarjeta
        digitarSoloNumeros(actor, NUMERO_TARJETA_FICTICIO);


        // Llenar datos ficticios de la tarjeta
        actor.attemptsTo(
                WaitFor.aTime(1000),
                Enter.theValue(NOMBRE_FICTICIO).into(TXT_NOMBRE_APELLIDO),
                WaitFor.aTime(1000),
                Click.on(SELECT_TIPO_DOCUMENTO),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene("C.C. (Cédula de Ciudadanía)"),
                WaitFor.aTime(1000),
                Click.on(TXT_NUMERO_DOCUMENTO)
        );

        // Digitar la cédula
        digitarSoloNumeros(actor, NUMERO_CEDULA_FICTICIO);

        actor.attemptsTo(
                WaitFor.aTime(1000),

                // Dar foco al campo de fecha
                Click.on(TXT_FECHA_EXPIRACION)
        );

        // Digitar la fecha de expiración
        digitarSoloNumeros(actor, "12/28");

        actor.attemptsTo(
                WaitFor.aTime(1000),

                // Dar foco al campo CVC
                Click.on(TXT_CVC)
        );

        // Digitar el CVC
        digitarSoloNumeros(actor, CVC_FICTICIO);


        CapturaDePantallaMovil.tomarCapturaPantalla("Datos básicos de tarjeta ingresados Hogar");
        ReportHooks.registrarPaso("Datos básicos de tarjeta ingresados Hogar");

        // Continuar con datos adicionales
        actor.attemptsTo(
                WaitFor.aTime(2000),
                ScrollGradual.bajar(0.25),
                Enter.theValue(CORREO_FICTICIO).into(TXT_CORREO_ELECTRONICO),
                WaitFor.aTime(1000),
                Enter.theValue(CELULAR_FICTICIO).into(TXT_NUMERO_CELULAR),
                WaitFor.aTime(1000)
        );

        // Modificar número de cuotas (Aumentar a 2 o 3, luego volver a 1)
        actor.attemptsTo(
                Click.on(BTN_AUMENTAR_CUOTAS),
                WaitFor.aTime(1000),
                Click.on(BTN_AUMENTAR_CUOTAS),
                WaitFor.aTime(1000),
                Click.on(BTN_DISMINUIR_CUOTAS),
                WaitFor.aTime(1000),
                Click.on(BTN_DISMINUIR_CUOTAS),
                WaitFor.aTime(1000)
        );

        // Interactuar con checkbox guardar tarjeta
        actor.attemptsTo(
                Click.on(CHK_GUARDAR_TARJETA_HOGAR),
                WaitFor.aTime(1000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Datos adicionales completados y cuotas validadas");
        ReportHooks.registrarPaso("Datos adicionales completados y cuotas validadas");

        // Validar botón Pagar habilitado (simplemente validar su existencia)
        actor.attemptsTo(
                ValidarTexto.validarTexto("Pagar")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Botón Pagar validado Hogar");
        ReportHooks.registrarPaso("Botón Pagar validado Hogar");

        actor.attemptsTo(
                Atras.irAtras(),
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    private <T extends Actor> void digitarSoloNumeros(
            T actor,
            String valor
    ) {
        for (char caracter : valor.toCharArray()) {
            if (Character.isDigit(caracter)) {
                DigitarNumeros(
                        actor,
                        String.valueOf(caracter)
                );
            }
        }
    }

    public static Performable transaccionTarjetaCreditoHogar() {
        return instrumented(TransaccionTarjetaCreditoHogar.class);
    }
}
