package tasks.OtrasOpciones;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class ValidarMenuLDI implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue("1").into(TXT_CAJA_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(MSJ_LDI_1)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("descripcion y marcacion");
        ReportHooks.registrarPaso("descripcion y marcacion");

        actor.attemptsTo(
                Click.on(LBL_MENU_ANTERIOR),
                WaitForResponse.withText(MENSAJE_LDI)
        );
        CapturaDePantallaMovil.tomarCapturaPantalla("menu opciones");
        ReportHooks.registrarPaso("menu opciones");

        actor.attemptsTo(
                Enter.theValue("2").into(TXT_CAJA_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(MSJ_LDI_2));

        CapturaDePantallaMovil.tomarCapturaPantalla("consulta de consumos");
        ReportHooks.registrarPaso("consulta de consumos");

        actor.attemptsTo(
                Click.on(LBL_MENU_ANTERIOR2),
                WaitForResponse.withText(MENSAJE_LDI)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("menu opciones ldi");
        ReportHooks.registrarPaso("menu opciones ldi");

        actor.attemptsTo(
                Enter.theValue("3").into(TXT_CAJA_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(MSJ_LDI_3)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("compra paquetes LDI");
        ReportHooks.registrarPaso("compra paquetes LDI");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(SELECCIONA),
                WaitForResponse.withText(MSJ_LDI_COMPRA),
                ClickElementByText.clickElementByText(MSJ_LDI_COMPRA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("compra paquetes LDI usa");
        ReportHooks.registrarPaso("compra paquetes LDI usa");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withText(ELIGE_COMPRA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("elige compra");
        ReportHooks.registrarPaso("elige compra");

    }

    public static Performable validarMenuLDI() {
        return instrumented(ValidarMenuLDI.class);
    }

}