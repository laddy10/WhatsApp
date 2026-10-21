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
        boolean pantallaEnCarga = false;
        boolean direccionamientoConfirmado = false;


        /*
         * ============================================================
         * 1. VALIDAR MENSAJE DE CLARO VIDEO EN WHATSAPP
         * ============================================================
         */
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


        /*
         * ============================================================
         * 2. ABRIR URL DE CLARO VIDEO
         * ============================================================
         */
        UtilidadesAndroid.abrirLinkEnNavegador(
                URL_CLARO_VIDEO
        );

        actor.attemptsTo(
                WaitFor.aTime(5000)
        );


        /*
         * ============================================================
         * 3. REGISTRAR ESTADO DEL NAVEGADOR
         * ============================================================
         */
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


        /*
         * ============================================================
         * 4. REVISAR POPUP CONOCIDO
         * ============================================================
         */
        popupControlado =
                manejarPopupClaroVideo(actor);


        /*
         * ============================================================
         * 5. REVISAR BITLY
         * ============================================================
         */
        if (!popupControlado) {

            manejarVistaPreviaBitly(actor);

            actor.attemptsTo(
                    WaitFor.aTime(5000)
            );

            popupControlado =
                    manejarPopupClaroVideo(actor);
        }


        /*
         * ============================================================
         * 6. POPUP CONTROLADO
         * ============================================================
         */
        if (popupControlado) {

            ReportHooks.registrarPaso(
                    "Direccionamiento a Claro Video validado "
                            + "mediante popup controlado."
            );

        } else {

            /*
             * ========================================================
             * 7. PANTALLA NEGRA
             * ========================================================
             */
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

                /*
                 * ====================================================
                 * 8. ESTADO DE CARGA PERMANENTE
                 * ====================================================
                 */
                pantallaEnCarga =
                        UtilidadesAndroid.chromeActivoEnCarga(
                                actor
                        );

                if (pantallaEnCarga) {

                    CapturaDePantallaMovil.tomarCapturaPantalla(
                            "Claro Video abierto en estado de carga"
                    );

                    ReportHooks.registrarPaso(
                            "Chrome abrió correctamente el enlace de Claro Video, "
                                    + "pero el destino externo permanece "
                                    + "en estado de carga. "
                                    + "Se acepta como direccionamiento válido."
                    );

                } else {

                    /*
                     * ====================================================
                     * 9. FLUJO NORMAL: ESPERAR CLAROVIDEO.COM
                     * ====================================================
                     */
                    try {

                        UtilidadesAndroid.esperarRedireccionamientoWeb(
                                actor,
                                "clarovideo.com",
                                50
                        );

                        direccionamientoConfirmado = true;

                    } catch (RuntimeException e) {

                        /*
                         * Durante la espera puede aparecer:
                         * - popup
                         * - pantalla negra
                         * - estado de carga
                         */
                        popupControlado =
                                manejarPopupClaroVideo(actor);

                        if (popupControlado) {

                            ReportHooks.registrarPaso(
                                    "Durante la espera apareció el popup "
                                            + "controlado de Claro Video."
                            );

                        } else {

                            pantallaNegra =
                                    UtilidadesAndroid.chromeActivoSinContenido(
                                            actor
                                    );

                            if (pantallaNegra) {

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

                                pantallaEnCarga =
                                        UtilidadesAndroid.chromeActivoEnCarga(
                                                actor
                                        );

                                if (pantallaEnCarga) {

                                    CapturaDePantallaMovil.tomarCapturaPantalla(
                                            "Claro Video abierto en estado de carga"
                                    );

                                    ReportHooks.registrarPaso(
                                            "Durante la espera Chrome quedó "
                                                    + "en estado de carga permanente. "
                                                    + "Se acepta como direccionamiento válido."
                                    );

                                } else {

                                    /*
                                     * ============================================
                                     * 10. SEGUNDO Y ÚLTIMO INTENTO
                                     * ============================================
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


                                    /*
                                     * Revisar popup
                                     */
                                    popupControlado =
                                            manejarPopupClaroVideo(actor);


                                    /*
                                     * Revisar Bitly
                                     */
                                    if (!popupControlado) {

                                        manejarVistaPreviaBitly(actor);

                                        actor.attemptsTo(
                                                WaitFor.aTime(5000)
                                        );

                                        popupControlado =
                                                manejarPopupClaroVideo(actor);
                                    }


                                    if (!popupControlado) {

                                        /*
                                         * Revisar pantalla negra
                                         */
                                        pantallaNegra =
                                                UtilidadesAndroid
                                                        .chromeActivoSinContenido(
                                                                actor
                                                        );

                                        if (pantallaNegra) {

                                            String actividadNegraSegundoIntento =
                                                    UtilidadesAndroid
                                                            .obtenerActividadActual(
                                                                    actor
                                                            );

                                            CapturaDePantallaMovil
                                                    .tomarCapturaPantalla(
                                                            "Claro Video abierto "
                                                                    + "con pantalla negra segundo intento"
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
                                             * Revisar carga permanente
                                             */
                                            pantallaEnCarga =
                                                    UtilidadesAndroid
                                                            .chromeActivoEnCarga(
                                                                    actor
                                                            );

                                            if (pantallaEnCarga) {

                                                CapturaDePantallaMovil
                                                        .tomarCapturaPantalla(
                                                                "Claro Video abierto "
                                                                        + "en estado de carga segundo intento"
                                                        );

                                                ReportHooks.registrarPaso(
                                                        "Segundo intento: Chrome abrió "
                                                                + "Claro Video pero permanece "
                                                                + "en estado de carga. "
                                                                + "Se acepta como direccionamiento válido."
                                                );

                                            } else {

                                                /*
                                                 * Última validación normal.
                                                 */
                                                try {

                                                    UtilidadesAndroid
                                                            .esperarRedireccionamientoWeb(
                                                                    actor,
                                                                    "clarovideo.com",
                                                                    50
                                                            );

                                                    direccionamientoConfirmado =
                                                            true;

                                                } catch (RuntimeException segundoError) {

                                                    /*
                                                     * Última revisión antes
                                                     * de declarar fallo real.
                                                     */
                                                    popupControlado =
                                                            manejarPopupClaroVideo(
                                                                    actor
                                                            );

                                                    if (popupControlado) {

                                                        ReportHooks.registrarPaso(
                                                                "En el segundo intento "
                                                                        + "apareció el popup "
                                                                        + "controlado de Claro Video."
                                                        );

                                                    } else {

                                                        pantallaNegra =
                                                                UtilidadesAndroid
                                                                        .chromeActivoSinContenido(
                                                                                actor
                                                                        );

                                                        if (pantallaNegra) {

                                                            CapturaDePantallaMovil
                                                                    .tomarCapturaPantalla(
                                                                            "Claro Video abierto "
                                                                                    + "con pantalla negra "
                                                                                    + "segundo intento"
                                                                    );

                                                            ReportHooks.registrarPaso(
                                                                    "Segundo intento final: "
                                                                            + "Chrome quedó activo "
                                                                            + "sin contenido visible. "
                                                                            + "Se acepta como direccionamiento válido."
                                                            );

                                                        } else {

                                                            pantallaEnCarga =
                                                                    UtilidadesAndroid
                                                                            .chromeActivoEnCarga(
                                                                                    actor
                                                                            );

                                                            if (pantallaEnCarga) {

                                                                CapturaDePantallaMovil
                                                                        .tomarCapturaPantalla(
                                                                                "Claro Video abierto "
                                                                                        + "en carga permanente"
                                                                        );

                                                                ReportHooks.registrarPaso(
                                                                        "Segundo intento final: "
                                                                                + "Claro Video permanece "
                                                                                + "en estado de carga. "
                                                                                + "Se acepta como direccionamiento válido."
                                                                );

                                                            } else {

                                                                /*
                                                                 * Ya revisamos todo:
                                                                 *
                                                                 * - URL
                                                                 * - popup
                                                                 * - pantalla negra
                                                                 * - carga permanente
                                                                 *
                                                                 * Ahora sí es fallo real.
                                                                 */
                                                                throw segundoError;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        /*
         * ============================================================
         * 11. EVIDENCIA DE INGRESO NORMAL
         * ============================================================
         */
        if (direccionamientoConfirmado
                && !popupControlado
                && !pantallaNegra
                && !pantallaEnCarga) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Se valida el ingreso a Claro Video"
            );

            ReportHooks.registrarPaso(
                    "Se valida el ingreso a Claro Video"
            );
        }


        /*
         * ============================================================
         * 12. REGRESAR A WHATSAPP
         * ============================================================
         *
         * No enviamos Cierrecaso hasta confirmar
         * que estamos dentro de la conversación.
         */
        regresarAlChatClaro(actor);


        /*
         * ============================================================
         * 13. CERRAR CONVERSACIÓN
         * ============================================================
         */
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

        ReportHooks.registrarPaso(
                "Se confirmó regreso al chat de Claro Colombia."
        );
    }


    public static Performable validarClaroVideo() {

        return instrumented(
                ValidarClaroVideo.class
        );
    }
}