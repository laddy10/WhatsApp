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
import static utils.ConstantesPost.TUS_EQUIPOS_EN_SOPORTE;

public class TusEquiposEnSoporte implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TUS_EQUIPOS_EN_SOPORTE)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Tus equipos en soporte");
        ReportHooks.registrarPaso("Seleccionar Tus equipos en soporte");

        // Enviar selección
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(NO_TIENES_EQUIPOS_MANTENIMIENTO),
                ValidarTextoQueContengaX.elTextoContiene(NO_TIENES_EQUIPOS_MANTENIMIENTO),
                ValidarTextoQueContengaX.elTextoContiene(MENSAJE_CAV),
                ClickTextoQueContengaX.elTextoContiene(VER_PUNTOS_FISICOS),
                WaitForTextContains.withTextContains(CAV),
                ValidarTextoQueContengaX.elTextoContiene(CAV),
                ValidarTextoQueContengaX.elTextoContiene(ENCUENTRA_CAV)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje y ver puntos fisicos");
        ReportHooks.registrarPaso("Validar mensaje y ver puntos fisicos");


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
                ValidarTexto.validarTexto(CENTROS_CLARO)
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

    public static Performable tusEquiposEnSoporte() {
        return instrumented(TusEquiposEnSoporte.class);
    }
}