package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.ABANDONAR_CONVERSACION;
import static utils.Constantes.SALIR;
import static utils.ConstantesPost.*;

public class ProductosFinanciados implements Task {

    private static final String MENSAJE_CAPTURA_1 = "Seleccionar 'Productos financiados'";
    private static final String MENSAJE_CAPTURA_2 = "Validar mensaje productos financiados";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar "Productos financiados"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PRODUCTOS_FINANCIADOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_1);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_1);


        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withText(FINALIZAR_CHAT)
        );


        // Esperar y validar respuesta
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(NO_EQUIPO_FINANCIADO),
                ValidarTextoQueContengaX.elTextoContiene(FAMILIA_CLARO_FINANCIAR),
                ValidarTextoQueContengaX.elTextoContiene(PRODUCTO_CON_CARGO),
                ValidarTextoQueContengaX.elTextoContiene(FACTURA_CLARO),
                ValidarTextoQueContengaX.elTextoContiene(DESCUBRE_CATALOGO),
                ValidarTextoQueContengaX.elTextoContiene(EXPLORAR_OPCIONES)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA_2);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA_2);

        actor.attemptsTo(
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );
    }

    public static Performable productosFinanciados() {
        return instrumented(ProductosFinanciados.class);
    }
}