package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitFor;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.Constantes.*;

public class AbrirFactura implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Buscar y hacer clic en el enlace o botón de la factura PDF
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PDF_FACTURA),
                WaitFor.aTime(2000)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en enlace de factura PDF");
        ReportHooks.registrarPaso("Clic en enlace de factura PDF");


        List<WebElementFacade> btnlectorpdfdrive = LBL_MAS.resolveAllFor(actor);
        if (!btnlectorpdfdrive.isEmpty()) {
            actor.attemptsTo(
                    Click.on(LBL_MAS),
                    Click.on(BTN_LECTOR_PDF_DRIVE)
            );

        } else {
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(SOLO_UNA_VEZ)
            );
        }

        actor.attemptsTo(
                WaitForResponse.withAnyText(ESTE_ARCHIVO_ESTA_PROTEGIDO),
                ValidarTexto.validarTexto(ESTE_ARCHIVO_ESTA_PROTEGIDO),
                Click.on(TXT_CONTRASENA_FACTURA),
                WaitFor.aTime(5000),
                Enter.theValue("99321987").into(TXT_CONTRASENA_FACTURA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Ingresar contraseña");
        ReportHooks.registrarPaso("Ingresar contraseña");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ABRIR),
                WaitFor.aTime(3000),
                ValidarTextoQueContengaX.elTextoContiene(PDF_FACTURA)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Factura validada correctamente");
        ReportHooks.registrarPaso("Factura validada correctamente");


        actor.attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );


    }

    public static Performable abrirFactura() {
        return instrumented(AbrirFactura.class);
    }
}