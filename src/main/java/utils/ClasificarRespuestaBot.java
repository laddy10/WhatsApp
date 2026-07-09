package utils;

import models.EstadoConversacion;
import net.serenitybdd.screenplay.Actor;
import questions.TextoQueContengaX;

public class ClasificarRespuestaBot {

    public static EstadoConversacion obtenerEstado(Actor actor) {

        if (TextoQueContengaX.verificarTexto("Ideas de regalo").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("¿Qué quieres hacer hoy?").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Te damos la bienvenida al chat de Claro")
                .answeredBy(actor)) {

            return EstadoConversacion.PANTALLA_INICIAL;
        }

        if (TextoQueContengaX.verificarTexto("Voy a comunicarte con uno de nuestros asesores").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("comunicarte con uno de nuestros asesores").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("te comunicaremos con un asesor").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("mi nombre es").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Mi nombre es").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("me encargare de tu solicitud").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("me encargaré de tu solicitud").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Asesor de Claro").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("asesor de Claro").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Gracias por comunicarte").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("como te encuentras").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("en que te puedo colaborar").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Es un gusto atenderte").answeredBy(actor)) {

            return EstadoConversacion.ESPERANDO_ASESOR;
        }

        if (TextoQueContengaX.verificarTexto("No entendí").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("respuesta no es válida").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Menú principal").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("opciones mostradas anteriormente").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("ingrese el número de opción").answeredBy(actor)) {

            return EstadoConversacion.ERROR;
        }

        return EstadoConversacion.FLUJO_NORMAL;
    }
}