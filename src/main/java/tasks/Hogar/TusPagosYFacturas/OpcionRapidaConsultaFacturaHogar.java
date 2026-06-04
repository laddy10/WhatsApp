package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.TEXTO_CODIGO_SEGURIDAD;
import static utils.Constantes.TEXTO_NECESITO_CONFIRMAR_IDENTIDAD;
import static utils.ConstantesPost.CONSULTA_TU_FACTURA;
import static utils.ConstantesPost.OPCIONES_RAPIDAS_HOGAR;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;

public class OpcionRapidaConsultaFacturaHogar implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Esperar el mensaje de opciones rápidas hogar
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(OPCIONES_RAPIDAS_HOGAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar opciones rápidas hogar");
        ReportHooks.registrarPaso("Validar opciones rápidas hogar");

        // 2. Clic en el botón de respuesta rápida "Consulta tu factura"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTA_TU_FACTURA),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opción rápida Consulta tu factura");
        ReportHooks.registrarPaso("Seleccionar opción rápida Consulta tu factura");

        // 3. Esperar el mensaje de validación de identidad
        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(TEXTO_NECESITO_CONFIRMAR_IDENTIDAD, TEXTO_CODIGO_SEGURIDAD)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje solicitud de código de seguridad");
        ReportHooks.registrarPaso("Validar mensaje solicitud de código de seguridad");
    }

    public static Performable opcionRapidaConsultaFacturaHogar() {
        return instrumented(OpcionRapidaConsultaFacturaHogar.class);
    }
}
