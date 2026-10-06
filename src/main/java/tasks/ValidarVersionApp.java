package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_MENU_ITEM;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import utils.CapturaDePantallaMovil;

public class ValidarVersionApp implements Task {

    private static final String TU = "Tú";

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * 1. Intentar primero la navegación tradicional:
         *
         * Tres puntos → Ajustes
         */
        actor.attemptsTo(
                Click.on(BTN_MENU_ITEM)
        );

        boolean ajustesVisible =
                TextoQueContengaX
                        .verificarTexto(AJUSTES)
                        .answeredBy(actor);

        if (ajustesVisible) {

            /*
             * VISTA ANTERIOR
             *
             * Tres puntos → Ajustes
             */
            ReportHooks.registrarPaso(
                    "Se detectó navegación tradicional de WhatsApp por menú Ajustes"
            );

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(
                            AJUSTES
                    )
            );

        } else {

            /*
             * VISTA NUEVA
             *
             * En el menú de tres puntos ya no aparece Ajustes.
             * Cerramos el menú y buscamos la pestaña Tú.
             */
            actor.attemptsTo(
                    Atras.irAtras()
            );

            boolean tabTuVisible =
                    TextoQueContengaX
                            .verificarTexto(TU)
                            .answeredBy(actor);

            if (!tabTuVisible) {

                throw new RuntimeException(
                        "No se encontró la opción 'Ajustes' "
                                + "en el menú ni la pestaña 'Tú' "
                                + "en la pantalla principal de WhatsApp."
                );
            }

            ReportHooks.registrarPaso(
                    "Se detectó nueva navegación de WhatsApp mediante la pestaña Tú"
            );


            /*
             * Entrar a la pestaña Tú
             */
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(
                            TU
                    )
            );


            /*
             * Dentro de Tú esperamos encontrar Ajustes.
             */
            boolean ajustesEnTu =
                    TextoQueContengaX
                            .verificarTexto(AJUSTES)
                            .answeredBy(actor);

            if (!ajustesEnTu) {

                throw new RuntimeException(
                        "Se ingresó correctamente a la pestaña 'Tú', "
                                + "pero no se encontró la opción 'Ajustes'."
                );
            }

            ReportHooks.registrarPaso(
                    "Se encontró la opción Ajustes dentro de la pestaña Tú"
            );

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(
                            AJUSTES
                    )
            );
        }


        /*
         * 2. Desde este punto ambas vistas ya están
         * dentro de Ajustes.
         *
         * Continuamos con el flujo existente.
         */
        actor.attemptsTo(
                ScrollHastaTexto.conTexto("Meta"),
                ClickTextoQueContengaX.elTextoContiene(
                        AYUDA
                ),
                ClickTextoQueContengaX.elTextoContiene(
                        INFO_APP
                ),
                WaitForResponse.withText(
                        WHATSAPP
                )
        );


        /*
         * 3. Evidencia
         */
        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar Version de la App"
        );

        ReportHooks.registrarPaso(
                "Validar Version de la App"
        );


        /*
         * 4. Validaciones existentes
         */
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        WHATSAPP
                ),
                ValidarTextoQueContengaX.elTextoContiene(
                        VERSION
                ),
                ValidarTextoQueContengaX.elTextoContiene(
                        LICENCIAS
                )
        );


        /*
         * 5. Regresar a la pantalla principal.
         *
         * Se conserva el comportamiento actual
         * de la tarea.
         */
        actor.attemptsTo(
                Atras.irAtras(),
                Atras.irAtras(),
                Atras.irAtras()
        );
    }


    public static Performable validarVersionApp() {

        return instrumented(
                ValidarVersionApp.class
        );
    }
}