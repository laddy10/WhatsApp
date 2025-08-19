package tasks.CompraWhatsApp;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTexto;
import interactions.comunes.Atras;
import interactions.comunes.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constantes.*;

public class MenuCompraWhatsApp implements Task {

    private static final String MENSAJE_CAPTURA = "Validación correcta Compra por WhatsApp";

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            // Esperar y validar el elemento principal primero
            actor.attemptsTo(
                    WaitForResponse.withText(SERVICIOS_PARA_TI),

                    // Realizar todas las validaciones
                    ValidarTexto.validarTexto(SERVICIOS_PARA_TI),
                    ValidarTexto.validarTexto(POSTPAGO_PARA_TI),
                    ValidarTexto.validarTexto(CAMBIA_TU_CELULAR),
                    ValidarTexto.validarTexto(MENU_ANTERIOR)
            );

            // Capturar evidencia de validación exitosa
            CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
            ReportHooks.registrarPaso(MENSAJE_CAPTURA);

            actor.attemptsTo(
                    Atras.irAtras()
            );

        } catch (Exception e) {
            CapturaDePantallaMovil.tomarCapturaPantalla("Error_Validacion_Menu_WhatsApp");
            ReportHooks.registrarPaso("ERROR: Fallo en validación del menú - " + e.getMessage());
            throw e;
        }
    }

    public static Performable menuCompraWhatsApp() {
        return instrumented(MenuCompraWhatsApp.class);
    }
}