package tasks.OtrasOpciones;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static tasks.CompraTusPaquetes.Constants_Paquetes.*;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;


public class ComprarRoaming implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Dar clic Comprar Roaming");
        ReportHooks.registrarPaso("Dar clic Comprar Roaming");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRAR_ROAMING),
                WaitForTextContains.withTextContains(ESCRIBE_NOMBRE_PAIS),
                Enter.theValue(ESPAÑA).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForTextContains.withTextContains(PAIS_ELEGIDO),
                ValidarTextoQueContengaX.elTextoContiene(PAIS_ELEGIDO),
                ValidarTextoQueContengaX.elTextoContiene(ESPAÑA)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion del pais seleccionado");
        ReportHooks.registrarPaso("Validacion del pais seleccionado");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SELECCIONA),
                ValidarTextoQueContengaX.elTextoContiene(PQ_1),
                ValidarTextoQueContengaX.elTextoContiene(PQ_2),
                ValidarTextoQueContengaX.elTextoContiene(PQ_3),
                ValidarTextoQueContengaX.elTextoContiene(PQ_4)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion de los paquetes");
        ReportHooks.registrarPaso("Validacion de los paquetes");


        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    public static Performable comprarRoaming() {
        return instrumented(ComprarRoaming.class);
    }
}

