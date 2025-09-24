package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
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

public class ConfirmarDatosRegistraEquipo implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(DECLARACION_REGISTRA_EQUIPO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Declaracion");
        ReportHooks.registrarPaso("Declaracion");


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ACEPTAR_DECLARACION),
                WaitForTextContains.withTextContains(CONFIRMAR_DATOS),
                ValidarTextoQueContengaX.elTextoContiene(CONFIRMAR_DATOS),
                ValidarTextoQueContengaX.elTextoContiene(NOMBRE),
                ValidarTextoQueContengaX.elTextoContiene(TIPO_DOCUMENTO_2),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_DOCUMENTO_2),
                ValidarTextoQueContengaX.elTextoContiene(DEPARTAMENTO),
                ValidarTextoQueContengaX.elTextoContiene(CIUDAD),
                ValidarTextoQueContengaX.elTextoContiene(DATOS_EQUIPO),
                ValidarTextoQueContengaX.elTextoContiene(IMEI),
                ValidarTextoQueContengaX.elTextoContiene(LINEA),
                ValidarTextoQueContengaX.elTextoContiene(MARCA),
                ValidarTextoQueContengaX.elTextoContiene(MODELO),
                ValidarTextoQueContengaX.elTextoContiene(ACTUALIZAR_DATOS),
                ValidarTextoQueContengaX.elTextoContiene(CONFIRMAR_DATOS_2)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Confirmar datos");
        ReportHooks.registrarPaso("Confirmar datos");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONFIRMAR_DATOS_2),
                WaitForTextContains.withTextContains(LISTO),
                ValidarTextoQueContengaX.elTextoContiene(REGISTRO_EQUIPO_PROCESO)

        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje de registro en proceso");
        ReportHooks.registrarPaso("Validar mensaje de registro en proceso");

        actor.attemptsTo(
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );



    }

    public static Performable confirmarDatosRegistraEquipo() {
        return instrumented(ConfirmarDatosRegistraEquipo.class);
    }
}