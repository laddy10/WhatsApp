package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class AbrirFacturaHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Buscar y hacer clic en el archivo PDF de la factura
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PDF_FACTURA),
                WaitFor.aTime(3000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Clic en archivo PDF Hogar");
        ReportHooks.registrarPaso("Clic en archivo PDF Hogar");

        // Manejar el selector de aplicación (Drive o similar)
        List<WebElementFacade> btnMas = LBL_MAS.resolveAllFor(actor);
        if (!btnMas.isEmpty()) {
            actor.attemptsTo(
                    Click.on(LBL_MAS),
                    Click.on(BTN_LECTOR_PDF_DRIVE)
            );
        } else {
            actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(SOLO_UNA_VEZ));
        }

        // Ingresar la contraseña específica para Hogar
        actor.attemptsTo(
                WaitForResponse.withAnyText(ESTE_ARCHIVO_ESTA_PROTEGIDO),
                ValidarTexto.validarTexto(ESTE_ARCHIVO_ESTA_PROTEGIDO),
                Click.on(TXT_CONTRASENA_FACTURA),
                WaitFor.aTime(2000),
                Enter.theValue(CONTRASENA_HOGAR).into(TXT_CONTRASENA_FACTURA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Ingresar contraseña Hogar");
        ReportHooks.registrarPaso("Ingresar contraseña Hogar");

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ABRIR),
                WaitFor.aTime(4000),
                ValidarTextoQueContengaX.elTextoContiene(PDF_FACTURA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Factura Hogar abierta correctamente");
        ReportHooks.registrarPaso("Factura Hogar abierta correctamente");

        // Volver a WhatsApp
        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    public static Performable abrirFacturaHogar() {
        return instrumented(AbrirFacturaHogar.class);
    }
}
