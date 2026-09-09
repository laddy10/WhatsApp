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

    private static final String LINEA_RECUPERACION = "1";

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * CASO 1:
         * La ejecución anterior quedó directamente
         * en la autorización de tratamiento de datos.
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
         * La ejecución anterior quedó esperando
         * que se seleccione una línea.
         */
        if (haySeleccionLineaPendiente(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto una conversacion anterior pendiente en seleccion de linea"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Seleccion de linea pendiente detectada"
            );

            /*
             * Seleccionamos una línea válida únicamente
             * para avanzar hasta tratamiento de datos.
             */
            actor.attemptsTo(
                    Enter.theValue(LINEA_RECUPERACION)
                            .into(TXT_ENVIAR_MENSAJE),

                    Click.on(BTN_ENVIAR)
            );

            /*
             * Esperamos que Claro avance hasta
             * la autorización de tratamiento de datos.
             */
            actor.attemptsTo(
                    WaitForTextContains.withAnyTextContains(
                            TEXTO_TRATAMIENTO_DATOS
                    )
            );

            /*
             * Confirmamos que realmente llegamos
             * a tratamiento de datos antes de continuar.
             */
            if (!hayTratamientoDatosPendiente(actor)) {

                throw new IllegalStateException(
                        "Se selecciono una linea, pero no aparecio la autorizacion de tratamiento de datos"
                );
            }

            ReportHooks.registrarPaso(
                    "Se alcanzo tratamiento de datos despues de seleccionar la linea"
            );

            rechazarTratamientoYCerrar(actor);

            return;
        }

        /*
         * No se encontró ningún estado pendiente conocido.
         * La Task termina y IniciarChatClaro continúa normalmente.
         */
    }

    /**
     * Detecta si quedó pendiente la autorización
     * de tratamiento de datos.
     */
    public static boolean hayTratamientoDatosPendiente(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(TEXTO_TRATAMIENTO_DATOS)
                .answeredBy(actor);
    }

    /**
     * Detecta si la conversación anterior quedó
     * esperando la selección de una línea.
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
     * Rechaza tratamiento de datos, cierra
     * la conversación anterior y vacía el chat.
     */
    private <T extends Actor> void rechazarTratamientoYCerrar(T actor) {

        List<WebElementFacade> btnNo =
                BTN_NO.resolveAllFor(actor);

        List<WebElementFacade> btnNoAutorizo =
                BTN_NO_AUTORIZO.resolveAllFor(actor);

        /*
         * Hay dos versiones conocidas del botón:
         *
         * No
         * No autorizo
         */
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

        /*
         * Esperamos que Claro procese la respuesta
         * antes de enviar Cierrecaso.
         */
        actor.attemptsTo(
                WaitFor.aTime(4000),
                SalirConversacion.salir()
        );

        /*
         * Una vez cerrado el caso, vaciamos
         * físicamente el chat.
         */
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