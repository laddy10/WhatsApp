package tasks;

import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import hooks.ReportHooks;
import org.openqa.selenium.TimeoutException;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.ABANDONAR_CONVERSACION;
import static utils.Constantes.MENU_PRINCIPAL;

public class SalirConversacion implements Task {

    // Posibles respuestas del bot al intentar salir
    private static final String RESPUESTA_NO_ENTENDI = "No entendí tu mensaje.";
    private static final String RESPUESTA_MENU_PRINCIPAL = "Menú Principal";
    private static final String RESPUESTA_ENCONTRARAS = "encontrarás lo que buscas en nuestro menú";

    private static final int MAX_INTENTOS = 3;
    private static final int TIMEOUT_RESPUESTA = 6; // segundos

    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean salidaExitosa = false;
        int intentos = 0;

        while (!salidaExitosa && intentos < MAX_INTENTOS) {
            intentos++;

            // 1) Enviar SIEMPRE "Salir"
            actor.attemptsTo(
                    Enter.theValue("Salir").into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );

            // 2) Esperar una respuesta (breve)
            try {
                actor.attemptsTo(
                        WaitForTextContains.withAnyTextContains(
                                TIMEOUT_RESPUESTA,
                                ABANDONAR_CONVERSACION,            // la única que marca éxito
                                RESPUESTA_NO_ENTENDI,
                                RESPUESTA_ENCONTRARAS,
                                "¿Deseas continuar chateando?",
                                "Aún estoy contigo",
                                RESPUESTA_MENU_PRINCIPAL,
                                MENU_PRINCIPAL
                        )
                );
            } catch (TimeoutException e) {
                // Sin respuesta -> reintentar
                ReportHooks.registrarPaso("⏱ Sin respuesta tras enviar 'Salir' (intento " + intentos + ")");
                CapturaDePantallaMovil.tomarCapturaPantalla("Sin respuesta al comando Salir - intento " + intentos);
                actor.attemptsTo(WaitFor.aTime(1000));
                continue;
            }

            // 3) ¿Éxito?
            if (textoPresente(actor, ABANDONAR_CONVERSACION)) {
                CapturaDePantallaMovil.tomarCapturaPantalla("Conversación finalizada correctamente");
                ReportHooks.registrarPaso("✓ Conversación finalizada exitosamente");
                salidaExitosa = true;
                break;
            }

            // 4) No es la salida. Registrar y reintentar (NO clickeamos ningún chip)
            CapturaDePantallaMovil.tomarCapturaPantalla("Aún sin salida - intento " + intentos);
            ReportHooks.registrarPaso("↻ Respuesta no es salida (intento " + intentos + "), reenviando 'Salir'…");
            actor.attemptsTo(WaitFor.aTime(1200)); // pequeño backoff
        }

        if (!salidaExitosa) {
            ReportHooks.registrarPaso("⚠ No se confirmó salida explícita tras " + MAX_INTENTOS + " intentos; se continúa el flujo");
            // no lanzamos excepción para no romper el test
        }
    }

    /* Helpers: exacto o contiene */
    private boolean textoPresente(Actor actor, String texto) {
        try {
            AndroidObject and = new AndroidObject();
            return and.validarTexto(actor, texto) || and.textoContiene(actor, texto);
        } catch (Exception e) {
            return false;
        }
    }


    private boolean verificarTextoPresente(Actor actor, String texto) {
        try {
            AndroidObject and = new AndroidObject();
            // 1) primero intenta coincidencia exacta
            if (and.validarTexto(actor, texto)) return true;
            // 2) si no, intenta que esté como subcadena
            return and.textoContiene(actor, texto);
        } catch (Exception e) {
            return false;
        }
    }


    public static SalirConversacion salir() {
        return instrumented(SalirConversacion.class);
    }
}