package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import questions.TextoQueContengaX;
import tasks.ManejarConversacionConAsesor;
import utils.CapturaDePantallaMovil;
import utils.EstadoAtencionHumana;

public class EstadoServiciosHogar implements Task {

    private static final String REDIRECCION_ASESOR =
            "te contactaré con un asesor";

    private static final String ESPERA_ASESOR =
            "En un momento estará contigo";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Validar que la lista de opciones contiene el texto explicativo y la opción "Estado de servicios"
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        TE_AYUDO_IDENTIFICAR_ESTADO
                )
        );

        // 2. Dar clic en el botón interactivo "Selecciona" (que viene después de Soporte y servicio)
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Clic en botón Selecciona de Soporte y servicio"
        );

        ReportHooks.registrarPaso(
                "Clic en botón Selecciona de Soporte y servicio"
        );

        // 3. Seleccionar "Estado de servicios" en el menú desplegable
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

        // 4. Hacer clic en el botón "Enviar"
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2)
        );

        /*
         * NUEVO:
         * Después de seleccionar Estado de servicios pueden pasar dos cosas:
         *
         * 1. Flujo normal de revisión de 3 minutos.
         * 2. Redirección inmediata a asesor humano.
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
         * NUEVO:
         * Si el bot redirecciona directamente a asesor,
         * reutilizamos el manejo existente de atención humana.
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
         * FLUJO NORMAL ORIGINAL
         * Desde aquí queda exactamente como estaba antes.
         */

        // 5. Validar los mensajes de revisión de 3 minutos
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

        // 6. Esperar a que se complete la revisión
        actor.attemptsTo(
                WaitFor.aTime(199000),
                WaitForTextContains.withAnyTextContains(
                        GRACIAS_POR_LA_ESPERA
                ),
                ValidarTextoQueContengaX.elTextoContiene(
                        GRACIAS_POR_LA_ESPERA
                ),
                ValidarTextoQueContengaX.elTextoContiene(
                        SERVICIOS_DE_AYUDA
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar finalización de la revisión de 3 minutos"
        );

        ReportHooks.registrarPaso(
                "Validar finalización de la revisión de 3 minutos"
        );

        // 7. Clic en el nuevo botón interactivo "Selecciona" que aparece al finalizar la revisión
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Clic en el botón Selecciona final de servicios de ayuda"
        );

        ReportHooks.registrarPaso(
                "Clic en el botón Selecciona final de servicios de ayuda"
        );
    }

    /**
     * Detecta si Estado de servicios fue redireccionado
     * directamente a atención humana.
     */
    private boolean hayRedireccionAsesor(Actor actor) {

        return TextoQueContengaX
                .verificarTexto(REDIRECCION_ASESOR)
                .answeredBy(actor)

                || TextoQueContengaX
                .verificarTexto(ESPERA_ASESOR)
                .answeredBy(actor);
    }

    public static Performable estadoServiciosHogar() {
        return instrumented(EstadoServiciosHogar.class);
    }
}