package tasks.OtrasOpciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static tasks.CompraTusPaquetes.Constants_Paquetes.COMPRAR_ROAMING;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.ENVIAR;
import static utils.Constantes.SELECCIONA;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ComprarPaqueteRoaming implements Task {

    private final String pais;
    private final String categoria;
    private final String paquete;

    public ComprarPaqueteRoaming(String pais, String paquete) {
        this(pais, null, paquete);
    }

    public ComprarPaqueteRoaming(String pais, String categoria, String paquete) {
        this.pais = pais;
        this.categoria = categoria;
        this.paquete = paquete;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Dar clic Comprar Roaming");
        ReportHooks.registrarPaso("Dar clic Comprar Roaming");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRAR_ROAMING),
                WaitForTextContains.withTextContains(ESCRIBE_NOMBRE_PAIS),
                Enter.theValue(pais).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForTextContains.withTextContains(PAIS_ELEGIDO),
                ValidarTextoQueContengaX.elTextoContiene(PAIS_ELEGIDO),
                ValidarTextoQueContengaX.elTextoContiene(pais.toUpperCase()));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion del pais seleccionado: " + pais);
        ReportHooks.registrarPaso("Validacion del pais seleccionado: " + pais);

        actor.attemptsTo(EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20));

        AndroidObject android = new AndroidObject();

        // Si requiere seleccionar una categoría previa (ej. Brasil -> Paq. datos y minutos, EEUU -> Paquetes de datos)
        if (categoria != null && !categoria.isEmpty()) {
            actor.attemptsTo(
                    WaitForTextContains.withTextContains(categoria),
                    ClickTextoQueContengaX.elTextoContiene(categoria));

            CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar categoria: " + categoria);
            ReportHooks.registrarPaso("Seleccionar categoria: " + categoria);

            actor.attemptsTo(
                    ClickElementByText.clickElementByText(ENVIAR),
                    WaitForTextContains.withTextContains(ELIGE_TU_PAQUETE),
                    ValidarTextoQueContengaX.elTextoContiene(ELIGE_TU_PAQUETE));

            CapturaDePantallaMovil.tomarCapturaPantalla("Confirmar mensaje Elige tu paquete");
            ReportHooks.registrarPaso("Confirmar mensaje Elige tu paquete");

            actor.attemptsTo(EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20));
        }

        actor.attemptsTo(
                WaitForTextContains.withTextContains(paquete),
                ClickTextoQueContengaX.elTextoContiene(paquete));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar paquete " + paquete);
        ReportHooks.registrarPaso("Seleccionar paquete " + paquete);

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForTextContains.withAnyTextContains("Paquete " + paquete, paquete),
                ValidarTextoQueContengaX.elTextoContiene(paquete));

        CapturaDePantallaMovil.tomarCapturaPantalla("Confirmar detalle del paquete: " + paquete);
        ReportHooks.registrarPaso("Confirmar detalle del paquete: " + paquete);

        boolean esComprarPaquete = (pais != null && (
                pais.equalsIgnoreCase(JAPON) || pais.equalsIgnoreCase("japon") ||
                pais.equalsIgnoreCase(ESTADOS_UNIDOS) || pais.equalsIgnoreCase("estados unidos")
        ));
        String accionConfirmacion = esComprarPaquete ? "comprar paquete" : ELEGIR_Y_ACEPTAR;
        if (android.textoContiene(actor, "comprar paquete") || android.textoContiene(actor, SI_COMPRAR_PAQUETE)) {
            accionConfirmacion = "comprar paquete";
        } else if (android.textoContiene(actor, ELEGIR_Y_ACEPTAR)) {
            accionConfirmacion = ELEGIR_Y_ACEPTAR;
        }

        actor.attemptsTo(
                ScrollHastaTexto.conTexto(accionConfirmacion),
                ClickTextoQueContengaX.elTextoContiene(accionConfirmacion));

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(ROAMING_ACTIVO, LISTO_SELECCIONASTE, "¡Listo! Seleccionaste", "Seleccionaste el paquete"),
                ValidarTextoQueContengaX.elTextoContiene(paquete));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validacion confirmacion paquete roaming");
        ReportHooks.registrarPaso("Validacion confirmacion paquete roaming");

        if (android.textoContiene(actor, FINALIZAR_CHAT) || android.textoContiene(actor, "Finalizar")) {
            actor.attemptsTo(
                    ScrollHastaTexto.conTexto(FINALIZAR_CHAT),
                    ClickTextoQueContengaX.elTextoContiene(FINALIZAR_CHAT),
                    WaitFor.aTime(2000));
            CapturaDePantallaMovil.tomarCapturaPantalla("Boton Finalizar chat presionado");
            ReportHooks.registrarPaso("Boton Finalizar chat presionado");
        }

        actor.attemptsTo(SalirConversacion.salir());

        CapturaDePantallaMovil.tomarCapturaPantalla("Chat finalizado");
        ReportHooks.registrarPaso("Chat finalizado");
    }

    public static Performable para(String pais, String paquete) {
        return instrumented(ComprarPaqueteRoaming.class, pais, paquete);
    }

    public static Performable para(String pais, String categoria, String paquete) {
        return instrumented(ComprarPaqueteRoaming.class, pais, categoria, paquete);
    }
}
