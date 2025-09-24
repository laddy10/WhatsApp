package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.ABANDONAR_CONVERSACION;
import static utils.Constantes.SALIR;
import static utils.ConstantesPost.*;

public class OtrasFacturasClaro implements Task {

    private static final String MENSAJE_CAPTURA_1 = "Seleccionar 'Otras facturas Claro'";
    private static final String MENSAJE_CAPTURA_2 = "Clic en 'Pagar otras facturas'";
    private static final String MENSAJE_CAPTURA_3 = "Validar direccionamiento portal de pagos";
    private static final String MENSAJE_CAPTURA_4 = "Desplegar opciones y seleccionar 'Pago de Facturas'";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Paso 1: Seleccionar "Otras facturas Claro"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(OTRAS_FACTURAS_CLARO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_1);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_1);

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withText(PAGAR_OTRAS_FACTURAS)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        // Paso 2: Dar clic en "Pagar otras facturas"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGAR_OTRAS_FACTURAS)
        );


        // Paso 3: Validar direccionamiento al portal de pagos
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(PORTAL_PAGOS_Y_RECARGAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_3);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_3);

        // Paso 4: Desplegar "Selecciona la opción de tu interés" y seleccionar "Pago de Facturas"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SELECCIONA_LA_OPCION),
                ClickTextoQueContengaX.elTextoContiene(PAGO_DE_FACTURAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_4);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_4);

        // Validar selección de Postpago
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_TIPO_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(POSTPAGO_OPCION)
        );

        actor.attemptsTo(
                Atras.irAtras(),
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable otrasFacturasClaro() {
        return instrumented(OtrasFacturasClaro.class);
    }
}