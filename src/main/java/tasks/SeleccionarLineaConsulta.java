package tasks;

import interactions.comunes.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;


public class SeleccionarLineaConsulta implements Task {

    User addCredentials;

    public SeleccionarLineaConsulta(User addCredentials) {
        this.addCredentials = addCredentials;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SeleccionarNumero.porUltimos4(addCredentials.getNumero()),
                WaitForResponse.withAnyText(
                        POLITICA_TRATAMIENTO,MENU_PRINCIPAL, VER_MENU_PREPAGO));
    }

    public static Performable seleccionarLineaConsulta(User addCredentials) {
        return instrumented(SeleccionarLineaConsulta.class, addCredentials);
    }
}