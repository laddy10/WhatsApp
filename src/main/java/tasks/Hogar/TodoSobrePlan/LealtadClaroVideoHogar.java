package tasks.Hogar.TodoSobrePlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class LealtadClaroVideoHogar implements Task {

    private static final String ERROR_CLARO_VIDEO =
            "no podemos realizar la acción solicitada";

    private static final String BTN_SALIR_CLARO_VIDEO =
            "SALIR";


    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Seleccionar "Tu lealtad merece más" y enviar
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        TU_LEALTAD_MERECE_MAS
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar Tu lealtad merece más hogar"
        );

        ReportHooks.registrarPaso(
                "Seleccionar Tu lealtad merece más hogar"
        );

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(3000),

                WaitForTextContains.withAnyTextContains(
                        TEXTO_GRACIAS_POR_PREFERIRNOS
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje gracias por preferirnos hogar"
        );

        ReportHooks.registrarPaso(
                "Validar mensaje gracias por preferirnos hogar"
        );


        // 2. Esperar y hacer click en Selecciona
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),

                ValidarTextoQueContengaX.elTextoContiene(
                        CLARO_VIDEO
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        CLARO_DRIVE
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        CLARO_CLUB
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar opciones del menú Tu lealtad merece más"
        );

        ReportHooks.registrarPaso(
                "Validar opciones del menú Tu lealtad merece más"
        );


        // 3. Seleccionar Claro Video
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        CLARO_VIDEO
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar Claro Video"
        );

        ReportHooks.registrarPaso(
                "Seleccionar Claro Video"
        );

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(3000),

                WaitForTextContains.withAnyTextContains(
                        PELIS_SERIES_TV,
                        URL_CLARO_VIDEO,
                        DESCARGA_LA_APP
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje y URL de Claro Video hogar"
        );

        ReportHooks.registrarPaso(
                "Validar mensaje y URL de Claro Video hogar"
        );


        // 4. Validación opcional del texto descriptivo
        AndroidObject androidObject =
                new AndroidObject();

        if (androidObject.textoContiene(
                actor,
                PELICULAS_TODOS_LOS_GENEROS
        )) {

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(
                            PELICULAS_TODOS_LOS_GENEROS
                    )
            );
        }


        // 5. Abrir URL de Claro Video
        UtilidadesAndroid.abrirLinkEnNavegador(
                URL_CLARO_VIDEO
        );

        actor.attemptsTo(
                WaitFor.aTime(5000)
        );


        // 6. Registrar package/activity
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


        boolean popupControlado =
                manejarPopupClaroVideo(actor);

        boolean pantallaNegra = false;
        boolean direccionamientoConfirmado = false;


        // 7. Revisar Bitly si todavía no apareció popup
        if (!popupControlado) {

            manejarVistaPreviaBitly(actor);

            actor.attemptsTo(
                    WaitFor.aTime(5000)
            );

            popupControlado =
                    manejarPopupClaroVideo(actor);
        }


        // 8. Si ya apareció popup, se acepta el direccionamiento
        if (popupControlado) {

            ReportHooks.registrarPaso(
                    "Direccionamiento a Claro Video validado "
                            + "mediante popup controlado."
            );

        } else {

            // 9. Revisar pantalla negra
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
                 * 10. Primer intento de validar clarovideo.com
                 */
                try {

                    UtilidadesAndroid.esperarRedireccionamientoWeb(
                            actor,
                            "clarovideo.com",
                            30
                    );

                    direccionamientoConfirmado = true;

                } catch (RuntimeException e) {

                    /*
                     * IMPORTANTE:
                     * Después de esperar la URL revisamos nuevamente
                     * si durante esos 30 segundos apareció el popup.
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

                            /*
                             * 11. Segundo y último intento
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


                            // Revisar popup
                            popupControlado =
                                    manejarPopupClaroVideo(actor);


                            // Revisar Bitly si todavía no hay popup
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
                                     * AQUÍ ESTABA EL PROBLEMA.
                                     *
                                     * Antes esta última espera podía lanzar
                                     * RuntimeException directamente sin volver
                                     * a revisar el popup.
                                     */
                                    try {

                                        UtilidadesAndroid.esperarRedireccionamientoWeb(
                                                actor,
                                                "clarovideo.com",
                                                30
                                        );

                                        direccionamientoConfirmado = true;

                                    } catch (RuntimeException segundoError) {

                                        /*
                                         * El popup pudo aparecer mientras
                                         * esperábamos esos últimos 30 segundos.
                                         */
                                        popupControlado =
                                                manejarPopupClaroVideo(actor);

                                        if (popupControlado) {

                                            ReportHooks.registrarPaso(
                                                    "En el segundo intento apareció "
                                                            + "el popup controlado de Claro Video."
                                            );

                                        } else {

                                            pantallaNegra =
                                                    UtilidadesAndroid.chromeActivoSinContenido(
                                                            actor
                                                    );

                                            if (pantallaNegra) {

                                                String actividadFinal =
                                                        UtilidadesAndroid.obtenerActividadActual(
                                                                actor
                                                        );

                                                CapturaDePantallaMovil.tomarCapturaPantalla(
                                                        "Claro Video abierto con pantalla negra segundo intento"
                                                );

                                                ReportHooks.registrarPaso(
                                                        "En el segundo intento Chrome quedó "
                                                                + "sin contenido visible. "
                                                                + "Se acepta como direccionamiento válido. "
                                                                + "Actividad: "
                                                                + actividadFinal
                                                );

                                            } else {

                                                /*
                                                 * Ya revisamos todo:
                                                 *
                                                 * - URL
                                                 * - popup
                                                 * - pantalla negra
                                                 *
                                                 * Ahora sí es un fallo real.
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


        // 12. Validar PREMIUM solo si navegó normalmente
        if (direccionamientoConfirmado
                && !popupControlado
                && !pantallaNegra) {

            actor.attemptsTo(
                    WaitFor.aTime(9000),

                    WaitForTextContains.withTextContains(
                            PREMIUM,
                            90
                    )
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Validar redirección a Claro Video"
            );

            ReportHooks.registrarPaso(
                    "Validar redirección a Claro Video"
            );
        }


        /*
         * 13. Regresar a WhatsApp y cerrar conversación
         */
        actor.attemptsTo(
                Atras.irAtras(),
                WaitFor.aTime(2000),
                SalirConversacion.salir()
        );
    }


    /**
     * Manejo no bloqueante de Bitly.
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
     * Detecta el popup conocido de Claro Video.
     */
    private <T extends Actor> boolean manejarPopupClaroVideo(
            T actor) {

        /*
         * Usamos la misma lectura que está funcionando
         * actualmente en Postpago.
         *
         * LBL_MENSAJES realmente resuelve todos los
         * android.widget.TextView visibles, por lo que
         * también permite leer este popup externo.
         */
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
                "Popup controlado Claro Video"
        );

        ReportHooks.registrarPaso(
                "Se detectó popup controlado de Claro Video: "
                        + "'no podemos realizar la acción solicitada'."
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


    public static Performable lealtadClaroVideoHogar() {

        return instrumented(
                LealtadClaroVideoHogar.class
        );
    }
}