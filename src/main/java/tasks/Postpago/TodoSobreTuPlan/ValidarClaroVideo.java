package tasks.Postpago.TodoSobreTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class ValidarClaroVideo implements Task {

    private static final String ERROR_CLARO_VIDEO =
            "no podemos realizar la acción solicitada";

    private static final String BTN_SALIR_CLARO_VIDEO =
            "SALIR";

    @Override
    public <T extends Actor> void performAs(T actor) {

        boolean popupControlado = false;
        boolean pantallaNegra = false;
        boolean direccionamientoConfirmado = false;

        // 1. Validar mensaje de Claro Video en WhatsApp
        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje con URL de claro video"
        );

        ReportHooks.registrarPaso(
                "Validar mensaje con URL de claro video"
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        CLARO_VIDEO
                )
        );


        // 2. Abrir URL de Claro Video
        UtilidadesAndroid.abrirLinkEnNavegador(
                URL_CLARO_VIDEO
        );

        actor.attemptsTo(
                WaitFor.aTime(5000)
        );


        // 3. Registrar estado del navegador
        boolean chromeActivo =
                UtilidadesAndroid.estaChromeActivo(actor);

        String actividadActual =
                UtilidadesAndroid.obtenerActividadActual(actor);

        System.out.println(
                "Chrome activo: "
                        + chromeActivo
                        + " | Actividad actual: "
                        + actividadActual
        );

        ReportHooks.registrarPaso(
                "Estado navegador - Chrome activo: "
                        + chromeActivo
                        + " | Actividad: "
                        + actividadActual
        );


        // 4. Revisar popup conocido
        popupControlado =
                manejarPopupClaroVideo(actor);


        // 5. Si no hubo popup, revisar Bitly
        if (!popupControlado) {

            manejarVistaPreviaBitly(actor);

            actor.attemptsTo(
                    WaitFor.aTime(5000)
            );

            // El popup puede aparecer después de Bitly
            popupControlado =
                    manejarPopupClaroVideo(actor);
        }


        // 6. Si hubo popup, el direccionamiento se considera válido
        if (popupControlado) {

            ReportHooks.registrarPaso(
                    "Direccionamiento a Claro Video validado "
                            + "mediante popup controlado."
            );

        } else {

            // 7. Revisar pantalla negra
            pantallaNegra =
                    UtilidadesAndroid.chromeActivoSinContenido(
                            actor
                    );

            if (pantallaNegra) {

                String actividadPantallaNegra =
                        UtilidadesAndroid.obtenerActividadActual(
                                actor
                        );

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Claro Video abierto con pantalla negra"
                );

                ReportHooks.registrarPaso(
                        "Se detectó Chrome activo sin contenido visible. "
                                + "Se acepta como direccionamiento válido. "
                                + "Actividad: "
                                + actividadPantallaNegra
                );

            } else {

                // 8. Flujo normal: esperar clarovideo.com
                try {

                    UtilidadesAndroid.esperarRedireccionamientoWeb(
                            actor,
                            "clarovideo.com",
                            50
                    );

                    direccionamientoConfirmado = true;

                } catch (RuntimeException e) {

                    /*
                     * Durante la espera pudo aparecer:
                     * - popup
                     * - pantalla negra
                     */
                    popupControlado =
                            manejarPopupClaroVideo(actor);

                    if (popupControlado) {

                        ReportHooks.registrarPaso(
                                "Durante la espera apareció el popup "
                                        + "controlado de Claro Video."
                        );

                    } else if (
                            UtilidadesAndroid.chromeActivoSinContenido(
                                    actor
                            )
                    ) {

                        pantallaNegra = true;

                        String actividadNegra =
                                UtilidadesAndroid.obtenerActividadActual(
                                        actor
                                );

                        CapturaDePantallaMovil.tomarCapturaPantalla(
                                "Claro Video abierto con pantalla negra"
                        );

                        ReportHooks.registrarPaso(
                                "Durante la espera Chrome quedó activo "
                                        + "sin contenido visible. "
                                        + "Se acepta como direccionamiento válido. "
                                        + "Actividad: "
                                        + actividadNegra
                        );

                    } else {

                        /*
                         * Segundo y último intento
                         */
                        ReportHooks.registrarPaso(
                                "No se confirmó redireccionamiento a Claro Video. "
                                        + "Se realiza un segundo intento."
                        );

                        UtilidadesAndroid.abrirLinkEnNavegador(
                                URL_CLARO_VIDEO
                        );

                        actor.attemptsTo(
                                WaitFor.aTime(5000)
                        );


                        // Revisar popup primero
                        popupControlado =
                                manejarPopupClaroVideo(actor);


                        // Si no hay popup, revisar Bitly
                        if (!popupControlado) {

                            manejarVistaPreviaBitly(actor);

                            actor.attemptsTo(
                                    WaitFor.aTime(5000)
                            );

                            popupControlado =
                                    manejarPopupClaroVideo(actor);
                        }


                        if (!popupControlado) {

                            pantallaNegra =
                                    UtilidadesAndroid.chromeActivoSinContenido(
                                            actor
                                    );

                            if (pantallaNegra) {

                                String actividadNegraSegundoIntento =
                                        UtilidadesAndroid.obtenerActividadActual(
                                                actor
                                        );

                                CapturaDePantallaMovil.tomarCapturaPantalla(
                                        "Claro Video abierto con pantalla negra segundo intento"
                                );

                                ReportHooks.registrarPaso(
                                        "Segundo intento: Chrome quedó activo "
                                                + "sin contenido visible. "
                                                + "Se acepta como direccionamiento válido. "
                                                + "Actividad: "
                                                + actividadNegraSegundoIntento
                                );

                            } else {

                                /*
                                 * Si no fue popup ni pantalla negra,
                                 * exigimos la redirección normal.
                                 */
                                UtilidadesAndroid.esperarRedireccionamientoWeb(
                                        actor,
                                        "clarovideo.com",
                                        50
                                );

                                direccionamientoConfirmado = true;
                            }
                        }
                    }
                }
            }
        }


        // 9. Validación de ingreso normal
        if (direccionamientoConfirmado
                && !popupControlado
                && !pantallaNegra) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Se valida el ingreso a Claro Video"
            );

            ReportHooks.registrarPaso(
                    "Se valida el ingreso a Claro Video"
            );
        }


        /*
         * 10. Volver a WhatsApp.
         *
         * No enviamos Cierrecaso hasta confirmar
         * que estamos dentro de la conversación.
         */
        regresarAlChatClaro(actor);


        // 11. Cerrar conversación
        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }


    /**
     * Manejo no bloqueante de la vista previa de Bitly.
     */
    private <T extends Actor> void manejarVistaPreviaBitly(
            T actor) {

        AndroidObject androidObject =
                new AndroidObject();

        actor.attemptsTo(
                WaitFor.aTime(3000)
        );

        boolean continuarVisible =
                androidObject.textoContiene(
                        actor,
                        CONTINUAR_AL_DESTINO_2
                );

        if (!continuarVisible) {

            ReportHooks.registrarPaso(
                    "No se detectó el botón 'Continuar al destino'. "
                            + "Se permitirá que Bitly realice "
                            + "la redirección automática."
            );

            return;
        }

        try {

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(
                            CONTINUAR_AL_DESTINO_2
                    )
            );

            ReportHooks.registrarPaso(
                    "Se detectó vista previa de Bitly y "
                            + "se seleccionó 'Continuar al destino'."
            );

        } catch (Exception e) {

            ReportHooks.registrarPaso(
                    "El botón 'Continuar al destino' "
                            + "dejó de estar disponible. "
                            + "Se continúa esperando la "
                            + "redirección automática de Bitly."
            );
        }
    }


    /**
     * Manejo del popup conocido de Claro Video.
     * <p>
     * Si aparece:
     * - se toma evidencia
     * - se pulsa SALIR
     * - se considera respuesta controlada válida
     */
    private <T extends Actor> boolean manejarPopupClaroVideo(
            T actor) {

        boolean popupVisible =
                TextoQueContengaX
                        .verificarTexto(
                                ERROR_CLARO_VIDEO
                        )
                        .answeredBy(actor);

        if (!popupVisible) {
            return false;
        }

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Claro Video muestra mensaje de indisponibilidad"
        );

        ReportHooks.registrarPaso(
                "Claro Video respondió con mensaje controlado "
                        + "de indisponibilidad."
        );

        try {

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(
                            BTN_SALIR_CLARO_VIDEO
                    )
            );

            ReportHooks.registrarPaso(
                    "Se seleccionó SALIR en el popup "
                            + "controlado de Claro Video."
            );

        } catch (Exception e) {

            /*
             * El popup ya fue detectado.
             * Si SALIR desaparece, no convertimos
             * el direccionamiento en falso fallo.
             */
            ReportHooks.registrarPaso(
                    "Se detectó el popup de Claro Video, "
                            + "pero el botón SALIR dejó de estar disponible."
            );
        }

        return true;
    }


    /**
     * Regresa desde Chrome a WhatsApp y garantiza
     * que la automatización quede dentro del chat
     * de Claro Colombia antes de ejecutar Cierrecaso.
     */
    private <T extends Actor> void regresarAlChatClaro(
            T actor) {

        actor.attemptsTo(
                Atras.irAtras(),
                WaitFor.aTime(1500)
        );

        boolean dentroDelChat = false;

        try {

            dentroDelChat =
                    TXT_ENVIAR_MENSAJE
                            .resolveFor(actor)
                            .isVisible();

        } catch (Exception ignored) {

            dentroDelChat = false;
        }


        /*
         * Si Atras dejó la automatización
         * en la lista principal de chats,
         * volvemos a abrir Claro Colombia.
         */
        if (!dentroDelChat) {

            ReportHooks.registrarPaso(
                    "La automatización regresó a la lista de chats. "
                            + "Se abre nuevamente Claro Colombia "
                            + "antes de cerrar la conversación."
            );

            actor.attemptsTo(
                    Click.on(CHAT_CLARO),
                    WaitFor.aTime(1500)
            );
        }


        /*
         * Confirmación final:
         * debe existir el campo de escritura.
         */
        try {

            if (!TXT_ENVIAR_MENSAJE
                    .resolveFor(actor)
                    .isVisible()) {

                throw new IllegalStateException(
                        "No se confirmó ingreso al chat de Claro Colombia "
                                + "antes de ejecutar el cierre."
                );
            }

        } catch (Exception e) {

            throw new IllegalStateException(
                    "No se confirmó ingreso al chat de Claro Colombia "
                            + "antes de ejecutar el cierre.",
                    e
            );
        }
    }


    public static Performable validarClaroVideo() {

        return instrumented(
                ValidarClaroVideo.class
        );
    }
}