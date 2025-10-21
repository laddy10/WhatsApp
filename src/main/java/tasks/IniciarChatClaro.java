package tasks;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.Scroll;
import interactions.scroll.ScrollInicio;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;
import utils.TestDataProvider;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class IniciarChatClaro implements Task {

    private final User user = TestDataProvider.getRealUser();
    private static final int MAX_REINTENTOS = 3;


    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean chatIniciadoCorrectamente = false;
        int intentos = 0;

        while (!chatIniciadoCorrectamente && intentos < MAX_REINTENTOS) {
            intentos++;

            try {
                // Enviar saludo inicial
                actor.attemptsTo(
                        Enter.theValue(user.getSaludo()).into(TXT_ENVIAR_MENSAJE),
                        Click.on(BTN_ENVIAR),
                        WaitFor.aTime(3000) // Esperar respuesta del sistema
                );

                // Verificar si aparece el mensaje de actualización del sistema
                List<WebElementFacade> mensajeActualizacion = LBL_MENSAJE_ACTUALIZACION.resolveAllFor(actor);

                if (!mensajeActualizacion.isEmpty()) {
                    CapturaDePantallaMovil.tomarCapturaPantalla("Mensaje de actualización del sistema detectado - Intento " + intentos);
                    ReportHooks.registrarPaso("Sistema detectó mensaje de actualización - Haciendo clic en 'No' - Intento " + intentos);

                    // Hacer clic en "No"
                    actor.attemptsTo(
                            Click.on(BTN_NO_ACTUALIZACION),
                            WaitFor.aTime(2000)
                    );

                    // Si no es el último intento, continuar el bucle para reintentar
                    if (intentos < MAX_REINTENTOS) {
                        CapturaDePantallaMovil.tomarCapturaPantalla("Reintentando envío de saludo después de rechazar actualización");
                        ReportHooks.registrarPaso("Reintentando envío de saludo - Intento " + intentos);
                        continue;
                    }
                } else {
                    // No hay mensaje de actualización, proceder normalmente
                    actor.attemptsTo(
                            WaitForTextContains.withAnyTextContains(
                                    SALUDO, SALUDO_PARA_AYUDARTE, INGRESAR_NUMERO_OPCION, INGRESAR_OPCION_VALIDA, NO_ENTENDI_TU_MENSAJE, MENU_PRINCIPAL, OPCIONES_MOSTRADAS_ANTERIORMENTE),
                            ValidarTextoErrorYLimpiarChat.validarYLimpiar(),
                            WaitForTextContains.withAnyTextContains(SALUDO, SALUDO_PARA_AYUDARTE),
                            ScrollInicio.scrollUnaVista()
                    );

                    CapturaDePantallaMovil.tomarCapturaPantalla("Iniciar el chat con Claro Colombia");
                    ReportHooks.registrarPaso("Iniciar el chat con Claro Colombia");

                    actor.attemptsTo(
                            //ValidarTextoQueContengaX.elTextoContiene(SALUDO),
                            //ValidarTextoQueContengaX.elTextoContiene(GESTIONAR),
                            ValidarTextoQueContengaX.elTextoContiene(LINEAS_POSTPAGO),
                            ValidarTextoQueContengaX.elTextoContiene(LINEAS_PREPAGO),
                            ValidarTextoQueContengaX.elTextoContiene(CUENTA),
                            Scroll.scrollUnaVista()
                    );

                    CapturaDePantallaMovil.tomarCapturaPantalla("Validar saludo");
                    ReportHooks.registrarPaso("Validar saludo");

                    chatIniciadoCorrectamente = true;
                }

            } catch (Exception e) {
                if (intentos == MAX_REINTENTOS) {
                    CapturaDePantallaMovil.tomarCapturaPantalla("Error al iniciar chat después de " + MAX_REINTENTOS + " intentos");
                    ReportHooks.registrarPaso("ERROR: Falló el inicio del chat después de " + MAX_REINTENTOS + " intentos");
                    throw new RuntimeException("No se pudo iniciar el chat correctamente después de " + MAX_REINTENTOS + " intentos. Error: " + e.getMessage());
                }

                CapturaDePantallaMovil.tomarCapturaPantalla("Error en intento " + intentos + " - Reintentando");
                ReportHooks.registrarPaso("Error en intento " + intentos + " - Reintentando: " + e.getMessage());

                // Esperar antes del siguiente intento
                actor.attemptsTo(WaitFor.aTime(2000));
            }
        }

        if (!chatIniciadoCorrectamente) {
            throw new RuntimeException("No se pudo iniciar el chat correctamente después de " + MAX_REINTENTOS + " intentos");
        }
    }

    public static Performable iniciarChatClaro() {
        return instrumented(IniciarChatClaro.class);
    }
}