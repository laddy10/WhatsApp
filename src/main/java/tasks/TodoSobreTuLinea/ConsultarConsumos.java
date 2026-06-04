package tasks.TodoSobreTuLinea;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ConsultarConsumos implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CONSULTA_TUS_CONSUMOS));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opcion 'Consulta tus consumos'");
        ReportHooks.registrarPaso("Seleccionar opcion 'Consulta tus consumos'");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withAnyText(SIN_SALDO_DISPONIBLE, RECARGA_ACTIVA, PAQUETE_VENCE, "Datos consumidos"));

        List<WebElementFacade> lblsinsaldo = LBL_SIN_SALDO.resolveAllFor(actor);

        if (!lblsinsaldo.isEmpty()) {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(SIN_SALDO_DISPONIBLE),
                    ValidarTexto.validarTexto(COMPRAR_PAQUETE),
                    ValidarTexto.validarTexto(COMPRAR_RECARGA),
                    ValidarTexto.validarTexto(MENU_ANTERIOR));
        } else {
            AndroidObject and = new AndroidObject();
            if (and.textoContiene(actor, RECARGA_ACTIVA)) {
                actor.attemptsTo(
                        ValidarTextoQueContengaX.elTextoContiene(RECARGA_ACTIVA),
                        ValidarTextoQueContengaX.elTextoContiene(SALDO_VENCE));
            }
            if (and.textoContiene(actor, PAQUETE_VENCE) || and.textoContiene(actor, "Datos consumidos")) {
                if (and.textoContiene(actor, CONSUMO)) {
                    actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(CONSUMO));
                }
                actor.attemptsTo(
                        ValidarTextoQueContengaX.elTextoContiene(CONSUMOS),
                        ValidarTextoQueContengaX.elTextoContiene(FECHA_ACTIVACION),
                        ValidarTextoQueContengaX.elTextoContiene(PAQUETE_VENCE));
            }
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Se validan los consumos de la linea");
        ReportHooks.registrarPaso("Se validan los consumos de la linea");

        actor.attemptsTo(
                SalirConversacion.salir());
    }

    public static Performable ConsultarConsumos() {
        return instrumented(ConsultarConsumos.class);
    }
}
