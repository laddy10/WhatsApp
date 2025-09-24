package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.SELECTOR_MEDIO_PAGO;
import static utils.ConstantesPost.*;

public class ValidarMediosPagoPostpago implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Hacer clic en el selector de medio de pago para expandir opciones
        actor.attemptsTo(
                Click.on(SELECTOR_MEDIO_PAGO),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Expandir opciones de medios de pago");
        ReportHooks.registrarPaso("Expandir opciones de medios de pago");

        // Validar medios de pago disponibles
        actor.attemptsTo(
                ValidarTexto.validarTexto(TARJETA_CREDITO),
                ValidarTextoQueContengaX.elTextoContiene(DEBITO_BANCARIO_PSE),
                ValidarTexto.validarTexto(BOTON_BANCOLOMBIA),
                ValidarTexto.validarTexto(TARJETA_CODENSA),
                ValidarTexto.validarTexto(MIS_TARJETAS_REGISTRADAS)
        );

    }

    public static Performable validarMediosPagoPostpago() {
        return instrumented(ValidarMediosPagoPostpago.class);
    }
}