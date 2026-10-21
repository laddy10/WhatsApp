package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import interactions.wait.WaitForTextContainsWithTimeout;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class FallasDatos implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * ============================================================
         * 1. SELECCIONAR DATOS
         * ============================================================
         */
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        DATOS_FALLAS
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar 'Datos'"
        );

        ReportHooks.registrarPaso(
                "Seleccionar 'Datos'"
        );


        /*
         * ============================================================
         * 2. ENVIAR SELECCIÓN Y EJECUTAR DIAGNÓSTICO
         * ============================================================
         */
        actor.attemptsTo(
                Click.on(
                        BTN_ENVIAR_2
                ),

                WaitForTextContains.withTextContains(
                        REALIZAR_DIAGNOSTICO
                ),

                ClickTextoQueContengaX.elTextoContiene(
                        REALIZAR_DIAGNOSTICO
                )
        );


        /*
         * ============================================================
         * 3. ESPERAR UNA DE LAS DOS RESPUESTAS VÁLIDAS
         * ============================================================
         *
         * RUTA NORMAL:
         * - Sistema operativo
         *
         * RUTA CON PAGO PENDIENTE:
         * - Es posible que tu servicio presente intermitencia
         *   o lentitud...
         */
        boolean resultadoDiagnostico =
                WaitForTextContainsWithTimeout.esperar(
                        58,
                        SISTEMA_OPERATIVO,
                        PAGO_FACTURA_PENDIENTE
                ).answeredBy(actor);


        if (!resultadoDiagnostico) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Sin respuesta valida despues de diagnostico Datos"
            );

            throw new IllegalStateException(
                    "No se recibió una respuesta válida después "
                            + "de iniciar el diagnóstico de Datos."
            );
        }


        /*
         * ============================================================
         * 4. RUTA ALTERNATIVA: PAGO PENDIENTE
         * ============================================================
         */
        boolean pagoPendiente =
                TextoQueContengaX
                        .verificarTexto(
                                PAGO_FACTURA_PENDIENTE
                        )
                        .answeredBy(actor);


        if (pagoPendiente) {

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(
                            PAGO_FACTURA_PENDIENTE
                    )
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Servicio con posible intermitencia por pago pendiente"
            );

            ReportHooks.registrarPaso(
                    "El bot indicó posible intermitencia o lentitud "
                            + "debido a pago pendiente. "
                            + "Se considera una respuesta válida "
                            + "para el estado actual de la cuenta."
            );

            /*
             * IMPORTANTE:
             *
             * En esta condición el bot no continúa hacia
             * Sistema operativo / Android.
             *
             * La ejecución de FallasDatos termina aquí.
             */

            actor.attemptsTo(
                    SalirConversacion.salir()
            );

            return;
        }


        /*
         * ============================================================
         * 5. RUTA NORMAL: CUENTA AL DÍA
         * ============================================================
         *
         * Desde aquí se conserva el comportamiento actual.
         */

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar 'Android'"
        );

        ReportHooks.registrarPaso(
                "Seleccionar 'Android'"
        );


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        ANDROID
                ),

                WaitForTextContains.withTextContains(
                        REVISION_SERVICIO
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        NECESARIO_CONFIGURACION
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        CONTINUAR_PASO_VIDEO
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar seguir paso a paso video"
        );

        ReportHooks.registrarPaso(
                "Validar seguir paso a paso video"
        );


        /*
         * ============================================================
         * 6. REPRODUCIR VIDEO
         * ============================================================
         */
        actor.attemptsTo(
                Click.on(
                        BTN_REPRODUCIR_VIDEO
                ),

                WaitFor.aTime(
                        6000
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Ver video"
        );

        ReportHooks.registrarPaso(
                "Ver video"
        );


        /*
         * ============================================================
         * 7. REGRESAR Y CERRAR CONVERSACIÓN
         * ============================================================
         */
        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }


    public static Performable fallasDatos() {

        return instrumented(
                FallasDatos.class
        );
    }
}