package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static userinterfaces.WhatsAppPage.LBL_MENSAJES;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

public class VisitasYTrasladosHogar implements Task {

    private static final String REDIRECCION_MENU =
            "También tenemos otras opciones para ti";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Primer intento
        seleccionarVisitasYTraslados(actor);

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(
                        58,
                        NO_TIENES_AGENDADAS_VISITAS,
                        REDIRECCION_MENU
                )
        );

        // Decisión basada en el mensaje más reciente del bot,
        // no en cualquier mensaje del historial.

        // Si llegó el mensaje esperado, finaliza correctamente
        if (ultimoMensajeContiene(actor, NO_TIENES_AGENDADAS_VISITAS)) {

            validarRespuestaEsperada(actor);
            return;
        }

        // Si el bot regresó al menú principal, realizar un solo reintento
        if (ultimoMensajeContiene(actor, REDIRECCION_MENU)) {

            ReportHooks.registrarPaso(
                    "El bot regresó al menú principal. Se realiza un segundo intento de Visitas y traslados."
            );

            reintentarVisitasYTraslados(actor);

            // En el segundo intento SOLO esperamos la respuesta correcta.
            // Si no aparece, WaitForTextContains genera el FAIL.
            actor.attemptsTo(
                    WaitForTextContains.withTextContains(
                            NO_TIENES_AGENDADAS_VISITAS,
                            58
                    )
            );

            validarRespuestaEsperada(actor);
        }
    }

    /**
     * Verifica el texto contra el mensaje más reciente
     * renderizado en el chat, no contra todo el historial.
     */
    private boolean ultimoMensajeContiene(Actor actor, String texto) {

        List<WebElementFacade> mensajes = LBL_MENSAJES.resolveAllFor(actor);

        if (mensajes.isEmpty()) {
            return false;
        }

        return mensajes.get(mensajes.size() - 1).getText().contains(texto);
    }

    private <T extends Actor> void seleccionarVisitasYTraslados(T actor) {

        // Abrir botón "Selecciona"
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Clic en botón Selecciona de Soporte y servicio"
        );

        ReportHooks.registrarPaso(
                "Clic en botón Selecciona de Soporte y servicio"
        );

        // Seleccionar Visitas y traslados
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VISITAS_Y_TRASLADOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar 'Visitas y traslados'"
        );

        ReportHooks.registrarPaso(
                "Seleccionar 'Visitas y traslados'"
        );

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2)
        );
    }

    private <T extends Actor> void reintentarVisitasYTraslados(T actor) {

        // 1. Volver a Menú principal
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(MENU_PRINCIPAL),
                Click.on(BTN_ENVIAR_2),

                // 2. Esperar y seleccionar Soporte y servicio
                WaitForTextContains.withTextContains(
                        "Soporte y servicio",
                        30
                ),

                ClickTextoQueContengaX.elTextoContiene(
                        "Soporte y servicio"
                ),

                Click.on(BTN_ENVIAR_2)
        );

        // 3. Abrir nuevamente Selecciona
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),

                // 4. Seleccionar nuevamente Visitas y traslados
                ClickTextoQueContengaX.elTextoContiene(
                        VISITAS_Y_TRASLADOS
                ),

                Click.on(BTN_ENVIAR_2)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Segundo intento Visitas y traslados"
        );

        ReportHooks.registrarPaso(
                "Segundo intento de Visitas y traslados"
        );
    }

    private <T extends Actor> void validarRespuestaEsperada(T actor) {

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        NO_TIENES_AGENDADAS_VISITAS
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje sin visitas técnicas agendadas"
        );

        ReportHooks.registrarPaso(
                "Validar mensaje sin visitas técnicas agendadas"
        );
    }

    public static Performable visitasYTrasladosHogar() {
        return instrumented(VisitasYTrasladosHogar.class);
    }
}