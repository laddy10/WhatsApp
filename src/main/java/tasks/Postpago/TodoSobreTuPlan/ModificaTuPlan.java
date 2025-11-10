package tasks.Postpago.TodoSobreTuPlan;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class ModificaTuPlan implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(MODIFICA_TU_PLAN));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opcion Modifica tu plan");
        ReportHooks.registrarPaso("Seleccionar opcion Modifica tu plan");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForTextContains.withAnyTextContains(VER_OTROS_PLANES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar botón Selecciona");
        ReportHooks.registrarPaso("Validar botón Selecciona");


        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),
                ValidarTextoQueContengaX.elTextoContiene(CAMBIAR_PLAN),
                ValidarTextoQueContengaX.elTextoContiene(PASAR_LINEA_A_PREPAGO),
                ValidarTextoQueContengaX.elTextoContiene(DESACTIVAR_LINEA),
                ValidarTextoQueContengaX.elTextoContiene(SERVICIOS_ADICIONALES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar menu Modifica tu plan");
        ReportHooks.registrarPaso("Validar menu Modifica tu plan");

        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    public static Performable modificaTuPlan() {
        return instrumented(ModificaTuPlan.class);
    }
}