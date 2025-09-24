package tasks.CompraTusPaquetes;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static tasks.CompraTusPaquetes.Constants_Paquetes.*;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class PaqDeDatos implements Task {

    private static final String MENSAJE_CAPTURA = "Seleccionar el Paq. de datos y enviar";
    private static final String MENSAJE_CAPTURA_2 = "Ingresar al boton Selecciona para validar los paquetes de datos";
    private static final String MENSAJE_CAPTURA_3 = "Se verifican los Paq. de datos disponibles";

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitForResponse.withText(PAQ_DE_DATOS),
                ClickTextoQueContengaX.elTextoContiene(PAQ_DE_DATOS));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(10000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        actor.attemptsTo(
                Click.on(BTN_SELECCIONA_PQ_TODO_INCLUIDO),
                WaitForResponse.withText(PAQ_DATOS_1500_1D_PRECIO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_3);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_3);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PAQ_DATOS_1500_1D_PRECIO),
                ValidarTexto.validarTexto(PAQ_DATOS_1500_1D_DESC),
                ValidarTexto.validarTexto(PAQ_DATOS_2500_1D_PRECIO),
                ValidarTexto.validarTexto(PAQ_DATOS_2500_1D_DESC),
                ValidarTexto.validarTexto(PAQ_DATOS_4500_3D_PRECIO),
                ValidarTexto.validarTexto(PAQ_DATOS_4500_3D_DESC),
                ValidarTexto.validarTexto(PAQ_DATOS_5500_2H_PRECIO),
                ValidarTexto.validarTexto(PAQ_DATOS_5500_2H_DESC),
                ValidarTexto.validarTexto(PAQ_DATOS_7500_7D_PRECIO),
                ValidarTexto.validarTexto(PAQ_DATOS_7500_7D_DESC)
        );

        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitFor.aTime(2000),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitFor.aTime(2000),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable validarPaqDeDatos() {
        return instrumented(PaqDeDatos.class);
    }
}