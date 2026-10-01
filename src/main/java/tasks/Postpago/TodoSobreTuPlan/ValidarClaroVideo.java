package tasks.Postpago.TodoSobreTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.Wait;
import questions.TextoQueContengaX;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class ValidarClaroVideo implements Task {

    private static final String ERROR_CLARO_VIDEO =
            "no podemos realizar la acción solicitada";

    private static final String BTN_SALIR_CLARO_VIDEO =
            "SALIR";

    @Override
    public <T extends Actor> void performAs(T actor) {

        boolean errorControladoClaroVideo = false;

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje con URL de claro video"
        );

        ReportHooks.registrarPaso(
                "Validar mensaje con URL de claro video"
        );

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLARO_VIDEO)
        );

        UtilidadesAndroid.abrirLinkEnNavegador(
                URL_CLARO_VIDEO
        );

        actor.attemptsTo(
                WaitFor.aTime(9000)
        );

        /*
         * Se mantiene la validación normal existente.
         *
         * Si la URL redirecciona correctamente a clarovideo.com,
         * el flujo continúa exactamente igual.
         *
         * Si la redirección falla, antes de marcar FAIL se verifica
         * si Claro Video mostró el mensaje controlado de
         * indisponibilidad.
         */
        try {

            UtilidadesAndroid.esperarRedireccionamientoWeb(
                    actor,
                    "clarovideo.com",
                    50
            );

        } catch (RuntimeException e) {

            boolean errorControlado =
                    TextoQueContengaX
                            .verificarTexto(ERROR_CLARO_VIDEO)
                            .answeredBy(actor);

            if (errorControlado) {

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Claro Video muestra mensaje de indisponibilidad"
                );

                ReportHooks.registrarPaso(
                        "Claro Video respondió con mensaje controlado de indisponibilidad"
                );

                /*
                 * Cerramos el modal mediante SALIR.
                 */
                actor.attemptsTo(
                        ClickTextoQueContengaX
                                .elTextoContiene(BTN_SALIR_CLARO_VIDEO)
                );

                errorControladoClaroVideo = true;

            } else {

                /*
                 * Si NO aparece el mensaje conocido,
                 * conservamos el comportamiento anterior:
                 * el caso falla.
                 */
                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Timeout esperando clarovideo.com en Chrome"
                );

                ReportHooks.registrarPaso(
                        "⚠️ No se confirmó redirección a clarovideo.com: "
                                + e.getMessage()
                );

                throw e;
            }
        }

        /*
         * Solo registramos ingreso exitoso cuando realmente
         * se confirmó la redirección normal.
         */
        if (!errorControladoClaroVideo) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Se valida el ingreso a Claro Video"
            );

            ReportHooks.registrarPaso(
                    "Se valida el ingreso a Claro Video"
            );
        }

        /*
         * En ambos caminos regresamos al chat
         * y cerramos la conversación.
         */
        actor.attemptsTo(
                Atras.irAtras(),
                SalirConversacion.salir()
        );
    }

    public static Performable validarClaroVideo() {
        return instrumented(
                ValidarClaroVideo.class
        );
    }
}