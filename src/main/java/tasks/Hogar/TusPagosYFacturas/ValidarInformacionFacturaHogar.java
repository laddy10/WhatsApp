package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.ConstantesPost.AL_DIA_EN_PAGOS;
import static utils.ConstantesPost.VALOR_A_PAGAR;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ValidarInformacionFacturaHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(VALOR_A_PAGAR, AL_DIA_EN_PAGOS)
        );

        AndroidObject and = new AndroidObject();

        if (and.textoContiene(actor, AL_DIA_EN_PAGOS)) {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(AL_DIA_EN_PAGOS)
            );
            actor.remember("alDia", true);
            CapturaDePantallaMovil.tomarCapturaPantalla("Validar información de la cuenta al día en pagos");
            ReportHooks.registrarPaso("Validar información de la cuenta al día en pagos");
        } else {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR)
            );
            actor.remember("alDia", false);
            CapturaDePantallaMovil.tomarCapturaPantalla("Validar información de la factura con valor a pagar");
            ReportHooks.registrarPaso("Validar información de la factura con valor a pagar");
        }
    }

    public static Performable validarInformacionFacturaHogar() {
        return instrumented(ValidarInformacionFacturaHogar.class);
    }
}
