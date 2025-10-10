package tasks.OtrasOpciones;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollHastaTexto;
import interactions.scroll.ScrollInicio;
import interactions.wait.WaitFor;
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
import utils.Constantes;
import utils.UtilidadesAndroid;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static tasks.CompraTusPaquetes.Constants_Paquetes.*;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.Constantes.COMPRA_DE_PAQUETES;
import static utils.ConstantesPost.*;


public class PaquetesYRecargasPost implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAQUETES_RECARGAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Dar clic opción Paquetes y recargas");
        ReportHooks.registrarPaso("Dar clic opción Paquetes y recargas");


        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForTextContains.withTextContains(MENU_PRINCIPAL)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje y link de compra");
        ReportHooks.registrarPaso("Validar mensaje y link de compra");


        String mensaje = LBL_MENSAJES.resolveAllFor(actor).stream()
                .map(WebElementFacade::getText)
                .filter(text -> text.contains("yoiz.me") || text.contains("clro.co"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró URL de pago."));


        String urlExtraida = ExtraerURL.desdeTexto(mensaje);


        UtilidadesAndroid.abrirLinkEnNavegador(urlExtraida);


        actor.attemptsTo(
                WaitForTextContains.withTextContains(PERSONAS),
                WaitFor.aTime(3000),
                ValidarTextoQueContengaX.elTextoContiene(PORTAL_PAGOS_Y_RECARGAS),
                ValidarTextoQueContengaX.elTextoContiene(COMPRA_DE_PAQUETES),
                ValidarTextoQueContengaX.elTextoContiene(DIGITA_NUMERO_CLARO)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Direccionamiento portal de pagos y recargas");
        ReportHooks.registrarPaso("Direccionamiento portal de pagos y recargas");

        actor.attemptsTo(
                ScrollHastaTexto.conTexto("No soy un robot"),
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONAR_TIPO_PAQUETE),
                ValidarTextoQueContengaX.elTextoContiene(PAQUETES_DATOS)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Direccionamiento correcto portal de pagos y recargas");
        ReportHooks.registrarPaso("Direccionamiento correcto portal de pagos y recargas");


        actor.attemptsTo(
                Atras.irAtras(),
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    public static Performable paquetesYRecargasPost() {
        return instrumented(PaquetesYRecargasPost.class);
    }
}

