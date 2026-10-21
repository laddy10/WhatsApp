package tasks.Hogar.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import interactions.wait.WaitForTextContainsWithTimeout;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.TextoQueContengaX;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class TransaccionBancolombiaHogar extends AndroidObject implements Task {

    private static final int TIMEOUT_SELECTOR_MEDIO_PAGO = 40;
    private static final int TIMEOUT_CARGA_BANCOLOMBIA = 60;
    private static final int TIMEOUT_CLAVE_PRINCIPAL = 60;

    @Override
    public <T extends Actor> void performAs(T actor) {

        Boolean alDia = actor.recall("alDia");

        if (alDia != null && alDia) {
            System.out.println(
                    "La cuenta está al día, omitiendo transacción Botón Bancolombia."
            );
            return;
        }

        /*
         * ============================================================
         * 1. ESPERAR QUE EL PORTAL DE PAGOS TERMINE DE CARGAR
         * ============================================================
         */
        esperarSelectorMedioPago(actor);

        /*
         * ============================================================
         * 2. SELECCIONAR BOTÓN BANCOLOMBIA
         * ============================================================
         */
        actor.attemptsTo(
                Click.on(CHECK_SELECCIONAR_MEDIO_PAGO),
                WaitForTextContains.withAnyTextContains(
                        BOTON_BANCOLOMBIA
                ),
                Click.on(BTN_BOTON_BANCOLOMBIA),
                WaitFor.aTime(2000)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar Botón Bancolombia Hogar"
        );

        ReportHooks.registrarPaso(
                "Seleccionar Botón Bancolombia Hogar"
        );

        /*
         * ============================================================
         * 3. CONTINUAR DESDE PORTAL DE PAGOS HACIA BANCOLOMBIA
         * ============================================================
         */
        actor.attemptsTo(
                ScrollGradual.bajar(0.20),

                ClickTextoQueContengaX.elTextoContiene(
                        CONTINUAR_BUTTON
                ),

                WaitForTextContains.withAnyTextContains(
                        140,
                        BANCOLOMBIA_BIENVENIDA
                )
        );

        /*
         * El texto de bienvenida puede aparecer antes de que
         * Bancolombia termine realmente de cargar.
         *
         * Por eso NO usamos ese texto como única señal funcional.
         */
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        BANCOLOMBIA_BIENVENIDA
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Portal de Bancolombia iniciado"
        );

        ReportHooks.registrarPaso(
                "Portal de Bancolombia iniciado"
        );

        /*
         * ============================================================
         * 4. ESPERAR QUE BANCOLOMBIA ESTÉ REALMENTE LISTO
         * ============================================================
         *
         * La página puede mostrar:
         *
         * - pantalla blanca
         * - loader
         * - logo Bancolombia
         * - texto de bienvenida
         *
         * y aún no estar lista.
         *
         * El criterio real será que el campo Usuario exista,
         * esté visible y esté habilitado.
         */
        esperarCampoUsuarioBancolombia(actor);

        /*
         * ============================================================
         * 5. INGRESAR USUARIO
         * ============================================================
         */
        actor.attemptsTo(
                Enter.theValue(
                        "Pruebas15"
                ).into(
                        TXT_USUARIO_BANCOLOMBIA
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Usuario ingresado en Bancolombia"
        );

        ReportHooks.registrarPaso(
                "Usuario ingresado en Bancolombia"
        );

        /*
         * ============================================================
         * 6. CONTINUAR EN BANCOLOMBIA
         * ============================================================
         */
        actor.attemptsTo(
                Click.on(
                        BTN_CONTINUAR_BANCOLOMBIA
                )
        );

        /*
         * Esperamos dinámicamente la transición.
         *
         * Ya no dependemos únicamente de un WaitFor fijo de 5 segundos.
         */
        boolean clavePrincipalVisible =
                WaitForTextContainsWithTimeout.esperar(
                        TIMEOUT_CLAVE_PRINCIPAL,
                        CLAVE_PRINCIPAL
                ).answeredBy(actor);

        /*
         * Si no apareció Clave principal, verificamos si todavía
         * seguimos en la bienvenida.
         *
         * En ese caso reintentamos una sola vez el botón Continuar.
         */
        if (!clavePrincipalVisible) {

            boolean sigueEnBienvenida =
                    TextoQueContengaX
                            .verificarTexto(
                                    BANCOLOMBIA_BIENVENIDA
                            )
                            .answeredBy(actor);

            if (sigueEnBienvenida) {

                ReportHooks.registrarPaso(
                        "Bancolombia permaneció en la pantalla de bienvenida. "
                                + "Se reintenta una vez el botón Continuar."
                );

                actor.attemptsTo(
                        Click.on(
                                BTN_CONTINUAR_BANCOLOMBIA
                        )
                );

                clavePrincipalVisible =
                        WaitForTextContainsWithTimeout.esperar(
                                TIMEOUT_CLAVE_PRINCIPAL,
                                CLAVE_PRINCIPAL
                        ).answeredBy(actor);
            }
        }

        /*
         * Si después del intento inicial y del único reintento
         * no aparece Clave principal, fallamos de forma controlada.
         */
        if (!clavePrincipalVisible) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Bancolombia no cargo pantalla de Clave Principal"
            );

            throw new IllegalStateException(
                    "Bancolombia no mostró la pantalla de Clave principal "
                            + "dentro del tiempo esperado."
            );
        }

        /*
         * ============================================================
         * 7. VALIDACIÓN FUNCIONAL FINAL
         * ============================================================
         */
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        CLAVE_PRINCIPAL
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Pantalla de Clave Principal Bancolombia validada"
        );

        ReportHooks.registrarPaso(
                "Pantalla de Clave Principal Bancolombia validada"
        );
    }

    /*
     * ================================================================
     * ESPERAR SELECTOR DE MEDIO DE PAGO
     * ================================================================
     */
    private <T extends Actor> void esperarSelectorMedioPago(T actor) {

        boolean selectorDisponible = false;
        int segundosEsperados = 0;

        while (!selectorDisponible
                && segundosEsperados < TIMEOUT_SELECTOR_MEDIO_PAGO) {

            try {

                List<WebElementFacade> selectores =
                        CHECK_SELECCIONAR_MEDIO_PAGO
                                .resolveAllFor(actor);

                if (!selectores.isEmpty()) {

                    WebElementFacade selector =
                            selectores.get(0);

                    if (selector.isVisible()
                            && selector.isEnabled()) {

                        selectorDisponible = true;
                        break;
                    }
                }

            } catch (Exception ignored) {
                /*
                 * El portal de pagos todavía está cargando.
                 */
            }

            actor.attemptsTo(
                    WaitFor.aTime(1000)
            );

            segundosEsperados++;
        }

        if (!selectorDisponible) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Portal de pagos no habilito selector de medio de pago"
            );

            throw new IllegalStateException(
                    "El portal de pagos no terminó de cargar el selector "
                            + "de medios de pago después de "
                            + TIMEOUT_SELECTOR_MEDIO_PAGO
                            + " segundos."
            );
        }

        ReportHooks.registrarPaso(
                "Portal de pagos cargado. "
                        + "El selector de medios de pago está disponible."
        );
    }

    /*
     * ================================================================
     * ESPERAR CAMPO USUARIO BANCOLOMBIA
     * ================================================================
     *
     * Esta es la señal real de que Bancolombia terminó
     * de cargar la pantalla de inicio.
     */
    private <T extends Actor> void esperarCampoUsuarioBancolombia(T actor) {

        boolean campoDisponible = false;
        int segundosEsperados = 0;

        while (!campoDisponible
                && segundosEsperados < TIMEOUT_CARGA_BANCOLOMBIA) {

            try {

                List<WebElementFacade> campos =
                        TXT_USUARIO_BANCOLOMBIA
                                .resolveAllFor(actor);

                if (!campos.isEmpty()) {

                    WebElementFacade campo =
                            campos.get(0);

                    if (campo.isVisible()
                            && campo.isEnabled()) {

                        campoDisponible = true;
                        break;
                    }
                }

            } catch (Exception ignored) {
                /*
                 * Bancolombia todavía está cargando.
                 */
            }

            actor.attemptsTo(
                    WaitFor.aTime(1000)
            );

            segundosEsperados++;
        }

        if (!campoDisponible) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Portal Bancolombia no habilito campo Usuario"
            );

            throw new IllegalStateException(
                    "El portal de Bancolombia no terminó de cargar "
                            + "el campo Usuario después de "
                            + TIMEOUT_CARGA_BANCOLOMBIA
                            + " segundos."
            );
        }

        ReportHooks.registrarPaso(
                "Portal Bancolombia completamente cargado. "
                        + "Campo Usuario disponible."
        );
    }

    public static Performable transaccionBancolombiaHogar() {

        return instrumented(
                TransaccionBancolombiaHogar.class
        );
    }
}