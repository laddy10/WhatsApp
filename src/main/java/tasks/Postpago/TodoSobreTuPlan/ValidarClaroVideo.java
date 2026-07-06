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

        // Se amplía el timeout a 25 segundos para dar tiempo al link acortado a resolver
        // y a Chrome a cargar el dominio de clarovideo.com
        try {
            UtilidadesAndroid.esperarRedireccionamientoWeb(actor, "clarovideo.com", 25);
        } catch (RuntimeException e) {
            CapturaDePantallaMovil.tomarCapturaPantalla("Timeout esperando clarovideo.com en Chrome");
            ReportHooks.registrarPaso("⚠️ No se confirmó redirección a clarovideo.com: " + e.getMessage());
            throw e;
        }

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
