package tasks.Hogar.SoporteHogar;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static utils.ConstantesPost.*;

public class PersonalizarClaveWifi implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PERSONALIZAR_CLAVE_WIFI)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Personalizar clave WIFI");
        ReportHooks.registrarPaso("Seleccionar Personalizar clave WIFI");


        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    public static Performable personalizarClaveWifi() {
        return instrumented(PersonalizarClaveWifi.class);
    }
}
