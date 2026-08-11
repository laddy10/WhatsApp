package tasks.CompraTusPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static tasks.CompraTusPaquetes.Constants_Paquetes.*;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.Constantes.ABANDONAR_CONVERSACION;

import hooks.ReportHooks;
import interactions.Click.ClickOpcionMenu;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.comunes.Atras;
import interactions.scroll.Scroll;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class PaqTodoIncluido implements Task {

    private static final String MENSAJE_CAPTURA = "Seleccionar el Paq. todo incluido y enviar";
    private static final String MENSAJE_CAPTURA_2 =
            "Ingresar al boton Selecciona para validar los paquetes ";
    private static final String MENSAJE_CAPTURA_3 =
            "Se verifican los Paq. todo incluidos disponibles";
    private static final String MENSAJE_CAPTURA_4 = "Clic en Ver más paquetes y enviar";
    private static final String MENSAJE_CAPTURA_5 =
            "Ingresar al boton selecciona y validar los siguientes paquetes";
    private static final String MENSAJE_CAPTURA_6 =
            "Se verifican correctamente Paq. todo incluidos disponibles";

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitForResponse.withText(PAQ_TODO_INCLUIDO),
                // Hacemos scroll y clic explícito en el elemento para asegurar la selección del RadioButton
                ScrollHastaTexto.conTexto(PAQ_TODO_INCLUIDO),
                ClickOpcionMenu.conTexto(PAQ_TODO_INCLUIDO));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                // Esperamos que el mensaje enviado aparezca en el chat
                WaitForTextContains.withAnyTextContains(AHORRA_Y_APROVECHA_MAXIMO),
                // Damos un tiempo de gracia para que el bot responda
                WaitFor.aTime(12000),
                // Clickeamos el botón de selecciona del nuevo mensaje
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(30)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        actor.attemptsTo(
                // Se eliminó el clic directo a BTN_SELECCIONA_PQ_TODO_INCLUIDO para evitar abrir la lista vieja
                WaitForResponse.withText(PAQ_TI_150MB_1D_PRECIO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_3);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_3);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PAQ_TI_150MB_1D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_150MB_1D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_400MB_3D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_400MB_3D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_1_4GB_6D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_1_4GB_6D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_2GB_7D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_2GB_7D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_WIN_2GB_7D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_WIN_2GB_7D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_10GB_7D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_10GB_7D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_WIN_10GB_7D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_WIN_10GB_7D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_WIN_3_5GB_10D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_WIN_3_5GB_10D_DESC),
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(VER_MAS_PAQUETES));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_4);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_4);

        actor.attemptsTo(Click.on(BTN_ENVIAR_2), WaitFor.aTime(2000));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_5);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_5);

        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),
                //Click.on(BTN_SELECCIONA_PQ_TODO_INCLUIDO),
                ValidarTexto.validarTexto(PAQ_TI_20GB_15D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_20GB_15D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_WIN_7_5GB_20D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_WIN_7_5GB_20D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_12GB_30D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_12GB_30D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_30GB_30D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_30GB_30D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_18GB_30D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_18GB_30D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_80GB_30D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_80GB_30D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_SALUD_400MB_3D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_SALUD_400MB_3D_DESC),
                ValidarTexto.validarTexto(PAQ_TI_SALUD_1_4GB_6D_PRECIO),
                ValidarTexto.validarTexto(PAQ_TI_SALUD_1_4GB_6D_DESC));

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_6);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_6);

        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir(),
                SalirConversacion.salir()
        );

    }

    public static Performable seleccionarTipoPaquete() {
        return instrumented(PaqTodoIncluido.class);
    }
}
