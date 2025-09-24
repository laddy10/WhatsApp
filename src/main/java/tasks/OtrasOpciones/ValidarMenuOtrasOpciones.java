package tasks.OtrasOpciones;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTexto;
import interactions.comunes.Atras;
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

public class ValidarMenuOtrasOpciones implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTexto.validarTexto(CAMBIATE_A_POSTPAGO),
                ValidarTexto.validarTexto(TUS_SERVICIOS_LDI),
                ValidarTexto.validarTexto(ROAMING_INTERNACIONAL),
                ValidarTexto.validarTexto(RENUEVA_TU_SIM),
                ValidarTexto.validarTexto(TUS_PQR_RADICADOS));


        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion exitosa menu otras opciones");
        ReportHooks.registrarPaso("Validacion exitosa menu otras opciones");

        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    public static Performable validarMenuOtrasOpciones() {
        return instrumented(ValidarMenuOtrasOpciones.class);
    }
}
