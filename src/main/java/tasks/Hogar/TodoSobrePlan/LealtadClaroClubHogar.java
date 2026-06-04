package tasks.Hogar.TodoSobrePlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static userinterfaces.WhatsAppPostpagoPage.BTN_CLOSE;
import static userinterfaces.WhatsAppPostpagoPage.BTN_CLOSE_2;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class LealtadClaroClubHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Seleccionar "Tu lealtad merece más" y enviar
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TU_LEALTAD_MERECE_MAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Tu lealtad merece más hogar");
        ReportHooks.registrarPaso("Seleccionar Tu lealtad merece más hogar");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(3000),
                WaitForTextContains.withAnyTextContains(TEXTO_GRACIAS_POR_PREFERIRNOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje gracias por preferirnos hogar");
        ReportHooks.registrarPaso("Validar mensaje gracias por preferirnos hogar");

        // 2. Esperar botón Selecciona y validar opciones del submenú
        actor.attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_DRIVE),
                ValidarTextoQueContengaX.elTextoContiene(CLARO_CLUB)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar opciones Tu lealtad merece más");
        ReportHooks.registrarPaso("Validar opciones Tu lealtad merece más");

        // 3. Seleccionar "Claro Club" y enviar
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_CLUB)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Club");
        ReportHooks.registrarPaso("Seleccionar Claro Club");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(3000),
                WaitForTextContains.withAnyTextContains(URL_CLARO_CLUB, CLARO_CLUB_2)
        );

        // 4. Validar mensaje con URL en el chat
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLARO_CLUB_2)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje con URL de Claro Club hogar");
        ReportHooks.registrarPaso("Validar mensaje con URL de Claro Club hogar");

        // 5. Abrir la URL de Claro Club
        UtilidadesAndroid.abrirLinkEnNavegador(URL_CLARO_CLUB);

        actor.attemptsTo(WaitFor.aTime(15000));

        // 6. Cerrar popup/publicidad si aparece (botón ×)
        if (!Presence.of(BTN_CLOSE).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(Click.on(BTN_CLOSE));
            actor.attemptsTo(WaitFor.aTime(2000));
        } else if (!Presence.of(BTN_CLOSE_2).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(Click.on(BTN_CLOSE_2));
            actor.attemptsTo(WaitFor.aTime(2000));
        }

        // 7. Validar redirección a Claro Club
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(CATEGORIAS, CUPONES_DESTACADOS, INICIO)
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CATEGORIAS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar redirección a Claro Club");
        ReportHooks.registrarPaso("Validar redirección a Claro Club");

        // 8. Volver a WhatsApp y salir
        actor.attemptsTo(
                Atras.irAtras(),
                WaitFor.aTime(2000),
                SalirConversacion.salir()
        );
    }

    public static Performable lealtadClaroClubHogar() {
        return instrumented(LealtadClaroClubHogar.class);
    }
}
