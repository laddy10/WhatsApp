package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static userinterfaces.WhatsAppPostpagoPage.LBL_EQUIPO_REGISTRADO;
import static utils.Constantes.ABANDONAR_CONVERSACION;
import static utils.Constantes.SALIR;
import static utils.ConstantesPost.*;

public class RealizarProcesoRegistro implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        List<WebElementFacade> equiporegistrado = LBL_EQUIPO_REGISTRADO.resolveAllFor(actor);
        if (!equiporegistrado.isEmpty()) {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(DISPOSITIVO_REGISTRADO)
            );

            CapturaDePantallaMovil.tomarCapturaPantalla("El equipo ya se encuentra registrado");
            ReportHooks.registrarPaso("El equipo ya se encuentra registrado");

        } else {
            actor.attemptsTo(
                    IngresarCodigoVerificacion.ingresarCodigoVerificacion(),
                    ConfirmarDatosRegistraEquipo.confirmarDatosRegistraEquipo()
            );
        }

        actor.attemptsTo(
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable realizarProcesoRegistro() {
        return instrumented(RealizarProcesoRegistro.class);
    }
}