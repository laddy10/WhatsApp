package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.*;

public class FallasEnTuNavegacion implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        Boolean diagnosticoYaResuelto =
                actor.recall(
                        EstadoServiciosHogar.ESTADO_SERVICIOS_RESUELTO
                );

        if (Boolean.TRUE.equals(diagnosticoYaResuelto)) {

            ReportHooks.registrarPaso(
                    "No se selecciona Internet porque Estado de servicios "
                            + "ya detectó y solucionó la falla."
            );

            return;
        }

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        INTERNET
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar Fallas en tu navegación"
        );

        ReportHooks.registrarPaso(
                "Seleccionar Fallas en tu navegación"
        );

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(
                        REINICIAR_TU_MODEM
                )
        );
    }

    public static Performable fallasEnTuNavegacion() {
        return instrumented(FallasEnTuNavegacion.class);
    }
}