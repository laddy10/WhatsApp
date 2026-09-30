package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;

import hooks.ReportHooks;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class SalirConversacion implements Task {

    private static final String COMANDO_CIERRE = "Cierrecaso";

    /*
     * ÚNICO estado que confirma realmente
     * que la conversación quedó cerrada.
     */
    private static final String CASO_CERRADO = "Caso cerrado";

    /*
     * Máximo 2 envíos:
     *
     * 1. Primer Cierrecaso
     * 2. Reintento si Claro respondió con ruido
     *    como "No entendí tu mensaje"
     */
    private static final int MAX_INTENTOS = 2;

    /*
     * En el primer intento no queremos esperar demasiado
     * porque ya sabemos que Claro puede responder
     * rápidamente con "No entendí".
     */
    private static final int TIMEOUT_PRIMER_INTENTO = 8;

    /*
     * En el segundo intento damos el tiempo normal
     * para confirmar definitivamente el cierre.
     */
    private static final int TIMEOUT_SEGUNDO_INTENTO = 20;

    @Override
    public <T extends Actor> void performAs(T actor) {

        boolean salidaExitosa = false;

        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {

            ReportHooks.registrarPaso(
                    "Enviando '" + COMANDO_CIERRE
                            + "' - intento " + intento
                            + " de " + MAX_INTENTOS
            );

            /*
             * 1. Enviar Cierrecaso
             */
            actor.attemptsTo(
                    Enter.theValue(COMANDO_CIERRE)
                            .into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );

            /*
             * 2. Esperar EXCLUSIVAMENTE la confirmación
             *    real de cierre.
             *
             * Ya NO consideramos:
             *
             * - No entendí
             * - Menú principal
             * - Aún estoy contigo
             * - código inválido
             *
             * como resultados satisfactorios.
             */
            int timeout =
                    intento == 1
                            ? TIMEOUT_PRIMER_INTENTO
                            : TIMEOUT_SEGUNDO_INTENTO;

            try {

                actor.attemptsTo(
                        WaitForTextContains.withTextContains(
                                CASO_CERRADO,
                                58
                        )
                );

                /*
                 * 3. Confirmación adicional.
                 */
                if (esCierreDetectado(actor)) {

                    CapturaDePantallaMovil.tomarCapturaPantalla(
                            "Conversación cerrada correctamente"
                    );

                    ReportHooks.registrarPaso(
                            "✓ Caso cerrado detectado correctamente"
                    );

                    salidaExitosa = true;
                    break;
                }

            } catch (RuntimeException e) {

                /*
                 * No apareció "Caso cerrado" durante
                 * el tiempo establecido.
                 */
                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "No se confirmó cierre - intento " + intento
                );

                if (intento < MAX_INTENTOS) {

                    ReportHooks.registrarPaso(
                            "No se detectó 'Caso cerrado' después del primer "
                                    + "Cierrecaso. Se realizará un segundo intento."
                    );

                    actor.attemptsTo(
                            WaitFor.aTime(1500)
                    );
                }
            }
        }

        /*
         * MUY IMPORTANTE:
         *
         * Si después de los dos intentos no apareció
         * "Caso cerrado", NO podemos permitir que
         * el escenario continúe hacia "Vaciar chat".
         *
         * Vaciarlo ocultaría el problema y dejaría
         * abierta la conversación lógica con Claro.
         */
        if (!salidaExitosa) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "No fue posible cerrar la conversación"
            );

            ReportHooks.registrarPaso(
                    "✗ No se confirmó el cierre de la conversación después de "
                            + MAX_INTENTOS + " intentos"
            );

            throw new IllegalStateException(
                    "No se pudo confirmar el cierre de la conversación con Claro. "
                            + "No se ejecutará el vaciado del chat."
            );
        }
    }

    /*
     * Confirmación real del cierre.
     */
    private boolean esCierreDetectado(Actor actor) {

        try {

            AndroidObject android = new AndroidObject();

            return android.textoContiene(
                    actor,
                    CASO_CERRADO
            );

        } catch (Exception e) {

            return false;
        }
    }

    public static SalirConversacion salir() {

        return instrumented(
                SalirConversacion.class
        );
    }
}