package tasks.Postpago.TodoSobreTuPlan;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.LOGO_CLARO_VIDEO;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.ValidarElemento;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class ValidarClaroVideo implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje con URL de claro video");
        ReportHooks.registrarPaso("Validar mensaje con URL de claro video");

        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO));

        UtilidadesAndroid.abrirLinkEnNavegador(URL_CLARO_VIDEO);

        actor.attemptsTo(
                WaitFor.aTime(20000),
                WaitForResponse.withAnyText(EXPLORAR),
                ValidarTextoQueContengaX.elTextoContiene(PREMIUM),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO_RECOMIENDA));


        CapturaDePantallaMovil.tomarCapturaPantalla("Se valida el ingreso a Claro Video");
        ReportHooks.registrarPaso("Se valida el ingreso a Claro Video");

        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    public static Performable validarClaroVideo() {
        return instrumented(ValidarClaroVideo.class);
    }
}
