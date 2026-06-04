package tasks.Hogar.TodoSobrePlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class LealtadClaroVideoHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Seleccionar "Tu lealtad merece más" y enviar
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TU_LEALTAD_MERECE_MAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Tu lealtad merece más hogar");
        ReportHooks.registrarPaso("Seleccionar Tu lealtad merece más hogar");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(3000),
                WaitForTextContains.withAnyTextContains(TEXTO_GRACIAS_POR_PREFERIRNOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje gracias por preferirnos hogar");
        ReportHooks.registrarPaso("Validar mensaje gracias por preferirnos hogar");

        // 2. Esperar y hacer click en el botón Selecciona del último mensaje
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_DRIVE),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_CLUB)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar opciones del menú Tu lealtad merece más");
        ReportHooks.registrarPaso("Validar opciones del menú Tu lealtad merece más");

        // 3. Seleccionar "Claro Video" y enviar
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_VIDEO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Video");
        ReportHooks.registrarPaso("Seleccionar Claro Video");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(3000),
                WaitForTextContains.withAnyTextContains(PELIS_SERIES_TV, URL_CLARO_VIDEO, DESCARGA_LA_APP)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje y URL de Claro Video hogar");
        ReportHooks.registrarPaso("Validar mensaje y URL de Claro Video hogar");

        // 4. Validar texto de la descripción del mensaje (URL puede ser cambiante)
        AndroidObject androidObject = new AndroidObject();
        if (androidObject.textoContiene(actor, PELICULAS_TODOS_LOS_GENEROS)) {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(PELICULAS_TODOS_LOS_GENEROS)
            );
        }

        // 5. Abrir la URL de Claro Video (puede ser cambiante - bit.ly)
        UtilidadesAndroid.abrirLinkEnNavegador(URL_CLARO_VIDEO);

        actor.attemptsTo(
                WaitFor.aTime(20000),
                WaitForResponse.withAnyText(EXPLORAR),
                ValidarTextoQueContengaX.elTextoContiene(PREMIUM),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO_RECOMIENDA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar redirección a Claro Video");
        ReportHooks.registrarPaso("Validar redirección a Claro Video");

        // 6. Volver a WhatsApp y salir
        actor.attemptsTo(
                Atras.irAtras(),
                WaitFor.aTime(2000),
                SalirConversacion.salir()
        );
    }

    public static Performable lealtadClaroVideoHogar() {
        return instrumented(LealtadClaroVideoHogar.class);
    }
}
