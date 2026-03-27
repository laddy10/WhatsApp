package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class ProgramaTusPagos implements Task {

    private static final String MENSAJE_CAPTURA_2 = "Validar información sobre programación de pagos";
    private static final String MENSAJE_CAPTURA_3 = "Clic en 'Programar pagos'";
    private static final String MENSAJE_CAPTURA_4 = "Validar direccionamiento a Mi Claro";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Validar mensaje informativo sobre programación
        actor.attemptsTo(
                WaitForTextContains.withTextContains(PROGRAMAR_PAGO_AUTOMATICO),
                ValidarTextoQueContengaX.elTextoContiene(PROGRAMAR_PAGO_AUTOMATICO),
                ValidarTextoQueContengaX.elTextoContiene(PROGRAMAR_PAGOS));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        // Clic en "Programar pagos"
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PROGRAMAR_PAGOS), WaitFor.aTime(10000));

        clickSiExiste(actor, BTN_PERMISO_UBICACION, MIENTRAS_APP_ESTA_EN_USO);
        clickSiExiste(actor, BTN_ACEPTAR_PERMISO, ACEPTAR_2);
        clickSiExiste(actor, BTN_PERMISO_UBICACION, MIENTRAS_APP_ESTA_EN_USO);
        clickSiExiste(actor, SMS_PERMISO_LLAMADAS, NO_PERMITIR);
        clickSiExiste(actor, SMS_PERMISO_NOTIFICACIONES, NO_PERMITIR);
        clickSiExiste(actor, BTN_OMITIR, OMITIR);
        clickSiExisteCheckboxYContinuar(actor, LBL_BIENVENIDA, CHECK_TC, CONTINUAR);
        clickSiExiste(actor, TXT_AUTORIZACION_VELOCIDAD, ACEPTAR_2);

        // Esperar redirección y validar Mi Claro

        if (isVisible(actor, LBL_SESION_CERRADA_POR_SEGURIDAD)) {
            actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(INICIAR_SESION));
        }

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(INICIAR_SESION),
                ValidarTextoQueContengaX.elTextoContiene(TE_PUEDE_INTERESAR),
                ValidarTextoQueContengaX.elTextoContiene(SERVICIOS_A_LA_MANO),
                ValidarTextoQueContengaX.elTextoContiene(PAGOS),
                ValidarTextoQueContengaX.elTextoContiene(RECARGAS_Y_PAQUETES));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_4);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_4);

        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    private <T extends Actor> void clickSiExiste(T actor, Target elemento, String texto) {
        if (isVisible(actor, elemento)) {
            actor.attemptsTo(ClickElementByText.clickElementByText(texto), WaitFor.aTime(4000));
        } else {
            actor.attemptsTo(WaitFor.aTime(1000));
        }
    }

    private <T extends Actor> boolean isVisible(T actor, Target element) {
        return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
    }

    private <T extends Actor> void clickSiExisteCheckboxYContinuar(
            T actor, Target condicion, Target checkbox, String botonTexto) {
        if (isVisible(actor, condicion)) {
            actor.attemptsTo(Click.on(checkbox), ClickElementByText.clickElementByText(botonTexto));
        }
    }

    public static Performable programaTusPagos() {
        return instrumented(ProgramaTusPagos.class);
    }
}
