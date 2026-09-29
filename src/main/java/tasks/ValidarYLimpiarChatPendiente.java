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

    /*
     * Primera variante observada:
     *
     * ¿Autorizas el tratamiento de tus datos personales
     * y aceptas los T&C...?
     *
     * Botones:
     * Sí
     * No
     */
    private static final String TEXTO_PREGUNTA_AUTORIZACION_1 =
            AUTORIZACION_TRATAMIENTO;

    /*
     * Segunda variante observada:
     *
     * ¿Autorizas y aceptas?
     *
     * Botones:
     * Si, autorizo
     * No autorizo
     */
    private static final String TEXTO_PREGUNTA_AUTORIZACION_2 =
            "Autorizas y aceptas";

    private static final String TEXTO_MENU_PRINCIPAL =
            "Menú principal";

    private static final String TEXTO_PAGAR_FACTURA =
            "Pagar factura";

    private static final String TEXTO_NO_ENTENDI =
            "No entendí";

    private static final String LINEA_RECUPERACION =
            "1";

    /*
     * Estados donde la conversación ya fue finalizada
     * por el propio bot.
     */
    private static final String TEXTO_CIERRE_CONVERSACION_1 =
            "Fue un gusto ayudarte";

    private static final String TEXTO_CIERRE_CONVERSACION_2 =
            "Una vez vuelvas a chatear, iniciaremos una nueva conversación";

    private static final String TEXTO_CIERRE_CONVERSACION_3 =
            "Han pasado 40 minutos y nuestro chat finalizó";

    private static final String TEXTO_RECHAZO_CONFIRMADO =
            "Has elegido la opción NO";

    private static final String TEXTO_NO_CONTINUAR_CANAL =
            "no podremos continuar la conversación por este canal";

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * ============================================================
         * CASO 0
         * CONVERSACIÓN YA FINALIZADA
         * ============================================================
         *
         * Ejemplos:
         *
         * - "Fue un gusto ayudarte"
         * - "Una vez vuelvas a chatear..."
         * - "Han pasado 40 minutos y nuestro chat finalizó"
         *
         * Aquí NO enviamos Cierrecaso.
         *
         * La conversación ya fue terminada por Claro.
         * Solo vaciamos WhatsApp y dejamos que IniciarChatClaro
         * continúe normalmente y envíe el nuevo saludo.
         */
        if (conversacionYaFinalizada(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto que la conversacion anterior ya fue finalizada por el bot"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Conversacion anterior ya finalizada detectada"
            );

            vaciarChat(actor);
            return;
        }

        /*
         * ============================================================
         * CASO 1
         * TRATAMIENTO DE DATOS PENDIENTE
         * ============================================================
         */
        if (hayTratamientoDatosPendiente(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto tratamiento de datos pendiente de una interaccion anterior"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Tratamiento de datos pendiente detectado"
            );

            manejarTratamientoDatosPendiente(actor);
            return;
        }

        /*
         * ============================================================
         * CASO 2
         * ESTADO DONDE CIERRECASO YA FUNCIONA
         * ============================================================
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
         * ============================================================
         * CASO 3
         * CONVERSACIÓN EN SELECCIÓN DE LÍNEA
         * ============================================================
         */
        if (haySeleccionLineaPendiente(actor)) {

            ReportHooks.registrarPaso(
                    "Se detecto una conversacion anterior pendiente en seleccion de linea"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Seleccion de linea pendiente detectada"
            );

            /*
             * Antes de enviar la línea, comprobamos nuevamente
             * si el bot ya cerró la conversación.
             */
            if (conversacionYaFinalizada(actor)) {

                ReportHooks.registrarPaso(
                        "Se detecto que la conversacion ya fue finalizada antes de enviar la linea de recuperacion"
                );

                vaciarChat(actor);
                return;
            }

            /*
             * Seleccionamos una línea válida para intentar
             * recuperar la conversación.
             */
            actor.attemptsTo(
                    Enter.theValue(LINEA_RECUPERACION)
                            .into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );

            /*
             * Esperamos alguno de los estados conocidos.
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
             * Puede haber llegado nuevamente a tratamiento
             * de datos.
             */
            if (hayTratamientoDatosPendiente(actor)) {

                ReportHooks.registrarPaso(
                        "Despues de seleccionar la linea se detecto tratamiento de datos"
                );

                manejarTratamientoDatosPendiente(actor);
                return;
            }

            /*
             * O puede haber llegado a un estado donde
             * Cierrecaso funciona.
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
         * Si no existe ningún estado pendiente conocido,
         * terminamos esta Task y dejamos continuar
         * IniciarChatClaro.
         */
    }

    /*
     * ================================================================
     * CONVERSACIÓN FINALIZADA
     * ================================================================
     */
    private boolean conversacionYaFinalizada(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(TEXTO_CIERRE_CONVERSACION_1)
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto(TEXTO_CIERRE_CONVERSACION_2)
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto(TEXTO_CIERRE_CONVERSACION_3)
                .answeredBy(actor);
    }

    /*
     * ================================================================
     * DETECTAR PRIMER MENSAJE DE POLÍTICA
     * ================================================================
     */
    public static boolean hayTratamientoDatosPendiente(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(TEXTO_TRATAMIENTO_DATOS)
                .answeredBy(actor);
    }

    /*
     * ================================================================
     * DETECTAR SI LA POLÍTICA LLEGÓ COMPLETA
     * ================================================================
     *
     * No basta con encontrar el primer mensaje de política.
     *
     * También debe existir una de las preguntas de autorización
     * observadas y alguno de los botones correspondientes.
     *
     * Esto evita asumir que cualquier botón "No" pertenece a
     * tratamiento de datos.
     */
    private boolean hayAutorizacionTratamientoDisponible(Actor actor) {

        boolean preguntaAutorizacionVisible =
                TextoQueContengaX
                        .verificarTexto(TEXTO_PREGUNTA_AUTORIZACION_1)
                        .answeredBy(actor)

                        || TextoQueContengaX
                        .verificarTexto(TEXTO_PREGUNTA_AUTORIZACION_2)
                        .answeredBy(actor);

        if (!preguntaAutorizacionVisible) {
            return false;
        }

        List<WebElementFacade> btnNo =
                BTN_NO.resolveAllFor(actor);

        List<WebElementFacade> btnNoAutorizo =
                BTN_NO_AUTORIZO.resolveAllFor(actor);

        return !btnNo.isEmpty()
                || !btnNoAutorizo.isEmpty();
    }

    /*
     * ================================================================
     * MANEJO DEL TRATAMIENTO DE DATOS
     * ================================================================
     *
     * Aquí está el nuevo escenario.
     *
     * POLÍTICA + PREGUNTA/BOTONES
     *     -> comportamiento existente
     *     -> rechazamos y cerramos.
     *
     * SOLO POLÍTICA, SIN PREGUNTA/BOTONES
     *     -> respuesta incompleta del bot
     *     -> NO Cierrecaso
     *     -> NO pulsar No
     *     -> vaciar chat
     *     -> permitir nuevo Hola.
     */
    private <T extends Actor> void manejarTratamientoDatosPendiente(T actor) {

        if (!hayAutorizacionTratamientoDisponible(actor)) {

            ReportHooks.registrarPaso(
                    "Tratamiento de datos incompleto: el bot envio la politica pero no envio la pregunta o los botones de autorizacion"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Politica de tratamiento incompleta - se limpiara el chat"
            );

            vaciarChat(actor);
            return;
        }

        /*
         * Si la autorización está completa, conservamos
         * exactamente el comportamiento que ya existía.
         */
        rechazarTratamientoYCerrar(actor);
    }

    /*
     * ================================================================
     * SELECCIÓN DE LÍNEA PENDIENTE
     * ================================================================
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

    /*
     * ================================================================
     * ESTADOS DONDE CIERRECASO FUNCIONA
     * ================================================================
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

    /*
     * ================================================================
     * RECHAZAR TRATAMIENTO COMPLETO
     * ================================================================
     */
    private <T extends Actor> void rechazarTratamientoYCerrar(T actor) {

        List<WebElementFacade> btnNo =
                BTN_NO.resolveAllFor(actor);

        List<WebElementFacade> btnNoAutorizo =
                BTN_NO_AUTORIZO.resolveAllFor(actor);

        /*
         * Variante:
         * Sí / No
         */
        if (!btnNo.isEmpty()) {

            actor.attemptsTo(
                    Click.on(BTN_NO)
            );

            /*
             * Variante:
             * Si, autorizo / No autorizo
             */
        } else if (!btnNoAutorizo.isEmpty()) {

            actor.attemptsTo(
                    Click.on(BTN_NO_AUTORIZO)
            );

        } else {

            /*
             * Salvaguarda.
             *
             * No debemos intentar interactuar con un botón
             * que realmente no existe.
             */
            ReportHooks.registrarPaso(
                    "La politica fue detectada pero ya no existen botones de autorizacion; se limpiara el chat"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Botones de autorizacion desaparecieron antes de interactuar"
            );

            vaciarChat(actor);
            return;
        }

        ReportHooks.registrarPaso(
                "Se rechazo la autorizacion de tratamiento de datos"
        );

        /*
         * Esperar la respuesta real del bot.
         *
         * Incluimos también los mensajes de conversación
         * finalizada para evitar que un cierre automático
         * produzca un timeout innecesario.
         */
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(
                        30,
                        TEXTO_RECHAZO_CONFIRMADO,
                        TEXTO_NO_CONTINUAR_CANAL,
                        TEXTO_MENU_PRINCIPAL,
                        TEXTO_PAGAR_FACTURA,
                        TEXTO_CIERRE_CONVERSACION_1,
                        TEXTO_CIERRE_CONVERSACION_2,
                        TEXTO_CIERRE_CONVERSACION_3
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Respuesta del bot tras rechazar tratamiento de datos"
        );

        /*
         * Claro también puede terminar directamente
         * la conversación.
         */
        if (conversacionYaFinalizada(actor)) {

            ReportHooks.registrarPaso(
                    "El bot finalizo directamente la conversacion despues del rechazo"
            );

            vaciarChat(actor);
            return;
        }

        ReportHooks.registrarPaso(
                "El bot confirmo el rechazo; el chat quedo en estado que permite cierre"
        );

        cerrarYVaciarChat(actor);
    }

    /*
     * ================================================================
     * CERRAR + VACIAR
     * ================================================================
     */
    private <T extends Actor> void cerrarYVaciarChat(T actor) {

        actor.attemptsTo(
                SalirConversacion.salir(),
                WaitFor.aTime(1500)
        );

        vaciarChat(actor);
    }

    /*
     * ================================================================
     * SOLO VACIAR CHAT
     * ================================================================
     *
     * IMPORTANTE:
     *
     * Vaciar WhatsApp NO significa cerrar la conversación
     * lógica con el bot de Claro.
     *
     * Se utiliza precisamente para estados incompletos
     * donde no queremos enviar Cierrecaso.
     */
    private <T extends Actor> void vaciarChat(T actor) {

        actor.attemptsTo(
                Click.on(BTN_MAS_OPCIONES),
                ClickTextoQueContengaX.elTextoContiene(MAS),
                ClickTextoQueContengaX.elTextoContiene(VACIAR_CHAT),
                Click.on(BTN_VACIAR_CHAT),
                WaitFor.aTime(1500)
        );

        ReportHooks.registrarPaso(
                "La conversacion pendiente fue limpiada"
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