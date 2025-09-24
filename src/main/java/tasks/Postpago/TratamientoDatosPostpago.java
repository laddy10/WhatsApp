package tasks.Postpago;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.BTN_SI_AUTORIZO;
import static userinterfaces.WhatsAppPostpagoPage.LINK_TRATAMIENTO_DATOS;
import static utils.Constantes.*;

public class TratamientoDatosPostpago implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        List<WebElementFacade> linktratamientodatos = LINK_TRATAMIENTO_DATOS.resolveAllFor(actor);
        if (!linktratamientodatos.isEmpty()) {

            CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar linea de consulta y Validar politica de tratamientos de datos");
            ReportHooks.registrarPaso("Seleccionar linea de consulta y Validar politica de tratamientos de datos");

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(POLITICA_TRATAMIENTO),
                    ValidarTextoQueContengaX.elTextoContiene(URL_TRATAMIENTO_INFORMACION),
                    ValidarTextoQueContengaX.elTextoContiene(URL_PORTAL_CLARO));

            UtilidadesAndroid.abrirLinkEnNavegador(URL_TRATAMIENTO_INFORMACION);

            actor.attemptsTo(
                    WaitForResponse.withText(LEY));

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(LEY),
                    ValidarTextoQueContengaX.elTextoContiene(CIRCULAR_SIC));

            CapturaDePantallaMovil.tomarCapturaPantalla("Redirección URL Circular Superintendencia");
            ReportHooks.registrarPaso("Redirección URL Circular Superintendencia");

            actor.attemptsTo(
                    Atras.irAtras());

            List<WebElementFacade> lblvistaprevia = LBL_VISTA_PREVIA.resolveAllFor(actor);
            if (!lblvistaprevia.isEmpty()) {
                actor.attemptsTo(
                        Atras.irAtras(),
                        WaitForResponse.withText(URL_PORTAL_CLARO));
            }

            UtilidadesAndroid.abrirLinkEnNavegador(URL_PORTAL_CLARO);

            actor.attemptsTo(
                    WaitForResponse.withText(PERSONAS),
                    ValidarTexto.validarTexto(PERSONAS),
                    ValidarTexto.validarTexto(MAS_DE_CLARO),
                    ValidarTexto.validarTexto(AUTOGESTION_WHATSAPP));

            CapturaDePantallaMovil.tomarCapturaPantalla("Redirección URL Autogestión Claro WhatsApp");
            ReportHooks.registrarPaso("Redirección URL Autogestión Claro WhatsApp");

            actor.attemptsTo(
                    Atras.irAtras());

            List<WebElementFacade> btnsi = BTN_SI2.resolveAllFor(actor);
            List<WebElementFacade> btnsiautorizo = BTN_SI_AUTORIZO.resolveAllFor(actor);

            if (!btnsi.isEmpty()) {
                actor.attemptsTo(
                        Click.on(BTN_SI2));

            } else if (!btnsiautorizo.isEmpty()) {
                actor.attemptsTo(
                        Click.on(BTN_SI_AUTORIZO));

            } else {
                actor.attemptsTo(
                        Click.on(BTN_SI));
            }

            actor.attemptsTo(
                    WaitForResponse.withAnyText(
                            VER_MENU_PREPAGO, MENU_PRINCIPAL));
        }
    }

    public static Performable tratamientoDatosPostpago() {
        return instrumented(TratamientoDatosPostpago.class);
    }
}