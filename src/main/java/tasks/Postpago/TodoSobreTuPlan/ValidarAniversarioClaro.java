package tasks.Postpago.TodoSobreTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
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
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class ValidarAniversarioClaro implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje con URL de claro Club");
        ReportHooks.registrarPaso("Validar mensaje con URL de claro Club");

        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(URL_ANIVERSARIO_CLARO));

        UtilidadesAndroid.abrirLinkEnNavegador("https://" + URL_ANIVERSARIO_CLARO);

        actor.attemptsTo(WaitFor.aTime(90000));

    /*    List<WebElementFacade> btnclose = BTN_CLOSE.resolveAllFor(actor);
    if (!btnclose.isEmpty()) {
        actor.attemptsTo(
                Click.on(BTN_CLOSE)
        );
    }


    actor.attemptsTo(
            WaitForResponse.withAnyText(CATEGORIAS),
            ValidarTextoQueContengaX.elTextoContiene(MAS_DESCARGADOS),
            ValidarTextoQueContengaX.elTextoContiene(COMIDAS),
            ValidarTextoQueContengaX.elTextoContiene(PRODUCTOS_CLARO),
            //ValidarTextoQueContengaX.elTextoContiene(VIAJES),
            ValidarTextoQueContengaX.elTextoContiene(ENTRETENIMIENTO),
            ValidarTextoQueContengaX.elTextoContiene(INICIO),
            ValidarTextoQueContengaX.elTextoContiene(CONOCE_MAS),
            ValidarTextoQueContengaX.elTextoContiene(PERFIL)
    );


    CapturaDePantallaMovil.tomarCapturaPantalla("Se valida el ingreso a Claro Club");
    ReportHooks.registrarPaso("Se valida el ingreso a Claro Club");


    actor.attemptsTo(
            Scroll.scrollUnaVista());


    CapturaDePantallaMovil.tomarCapturaPantalla("Direccionamiento pagina claro Club");
    ReportHooks.registrarPaso("Direccionamiento pagina claro Club");


    List<WebElementFacade> lblvistaprevia = LBL_VISTA_PREVIA.resolveAllFor(actor);
    if (!lblvistaprevia.isEmpty()) {
        actor.attemptsTo(
                Atras.irAtras());
    } */

        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    public static Performable validarAniversarioClaro() {
        return instrumented(ValidarAniversarioClaro.class);
    }
}
