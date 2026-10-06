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
         * ============================================================
         * 1. IDENTIFICAR LA VISTA DE WHATSAPP
         * ============================================================
         *
         * VISTA NUEVA:
         * aparece la pestaña "Tú" en la barra inferior.
         *
         * VISTA ANTERIOR:
         * no aparece "Tú" y se debe ingresar por los tres puntos.
         */
        boolean vistaNuevaConTu =
                TextoQueContengaX
                        .verificarTexto(TU)
                        .answeredBy(actor);


        if (vistaNuevaConTu) {

            /*
             * ========================================================
             * VISTA NUEVA
             * ========================================================
             *
             * Tú
             * ↓
             * Scroll hasta Meta
             * ↓
             * Ayuda
             *
             * IMPORTANTE:
             * En esta vista NO existe un paso adicional por Ajustes.
             * La pestaña Tú ya corresponde a la pantalla de configuración.
             */
            ReportHooks.registrarPaso(
                    "Se detectó nueva navegación de WhatsApp mediante la pestaña Tú"
            );

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(
                            TU
                    )
            );

        } else {

            /*
             * ========================================================
             * VISTA ANTERIOR
             * ========================================================
             *
             * Tres puntos
             * ↓
             * Scroll hasta Ajustes
             * ↓
             * Ajustes
             *
             * IMPORTANTE:
             * Se conserva el scroll original para localizar Ajustes.
             */
            ReportHooks.registrarPaso(
                    "Se detectó navegación tradicional de WhatsApp"
            );

            actor.attemptsTo(
                    Click.on(BTN_MENU_ITEM),
                    ClickTextoQueContengaX.elTextoContiene(
                            AJUSTES
                    )
            );

            ReportHooks.registrarPaso(
                    "Se ingresó a Ajustes desde el menú tradicional de WhatsApp"
            );
        }


        /*
         * ============================================================
         * 2. FLUJO COMÚN PARA AMBAS VISTAS
         * ============================================================
         *
         * Vista nueva:
         * Tú → scroll hasta Meta
         *
         * Vista anterior:
         * Ajustes → scroll hasta Meta
         *
         * Desde ahí ambas continúan igual.
         */
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(
                        "Meta"
                ),

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
         * ============================================================
         * 3. EVIDENCIA
         * ============================================================
         */
        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar Version de la App"
        );

        ReportHooks.registrarPaso(
                "Validar Version de la App"
        );


        /*
         * ============================================================
         * 4. VALIDAR INFORMACIÓN DE LA APLICACIÓN
         * ============================================================
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
         * ============================================================
         * 5. REGRESAR
         * ============================================================
         *
         * Se conserva el comportamiento existente.
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