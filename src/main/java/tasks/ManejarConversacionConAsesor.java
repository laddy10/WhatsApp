package tasks;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContainsWithTimeout;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.TextoQueContengaX;
import utils.CapturaDePantallaMovil;
import utils.EstadoAtencionHumana;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.MAS;
import static utils.Constantes.VACIAR_CHAT;

public class ManejarConversacionConAsesor implements Task {

    private static final int TIMEOUT_ASIGNACION_ASESOR =
            Integer.getInteger("whatsapp.asesor.asignacion.timeout.seconds", 1800);
    private static final int TIMEOUT_CIERRE_ASESOR =
            Integer.getInteger("whatsapp.asesor.cierre.timeout.seconds", 1800);
    private static final String MENSAJE_FINALIZACION = "Por favor finalizar interacción, es una prueba";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EstadoAtencionHumana.marcarEnCola();

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Conversacion escalada a asesor humano"
        );

        ReportHooks.registrarPaso("Escalamiento a asesor detectado");
        ReportHooks.registrarPaso(
                "Marca persistente de asesor pendiente: " + EstadoAtencionHumana.rutaMarcaPersistente());

        if (cierreAsesorVisible(actor)) {
            liberarCasoCerrado(actor, "Cierre de asesor ya estaba visible al recuperar el chat");
            return;
        }

        // Estado EN_COLA: todavia no existe una conversacion humana activa.
        ReportHooks.registrarPaso("Caso en cola; esperando asignacion y saludo del asesor");
        boolean asesorActivo = asesorActivoVisible(actor) || WaitForTextContainsWithTimeout.esperar(
                TIMEOUT_ASIGNACION_ASESOR,
                "Mi nombre es",
                "mi nombre es",
                "me encargare de tu solicitud",
                "me encargaré de tu solicitud",
                "Asesor de Claro",
                "asesor de Claro",
                "Es un gusto atenderte",
                "es un gusto atenderte",
                "como te encuentras",
                "como te encuentras el dia de hoy",
                "en que te puedo colaborar",
                "Buen dia",
                "Buen día",
                "Buenos dias",
                "Buenos días",
                "Buenas tardes",
                "Buenas noches"
        ).answeredBy(actor);

        if (!asesorActivo) {
            EstadoAtencionHumana.marcarTimeoutSinAsignacion();
            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Caso continuo en cola sin asignacion de asesor");
            ReportHooks.registrarPaso("Timeout esperando asignacion y saludo del asesor");
            throw new IllegalStateException(
                    "El caso continuo en cola durante " + TIMEOUT_ASIGNACION_ASESOR
                            + " segundos. Se deja marca persistente y se bloquea la suite para no enviar otro Hola. Archivo: "
                            + EstadoAtencionHumana.rutaMarcaPersistente());
        }

        if (cierreAsesorVisible(actor)) {
            liberarCasoCerrado(actor, "Cierre de asesor detectado despues de la asignacion");
            return;
        }

        // Estado ASESOR_ACTIVO: solo ahora se solicita finalizar la interaccion.
        CapturaDePantallaMovil.tomarCapturaPantalla("Asesor asignado y saludo detectado");
        ReportHooks.registrarPaso("Asesor activo; se solicita finalizar interaccion por pruebas");

        if (!mensajeFinalizacionYaEnviado(actor)) {
            actor.attemptsTo(
                    Enter.theValue(MENSAJE_FINALIZACION).into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );
            ReportHooks.registrarPaso("Se envia mensaje para finalizar interaccion");
        } else {
            ReportHooks.registrarPaso("La solicitud de finalizar interaccion ya estaba enviada; se espera cierre real");
        }

        // Esperar el cierre real antes de permitir que se inicie otra prueba.
        boolean cierreDetectado = cierreAsesorVisible(actor) || WaitForTextContainsWithTimeout.esperar(
                TIMEOUT_CIERRE_ASESOR,
                "finalizó",
                "finalizo",
                "Nuestro chat con agente finalizo",
                "Nuestro chat con agente finalizó",
                "Fue un gusto ayudarte",
                "La conversacion ha finalizado",
                "La conversación ha finalizado",
                "chat con agente ha finalizado",
                "Caso cerrado"
        ).answeredBy(actor);

        if (!cierreDetectado) {

            EstadoAtencionHumana.marcarTimeoutSinCierre();
            ReportHooks.registrarPaso(
                    "Timeout esperando cierre de conversacion por asesor"
            );

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Asesor no cerro la conversacion"
            );

            throw new IllegalStateException(
                    "El asesor no cerro la conversacion en " + TIMEOUT_CIERRE_ASESOR
                            + " segundos. Se deja marca persistente y se detiene para no enviar otro Hola. Archivo: "
                            + EstadoAtencionHumana.rutaMarcaPersistente());
        }

        liberarCasoCerrado(actor, "Conversacion finalizada por el asesor");
    }

    private boolean asesorActivoVisible(Actor actor) {
        return TextoQueContengaX.verificarTexto("Mi nombre es").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("mi nombre es").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("me encargare de tu solicitud").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("me encargaré de tu solicitud").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Asesor de Claro").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("asesor de Claro").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Es un gusto atenderte").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("es un gusto atenderte").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("en que te puedo colaborar").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("como te encuentras").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Buen dia").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Buen día").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Buenos dias").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Buenos días").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Buenas tardes").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Buenas noches").answeredBy(actor);
    }

    private boolean cierreAsesorVisible(Actor actor) {
        return TextoQueContengaX.verificarTexto("finalizó").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("finalizo").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Fue un gusto ayudarte").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Caso cerrado").answeredBy(actor);
    }

    private boolean mensajeFinalizacionYaEnviado(Actor actor) {
        return TextoQueContengaX.verificarTexto("Finalizar interacci").answeredBy(actor);
    }

    private <T extends Actor> void liberarCasoCerrado(T actor, String mensajeReporte) {
        EstadoAtencionHumana.marcarCerrado();
        ReportHooks.registrarPaso(mensajeReporte);
        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Conversacion con asesor finalizada correctamente");

        actor.attemptsTo(
                WaitFor.aTime(2000),
                Click.on(BTN_MAS_OPCIONES),
                ClickTextoQueContengaX.elTextoContiene(MAS),
                ClickTextoQueContengaX.elTextoContiene(VACIAR_CHAT),
                Click.on(BTN_VACIAR_CHAT)
        );

        ReportHooks.registrarPaso("Chat limpiado para reiniciar flujo");
    }

    public static ManejarConversacionConAsesor ejecutar() {
        return instrumented(ManejarConversacionConAsesor.class);
    }
}
