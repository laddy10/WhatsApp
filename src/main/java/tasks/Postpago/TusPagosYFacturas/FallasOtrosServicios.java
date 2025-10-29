package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollHastaTexto;
import interactions.scroll.ScrollInicio;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class FallasOtrosServicios implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(OTROS_SERVICIOS_FALLAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Otros servicios");
        ReportHooks.registrarPaso("Seleccionar Otros servicios");

        // Enviar selección
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(CLARO_VIDEO_SOPORTE),
                ClickTextoQueContengaX.elTextoContiene(CLARO_VIDEO_SOPORTE),
                WaitForTextContains.withTextContains(CLARO_MUSICA_SOPORTE)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar Claro Video");
        ReportHooks.registrarPaso("Validar Claro Video");

        actor.attemptsTo(
                Click.on(BTN_CLARO_VIDEO),
                WaitForTextContains.withTextContains(MENU_ANTERIOR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar imagen Claro Video");
        ReportHooks.registrarPaso("Validar imagen Claro Video");

        actor.attemptsTo(
                Click.on(IMAGEN_CLARO_VIDEO),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Ver imagen Claro Video");
        ReportHooks.registrarPaso("Ver imagen Claro Video");

        actor.attemptsTo(
                Atras.irAtras(),
                ScrollInicio.scrollUnaVista(),
                ScrollHastaTexto.conTexto(CLARO_MUSICA_SOPORTE),
                Click.on(BTN_CLARO_MUSICA),
                WaitForTextContains.withTextContains(MENU_ANTERIOR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar imagen Claro Musica");
        ReportHooks.registrarPaso("Validar imagen Claro Musica");


        actor.attemptsTo(
                Click.on(IMAGEN_CLARO_MUSICA),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Ver imagen Claro Musica");
        ReportHooks.registrarPaso("Ver imagen Claro Musica");


        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    public static Performable fallasOtrosServicios() {
        return instrumented(FallasOtrosServicios.class);
    }
}