package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.ABANDONAR_CONVERSACION;
import static utils.Constantes.SALIR;
import static utils.ConstantesPost.*;

public class ProgramaTusPagos implements Task {

    private static final String MENSAJE_CAPTURA_2 = "Validar información sobre programación de pagos";
    private static final String MENSAJE_CAPTURA_3 = "Clic en 'Programar pagos'";
    private static final String MENSAJE_CAPTURA_4 = "Validar direccionamiento a Mi Claro";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Validar mensaje informativo sobre programación
        actor.attemptsTo(
                WaitForTextContains.withTextContains(PROGRAMAR_PAGO_AUTOMATICO),
                ValidarTextoQueContengaX.elTextoContiene(PROGRAMAR_PAGO_AUTOMATICO),
                ValidarTextoQueContengaX.elTextoContiene(PROGRAMAR_PAGOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        // Clic en "Programar pagos"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PROGRAMAR_PAGOS)
        );

        // Esperar redirección y validar Mi Claro
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(INICIAR_SESION),
                ValidarTextoQueContengaX.elTextoContiene(HOLA_MICLARO),
                ValidarTextoQueContengaX.elTextoContiene(TE_PUEDE_INTERESAR),
                ValidarTextoQueContengaX.elTextoContiene(SERVICIOS_A_LA_MANO),
                ValidarTextoQueContengaX.elTextoContiene(INICIO),
                ValidarTextoQueContengaX.elTextoContiene(TIENDA),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_EMPRESAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_4);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_4);

        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable programaTusPagos() {
        return instrumented(ProgramaTusPagos.class);
    }
}