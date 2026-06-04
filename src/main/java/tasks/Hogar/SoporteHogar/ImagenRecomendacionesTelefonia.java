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

public class ImagenRecomendacionesTelefonia implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Click.on(IMG_RECOMENDACIONES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Imagen de recomendaciones telefonia");
        ReportHooks.registrarPaso("Imagen de recomendaciones telefonia");

        actor.attemptsTo(
                Atras.irAtras(),
                ValidarTextoQueContengaX.elTextoContiene(MENSAJE_SE_RESOLVIO_FALLA_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(SI),
                ValidarTextoQueContengaX.elTextoContiene(NO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje ¿Se resolvió la falla de tu servicio? telefonia");
        ReportHooks.registrarPaso("Validar mensaje ¿Se resolvió la falla de tu servicio? telefonia");

        actor.attemptsTo(
                Click.on(LBL_NO),
                WaitForTextContains.withAnyTextContains(CONTACTAR_ASESOR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje " + CONTACTAR_ASESOR);
        ReportHooks.registrarPaso("Validar mensaje " + CONTACTAR_ASESOR);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(COMUNICAR_ASESOR),
                Click.on(LBL_NO_2),
                ValidarTextoQueContengaX.elTextoContiene(MENSAJE_AYUDA_SOPORTE_TV)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje " + MENSAJE_AYUDA_SOPORTE_TV);
        ReportHooks.registrarPaso("Validar mensaje " + MENSAJE_AYUDA_SOPORTE_TV);

        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }

    public static Performable imagenRecomendacionesTelefonia() {
        return instrumented(ImagenRecomendacionesTelefonia.class);
    }
}
