package tasks;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContainsWithTimeout;
import models.EstadoAsesor;
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

    // Se ajusta a ~4.5 minutos para no exceder los 5 minutos del servidor CI
    private static final int TIMEOUT_ESPERA_MAXIMO =
            Integer.getInteger("whatsapp.asesor.timeout.seconds", 270);
    private static final String MENSAJE_FINALIZACION = "Por favor finalizar interacción, es una prueba";

    @Override
    public <T extends Actor> void performAs(T actor) {
        
        EstadoAsesor estadoActual = EstadoAtencionHumana.leerEstado();
        
        if (estadoActual == EstadoAsesor.SIN_CONVERSACION_PENDIENTE) {
            EstadoAtencionHumana.marcarEnCola();
            estadoActual = EstadoAsesor.EN_COLA;
            CapturaDePantallaMovil.tomarCapturaPantalla("Conversacion escalada a asesor humano");
            ReportHooks.registrarPaso("Escalamiento a asesor detectado");
        } else {
            ReportHooks.registrarPaso("Retomando seguimiento de asesor. Estado actual: " + estadoActual);
        }

        long tiempoInicio = System.currentTimeMillis();

        if (cierreAsesorVisible(actor)) {
            liberarCasoCerrado(actor, "Cierre de asesor ya estaba visible al recuperar el chat");
            return;
        }

        // Estado EN_COLA: Esperar asignacion del asesor
        if (estadoActual == EstadoAsesor.EN_COLA) {
            ReportHooks.registrarPaso("Caso en cola; esperando asignacion y saludo del asesor");
            int tiempoRestante = calcularTiempoRestante(tiempoInicio);
            
            boolean asesorAsignado = asesorActivoVisible(actor);
            if (!asesorAsignado && tiempoRestante > 0) {
                 asesorAsignado = WaitForTextContainsWithTimeout.esperar(
                    tiempoRestante,
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
            }

            if (!asesorAsignado) {
                CapturaDePantallaMovil.tomarCapturaPantalla("Caso continuo en cola sin asignacion de asesor");
                ReportHooks.registrarPaso("Timeout esperando asignacion del asesor. Se conserva estado EN_COLA.");
                throw new IllegalStateException(
                        "El caso continuo en cola durante " + TIMEOUT_ESPERA_MAXIMO
                                + " segundos. Se conserva marca EN_COLA para la proxima ejecucion.");
            } else {
                EstadoAtencionHumana.marcarAsesorActivo();
                estadoActual = EstadoAsesor.ASESOR_ACTIVO;
            }
        }

        if (cierreAsesorVisible(actor)) {
            liberarCasoCerrado(actor, "Cierre de asesor detectado antes de enviar mensaje de finalizacion");
            return;
        }

        // Estado ASESOR_ACTIVO: Asesor asignado, enviar mensaje para finalizar interaccion
        if (estadoActual == EstadoAsesor.ASESOR_ACTIVO) {
            CapturaDePantallaMovil.tomarCapturaPantalla("Asesor asignado y saludo detectado");
            ReportHooks.registrarPaso("Asesor activo; se solicita finalizar interaccion por pruebas");

            if (!mensajeFinalizacionYaEnviado(actor)) {
                actor.attemptsTo(
                        Enter.theValue(MENSAJE_FINALIZACION).into(TXT_ENVIAR_MENSAJE),
                        Click.on(BTN_ENVIAR)
                );
                ReportHooks.registrarPaso("Se envia mensaje para finalizar interaccion");
            } else {
                ReportHooks.registrarPaso("La solicitud de finalizar interaccion ya estaba enviada");
            }
            
            EstadoAtencionHumana.marcarCierrePendiente();
            estadoActual = EstadoAsesor.CIERRE_PENDIENTE;
        }

        // Estado CIERRE_PENDIENTE: Esperar que el asesor realmente cierre la interaccion
        if (estadoActual == EstadoAsesor.CIERRE_PENDIENTE) {
            ReportHooks.registrarPaso("Esperando cierre real por parte del asesor");
            int tiempoRestante = calcularTiempoRestante(tiempoInicio);
            
            boolean cierreDetectado = cierreAsesorVisible(actor);
            if (!cierreDetectado && tiempoRestante > 0) {
                 cierreDetectado = WaitForTextContainsWithTimeout.esperar(
                    tiempoRestante,
                    "finalizó",
                    "finalizo",
                    "Nuestro chat con agente finalizo",
                    "Nuestro chat con agente finalizó",
                    "Fue un gusto ayudarte",
                    "La conversacion ha finalizado",
                    "La conversación ha finalizado",
                    "chat con agente ha finalizado",
                    "Caso cerrado",
                    "sencilla encuesta para conocer como fue tu experiencia"
                ).answeredBy(actor);
            }

            if (!cierreDetectado) {
                CapturaDePantallaMovil.tomarCapturaPantalla("Asesor no cerro la conversacion aun");
                ReportHooks.registrarPaso("Timeout esperando cierre. Se conserva estado CIERRE_PENDIENTE.");
                throw new IllegalStateException(
                        "El asesor no cerro la conversacion en el tiempo permitido de la ejecucion. "
                                + "Se conserva marca CIERRE_PENDIENTE para la proxima ejecucion.");
            }
        }

        liberarCasoCerrado(actor, "Conversacion finalizada por el asesor");
    }
    
    private int calcularTiempoRestante(long tiempoInicio) {
        long transcurrido = (System.currentTimeMillis() - tiempoInicio) / 1000;
        int restante = TIMEOUT_ESPERA_MAXIMO - (int) transcurrido;
        return Math.max(restante, 5); // Al menos 5 segundos para intentar
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
                || TextoQueContengaX.verificarTexto("Caso cerrado").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("sencilla encuesta para conocer como fue tu experiencia").answeredBy(actor);
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
