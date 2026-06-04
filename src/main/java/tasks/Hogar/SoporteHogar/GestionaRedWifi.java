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
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;

public class GestionaRedWifi implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // 1. Seleccionar "Gestiona tu red Wi-Fi" en el menú desplegable
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene("Gestiona tu red Wi-Fi")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Gestiona tu red Wi-Fi'");
        ReportHooks.registrarPaso("Seleccionar 'Gestiona tu red Wi-Fi'");

        // 2. Hacer clic en "Enviar"
        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2)
        );

        // 3. Validar el mensaje de confirmación de identidad
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(TEXTO_NECESITO_CONFIRMAR_IDENTIDAD, TEXTO_CODIGO_SEGURIDAD),
                ValidarTextoQueContengaX.elTextoContiene(TEXTO_NECESITO_CONFIRMAR_IDENTIDAD),
                ValidarTextoQueContengaX.elTextoContiene(TEXTO_CODIGO_SEGURIDAD)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje de confirmacion de identidad");
        ReportHooks.registrarPaso("Validar mensaje de confirmacion de identidad");

        // 4. Finalizar y cerrar el caso por completo con manejo personalizado para liberar el bot del loop de código
        AndroidObject and = new AndroidObject();
        int intentosCierre = 0;
        boolean resetExitoso = false;

        while (!resetExitoso && intentosCierre < 6) {
            intentosCierre++;
            
            actor.attemptsTo(
                    Enter.theValue("cierrecaso").into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR),
                    WaitFor.aTime(2000)
            );

            if (and.textoContiene(actor, "escribe la palabra Hola") || and.textoContiene(actor, "escribe la palabra")) {
                resetExitoso = true;
                break;
            }
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Conversacion finalizada totalmente y reseteada");
        ReportHooks.registrarPaso("✓ Conversacion reseteada totalmente con exito");
    }

    public static Performable gestionarRedWifi() {
        return instrumented(GestionaRedWifi.class);
    }
}
