package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.ExtraerURL;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class TusPQRRadicados implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TUS_PQRS_RADICADOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Tus PQR Radicados");
        ReportHooks.registrarPaso("Seleccionar Tus PQR Radicados");

        // Enviar selección
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(CONSULTAR_QUEJAS_RECLAMOS),
                ValidarTextoQueContengaX.elTextoContiene(CONSULTAR_QUEJAS_RECLAMOS)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Abrir URL para consultar quejas y reclamos");
        ReportHooks.registrarPaso("Abrir URL para consultar quejas y reclamos");


        String mensaje = LBL_MENSAJES.resolveAllFor(actor).stream()
                .map(WebElementFacade::getText)
                .filter(text -> text.contains("yoiz.me") || text.contains("clro.co"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró URL de pago."));


        String urlExtraida = ExtraerURL.desdeTexto(mensaje);


        UtilidadesAndroid.abrirLinkEnNavegador(urlExtraida);


        actor.attemptsTo(
                WaitForTextContains.withTextContains(PERSONAS),
                ValidarTexto.validarTexto(PERSONAS),
                ValidarTexto.validarTexto(CLARO_TE_ESCUCHA)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Direccionamiento pagina Claro");
        ReportHooks.registrarPaso("Direccionamiento pagina Claro");


        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    public static Performable tusPQRRadicados() {
        return instrumented(TusPQRRadicados.class);
    }
}