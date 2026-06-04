package tasks.TodoSobreTuLinea;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import questions.ValidarElemento;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.BTN_ACCEDER_CLARO_MUSICA;
import static utils.Constantes.*;

public class IngresarClaroMusica implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_MUSICA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Música");
        ReportHooks.registrarPaso("Seleccionar Claro Música");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withText(URL_CLARO_MUSICA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje con URL de claro musica");
        ReportHooks.registrarPaso("Validar mensaje con URL de claro musica");

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLARO_MUSICA),
                SalirConversacion.salir()
        );
    }

    public static Performable ingresarClaroMusica() {
        return instrumented(IngresarClaroMusica.class);
    }
}
