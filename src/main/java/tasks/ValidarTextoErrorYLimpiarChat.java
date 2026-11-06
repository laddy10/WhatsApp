package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import utils.CapturaDePantallaMovil;

public class ValidarTextoErrorYLimpiarChat implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * ============================================================
         * 1. VALIDAR SI LA CONVERSACIÓN YA FUE FINALIZADA
         * ============================================================
         *
         * En este caso no intentamos cerrar nuevamente.
         * Solamente limpiamos el chat.
         */
        boolean chatFinalizadoPorInactividad =
                TextoQueContengaX
                        .verificarTexto(
                                "Fue un gusto ayudarte"
                        )
                        .answeredBy(actor);

        if (chatFinalizadoPorInactividad) {

            ReportHooks.registrarPaso(
                    "Se detectó que la conversación anterior "
                            + "ya fue finalizada por el bot."
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Conversacion anterior ya finalizada"
            );

            vaciarChat(actor);

            return;
        }


        /*
         * ============================================================
         * 2. MENSAJES DE ERROR / CONVERSACIÓN RESIDUAL
         * ============================================================
         */
        List<String> mensajesError =
                Arrays.asList(
                        "Por favor ingrese el número de opción",
                        "¡Lo siento! No entendí lo que escribiste",
                        "No entendí tu mensaje.",
                        "No entendí tu mensaje",
                        "No logré entender tu mensaje",
                        "Menú principal",
                        "Para continuar debes seleccionar alguna de las opciones mostradas anteriormente",
                        "Lo siento, tu respuesta no es válida"
                );


        boolean textoErrorDetectado =
                mensajesError
                        .stream()
                        .anyMatch(
                                mensaje ->
                                        TextoQueContengaX
                                                .verificarTexto(
                                                        mensaje
                                                )
                                                .answeredBy(actor)
                        );


        /*
         * Si no encontramos ninguno de los estados conocidos,
         * la tarea no debe hacer nada.
         */
        if (!textoErrorDetectado) {
            return;
        }


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Conversacion residual o mensaje de error detectado"
        );

        ReportHooks.registrarPaso(
                "Se detectó una conversación anterior "
                        + "o un mensaje de error pendiente."
        );


        /*
         * ============================================================
         * 3. PRIORIDAD: FINALIZAR CHAT
         * ============================================================
         *
         * Caso observado:
         *
         * "No logré entender tu mensaje..."
         *
         * Hacer una consulta
         * Finalizar chat
         *
         * Si el bot nos ofrece explícitamente Finalizar chat,
         * utilizamos esa opción antes que Cierrecaso.
         */
        boolean finalizarChatVisible =
                TextoQueContengaX
                        .verificarTexto(
                                "Finalizar chat"
                        )
                        .answeredBy(actor);


        if (finalizarChatVisible) {

            ReportHooks.registrarPaso(
                    "Se detectó la opción 'Finalizar chat'. "
                            + "Se utilizará para cerrar "
                            + "la conversación anterior."
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Opcion Finalizar chat detectada"
            );


            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(
                            "Finalizar chat"
                    ),

                    WaitFor.aTime(
                            2000
                    )
            );


            ReportHooks.registrarPaso(
                    "Se seleccionó 'Finalizar chat' "
                            + "para cerrar la conversación anterior."
            );

        } else {

            /*
             * ========================================================
             * 4. CIERRE NORMAL
             * ========================================================
             *
             * Si Finalizar chat NO está disponible,
             * conservamos exactamente el mecanismo existente:
             *
             * SalirConversacion.
             */
            ReportHooks.registrarPaso(
                    "No se encontró la opción 'Finalizar chat'. "
                            + "Se utilizará el cierre normal "
                            + "mediante SalirConversacion."
            );


            actor.attemptsTo(
                    SalirConversacion.salir(),

                    WaitFor.aTime(
                            1500
                    )
            );
        }


        /*
         * ============================================================
         * 5. VACIAR CHAT
         * ============================================================
         *
         * IMPORTANTE:
         *
         * Esta Task ya NO envía Hola.
         *
         * Su única responsabilidad es:
         *
         * - recuperar conversación anterior
         * - cerrar
         * - limpiar
         *
         * IniciarChatClaro será quien inicie
         * posteriormente una nueva conversación.
         */
        vaciarChat(actor);


        ReportHooks.registrarPaso(
                "La conversación residual fue cerrada y limpiada. "
                        + "El nuevo saludo será gestionado "
                        + "por IniciarChatClaro."
        );
    }


    /*
     * ================================================================
     * VACIAR CHAT
     * ================================================================
     */
    private <T extends Actor> void vaciarChat(T actor) {

        actor.attemptsTo(
                Click.on(
                        BTN_MAS_OPCIONES
                ),

                ClickTextoQueContengaX.elTextoContiene(
                        MAS
                ),

                ClickTextoQueContengaX.elTextoContiene(
                        VACIAR_CHAT
                ),

                Click.on(
                        BTN_VACIAR_CHAT
                ),

                WaitFor.aTime(
                        1500
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Chat limpio despues de recuperar conversacion anterior"
        );

        ReportHooks.registrarPaso(
                "La conversación anterior fue limpiada."
        );
    }


    public static ValidarTextoErrorYLimpiarChat validarYLimpiar() {

        return instrumented(
                ValidarTextoErrorYLimpiarChat.class
        );
    }
}