package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;

import hooks.ReportHooks;
import interactions.wait.WaitFor;

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

    private static final String CASO_CERRADO =
            "Caso cerrado";

    private static final String RESPUESTA_NO_ENTENDI =
            "No entendí tu mensaje";

    private static final String CIERRE_NATURAL_1 =
            "Fue un gusto ayudarte";

    private static final String CIERRE_NATURAL_2 =
            "nuestro chat finalizó";

    private static final Target BTN_FINALIZAR_CHAT =
            Target.the("Botón Finalizar chat")
                    .located(
                            By.xpath(
                                    "//*[contains(@text,'Finalizar chat')]"
                            )
                    );

    private static final int MAX_INTENTOS = 2;

    private static final int TIMEOUT_PRIMER_INTENTO = 8;
    private static final int TIMEOUT_SEGUNDO_INTENTO = 15;

    private static final long POLL_MILLIS = 500;

    @Override
    public <T extends Actor> void performAs(T actor) {

        boolean salidaExitosa = false;

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

            /*
             * Enviar Cierrecaso.
             */
            actor.attemptsTo(
                    Enter.theValue(COMANDO_CIERRE)
                            .into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR)
            );

            int timeout =
                    intento == 1
                            ? TIMEOUT_PRIMER_INTENTO
                            : TIMEOUT_SEGUNDO_INTENTO;

            /*
             * Espera controlada.
             *
             * IMPORTANTE:
             * no lanza excepción si se cumple el timeout.
             */
            EstadoCierre estado =
                    esperarRespuestaCierre(
                            actor,
                            timeout
                    );

            if (estado == EstadoCierre.CERRADO) {

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Conversación cerrada correctamente"
                );

                ReportHooks.registrarPaso(
                        "✓ Caso cerrado detectado correctamente"
                );

                salidaExitosa = true;
                break;
            }

            if (estado == EstadoCierre.CIERRE_NATURAL) {

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Conversación finalizada por el bot"
                );

                ReportHooks.registrarPaso(
                        "✓ Se detectó finalización natural "
                                + "de la conversación"
                );

                salidaExitosa = true;
                break;
            }

            /*
             * No entendió Cierrecaso.
             *
             * Ya sabemos que NO cerró, por lo que no
             * tenemos que seguir esperando.
             */
            if (estado == EstadoCierre.NO_ENTENDIO) {

                ReportHooks.registrarPaso(
                        "Claro respondió 'No entendí tu mensaje'. "
                                + "La conversación continúa abierta."
                );
            }

            /*
             * No hubo ninguna respuesta de cierre reconocida.
             */
            if (estado == EstadoCierre.SIN_CONFIRMACION) {

                ReportHooks.registrarPaso(
                        "No se recibió confirmación de cierre "
                                + "durante "
                                + timeout
                                + " segundos."
                );

                CapturaDePantallaMovil.tomarCapturaPantalla(
                        "Sin confirmación de cierre - intento "
                                + intento
                );
            }

            /*
             * Si todavía queda un intento,
             * volvemos a enviar Cierrecaso.
             */
            if (intento < MAX_INTENTOS) {

                ReportHooks.registrarPaso(
                        "Se realizará un nuevo intento enviando '"
                                + COMANDO_CIERRE
                                + "'."
                );

                actor.attemptsTo(
                        WaitFor.aTime(1500)
                );
            }
        }

        /*
         * =========================================================
         * FALLBACK: FINALIZAR CHAT
         * =========================================================
         */
        if (!salidaExitosa) {

            List<WebElementFacade> botonesFinalizar =
                    BTN_FINALIZAR_CHAT.resolveAllFor(actor);

            if (!botonesFinalizar.isEmpty()) {

                ReportHooks.registrarPaso(
                        "No se confirmó cierre con 'Cierrecaso'. "
                                + "Se intentará utilizar "
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
         * No convertir la limpieza en falso FAIL.
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
                            + "'. Se continuará con la limpieza."
            );
        }
    }

    /*
     * Espera SIN EXCEPCIONES.
     *
     * Esta es la parte importante del cambio.
     */
    private EstadoCierre esperarRespuestaCierre(
            Actor actor,
            int timeoutSegundos) {

        long limite =
                System.currentTimeMillis()
                        + timeoutSegundos * 1000L;

        while (System.currentTimeMillis() < limite) {

            if (esCierreDetectado(actor)) {
                return EstadoCierre.CERRADO;
            }

            if (esCierreNaturalDetectado(actor)) {
                return EstadoCierre.CIERRE_NATURAL;
            }

            if (textoVisible(
                    actor,
                    RESPUESTA_NO_ENTENDI
            )) {
                return EstadoCierre.NO_ENTENDIO;
            }

            try {

                Thread.sleep(POLL_MILLIS);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;
            }
        }

        return EstadoCierre.SIN_CONFIRMACION;
    }

    private boolean esCierreDetectado(Actor actor) {

        return textoVisible(
                actor,
                CASO_CERRADO
        );
    }

    private boolean esCierreNaturalDetectado(
            Actor actor) {

        return textoVisible(
                actor,
                CIERRE_NATURAL_1
        )
                || textoVisible(
                actor,
                CIERRE_NATURAL_2
        );
    }

    private boolean textoVisible(
            Actor actor,
            String texto) {

        try {

            AndroidObject android =
                    new AndroidObject();

            return android.textoContiene(
                    actor,
                    texto
            );

        } catch (Exception e) {

            return false;
        }
    }

    private enum EstadoCierre {

        CERRADO,
        CIERRE_NATURAL,
        NO_ENTENDIO,
        SIN_CONFIRMACION
    }

    public static SalirConversacion salir() {

        return instrumented(
                SalirConversacion.class
        );
    }
}