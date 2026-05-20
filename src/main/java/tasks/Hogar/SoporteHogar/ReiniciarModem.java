package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
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
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(REINICIAR_TU_MODEM),
                ValidarTextoQueContengaX.elTextoContiene(COMENZAR_EL_REINICIO),
                ValidarTextoQueContengaX.elTextoContiene(SI_REINICIAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar opcion reiniciar modem");
        ReportHooks.registrarPaso("Validar opcion reiniciar modem");

        actor.attemptsTo(
                SalirConversacion.salir());

    }

    public static Performable reiniciarModem() {
        return instrumented(ReiniciarModem.class);
    }
}
