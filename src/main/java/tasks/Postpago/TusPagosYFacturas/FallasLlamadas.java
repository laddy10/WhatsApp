package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
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

public class FallasLlamadas implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(LLAMADAS_FALLAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Llamadas");
        ReportHooks.registrarPaso("Seleccionar Llamadas");

        // Enviar selección
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(REALIZAR_DIAGNOSTICO),
                ClickTextoQueContengaX.elTextoContiene(REALIZAR_DIAGNOSTICO),
                WaitForTextContains.withTextContains(REVISION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(NECESARIO_CONFIGURACION),
                ValidarTextoQueContengaX.elTextoContiene(CONTINUAR_PASO_VIDEO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar seguir paso a paso video");
        ReportHooks.registrarPaso("Validar seguir paso a paso video");

        actor.attemptsTo(
                Click.on(BTN_REPRODUCIR_VIDEO),
                WaitFor.aTime(6000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Ver video");
        ReportHooks.registrarPaso("Ver video");


        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    public static Performable fallasLlamadas() {
        return instrumented(FallasLlamadas.class);
    }
}