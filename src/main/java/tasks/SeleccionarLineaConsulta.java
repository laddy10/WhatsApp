package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;

import interactions.wait.WaitForResponse;
import java.util.List;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.TestDataProvider;

public class SeleccionarLineaConsulta implements Task {

  private final User user = TestDataProvider.getRealUser();

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        SeleccionarNumero.porUltimos4(user.getNumeroPre()),
        WaitForResponse.withAnyTextFailingOn(
            10,
            List.of(
                OPCIONES_MOSTRADAS_ANTERIORMENTE,
                TU_RESPUESTA_NO_ES_VALIDA,
                INGRESAR_OPCION_VALIDA,
                NO_ENTENDI_TU_MENSAJE),
            POLITICA_TRATAMIENTO,
            MENU_PRINCIPAL,
            VER_MENU_PREPAGO));
  }

  public static Performable seleccionarLineaConsulta() {
    return instrumented(SeleccionarLineaConsulta.class);
  }
}
