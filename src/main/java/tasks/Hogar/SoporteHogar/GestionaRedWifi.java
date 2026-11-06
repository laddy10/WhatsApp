package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import interactions.wait.WaitForTextContainsWithTimeout;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class GestionaRedWifi implements Task {

    private static final String CODIGO_INVALIDO =
            "El código ingresado no es válido";

    private static final String SUPERO_INTENTOS =
            "Superaste el número de intentos";

    private static final String RECIBIR_OTRO_CODIGO =
            "Recibir otro código";

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * ============================================================
         * 1. SELECCIONAR "GESTIONA TU RED WI-FI"
         * ============================================================
         */
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        "Gestiona tu red Wi-Fi"
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar 'Gestiona tu red Wi-Fi'"
        );

        ReportHooks.registrarPaso(
                "Seleccionar 'Gestiona tu red Wi-Fi'"
        );


        /*
         * ============================================================
         * 2. ENVIAR LA SELECCIÓN
         * ============================================================
         */
        actor.attemptsTo(
                Click.on(
                        BTN_ENVIAR_2
                )
        );


        /*
         * ============================================================
         * 3. VALIDAR SOLICITUD DE CÓDIGO DE SEGURIDAD
         * ============================================================
         */
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(
                        TEXTO_NECESITO_CONFIRMAR_IDENTIDAD,
                        TEXTO_CODIGO_SEGURIDAD
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        TEXTO_NECESITO_CONFIRMAR_IDENTIDAD
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        TEXTO_CODIGO_SEGURIDAD
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje de confirmacion de identidad"
        );

        ReportHooks.registrarPaso(
                "Validar mensaje de confirmacion de identidad"
        );


        /*
         * ============================================================
         * 4. PRIMER CIERRE DE CASO
         * ============================================================
         *
         * En este momento el bot está esperando un código
         * de seguridad de 4 dígitos.
         *
         * Por eso el primer Cierrecaso será interpretado
         * como un código inválido.
         */
        actor.attemptsTo(
                Enter.theValue(
                        "Cierrecaso"
                ).into(
                        TXT_ENVIAR_MENSAJE
                ),

                Click.on(
                        BTN_ENVIAR
                )
        );


        /*
         * Esperamos específicamente la respuesta:
         *
         * "El código ingresado no es válido"
         */
        boolean primerIntentoRespondido =
                WaitForTextContainsWithTimeout.esperar(
                        20,
                        CODIGO_INVALIDO
                ).answeredBy(actor);


        if (!primerIntentoRespondido) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "No se recibio respuesta al primer Cierrecaso"
            );

            throw new IllegalStateException(
                    "El bot no respondió con código inválido "
                            + "después del primer Cierrecaso."
            );
        }


        ReportHooks.registrarPaso(
                "Primer Cierrecaso interpretado correctamente "
                        + "como código de seguridad inválido."
        );


        /*
         * ============================================================
         * 5. SEGUNDO CIERRE DE CASO
         * ============================================================
         *
         * El segundo intento debe provocar:
         *
         * "Superaste el número de intentos"
         *
         * y habilitar:
         *
         * - Recibir otro código
         * - Menú principal
         */
        actor.attemptsTo(
                Enter.theValue(
                        "Cierrecaso"
                ).into(
                        TXT_ENVIAR_MENSAJE
                ),

                Click.on(
                        BTN_ENVIAR
                )
        );


        boolean intentosSuperados =
                WaitForTextContainsWithTimeout.esperar(
                        20,
                        SUPERO_INTENTOS,
                        RECIBIR_OTRO_CODIGO,
                        MENU_PRINCIPAL
                ).answeredBy(actor);


        if (!intentosSuperados) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "No se habilito Menu principal despues del segundo Cierrecaso"
            );

            throw new IllegalStateException(
                    "Después del segundo Cierrecaso "
                            + "no apareció el mensaje de intentos superados "
                            + "ni las opciones de recuperación."
            );
        }


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Intentos de codigo de seguridad superados"
        );

        ReportHooks.registrarPaso(
                "Se agotaron los intentos del código de seguridad."
        );


        /*
         * ============================================================
         * 6. ESPERAR ESPECÍFICAMENTE MENÚ PRINCIPAL
         * ============================================================
         *
         * No llamamos todavía a SalirConversacion.
         *
         * Primero debemos sacar al bot del estado
         * de validación de código.
         */
        boolean menuPrincipalVisible =
                WaitForTextContainsWithTimeout.esperar(
                        15,
                        MENU_PRINCIPAL
                ).answeredBy(actor);


        if (!menuPrincipalVisible) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Menu principal no disponible despues de codigo de seguridad"
            );

            throw new IllegalStateException(
                    "No apareció la opción Menú principal "
                            + "después de superar los intentos "
                            + "del código de seguridad."
            );
        }


        /*
         * ============================================================
         * 7. SELECCIONAR MENÚ PRINCIPAL
         * ============================================================
         */
        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Menu principal disponible despues de codigo de seguridad"
        );

        ReportHooks.registrarPaso(
                "Menú principal disponible después "
                        + "de superar los intentos del código."
        );


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        MENU_PRINCIPAL
                )
        );


        ReportHooks.registrarPaso(
                "Se seleccionó Menú principal para salir "
                        + "del estado de validación del código."
        );


        /*
         * ============================================================
         * 8. ESPERAR QUE EL BOT REGRESE AL FLUJO NORMAL
         * ============================================================
         *
         * Le damos un pequeño margen para que el bot procese
         * la selección de Menú principal.
         *
         * Solo después de este punto se permite el cierre normal.
         */
        actor.attemptsTo(
                WaitFor.aTime(
                        3000
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Regreso al menu principal despues de codigo de seguridad"
        );

        ReportHooks.registrarPaso(
                "Se salió correctamente del flujo "
                        + "de código de seguridad."
        );


        /*
         * ============================================================
         * 9. CIERRE NORMAL DE LA CONVERSACIÓN
         * ============================================================
         *
         * Ahora sí usamos SalirConversacion.
         *
         * En este punto Cierrecaso ya no debería ser interpretado
         * como código de seguridad.
         */
        actor.attemptsTo(
                SalirConversacion.salir()
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Conversacion finalizada Gestiona red Wi-Fi"
        );

        ReportHooks.registrarPaso(
                "Conversación finalizada después "
                        + "del flujo de código de seguridad."
        );
    }


    public static Performable gestionarRedWifi() {

        return instrumented(
                GestionaRedWifi.class
        );
    }
}