package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static userinterfaces.WhatsAppPostpagoPage.TXT_MARCAR_COMO_LEIDO;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForTextContains;

import java.util.ArrayList;
import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class IngresarCodigoHogar extends AndroidObject implements Task {

    ArrayList<Character> lista = new ArrayList<>();

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla solicitud código de verificación Hogar");
        ReportHooks.registrarPaso("Pantalla solicitud código de verificación Hogar");

        // Usar el nuevo método específico para PIN de validación
        lista = LeerPINValidacion(actor);

        // Marcar como leído si el botón está presente
        if (!Presence.of(TXT_MARCAR_COMO_LEIDO).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(Click.on(TXT_MARCAR_COMO_LEIDO));
        }

        // Cerrar notificaciones
        actor.attemptsTo(Atras.irAtras());

        StringBuilder codigoVerificacion = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            codigoVerificacion.append(lista.get(i));
        }

        actor.attemptsTo(
                Enter.theValue(codigoVerificacion.toString()).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForTextContains.withTextContains(IDENTIDAD_CONFIRMADA, 185));

    }

    public static Performable ingresarCodigoHogar() {
        return instrumented(IngresarCodigoHogar.class);
    }
}
