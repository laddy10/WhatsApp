package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.VISITAS_Y_TRASLADOS;
import static utils.ConstantesPost.NO_TIENES_AGENDADAS_VISITAS;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

public class VisitasYTrasladosHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Dar clic en el botón interactivo "Selecciona" (que viene después de Soporte y servicio)
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en botón Selecciona de Soporte y servicio");
        ReportHooks.registrarPaso("Clic en botón Selecciona de Soporte y servicio");

        // 2. Seleccionar "Visitas y traslados" en el menú desplegable
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VISITAS_Y_TRASLADOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Visitas y traslados'");
        ReportHooks.registrarPaso("Seleccionar 'Visitas y traslados'");

        // 3. Hacer clic en el botón "Enviar"
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2)
        );

        // 4. Validar el mensaje de no tener visitas técnicas agendadas
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(NO_TIENES_AGENDADAS_VISITAS),
                ValidarTextoQueContengaX.elTextoContiene(NO_TIENES_AGENDADAS_VISITAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje sin visitas técnicas agendadas");
        ReportHooks.registrarPaso("Validar mensaje sin visitas técnicas agendadas");
    }

    public static Performable visitasYTrasladosHogar() {
        return instrumented(VisitasYTrasladosHogar.class);
    }
}
