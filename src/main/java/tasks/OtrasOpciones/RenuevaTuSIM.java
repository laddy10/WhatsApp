package tasks.OtrasOpciones;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;

public class RenuevaTuSIM implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(RENUEVA_TU_SIM),
                ValidarTexto.validarTexto(RENUEVA_TU_SIM));


        CapturaDePantallaMovil.tomarCapturaPantalla("renueva tu sim");
        ReportHooks.registrarPaso("renueva tu sim");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withAnyText(MENSAJE_ACTUALIZA_SIM)
        );
        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion mensaje boton actualizar");
        ReportHooks.registrarPaso("Validacion mensaje boton actualizar");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(QUIERO_ACTUALIZAR),
                WaitForResponse.withText("Para la actualización")
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("pasos para actualizar");
        ReportHooks.registrarPaso("pasos para actualizar");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(MENU_PRINCIPAL),
                WaitForResponse.withText("Conoce las opciones")
        );


    }

    public static Performable renuevaTuSIM() {
        return instrumented(RenuevaTuSIM.class);
    }
}
