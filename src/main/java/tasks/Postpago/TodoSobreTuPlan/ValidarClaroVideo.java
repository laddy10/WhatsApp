package tasks.Postpago.TodoSobreTuPlan;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.ValidarElemento;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.LOGO_CLARO_VIDEO;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class ValidarClaroVideo implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje con URL de claro video");
        ReportHooks.registrarPaso("Validar mensaje con URL de claro video");


        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO));


        UtilidadesAndroid.abrirLinkEnNavegador(URL_CLARO_VIDEO);

        actor.attemptsTo(
                WaitForResponse.withAnyText(EXPLORAR),
                WaitFor.aTime(6000),
                ValidarTextoQueContengaX.elTextoContiene(PREMIUM),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO_RECOMIENDA)
        );

        theActorInTheSpotlight().should(seeThat(
                ValidarElemento.esVisible(LOGO_CLARO_VIDEO))
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Se valida el ingreso a Claro Video");
        ReportHooks.registrarPaso("Se valida el ingreso a Claro Video");


        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable validarClaroVideo() {
        return instrumented(ValidarClaroVideo.class);
    }
}