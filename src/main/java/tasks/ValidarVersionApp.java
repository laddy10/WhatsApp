package tasks;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.scroll.ScrollAndClick;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_MENU_ITEM;
import static utils.Constantes.*;

public class ValidarVersionApp implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BTN_MENU_ITEM),
                ClickElementByText.clickElementByText(AJUSTES),
                ScrollHastaTexto.conTexto("Meta"),
                ClickTextoQueContengaX.elTextoContiene(AYUDA),
                ClickTextoQueContengaX.elTextoContiene(INFO_APP),
                WaitForResponse.withText(WHATSAPP));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar Version de la App");
        ReportHooks.registrarPaso("Validar Version de la App");

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(WHATSAPP),
                ValidarTextoQueContengaX.elTextoContiene(VERSION),
                ValidarTextoQueContengaX.elTextoContiene(LICENCIAS),
                Atras.irAtras(),
                Atras.irAtras(),
                Atras.irAtras()
        );
    }

    public static Performable validarVersionApp() {
        return instrumented(ValidarVersionApp.class);
    }
}