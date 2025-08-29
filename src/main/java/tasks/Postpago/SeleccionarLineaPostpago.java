package tasks.Postpago;

import interactions.comunes.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SeleccionarNumero;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;
import static utils.ConstantesPost.SI_AUTORIZO;
import static utils.ConstantesPost.VER_Y_PAGAR_FACTURA;


public class SeleccionarLineaPostpago implements Task {

    User addCredentials;

    public SeleccionarLineaPostpago(User addCredentials) {
        this.addCredentials = addCredentials;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SeleccionarNumero.porUltimos4(addCredentials.getNumeroPost()),
                WaitForResponse.withAnyText(VER_Y_PAGAR_FACTURA,SI_AUTORIZO));
    }



    public static Performable seleccionarLineaPostpago(User addCredentials) {
        return instrumented(SeleccionarLineaPostpago.class, addCredentials);
    }
}