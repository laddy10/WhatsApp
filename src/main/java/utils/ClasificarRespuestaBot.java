package utils;

import models.EstadoConversacion;
import net.serenitybdd.screenplay.Actor;
import questions.TextoQueContengaX;

public class ClasificarRespuestaBot {

    public static EstadoConversacion obtenerEstado(Actor actor) {

        // 🟡 Pantalla inicial NO válida
        if (TextoQueContengaX.verificarTexto("Ideas de regalo").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("¿Qué quieres hacer hoy?").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Te damos la bienvenida al chat de Claro").answeredBy(actor)) {

            return EstadoConversacion.PANTALLA_INICIAL;
        }

        // 🔴 Errores conversacionales
        if (TextoQueContengaX.verificarTexto("No entendí").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("respuesta no es válida").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("Menú principal").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("opciones mostradas anteriormente").answeredBy(actor)
                || TextoQueContengaX.verificarTexto("ingrese el número de opción").answeredBy(actor)) {

            return EstadoConversacion.ERROR;
        }

        // 🟢 Flujo normal
        return EstadoConversacion.FLUJO_NORMAL;
    }
}
