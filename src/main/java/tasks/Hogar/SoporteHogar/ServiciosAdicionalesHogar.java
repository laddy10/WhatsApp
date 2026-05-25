package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

public class ServiciosAdicionalesHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONOCE_MEJORA_TU_PLAN)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Conoce/mejora tu plan'");
        ReportHooks.registrarPaso("Seleccionar 'Conoce/mejora tu plan'");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(TU_PLAN_ACTUAL)
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(TU_PLAN_ACTUAL),
                ValidarTextoQueContengaX.elTextoContiene(TELEVISION),
                ValidarTextoQueContengaX.elTextoContiene(TELEFONIA),
                ValidarTextoQueContengaX.elTextoContiene(PLANES_PREMIUM_HOGAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar información del plan hogar");
        ReportHooks.registrarPaso("Validar información del plan hogar");

        actor.attemptsTo(
                ScrollGradual.bajar(0.20),
                ClickTextoQueContengaX.elTextoContiene(EXPLORAR_OPCIONES),
                WaitForTextContains.withAnyTextContains(CONOCE_OTROS_PLANES),
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Abrir opciones de planes y servicios premium");
        ReportHooks.registrarPaso("Abrir opciones de planes y servicios premium");

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(SERVICIOS_ADICIONALES_HOGAR),
                ClickTextoQueContengaX.elTextoContiene(SERVICIOS_ADICIONALES_HOGAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Servicios adicionales");
        ReportHooks.registrarPaso("Seleccionar Servicios adicionales");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(
                        TEXTO_NECESITO_CONFIRMAR_IDENTIDAD,
                        TEXTO_CODIGO_SEGURIDAD)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar solicitud de código de seguridad");
        ReportHooks.registrarPaso("Validar solicitud de código de seguridad");
    }

    public static Performable serviciosAdicionalesHogar() {
        return instrumented(ServiciosAdicionalesHogar.class);
    }
}
