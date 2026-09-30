package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;

import hooks.ReportHooks;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class SalirConversacion implements Task {

    private static final String COMANDO_CIERRE = "Cierrecaso";
    private static final String CASO_CERRADO = "Caso cerrado";

    /*
     * Respuesta observada cuando Claro no reconoce
     * Cierrecaso como comando de cierre.
     */
    private static final String RESPUESTA_NO_ENTENDI =
            "No entendí tu mensaje";

    /*
     * Cierres naturales que también pueden aparecer.
     */
    private static final String CIERRE_NATURAL_1 =
            "Fue un gusto ayudarte";

    private static final String CIERRE_NATURAL_2 =
            "nuestro chat finalizó";

    /*
     * Último recurso disponible en algunos menús.
     */
    private static final Target BTN_FINALIZAR_CHAT =
            Target.the("Botón Finalizar chat")
                    .located(
                            By.xpath(
                                    "//*[contains(@text,'Finalizar chat')]"
                            )
                    );

    /*
     * Dos intentos reales de Cierrecaso.
     */
    private static final int MAX_INTENTOS = 2;

    private static final int TIMEOUT_PRIMER_INTENTO = 8;
    private static final int TIMEOUT_SEGUNDO_INTENTO = 15;

    @Override
    public <T extends Actor> void performAs(T actor) {

        boolean salidaExitosa = false;

        /*
         * ==========================================================
         * INTENTOS CON CIERRECASO
         * ==========================================================
         */
        for (int intento = 1;
             intento <= MAX_INTENTOS;
             intento++) {

            ReportHooks.registrarPaso(
                    "Enviando '"
                            + COMANDO_CIERRE
                            + "' - intento "
                            + intento
                            + " de "
                            + MAX_INTENTOS
            );

            actor.attemptsTo(
                    Enter.theValue(COMANDO_CIERRE)
                            .into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );

            int timeout =
                    intento == 1
                            ? TIMEOUT_PRIMER_INTENTO
                            : TIMEOUT_SEGUNDO_INTENTO;

            try {

                /*
                 * Esperamos únicamente respuestas que realmente
                 * nos ayudan a decidir:
                 *
                 * - Caso cerrado:
                 *      éxito.
                 *
                 * - No entendí:
                 *      el bot respondió, pero NO cerró.
                 *      Se debe reintentar.
                 *
                 * No usamos "Menú principal" aquí porque puede
                 * existir desde antes en pantalla y provocar
                 * falsos positivos.
                 */
                actor.attemptsTo(
                        WaitForTextContains.withAnyTextContains(
                                timeout,
                                CASO_CERRADO,
                                RESPUESTA_NO_ENTENDI,
                                CIERRE_NATURAL_1,
                                CIERRE_NATURAL_2
                        )
                );

            } catch (RuntimeException e) {

                /*
                 * Si no apareció ninguna respuesta reconocida,
                 * NO fallamos.
                 *
                 * Si todavía queda otro intento,
                 * volvemos a enviar Cierrecaso.
                 */
                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Sin confirmación de cierre - intento "
                                + intento
                );
            }

            /*
             * Primero comprobar cierre real.
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

            /*
             * También aceptamos un cierre natural del bot.
             */
            if (esCierreNaturalDetectado(actor)) {

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Conversación finalizada por el bot"
                );

                ReportHooks.registrarPaso(
                        "✓ Se detectó finalización natural de la conversación"
                );

                salidaExitosa = true;
                break;
            }

            /*
             * Si llegamos aquí:
             *
             * - apareció "No entendí"
             * - apareció otra respuesta
             * - o simplemente no apareció Caso cerrado
             *
             * y todavía queda otro intento.
             */
            if (intento < MAX_INTENTOS) {

                ReportHooks.registrarPaso(
                        "Claro respondió pero no confirmó el cierre. "
                                + "Se enviará nuevamente '"
                                + COMANDO_CIERRE
                                + "'."
                );

                actor.attemptsTo(
                        WaitFor.aTime(1500)
                );
            }
        }

        /*
         * ==========================================================
         * FALLBACK: FINALIZAR CHAT
         * ==========================================================
         *
         * Si después de los dos Cierrecaso seguimos sin cierre,
         * intentamos usar el botón que ofrece el propio bot.
         */
        if (!salidaExitosa) {

            List<WebElementFacade> botonesFinalizar =
                    BTN_FINALIZAR_CHAT.resolveAllFor(actor);

            if (!botonesFinalizar.isEmpty()) {

                ReportHooks.registrarPaso(
                        "No se confirmó cierre con 'Cierrecaso'. "
                                + "Se intentará finalizar mediante "
                                + "'Finalizar chat'."
                );

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Fallback Finalizar chat"
                );

                try {

                    actor.attemptsTo(
                            Click.on(BTN_FINALIZAR_CHAT),
                            WaitFor.aTime(2000)
                    );

                    /*
                     * Después de pulsar Finalizar chat,
                     * verificamos nuevamente si hubo cierre.
                     */
                    if (esCierreDetectado(actor)
                            || esCierreNaturalDetectado(actor)) {

                        salidaExitosa = true;

                        ReportHooks.registrarPaso(
                                "✓ Conversación finalizada mediante "
                                        + "'Finalizar chat'"
                        );
                    }

                } catch (Exception e) {

                    ReportHooks.registrarPaso(
                            "⚠ No fue posible utilizar "
                                    + "'Finalizar chat'."
                    );
                }
            }
        }

        /*
         * ==========================================================
         * NO GENERAR FALSA ALERTA
         * ==========================================================
         *
         * El flujo funcional ya terminó.
         *
         * Si Claro no confirmó el cierre después de todos
         * los mecanismos, registramos evidencia pero
         * NO tumbamos el escenario.
         *
         * El siguiente paso podrá ejecutar Vaciar chat.
         */
        if (!salidaExitosa) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Cierre lógico no confirmado - continuar limpieza"
            );

            ReportHooks.registrarPaso(
                    "⚠ No se pudo confirmar el cierre lógico "
                            + "después de "
                            + MAX_INTENTOS
                            + " intentos de '"
                            + COMANDO_CIERRE
                            + "'. "
                            + "Se continuará con la limpieza del chat."
            );
        }
    }

    /*
     * Caso cerrado:
     * confirmación ideal.
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

    /*
     * También reconocer cierres naturales ya observados
     * en el bot.
     */
    private boolean esCierreNaturalDetectado(Actor actor) {

        try {

            AndroidObject android = new AndroidObject();

            return android.textoContiene(
                    actor,
                    CIERRE_NATURAL_1
            )
                    || android.textoContiene(
                    actor,
                    CIERRE_NATURAL_2
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