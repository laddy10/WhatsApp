package tasks.Hogar.SoporteHogar;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class TResuelveOpcionesHogar implements Task {

    private final String opcion;
    private final String nombrePlan;
    private final String textoEspera;
    private final String[] textosValidacion;

    public TResuelveOpcionesHogar(
            String opcion, String nombrePlan, String textoEspera, String[] textosValidacion) {
        this.opcion = opcion;
        this.nombrePlan = nombrePlan;
        this.textoEspera = textoEspera;
        this.textosValidacion = textosValidacion;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        abrirMenuTResuelve(actor);
        seleccionarOpcion(actor);
        validarRespuesta(actor);
        actor.attemptsTo(SalirConversacion.salir());
    }

    private <T extends Actor> void abrirMenuTResuelve(T actor) {
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CONOCE_MEJORA_TU_PLAN));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Conoce/mejora tu plan'");
        ReportHooks.registrarPaso("Seleccionar 'Conoce/mejora tu plan'");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(TU_PLAN_ACTUAL)
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(TU_PLAN_ACTUAL),
                ValidarTextoQueContengaX.elTextoContiene(TELEVISION),
                ValidarTextoQueContengaX.elTextoContiene(TELEFONIA),
                ValidarTextoQueContengaX.elTextoContiene(PLANES_PREMIUM_HOGAR)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar informacion del plan hogar");
        ReportHooks.registrarPaso("Validar informacion del plan hogar");

        actor.attemptsTo(
                ScrollGradual.bajar(0.20),
                ClickTextoQueContengaX.elTextoContiene(EXPLORAR_OPCIONES),
                WaitForTextContains.withAnyTextContains(CONOCE_OTROS_PLANES),
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Abrir opciones de planes y servicios premium");
        ReportHooks.registrarPaso("Abrir opciones de planes y servicios premium");

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(T_RESUELVE),
                ClickTextoQueContengaX.elTextoContiene(T_RESUELVE)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar T-Resuelve");
        ReportHooks.registrarPaso("Seleccionar T-Resuelve");

        actor.attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(PLAN_DE_ASISTENCIA)
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_SOPORTE_TECNOLOGICO),
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_MULTIASISTENCIAS),
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_MASCOTAS),
                ValidarTextoQueContengaX.elTextoContiene(T_RESUELVE_REUNIONES_EN_CASA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar opciones de T-Resuelve");
        ReportHooks.registrarPaso("Validar opciones de T-Resuelve");
    }

    private <T extends Actor> void seleccionarOpcion(T actor) {
        actor.attemptsTo(
                Enter.theValue(opcion).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForTextContains.withAnyTextContains(textoEspera)
        );
    }

    private <T extends Actor> void validarRespuesta(T actor) {
        for (String texto : textosValidacion) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(texto));
        }

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar plan de T-Resuelve " + nombrePlan);
        ReportHooks.registrarPaso("Validar plan de T-Resuelve " + nombrePlan);
    }

    public static Performable opcionDos() {
        return instrumented(
                TResuelveOpcionesHogar.class,
                "2",
                "multiasistencias",
                ADQUIRIENDO_PLAN_MULTIASISTENCIAS,
                new String[] {
                        ASISTENCIA_MEDICA,
                        ASISTENCIA_BIENESTAR,
                        ASISTENCIA_HOGAR,
                        ASISTENCIA_DESEMPLEO,
                        COMPRAR_PLAN
                });
    }

    public static Performable opcionTres() {
        return instrumented(
                TResuelveOpcionesHogar.class,
                "3",
                "mascotas",
                T_RESUELVE_MASCOTAS_ACTIVO,
                new String[] {
                        DESCRIPCION_PLAN,
                        ASISTENCIA_MASCOTA,
                        ASISTENCIA_EXEQUIAL_MASCOTAS,
                        TERMINOS_CONDICIONES,
                        SOLICITAR_ASISTENCIA
                });
    }

    public static Performable opcionCuatro() {
        return instrumented(
                TResuelveOpcionesHogar.class,
                "4",
                "reuniones en casa",
                ADQUIRIENDO_PLAN_REUNIONES_CASA,
                new String[] {
                        ASISTENCIA_HOGAR_TRADICIONAL,
                        ASISTENCIA_REUNIONES_CASA,
                        COMPRAR_PLAN
                });
    }
}
