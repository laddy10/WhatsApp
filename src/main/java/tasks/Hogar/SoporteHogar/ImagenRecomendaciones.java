package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppHogarPage.*;
import static utils.ConstantesPost.*;

public class ImagenRecomendaciones implements Task {


    @Override
    public <T extends Actor> void performAs(T actor) {

        Boolean diagnosticoYaResuelto =
                actor.recall(
                        EstadoServiciosHogar.ESTADO_SERVICIOS_RESUELTO
                );

        if (Boolean.TRUE.equals(diagnosticoYaResuelto)) {

            ReportHooks.registrarPaso(
                    "No se valida imagen de recomendaciones porque "
                            + "Estado de servicios ya solucionó la falla."
            );

            return;
        }

        actor.attemptsTo(
                Click.on(IMG_RECOMENDACIONES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Imagen de recomendaciones");
        ReportHooks.registrarPaso("Imagen de recomendaciones");

        actor.attemptsTo(
                Atras.irAtras(),
                ValidarTextoQueContengaX.elTextoContiene(MENSAJE_SE_RESOLVIO_FALLA_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(SI),
                ValidarTextoQueContengaX.elTextoContiene(NO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje ¿Se resolvió la falla de tu servicio?");
        ReportHooks.registrarPaso("Validar mensaje ¿Se resolvió la falla de tu servicio?");

        actor.attemptsTo(
                Click.on(LBL_SI),
                WaitForTextContains.withAnyTextContains(MENSAJE_AYUDA_SOPORTE_TV)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje" + MENSAJE_AYUDA_SOPORTE_TV);
        ReportHooks.registrarPaso("Validar mensaje" + MENSAJE_AYUDA_SOPORTE_TV);


        actor.attemptsTo(
                SalirConversacion.salir());

    }

    public static Performable imagenRecomendaciones() {
        return instrumented(ImagenRecomendaciones.class);
    }
}
