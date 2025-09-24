package tasks.Postpago;

import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SeleccionarNumero;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.ConstantesPost.SI_AUTORIZO;
import static utils.ConstantesPost.VER_Y_PAGAR_FACTURA;


public class SeleccionarLineaPostpago implements Task {

    private final User user = TestDataProvider.getRealUser();


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SeleccionarNumero.porUltimos4(user.getNumeroPost()),
                WaitForResponse.withAnyText(VER_Y_PAGAR_FACTURA, SI_AUTORIZO));
    }


    public static Performable seleccionarLineaPostpago() {
        return instrumented(SeleccionarLineaPostpago.class);
    }
}