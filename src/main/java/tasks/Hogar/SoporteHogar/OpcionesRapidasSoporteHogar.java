package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesHogar.FALLAS_CUENTA_HOGAR;
import static utils.ConstantesHogar.REVISAR_SERVICIO;
import static utils.ConstantesPost.*;

public class OpcionesRapidasSoporteHogar implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTexto.validarTexto(FALLAS_CUENTA_HOGAR),
                ClickTextoQueContengaX.elTextoContiene(REVISAR_SERVICIO));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar" + REVISAR_SERVICIO);
        ReportHooks.registrarPaso("Seleccionar" + REVISAR_SERVICIO);

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ESPERA_3_MINUTOS),
                ValidarTextoQueContengaX.elTextoContiene(ESPERA_3_MINUTOS),
                ValidarTextoQueContengaX.elTextoContiene(REVISION_GENERAL),
                ValidarTextoQueContengaX.elTextoContiene(NO_RESPONDAS_MENSAJE));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mesaje de revicion de 3 minutos");
        ReportHooks.registrarPaso("Validar mesaje de revicion de 3 minutos");

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(GRACIAS_POR_LA_ESPERA),
                ValidarTextoQueContengaX.elTextoContiene(SERVICIOS_DE_AYUDA),
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),
                ValidarTextoQueContengaX.elTextoContiene(FALLAS_EN_TU_NAVEGACION)
        );

    }

    public static Performable opcionesRapidasSoporteHogar() {
        return instrumented(OpcionesRapidasSoporteHogar.class);
    }
}
