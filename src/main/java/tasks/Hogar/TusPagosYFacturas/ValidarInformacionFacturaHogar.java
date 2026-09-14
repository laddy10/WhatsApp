package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.ConstantesPost.*;

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
                WaitForTextContains.withAnyTextContains(
                        VALOR_A_PAGAR,
                        AL_DIA_EN_PAGOS
                )
        );

        AndroidObject and = new AndroidObject();

        boolean mensajeAlDia =
                and.textoContiene(actor, AL_DIA_EN_PAGOS);

        boolean valorCero =
                and.textoContiene(actor, VALOR_A_PAGAR_CERO);

        if (mensajeAlDia || valorCero) {

            actor.remember("alDia", true);

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Validar información de la cuenta al día en pagos"
            );

            ReportHooks.registrarPaso(
                    "Cuenta al día: sin saldo pendiente de pago"
            );

        } else {

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR)
            );

            actor.remember("alDia", false);

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Validar información de la factura con valor a pagar"
            );

            ReportHooks.registrarPaso(
                    "Validar información de la factura con valor a pagar"
            );
        }
    }

    public static Performable validarInformacionFacturaHogar() {
        return instrumented(ValidarInformacionFacturaHogar.class);
    }
}
