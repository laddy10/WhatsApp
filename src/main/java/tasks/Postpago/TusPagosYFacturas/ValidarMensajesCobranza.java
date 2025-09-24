package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class ValidarMensajesCobranza implements Task {

    private static final String MENSAJE_CAPTURA_2 = "Validar información de la casa de cobranza";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Validar mensaje de la casa de cobranza (sin datos específicos cambiantes)
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(RECUERDA_SUSPENSION),
                ValidarTextoQueContengaX.elTextoContiene(CASA_COBRO_ASIGNADA),
                ValidarTextoQueContengaX.elTextoContiene(PROCESO_NOTIFICACION)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        // Validar opciones de navegación
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(FINALIZAR_CHAT),
                ClickTextoQueContengaX.elTextoContiene(FINALIZAR_CHAT),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable validarMensajesCobranza() {
        return instrumented(ValidarMensajesCobranza.class);
    }
}