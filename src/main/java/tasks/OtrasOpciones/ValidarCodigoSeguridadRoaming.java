package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.TEXTO_CODIGO_SEGURIDAD;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class ValidarCodigoSeguridadRoaming implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(TEXTO_CODIGO_SEGURIDAD)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje código de seguridad");
        ReportHooks.registrarPaso("Validar mensaje código de seguridad");

        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }

    public static Performable validarCodigoSeguridadRoaming() {
        return instrumented(ValidarCodigoSeguridadRoaming.class);
    }
}
