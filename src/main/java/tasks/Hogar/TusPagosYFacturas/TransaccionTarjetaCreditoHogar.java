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
            System.out.println(
                    "La cuenta está al día, omitiendo transacción con tarjeta de crédito."
            );
            return;
        }

        // Seleccionar Tarjeta de Crédito
        actor.attemptsTo(
                Click.on(CHECK_SELECCIONAR_MEDIO_PAGO),
                WaitForTextContains.withAnyTextContains(TARJETA_CREDITO),
                Click.on(BTN_TARJETA_CREDITO),
                WaitForResponse.withText(CONTINUAR_BUTTON)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar Tarjeta de Crédito y continuar Hogar"
        );

        ReportHooks.registrarPaso(
                "Seleccionar Tarjeta de Crédito y continuar Hogar"
        );

        // Continuar al formulario de tarjeta
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(SERVICIOS_PORTAL_PAGOS),
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR_BUTTON)
        );

        // Esperar cualquiera de las dos vistas posibles
        // Esperar unos segundos por la vista actual
        // Esperar a que cargue el formulario
        actor.attemptsTo(
                WaitFor.aTime(80000)
        );

// Vista actual: tiene un único campo MM/AA
        boolean vistaActual =
                TXT_FECHA_EXPIRACION.resolveFor(actor).isVisible();

        if (vistaActual) {

            ReportHooks.registrarPaso(
                    "Se detectó formulario actual de tarjeta de crédito"
            );

            ejecutarFormularioActual(actor);

        } else {

            // Vista nueva: tiene mes y año separados
            boolean vistaNueva =
                    SELECT_MES_EXPIRACION.resolveFor(actor).isVisible()
                            && SELECT_ANIO_EXPIRACION.resolveFor(actor).isVisible();

            if (vistaNueva) {

                ReportHooks.registrarPaso(
                        "Se detectó nuevo formulario de tarjeta de crédito"
                );

                ejecutarFormularioNuevo(actor);

            } else {

                throw new RuntimeException(
                        "No fue posible identificar ninguna de las dos vistas del formulario de tarjeta de crédito."
                );
            }
        }

    }

    /**
     * Vista que ya existía:
     * "Agregar tarjeta"
     */
    private <T extends Actor> void ejecutarFormularioActual(T actor) {

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene("Agregar tarjeta")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Formulario actual de tarjeta de crédito cargado Hogar"
        );

        ReportHooks.registrarPaso(
                "Formulario actual de tarjeta de crédito cargado Hogar"
        );

        // Número de tarjeta
        actor.attemptsTo(
                WaitFor.aTime(2000),
                Click.on(TXT_NUMERO_TARJETA)
        );

        digitarSoloNumeros(
                actor,
                NUMERO_TARJETA_FICTICIO
        );

        // Nombre, tipo y número de documento
        actor.attemptsTo(
                WaitFor.aTime(1000),

                Enter.theValue(NOMBRE_FICTICIO)
                        .into(TXT_NOMBRE_APELLIDO),

                WaitFor.aTime(1000),

                Click.on(SELECT_TIPO_DOCUMENTO),

                WaitFor.aTime(1000),

                ClickTextoQueContengaX.elTextoContiene(
                        "C.C. (Cédula de Ciudadanía)"
                ),

                WaitFor.aTime(1000),

                Click.on(TXT_NUMERO_DOCUMENTO)
        );

        digitarSoloNumeros(
                actor,
                NUMERO_CEDULA_FICTICIO
        );

        // Fecha expiración
        actor.attemptsTo(
                WaitFor.aTime(1000),
                Click.on(TXT_FECHA_EXPIRACION)
        );

        digitarSoloNumeros(
                actor,
                "12/28"
        );

        // CVC
        actor.attemptsTo(
                WaitFor.aTime(1000),
                Click.on(TXT_CVC)
        );

        digitarSoloNumeros(
                actor,
                CVC_FICTICIO
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Datos básicos de tarjeta ingresados Hogar"
        );

        ReportHooks.registrarPaso(
                "Datos básicos de tarjeta ingresados Hogar"
        );

        // Datos adicionales
        actor.attemptsTo(
                WaitFor.aTime(2000),

                ScrollGradual.bajar(0.25),

                Enter.theValue(CORREO_FICTICIO)
                        .into(TXT_CORREO_ELECTRONICO),

                WaitFor.aTime(1000),

                Enter.theValue(CELULAR_FICTICIO)
                        .into(TXT_NUMERO_CELULAR),

                WaitFor.aTime(1000)
        );

        // Cuotas: aumentar y regresar a 1
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

        // Guardar tarjeta
        actor.attemptsTo(
                Click.on(CHK_GUARDAR_TARJETA_HOGAR),
                WaitFor.aTime(1000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Datos adicionales completados y cuotas validadas"
        );

        ReportHooks.registrarPaso(
                "Datos adicionales completados y cuotas validadas"
        );

        // Botón final vista actual
        actor.attemptsTo(
                ValidarTexto.validarTexto("Pagar")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Botón Pagar validado Hogar"
        );

        ReportHooks.registrarPaso(
                "Botón Pagar validado Hogar"
        );
    }

    /**
     * Nueva vista:
     * inicia directamente con "Número de la tarjeta"
     */
    private <T extends Actor> void ejecutarFormularioNuevo(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Nuevo formulario de tarjeta de crédito cargado Hogar"
        );

        ReportHooks.registrarPaso(
                "Nuevo formulario de tarjeta de crédito cargado Hogar"
        );

        // Número de tarjeta
        actor.attemptsTo(
                Click.on(TXT_NUMERO_TARJETA)
        );

        digitarSoloNumeros(
                actor,
                NUMERO_TARJETA_FICTICIO
        );

        // Nombre y apellido
        actor.attemptsTo(
                WaitFor.aTime(1000),
                Enter.theValue(NOMBRE_FICTICIO)
                        .into(TXT_NOMBRE_APELLIDO),

                WaitFor.aTime(1000),
                ScrollGradual.bajar(0.30),

                // Tipo documento
                Click.on(SELECT_TIPO_DOCUMENTO),

                WaitFor.aTime(1000),

                ClickTextoQueContengaX.elTextoContiene(
                        "C.C. (Cédula de Ciudadanía)"
                ),

                WaitFor.aTime(1000),


                // Número documento
                Click.on(TXT_NUMERO_DOCUMENTO)
        );

        digitarSoloNumeros(
                actor,
                NUMERO_CEDULA_FICTICIO
        );

        // Mes de expiración
        actor.attemptsTo(
                WaitFor.aTime(1000),
                Click.on(SELECT_MES_EXPIRACION),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene("10")
        );

        // Año de expiración
        actor.attemptsTo(
                WaitFor.aTime(1000),
                Click.on(SELECT_ANIO_EXPIRACION),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene("2028")
        );

        // CVC / CVV
        actor.attemptsTo(
                WaitFor.aTime(1000),
                Click.on(TXT_CVC)
        );

        digitarSoloNumeros(
                actor,
                CVC_FICTICIO
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Datos básicos nuevo formulario tarjeta Hogar"
        );

        // Bajar hacia datos adicionales
        actor.attemptsTo(
                WaitFor.aTime(1000),
                ScrollGradual.bajar(0.30),

                Enter.theValue(CORREO_FICTICIO)
                        .into(TXT_CORREO_ELECTRONICO),

                WaitFor.aTime(1000),

                Enter.theValue(CELULAR_FICTICIO)
                        .into(TXT_NUMERO_CELULAR),

                WaitFor.aTime(1000)
        );

        // Número de cuotas
        actor.attemptsTo(
                // ScrollGradual.bajar(0.30),
                Click.on(SELECT_NUMERO_CUOTAS),

                WaitFor.aTime(1000),

                ClickTextoQueContengaX.elTextoContiene("4"),

                WaitFor.aTime(1000)
        );

        // Guardar esta tarjeta -> Sí
        actor.attemptsTo(
                Click.on(BTN_GUARDAR_TARJETA_SI),
                WaitFor.aTime(1000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Datos adicionales nuevo formulario completados Hogar"
        );

        ReportHooks.registrarPaso(
                "Datos adicionales nuevo formulario completados Hogar"
        );

        // En la nueva vista el botón final es Confirmar
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene("Confirmar")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Botón Confirmar validado Hogar"
        );

        ReportHooks.registrarPaso(
                "Botón Confirmar validado Hogar"
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
        return instrumented(
                TransaccionTarjetaCreditoHogar.class
        );
    }
}