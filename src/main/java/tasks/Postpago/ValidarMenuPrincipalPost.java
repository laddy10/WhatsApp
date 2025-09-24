package tasks.Postpago;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTexto;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class ValidarMenuPrincipalPost implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ValidarTexto.validarTexto(MENU_PRINCIPAL),
                ValidarTexto.validarTexto(COMPRA_WHATSAPP_POST),
                ValidarTexto.validarTexto(TODO_SOBRE_TU_PLAN),
                ValidarTexto.validarTexto(TUS_PAGOS_Y_FACTURAS),
                ValidarTexto.validarTexto(SOPORTE_Y_SERVICIO),
                ValidarTexto.validarTexto(OTRAS_OPCIONES_POST),
                ValidarTexto.validarTexto(HABLAR_CON_ASESOR),
                ValidarTexto.validarTexto(CONSULTAR_OTRA_CUENTA));


        CapturaDePantallaMovil.tomarCapturaPantalla("Validar Menu principal y opciones");
        ReportHooks.registrarPaso("Validar Menu principal y opciones");

        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    public static Performable validarMenuPrincipalPost() {
        return instrumented(ValidarMenuPrincipalPost.class);
    }
}