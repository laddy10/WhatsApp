package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.ConstantesPost.*;

public class ReiniciarModem implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        Boolean diagnosticoYaResuelto =
                actor.recall(
                        EstadoServiciosHogar.ESTADO_SERVICIOS_RESUELTO
                );

        if (Boolean.TRUE.equals(diagnosticoYaResuelto)) {

            ReportHooks.registrarPaso(
                    "No se valida reinicio de módem porque "
                            + "Estado de servicios ya solucionó la falla."
            );

            return;
        }

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        REINICIAR_TU_MODEM
                ),
                ValidarTextoQueContengaX.elTextoContiene(
                        COMENZAR_EL_REINICIO
                ),
                ValidarTextoQueContengaX.elTextoContiene(
                        SI_REINICIAR
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar opcion reiniciar modem"
        );

        ReportHooks.registrarPaso(
                "Validar opcion reiniciar modem"
        );

        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }

    public static Performable reiniciarModem() {
        return instrumented(ReiniciarModem.class);
    }
}