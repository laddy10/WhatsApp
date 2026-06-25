package tasks.Hogar;

import interactions.wait.WaitForResponse;
import java.util.List;
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
import static utils.Constantes.*;


public class SeleccionarLineaHogar implements Task {

    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue("1").into(TXT_CAJA_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withAnyTextFailingOn(
                        10,
                        List.of(
                                OPCIONES_MOSTRADAS_ANTERIORMENTE,
                                TU_RESPUESTA_NO_ES_VALIDA,
                                INGRESAR_OPCION_VALIDA,
                                NO_ENTENDI_TU_MENSAJE),
                        POLITICA_TRATAMIENTO,
                        MENU_PRINCIPAL));
    }

    public static Performable seleccionarLineaHogar() {
        return instrumented(SeleccionarLineaHogar.class);
    }
}
