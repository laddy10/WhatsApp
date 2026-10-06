package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_MENU_ITEM;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollGradual;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import utils.CapturaDePantallaMovil;

public class ValidarVersionApp implements Task {

    private static final String TU = "Tú";
    private static final int MAX_SCROLLS_AYUDA = 6;

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
             * Luego se aplica el scroll común hasta Ayuda.
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
             * Ajustes
             * ↓
             * Luego se aplica el mismo scroll común hasta Ayuda.
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



            actor.attemptsTo(
                    ScrollGradual.bajar(0.60)
            );



        /*
         * ============================================================
         * 3. INGRESAR A AYUDA → INFO APP
         * ============================================================
         */
        actor.attemptsTo(
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
         * 4. EVIDENCIA
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
         * 5. VALIDACIONES EXISTENTES
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
         * 6. REGRESAR A LA PANTALLA PRINCIPAL
         * ============================================================
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