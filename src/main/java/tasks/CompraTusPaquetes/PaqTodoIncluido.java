package tasks.CompraTusPaquetes;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
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
import static utils.Constantes.ABANDONAR_CONVERSACION;


public class PaqTodoIncluido implements Task {

    private static final String MENSAJE_CAPTURA = "Seleccionar el Paq. todo incluido y enviar";
    private static final String MENSAJE_CAPTURA_2 = "Ingresar al boton Selecciona para validar los paquetes ";
    private static final String MENSAJE_CAPTURA_3 = "Se verifican los Paq. todo incluidos disponibles";
    private static final String MENSAJE_CAPTURA_4 = "Clic en Ver más paquetes y enviar";
    private static final String MENSAJE_CAPTURA_5 = "Ingresar al boton selecciona y validar los siguientes paquetes";
    private static final String MENSAJE_CAPTURA_6 = "Se verifican correctamente Paq. todo incluidos disponibles";


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitForResponse.withText(PAQ_TODO_INCLUIDO),
                ClickTextoQueContengaX.elTextoContiene(PAQ_TODO_INCLUIDO));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(10000),
                WaitForTextContains.withAnyTextContains(AHORRA_Y_APROVECHA_MAXIMO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);


        actor.attemptsTo(
                Click.on(BTN_SELECCIONA_PQ_TODO_INCLUIDO),
                WaitForResponse.withText(VER_MAS_PAQUETES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_3);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_3);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PAQ_TI_1D_2500_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_1D_2500_DESC),
                ValidarTexto.validarTexto(PAQ_TI_3D_4500_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_3D_4500_DESC),
                ValidarTexto.validarTexto(PAQ_TI_6D_7500_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_6D_7500_DESC),
                ValidarTexto.validarTexto(PAQ_TI_7D_8500_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_7D_8500_DESC),
                ValidarTexto.validarTexto(PAQ_TI_10D_12000_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_10D_12000_DESC),
                ValidarTexto.validarTexto(PAQ_TI_7D_12000_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_7D_12000_DESC),
                ClickTextoQueContengaX.elTextoContiene(VER_MAS_PAQUETES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_4);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_4);


        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(2000));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_5);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_5);


        actor.attemptsTo(
                Click.on(BTN_SELECCIONA_PQ_TODO_INCLUIDO),
                ValidarTexto.validarTexto(PAQ_TI_20D_22000_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_20D_22000_DESC),
                ValidarTexto.validarTexto(PAQ_TI_15D_22000_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_15D_22000_DESC),
                ValidarTexto.validarTexto(PAQ_TI_30D_32000_PRECIO_1),
                ValidarTexto.validarTexto(PAQ_TI_30D_32000_DESC_1),
                ValidarTexto.validarTexto(PAQ_TI_30D_32000_PRECIO_2),
                ValidarTexto.validarTexto(PAQ_TI_30D_32000_DESC_2),
                ValidarTexto.validarTexto(PAQ_TI_30D_42000_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_30D_42000_DESC),
                ValidarTexto.validarTexto(PAQ_TI_30D_100000_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_30D_100000_DESC),
                ValidarTexto.validarTexto(PAQ_TI_3D_5500_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_3D_5500_DESC),
                ValidarTexto.validarTexto(PAQ_TI_6D_8500_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_6D_8500_DESC)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_6);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_6);

        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitFor.aTime(2000),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }


    public static Performable seleccionarTipoPaquete() {
        return instrumented(PaqTodoIncluido.class);
    }
}