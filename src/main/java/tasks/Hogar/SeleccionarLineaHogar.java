package tasks.Hogar;

import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_CAJA_MENSAJE;
import static utils.Constantes.MENU_PRINCIPAL;
import static utils.Constantes.POLITICA_TRATAMIENTO;


public class SeleccionarLineaHogar implements Task {

    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue("1").into(TXT_CAJA_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withAnyText(POLITICA_TRATAMIENTO, MENU_PRINCIPAL));
    }

    public static Performable seleccionarLineaHogar() {
        return instrumented(SeleccionarLineaHogar.class);
    }
}
