package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPage.BTN_NO;
import static userinterfaces.WhatsAppPostpagoPage.BTN_NO_AUTORIZO;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.TextoQueContengaX;
import utils.CapturaDePantallaMovil;

import java.util.List;

public class ValidarYLimpiarChatPendiente implements Task {

    private static final String TEXTO_TRATAMIENTO_DATOS =
            POLITICA_TRATAMIENTO;

    private static final String TEXTO_MENU_PRINCIPAL =
            "Menú principal";

    private static final String TEXTO_PAGAR_FACTURA =
            "Pagar factura";

    private static final String TEXTO_NO_ENTENDI =
            "No entendí";

    private static final String LINEA_RECUPERACION =
            "1";

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * CASO 1:
         * La conversación anterior quedó directamente
         * en tratamiento de datos.
         */
        if (hayTratamientoDatosPendiente(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto tratamiento de datos pendiente de una interaccion anterior"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Tratamiento de datos pendiente detectado"
            );

            rechazarTratamientoYCerrar(actor);
            return;
        }

        /*
         * CASO 2:
         * La conversación ya está en un estado
         * donde Cierrecaso funciona.
         */
        if (hayEstadoQuePermiteCierre(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto una conversacion anterior en un estado que permite cierre"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Conversacion pendiente lista para cierre"
            );

            cerrarYVaciarChat(actor);
            return;
        }

        /*
         * CASO 3:
         * La conversación anterior quedó esperando
         * selección de cuenta o línea.
         */
        if (haySeleccionLineaPendiente(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto una conversacion anterior pendiente en seleccion de linea"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Seleccion de linea pendiente detectada"
            );

            /*
             * Seleccionamos una línea válida para sacar
             * la conversación del estado de selección.
             */
            actor.attemptsTo(
                    Enter.theValue(LINEA_RECUPERACION)
                            .into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );

            /*
             * El bot no siempre responde igual.
             *
             * Puede mostrar:
             * - tratamiento de datos
             * - menú principal
             * - pagar factura
             * - no entendí
             */
            actor.attemptsTo(
                    WaitForTextContains.withAnyTextContains(
                            TEXTO_TRATAMIENTO_DATOS,
                            TEXTO_MENU_PRINCIPAL,
                            TEXTO_PAGAR_FACTURA,
                            TEXTO_NO_ENTENDI
                    )
            );

            /*
             * Si llegó a tratamiento de datos,
             * primero rechazamos la autorización.
             */
            if (hayTratamientoDatosPendiente(actor)) {

                ReportHooks.registrarPaso(
                        "Despues de seleccionar la linea se detecto tratamiento de datos"
                );

                rechazarTratamientoYCerrar(actor);
                return;
            }

            /*
             * Si llegó a cualquiera de los estados
             * que permiten cierre, cerramos directamente.
             */
            if (hayEstadoQuePermiteCierre(actor)) {

                ReportHooks.registrarPaso(
                        "Despues de seleccionar la linea se detecto un estado que permite cierre"
                );

                cerrarYVaciarChat(actor);
                return;
            }

            throw new IllegalStateException(
                    "Despues de seleccionar la linea no se detecto un estado conocido para limpiar la conversacion"
            );
        }

        /*
         * No se encontró ningún estado pendiente conocido.
         * La tarea termina y el flujo normal continúa.
         */
    }

    /**
     * Detecta si quedó pendiente el tratamiento de datos.
     */
    public static boolean hayTratamientoDatosPendiente(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(TEXTO_TRATAMIENTO_DATOS)
                .answeredBy(actor);
    }

    /**
     * Detecta si la conversación quedó esperando
     * selección de cuenta o línea.
     */
    private boolean haySeleccionLineaPendiente(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(LINEAS_POSTPAGO)
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto(LINEAS_PREPAGO)
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto("Escribe el número de la opción")
                .answeredBy(actor);
    }

    /**
     * Detecta estados conocidos donde el bot
     * ya permite enviar Cierrecaso.
     */
    private boolean hayEstadoQuePermiteCierre(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(TEXTO_MENU_PRINCIPAL)
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto(TEXTO_PAGAR_FACTURA)
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto(TEXTO_NO_ENTENDI)
                .answeredBy(actor);
    }

    /**
     * Rechaza el tratamiento de datos
     * y después cierra y vacía el chat.
     */
    private <T extends Actor> void rechazarTratamientoYCerrar(T actor) {

        List<WebElementFacade> btnNo =
                BTN_NO.resolveAllFor(actor);

        List<WebElementFacade> btnNoAutorizo =
                BTN_NO_AUTORIZO.resolveAllFor(actor);

        if (!btnNo.isEmpty()) {

            actor.attemptsTo(
                    Click.on(BTN_NO)
            );

        } else if (!btnNoAutorizo.isEmpty()) {

            actor.attemptsTo(
                    Click.on(BTN_NO_AUTORIZO)
            );

        } else {

            throw new IllegalStateException(
                    "Se detecto tratamiento de datos, pero no se encontro la opcion No ni No autorizo"
            );
        }

        ReportHooks.registrarPaso(
                "Se rechazo la autorizacion de tratamiento de datos"
        );

        actor.attemptsTo(
                WaitFor.aTime(4000)
        );

        cerrarYVaciarChat(actor);
    }

    /**
     * Envía Cierrecaso y luego vacía el chat.
     */
    private <T extends Actor> void cerrarYVaciarChat(T actor) {

        actor.attemptsTo(
                SalirConversacion.salir(),
                WaitFor.aTime(1500)
        );

        actor.attemptsTo(
                Click.on(BTN_MAS_OPCIONES),
                ClickTextoQueContengaX.elTextoContiene(MAS),
                ClickTextoQueContengaX.elTextoContiene(VACIAR_CHAT),
                Click.on(BTN_VACIAR_CHAT),
                WaitFor.aTime(1500)
        );

        ReportHooks.registrarPaso(
                "La conversacion pendiente fue cerrada y el chat fue limpiado"
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Chat limpio despues de recuperar conversacion pendiente"
        );
    }

    public static Performable ejecutar() {
        return instrumented(
                ValidarYLimpiarChatPendiente.class
        );
    }
}