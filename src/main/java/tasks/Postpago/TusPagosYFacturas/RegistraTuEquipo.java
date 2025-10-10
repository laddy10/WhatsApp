package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.LBL_IMEI_REGISTRADO;
import static userinterfaces.WhatsAppPostpagoPage.LINK_TRATAMIENTO_DATOS;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class RegistraTuEquipo implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        List<WebElementFacade> imeiregistrado = LBL_IMEI_REGISTRADO.resolveAllFor(actor);
        if (!imeiregistrado.isEmpty()) {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(IMEI_REGISTRADO),
                    ValidarTextoQueContengaX.elTextoContiene(VOLVER_MENU_PRINCIPAL)
            );

            CapturaDePantallaMovil.tomarCapturaPantalla("Validar IMEI ya registrado");
            ReportHooks.registrarPaso("Validar IMEI ya registrado");
            
        } else {

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(DATOS_PERSONALES),
                    ValidarTextoQueContengaX.elTextoContiene(TRATAMIENTO_INFORMACION),
                    ValidarTextoQueContengaX.elTextoContiene(AUTORIZAR)
            );

            CapturaDePantallaMovil.tomarCapturaPantalla("Aceptar tratamiento de la informacion");
            ReportHooks.registrarPaso("Aceptar tratamiento de la informacion");


            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(SI_ACEPTO),
                    WaitForTextContains.withAnyTextContains(TEXTO_CODIGO_SEGURIDAD, DISPOSITIVO_REGISTRADO)
            );
        }
    }

    public static Performable registraTuEquipo() {
        return instrumented(RegistraTuEquipo.class);
    }
}