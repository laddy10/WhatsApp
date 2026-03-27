package tasks.Postpago.TodoSobreTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.Scroll;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class ValidarClaroDrivePost implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje con URL de claro Drive");
        ReportHooks.registrarPaso("Validar mensaje con URL de claro Drive");

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLARO_DRIVE),
                ValidarTextoQueContengaX.elTextoContiene(URL_CLARO_DRIVE));

        UtilidadesAndroid.abrirLinkEnNavegador("https://bit.ly/3oNvleC");

        actor.attemptsTo(WaitFor.aTime(10000));

        CapturaDePantallaMovil.tomarCapturaPantalla("Direccionamiento claro Drive");
        ReportHooks.registrarPaso("Direccionamiento claro Drive");

        actor.attemptsTo(
                ValidarTexto.validarTexto(MI_CLARO_DRIVE),
                ValidarTexto.validarTexto(NEGOCIO),
                ValidarTextoQueContengaX.elTextoContiene(TUS_ARCHIVOS),
                Scroll.scrollUnaVista());

        CapturaDePantallaMovil.tomarCapturaPantalla("Direccionamiento pagina claro Drive");
        ReportHooks.registrarPaso("Direccionamiento pagina claro Drive");

        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    public static Performable validarClaroDrivePost() {
        return instrumented(ValidarClaroDrivePost.class);
    }
}
