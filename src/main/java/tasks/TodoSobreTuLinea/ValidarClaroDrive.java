package tasks.TodoSobreTuLinea;

import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.scroll.Scroll;
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
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class ValidarClaroDrive implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLARO_DRIVE),
                ValidarTextoQueContengaX.elTextoContiene(URL_CLARO_DRIVE));

        UtilidadesAndroid.abrirLinkEnNavegador("https://bit.ly/3oNvleC");

        actor.attemptsTo(
                WaitFor.aTime(10000));

        actor.should(seeThat(
                ValidarElemento.esVisible(IMAG_CLARO_DRIVE)));

        CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");

        actor.attemptsTo(
                ValidarTexto.validarTexto(INICIA_SESION),
                Scroll.scrollUnaVista());

        CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");

        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitFor.aTime(2000),
                Atras.irAtras(),
                Atras.irAtras());

        //  WordAppium.main();
    }

    public static Performable validarClaroDrive() {
        return instrumented(ValidarClaroDrive.class);
    }
}