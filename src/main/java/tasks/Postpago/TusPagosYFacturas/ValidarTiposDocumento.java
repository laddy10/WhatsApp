package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.SELECT_TIPO_DOCUMENTO;
import static utils.ConstantesPost.*;

public class ValidarTiposDocumento implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Array con todos los tipos de documento a validar
        String[][] tiposDocumento = {
                {CEDULA_CIUDADANIA, "C.C. (Cédula de Ciudadanía)"},
                {CEDULA_EXTRANJERIA, "C.E. (Cédula de Extranjería)"},
                {NIT_NUMERO_IDENTIFICACION, "NIT (Número Identificación Tributaria)"},
                {TARJETA_IDENTIDAD, "T.I. (Tarjeta de Identidad)"},
                {PASAPORTE, "PP (Pasaporte)"},
                {NUMERO_MOVIL_CEL, "CEL (Número Móvil)"},
                {REGISTRO_CIVIL, "R.C. (Registro Civil)"},
                {DOCUMENTO_IDENTIFICACION_EXTRANJERO, "D.E. (Documento de Identificación Extranjero)"},
                {CARNET_DIPLOMATICO, "C.D. (Carnet Diplomatico)"},
                {TARJETA_EXTRANJERIA, "T.E. (Tarjeta de Extranjeria)"},
                {NIT_EXTRANJERIA, "N.E. (NIT de Extranjeria)"}
        };

        for (String[] tipoDoc : tiposDocumento) {
            try {
                actor.attemptsTo(
                        Click.on(SELECT_TIPO_DOCUMENTO),
                        WaitFor.aTime(2000),
                        ClickTextoQueContengaX.elTextoContiene(tipoDoc[1]),
                        WaitFor.aTime(1000)
                );

                CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar " + tipoDoc[1]);
                ReportHooks.registrarPaso("Seleccionar " + tipoDoc[1]);

            } catch (Exception e) {
                CapturaDePantallaMovil.tomarCapturaPantalla("Error al seleccionar " + tipoDoc[1]);
                ReportHooks.registrarPaso("ERROR: No se pudo seleccionar " + tipoDoc[1] + " - " + e.getMessage());
            }
        }

        // Volver a seleccionar C.C. por defecto para continuar el flujo
        actor.attemptsTo(
                Click.on(SELECT_TIPO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(CEDULA_CIUDADANIA)

                );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación completa - Cédula de Ciudadanía seleccionada");
        ReportHooks.registrarPaso("Validación de tipos de documento completada");
    }

    public static Performable validarTiposDocumento() {
        return instrumented(ValidarTiposDocumento.class);
    }
}