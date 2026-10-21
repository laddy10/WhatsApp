package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import interactions.wait.WaitForTextContainsWithTimeout;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import tasks.ManejarConversacionConAsesor;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import utils.EstadoAtencionHumana;

public class EstadoServiciosHogar implements Task {

    public static final String ESTADO_SERVICIOS_RESUELTO =
            "estadoServiciosHogarResuelto";

    private static final String REDIRECCION_ASESOR =
            "te contactaré con un asesor";

    private static final String ESPERA_ASESOR =
            "En un momento estará contigo";

    private static final String REVISION_FINALIZADA =
            "Gracias por tu espera";

    private static final String OPCIONES_SERVICIO =
            "en cuál de estos servicios necesitas ayuda";

    /*
     * Nueva respuesta observada:
     *
     * "Identificamos una falla en tu servicio.
     * Espera 3 minutos más por favor..."
     */
    private static final String FALLA_DETECTADA =
            "Identificamos una falla en tu servicio";

    /*
     * Respuesta final observada después
     * de la espera adicional.
     */
    private static final String FALLA_SOLUCIONADA =
            "Tenías una falla en tu servicio hogar la cual ya fue solucionada";

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * Al iniciar asumimos que el diagnóstico todavía
         * no ha terminado por la ruta alternativa.
         */
        actor.remember(
                ESTADO_SERVICIOS_RESUELTO,
                false
        );


        // 1. Validar texto explicativo de Estado de servicios
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        TE_AYUDO_IDENTIFICAR_ESTADO
                )
        );


        // 2. Clic en Selecciona
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(
                        20
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Clic en botón Selecciona de Soporte y servicio"
        );

        ReportHooks.registrarPaso(
                "Clic en botón Selecciona de Soporte y servicio"
        );


        // 3. Seleccionar Estado de servicios
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        ESTADO_SERVICIOS
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar 'Estado de servicios'"
        );

        ReportHooks.registrarPaso(
                "Seleccionar 'Estado de servicios'"
        );


        // 4. Enviar
        actor.attemptsTo(
                Click.on(
                        BTN_ENVIAR_2
                )
        );


        /*
         * Después de seleccionar Estado de servicios:
         *
         * A. Revisión normal de 3 minutos.
         * B. Redirección a asesor.
         */
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(
                        58,
                        ESPERA_3_MINUTOS,
                        REDIRECCION_ASESOR,
                        ESPERA_ASESOR
                )
        );


        /*
         * ============================================================
         * RUTA ASESOR
         * ============================================================
         */
        if (hayRedireccionAsesor(actor)) {

            ReportHooks.registrarPaso(
                    "Estado de servicios redireccionó la conversación a un asesor"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Redirección a asesor detectada"
            );

            EstadoAtencionHumana.marcarEnCola();

            actor.attemptsTo(
                    ManejarConversacionConAsesor.ejecutar()
            );

            return;
        }


        /*
         * ============================================================
         * REVISIÓN INICIAL
         * ============================================================
         */
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        ESPERA_3_MINUTOS
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        REVISION_GENERAL
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        NO_RESPONDAS_MENSAJE
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje de revisión de 3 minutos en proceso"
        );

        ReportHooks.registrarPaso(
                "Validar mensaje de revisión de 3 minutos en proceso"
        );


        /*
         * ============================================================
         * ESPERAR RESULTADO DE LA PRIMERA REVISIÓN
         * ============================================================
         *
         * Ahora aceptamos tres posibles resultados:
         *
         * 1. Gracias por tu espera
         * 2. Opciones de servicio
         * 3. Se detectó una falla y se requieren 3 minutos adicionales
         */
        boolean resultadoRevision =
                WaitForTextContainsWithTimeout.esperar(
                        240,
                        REVISION_FINALIZADA,
                        OPCIONES_SERVICIO,
                        FALLA_DETECTADA
                ).answeredBy(actor);


        if (!resultadoRevision) {

            throw new IllegalStateException(
                    "No finalizo la revision de servicios "
                            + "dentro del tiempo esperado"
            );
        }


        /*
         * ============================================================
         * NUEVA RUTA:
         * SE DETECTÓ UNA FALLA
         * ============================================================
         */
        if (hayFallaDetectada(actor)) {

            ReportHooks.registrarPaso(
                    "La revisión detectó una falla en el servicio. "
                            + "Se realizará la espera adicional indicada por el bot."
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Falla detectada durante revisión de servicios"
            );


            /*
             * Validamos el mensaje intermedio observado.
             */
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(
                            FALLA_DETECTADA
                    )
            );


            /*
             * El bot solicita otros 3 minutos.
             *
             * Damos hasta 240 segundos para recibir
             * el resultado definitivo.
             */
            boolean fallaSolucionada =
                    WaitForTextContainsWithTimeout.esperar(
                            270,
                            FALLA_SOLUCIONADA
                    ).answeredBy(actor);


            if (!fallaSolucionada) {

                throw new IllegalStateException(
                        "Se detectó una falla en el servicio, "
                                + "pero no llegó el resultado final "
                                + "después de la espera adicional."
                );
            }


            /*
             * Validar resultado definitivo.
             */
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(
                            FALLA_SOLUCIONADA
                    )
            );


            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Falla del servicio solucionada después de revisión"
            );

            ReportHooks.registrarPaso(
                    "El bot confirmó que la falla del servicio fue solucionada"
            );


            /*
             * IMPORTANTE:
             *
             * Este flujo ya terminó el diagnóstico.
             *
             * Los pasos posteriores:
             *
             * - Seleccionar servicio TV
             * - Validar imagen de recomendaciones
             *
             * ya no corresponden a esta ruta.
             *
             * Dejamos una marca para que esas tareas se omitan.
             */
            actor.remember(
                    ESTADO_SERVICIOS_RESUELTO,
                    true
            );


            /*
             * Cerrar la conversación.
             *
             * El último paso del feature "Vaciar chat"
             * seguirá ejecutándose después.
             */
            actor.attemptsTo(
                    SalirConversacion.salir()
            );

            return;
        }


        /*
         * ============================================================
         * RUTA NORMAL ORIGINAL
         * ============================================================
         *
         * Si NO hubo falla detectada, conservamos el comportamiento
         * que ya funcionaba.
         */
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        REVISION_FINALIZADA
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        OPCIONES_SERVICIO
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar finalización de la revisión de 3 minutos"
        );

        ReportHooks.registrarPaso(
                "Validar finalización de la revisión de 3 minutos"
        );


        // 7. Clic en Selecciona para continuar con TV / telefonía / internet
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(
                        20
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Clic en el botón Selecciona final de servicios de ayuda"
        );

        ReportHooks.registrarPaso(
                "Clic en el botón Selecciona final de servicios de ayuda"
        );
    }


    /*
     * Detectar redirección a atención humana.
     */
    private boolean hayRedireccionAsesor(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(
                        REDIRECCION_ASESOR
                )
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto(
                        ESPERA_ASESOR
                )
                .answeredBy(actor);
    }


    /*
     * Detectar la nueva respuesta observada:
     * "Identificamos una falla en tu servicio".
     */
    private boolean hayFallaDetectada(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(
                        FALLA_DETECTADA
                )
                .answeredBy(actor);
    }


    public static Performable estadoServiciosHogar() {

        return instrumented(
                EstadoServiciosHogar.class
        );
    }
}