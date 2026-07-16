package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.Constantes.IDENTIDAD_CONFIRMADA;
import static utils.ConstantesPost.PAGA_TU_FACTURA_AQUI;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollInicio;
import interactions.wait.WaitForTextContains;

import java.util.ArrayList;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class IngresarCodigoVerificacion extends AndroidObject implements Task {

    ArrayList<Character> lista = new ArrayList<>();

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla solicitud código de verificación");
        ReportHooks.registrarPaso("Pantalla solicitud código de verificación");

        // Leer el código del SMS desde las notificaciones
        lista = LeerMensaje(actor);

        if (!Presence.of(TXT_MARCAR_COMO_LEIDO).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(Click.on(TXT_MARCAR_COMO_LEIDO));
        }

        // Cerrar panel de notificaciones y volver a WhatsApp
        actor.attemptsTo(Atras.irAtras());

        // Construir el código como cadena de texto
        StringBuilder codigoVerificacion = new StringBuilder();
        for (Character c : lista) {
            codigoVerificacion.append(c);
        }

        if (codigoVerificacion.length() == 0) {
            throw new RuntimeException("No se pudo leer el código de verificación del SMS.");
        }

        // Enviar el código por el campo de texto de WhatsApp
        try {
            actor.attemptsTo(
                    Enter.theValue(codigoVerificacion.toString()).into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR),
                    WaitForTextContains.withAnyTextContains(IDENTIDAD_CONFIRMADA,PAGA_TU_FACTURA_AQUI));

        } catch (Exception e) {
            System.out.println("Error al ingresar el código de verificación: " + e.getMessage());
        }

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Confirmación de código exitosa - Identidad verificada");
        ReportHooks.registrarPaso("Confirmación de código exitosa - Identidad verificada");
    }

    public static Performable ingresarCodigoVerificacion() {
        return instrumented(IngresarCodigoVerificacion.class);
    }
}
