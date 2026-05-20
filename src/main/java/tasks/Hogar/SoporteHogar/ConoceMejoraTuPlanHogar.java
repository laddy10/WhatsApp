package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollGradual;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class ConoceMejoraTuPlanHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Seleccionar la opción de Conoce/mejora tu plan en la lista desplegable actual
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONOCE_MEJORA_TU_PLAN)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Conoce/mejora tu plan'");
        ReportHooks.registrarPaso("Seleccionar 'Conoce/mejora tu plan'");

        // 2. Hacer clic en el botón de WhatsApp Enviar
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2)
        );

        // 3. Esperar y Validar la información del plan
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(TU_PLAN_ACTUAL),
                WaitForTextContains.withAnyTextContains(INCLUYE),
                ValidarTextoQueContengaX.elTextoContiene(TELEVISION),
                ValidarTextoQueContengaX.elTextoContiene(TELEFONIA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar información del plan hogar");
        ReportHooks.registrarPaso("Validar información del plan hogar");

        // 4. Hacer clic en el botón interactivo "Explorar opciones"
        actor.attemptsTo(
                ScrollGradual.bajar(0.20),
                ClickTextoQueContengaX.elTextoContiene(EXPLORAR_OPCIONES)
        );

        // 5. Esperar el mensaje correspondiente a explorar opciones y dar clic en "Selecciona"
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(CONOCE_OTROS_PLANES),
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en el botón Selecciona final de Cambia tu plan");
        ReportHooks.registrarPaso("Clic en el botón Selecciona final de Cambia tu plan");

        // 6. Validar las opciones desplegadas en el menú
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(CAMBIA_TU_PLAN),
                ValidarTextoQueContengaX.elTextoContiene(CAMBIA_TU_PLAN),
                ValidarTextoQueContengaX.elTextoContiene(SERVICIOS_ADICIONALES_HOGAR),
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE),
                Atras.irAtras()
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar opciones de Cambia tu plan");
        ReportHooks.registrarPaso("Validar opciones de Cambia tu plan");

        // 7. Salir de la conversación
        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }

    public static Performable conoceMejoraTuPlanHogar() {
        return instrumented(ConoceMejoraTuPlanHogar.class);
    }
}
