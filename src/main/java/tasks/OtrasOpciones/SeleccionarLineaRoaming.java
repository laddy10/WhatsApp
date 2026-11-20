package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;
import static utils.ConstantesPost.SI_AUTORIZO;
import static utils.ConstantesPost.VER_Y_PAGAR_FACTURA;

import interactions.wait.WaitForResponse;
import java.util.List;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SeleccionarNumero;
import utils.ContextoST;
import utils.TestDataProvider;

public class SeleccionarLineaRoaming implements Task {

  private final User user = TestDataProvider.getRealUser();

  @Override
  public <T extends Actor> void performAs(T actor) {
    // Contrato st-context: este flujo consulta la linea ROAMING (numeroRoaming).
    ContextoST.registrarSegmento("pospago");
    actor.attemptsTo(
        SeleccionarNumero.porUltimos4(user.getNumeroRoaming()),
        WaitForResponse.withAnyTextFailingOn(
            57,
            List.of(
                OPCIONES_MOSTRADAS_ANTERIORMENTE,
                TU_RESPUESTA_NO_ES_VALIDA,
                INGRESAR_OPCION_VALIDA,
                NO_ENTENDI_TU_MENSAJE),
            POLITICA_TRATAMIENTO,
            AUTORIZACION_TRATAMIENTO,
            MENU_PRINCIPAL,
            VER_Y_PAGAR_FACTURA,
            SI_AUTORIZO));
  }

  public static Performable seleccionarLineaRoaming() {
    return instrumented(SeleccionarLineaRoaming.class);
  }
}
