package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
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
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class TResuelveOpcionUnoHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CONOCE_MEJORA_TU_PLAN));

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
                WaitForTextContains.withAnyTextContains(T_RESUELVE),
                ClickTextoQueContengaX.elTextoContiene(T_RESUELVE)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar T-Resuelve");
        ReportHooks.registrarPaso("Seleccionar T-Resuelve");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(PLAN_DE_ASISTENCIA)
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_SOPORTE_TECNOLOGICO),
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_MULTIASISTENCIAS),
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_MASCOTAS),
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_REUNIONES_EN_CASA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar opciones de T-Resuelve");
        ReportHooks.registrarPaso("Validar opciones de T-Resuelve");

        actor.attemptsTo(
                Enter.theValue("1").into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForTextContains.withAnyTextContains(ADQUIRIENDO_PLAN_T_RESUELVE)
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(PLAN_BRONCE),
                ValidarTextoQueContengaX.elTextoContiene(PLAN_PLATA),
                ValidarTextoQueContengaX.elTextoContiene(PLAN_ORO),
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_PLAN_INTERES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar planes de T-Resuelve soporte tecnológico");
        ReportHooks.registrarPaso("Validar planes de T-Resuelve soporte tecnológico");

        actor.attemptsTo(SalirConversacion.salir());
    }

    public static Performable tResuelveOpcionUnoHogar() {
        return instrumented(TResuelveOpcionUnoHogar.class);
    }
}
