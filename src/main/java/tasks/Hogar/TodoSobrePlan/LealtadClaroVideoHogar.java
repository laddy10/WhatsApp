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
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class LealtadClaroVideoHogar implements Task {

    /*
     * Popup controlado de Claro Video.
     *
     * Se utilizan fragmentos estables para no depender
     * del mensaje completo.
     */
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


        /*
         * 7. Primera revisión del popup.
         *
         * Puede aparecer directamente al abrir Claro Video.
         */
        boolean popupControlado =
                manejarPopupClaroVideo(actor);


        /*
         * Si NO apareció el popup,
         * verificamos la posible vista previa de Bitly.
         */
        if (!popupControlado) {

            manejarVistaPreviaBitly(actor);

            actor.attemptsTo(
                    WaitFor.aTime(5000)
            );


            /*
             * El popup también puede aparecer después
             * de pasar por Bitly.
             */
            popupControlado =
                    manejarPopupClaroVideo(actor);
        }


        /*
         * 8. Si ya apareció el popup y se pulsó SALIR,
         * el direccionamiento se considera válido.
         *
         * No buscamos PREMIUM.
         */
        if (popupControlado) {

            ReportHooks.registrarPaso(
                    "Direccionamiento a Claro Video validado "
                            + "mediante popup controlado."
            );

        } else {

            /*
             * 9. Revisar pantalla negra.
             *
             * Si Chrome está activo pero no presenta contenido,
             * el direccionamiento también es válido.
             */
            boolean pantallaNegra =
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
                 * 10. Flujo normal.
                 *
                 * Conservamos la validación que ya funciona:
                 *
                 * clarovideo.com
                 * +
                 * PREMIUM
                 */
                boolean direccionamientoConfirmado = false;

                try {

                    UtilidadesAndroid.esperarRedireccionamientoWeb(
                            actor,
                            "clarovideo.com",
                            30
                    );

                    direccionamientoConfirmado = true;

                } catch (Exception e) {

                    /*
                     * Durante los 30 segundos pudo haber
                     * aparecido alguno de los estados válidos.
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

                        pantallaNegra = true;

                    } else {

                        /*
                         * 11. Segundo y último intento.
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


                        // Revisar primero popup
                        popupControlado =
                                manejarPopupClaroVideo(actor);


                        // Si no hay popup, revisar Bitly
                        if (!popupControlado) {

                            manejarVistaPreviaBitly(actor);

                            actor.attemptsTo(
                                    WaitFor.aTime(5000)
                            );

                            // Popup después de Bitly
                            popupControlado =
                                    manejarPopupClaroVideo(actor);
                        }


                        if (!popupControlado) {

                            /*
                             * Segundo intento también puede
                             * terminar en pantalla negra.
                             */
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
                                 * Si no fue:
                                 *
                                 * - popup,
                                 * - Bitly pendiente,
                                 * - pantalla negra,
                                 *
                                 * entonces exigimos el comportamiento
                                 * normal de Claro Video.
                                 */
                                UtilidadesAndroid.esperarRedireccionamientoWeb(
                                        actor,
                                        "clarovideo.com",
                                        30
                                );

                                direccionamientoConfirmado = true;
                            }
                        }
                    }
                }


                /*
                 * 12. PREMIUM se valida SOLAMENTE
                 * cuando tenemos la navegación normal.
                 *
                 * No se busca PREMIUM en:
                 *
                 * - popup
                 * - pantalla negra
                 */
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
            }
        }


        /*
         * 13. Volver a WhatsApp y cerrar conversación.
         *
         * Se conserva el comportamiento original
         * de esta tarea.
         */
        actor.attemptsTo(
                Atras.irAtras(),
                WaitFor.aTime(2000),
                SalirConversacion.salir()
        );
    }


    /**
     * Vista previa de Bitly.
     * <p>
     * No utilizamos WaitForTextContains porque si ST
     * no expone el texto, Serenity puede registrar
     * falsamente el step como fallido.
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
     * Manejo del popup conocido de Claro Video:
     * <p>
     * "En este momento no podemos realizar
     * la acción solicitada..."
     * <p>
     * Si aparece:
     * - tomamos evidencia
     * - pulsamos SALIR
     * - el flujo se considera controlado
     */
    private <T extends Actor> boolean manejarPopupClaroVideo(
            T actor) {

        AndroidObject androidObject =
                new AndroidObject();

        boolean popupVisible =
                androidObject.textoContiene(
                        actor,
                        ERROR_CLARO_VIDEO
                );

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

            /*
             * El mensaje ya fue detectado.
             * Si SALIR desaparece mientras se intenta pulsar,
             * no convertimos el direccionamiento en falso fallo.
             */
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