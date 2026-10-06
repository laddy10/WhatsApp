package tasks.HazTusRecargas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.ChannelUnavailableException;
import interactions.wait.WaitForResponse;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.ExtraerURL;
import tasks.SalirConversacion;
import userinterfaces.WhatsAppPage;
import utils.CapturaDePantallaMovil;
import utils.UtilidadesAndroid;

public class DireccionamientoMediosPago implements Task {

    private final String medioPago;

    public DireccionamientoMediosPago(String medioPago) {
        this.medioPago = medioPago;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * 1. Si aparece "Continuar recarga",
         * abrir el menú de Medios de pago.
         */
        if (!BTN_CONTINUAR_RECARGA.resolveAllFor(actor).isEmpty()) {

            abrirMediosDePago(actor);

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Dar clic en boton 'Continuar recarga' y se habilita el boton 'Medios de pago'"
            );

            ReportHooks.registrarPaso(
                    "Dar clic en boton 'Continuar recarga' y se habilita el boton 'Medios de pago'"
            );
        }


        /*
         * 2. Abrir el listado de medios de pago.
         */
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        MEDIOS_DE_PAGO
                )
        );


        /*
         * 3. Ejecutar únicamente el medio solicitado
         * por el escenario.
         */
        if (NEQUI.equals(medioPago)) {

            realizarFlujoNequi(
                    actor,
                    NEQUI,
                    SIN_CUENTA_NEQUI,
                    SMS_NEQUI_NO_DISPONIBLE
            );

        } else if (TARJETA_DEBIDO_O_CREDITO.equals(medioPago)) {

            realizarFlujoConLink(
                    actor,
                    TARJETA_DEBIDO_O_CREDITO,
                    URL_PAGO_RECARGAS
            );

        } else if (PSE.equals(medioPago)) {

            realizarFlujoConLink(
                    actor,
                    PSE,
                    URL_PAGO_PSE_RECARGAS
            );

        } else {

            throw new IllegalArgumentException(
                    "Medio de pago no soportado: "
                            + medioPago
            );
        }


        /*
         * 4. Cerrar conversación según el medio de pago.
         *
         * Nequi permanece dentro del chat de WhatsApp,
         * por lo tanto NO se debe ejecutar Atras.
         *
         * Tarjeta y PSE abren un portal externo,
         * por lo tanto sí regresamos antes de cerrar.
         */
        if (NEQUI.equals(medioPago)) {

            actor.attemptsTo(
                    SalirConversacion.salir()
            );

        } else {

            actor.attemptsTo(
                    Atras.irAtras(),
                    SalirConversacion.salir()
            );
        }
    }


    /**
     * Hace clic en el último botón "Continuar recarga".
     * <p>
     * Conserva el reintento que ya tenía la tarea.
     */
    private <T extends Actor> void abrirMediosDePago(
            T actor) {

        RuntimeException primerFallo;

        try {

            clickUltimoBotonContinuar(actor);
            esperarResultadoMediosPago(actor);

            return;

        } catch (RuntimeException e) {

            primerFallo = e;
        }


        if (BTN_CONTINUAR_RECARGA
                .resolveAllFor(actor)
                .isEmpty()) {

            throw primerFallo;
        }


        clickUltimoBotonContinuar(actor);
        esperarResultadoMediosPago(actor);
    }


    /**
     * Espera que aparezca Medios de pago o
     * el error conocido del canal.
     */
    private <T extends Actor> void esperarResultadoMediosPago(
            T actor) {

        actor.attemptsTo(
                WaitForResponse.withAnyText(
                        20,
                        MEDIOS_DE_PAGO,
                        ERROR_PROCESAR_SOLICITUD
                )
        );


        String source =
                utils.AndroidObject
                        .androidDriver(actor)
                        .getPageSource()
                        .toLowerCase();


        if (source.contains(
                ERROR_PROCESAR_SOLICITUD
        )) {

            throw new ChannelUnavailableException(
                    "Claro no pudo procesar la solicitud de recarga. "
                            + "Reintento de canal requerido."
            );
        }
    }


    /**
     * Selecciona el último botón Continuar recarga.
     */
    private <T extends Actor> void clickUltimoBotonContinuar(
            T actor) {

        List<WebElementFacade> botones =
                BTN_CONTINUAR_RECARGA
                        .resolveAllFor(actor);


        if (botones.isEmpty()) {

            throw new RuntimeException(
                    "No se encontro el boton 'Continuar recarga'."
            );
        }


        botones
                .get(botones.size() - 1)
                .click();
    }


    /**
     * Flujo exclusivo de Nequi.
     */
    private <T extends Actor> void realizarFlujoNequi(
            T actor,
            String medioPago,
            String... textosValidacion) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        medioPago
                ),

                WaitForResponse.withText(
                        ENVIAR2
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar el medio de pago Nequi para continuar con la recarga"
        );

        ReportHooks.registrarPaso(
                "Seleccionar el medio de pago Nequi para continuar con la recarga"
        );


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        ENVIAR2
                ),

                WaitForResponse.withAnyText(
                        textosValidacion
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar respuesta del medio de pago Nequi"
        );

        ReportHooks.registrarPaso(
                "Validar respuesta del medio de pago Nequi"
        );


        if (!LBL_SIN_CUENTA_NEQUI
                .resolveAllFor(actor)
                .isEmpty()) {

            actor.attemptsTo(
                    ValidarTexto.validarTexto(
                            SIN_CUENTA_NEQUI
                    )
            );

        } else {

            actor.attemptsTo(
                    ValidarTexto.validarTexto(
                            SMS_NEQUI_NO_DISPONIBLE
                    )
            );
        }
    }


    /**
     * Flujo utilizado por:
     * <p>
     * - Tarjeta Débito o Crédito
     * - PSE
     */
    private <T extends Actor> void realizarFlujoConLink(
            T actor,
            String medioPago,
            String textoEnlace) {

        /*
         * Confirmar que el medio esté disponible
         * dentro del menú actual.
         */
        boolean medioPagoDisponible =
                WhatsAppPage.LBL_MENSAJES
                        .resolveAllFor(actor)
                        .stream()
                        .anyMatch(
                                el ->
                                        el.getText() != null
                                                && el.getText()
                                                .contains(medioPago)
                        );


        if (!medioPagoDisponible) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Medio de pago no disponible: "
                            + medioPago
            );

            ReportHooks.registrarPaso(
                    "Medio de pago no disponible en esta recarga: "
                            + medioPago
            );

            return;
        }


        /*
         * Seleccionar medio de pago.
         */
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        medioPago
                ),

                WaitForResponse.withText(
                        ENVIAR2
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar el medio de pago: "
                        + medioPago
                        + " para continuar con la recarga"
        );

        ReportHooks.registrarPaso(
                "Seleccionar el medio de pago: "
                        + medioPago
        );


        /*
         * Enviar selección y esperar enlace de pago.
         */
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        ENVIAR2
                ),

                WaitForResponse.withText(
                        SMS_ENLACE_PAGO
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        textoEnlace
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Validar mensaje del medio de pago: "
                        + medioPago
        );

        ReportHooks.registrarPaso(
                "Validar mensaje del medio de pago: "
                        + medioPago
        );


        /*
         * Extraer URL del mensaje.
         */
        String mensaje =
                WhatsAppPage.LBL_MENSAJES
                        .resolveAllFor(actor)
                        .stream()
                        .map(WebElementFacade::getText)
                        .filter(
                                text ->
                                        text != null
                                                && text.contains(
                                                textoEnlace
                                        )
                        )
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "No se encontró mensaje con URL para "
                                                        + medioPago
                                        )
                        );


        String urlExtraida =
                ExtraerURL.desdeTexto(
                        mensaje
                );


        /*
         * Abrir portal externo.
         */
        UtilidadesAndroid.abrirLinkEnNavegador(
                "http://" + urlExtraida
        );


        /*
         * Conservar las validaciones que ya tenía
         * el flujo original.
         */
        actor.attemptsTo(
                WaitForResponse.withText(
                        PAQUETES_Y_RECARGAS
                ),

                ValidarTexto.validarTexto(
                        PAQUETES_Y_RECARGAS
                ),

                ValidarTexto.validarTexto(
                        INGRESA_NUMERO_CLARO
                ),

                ValidarTexto.validarTexto(
                        NUMERO_CLARO
                )
        );


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Se verifica el correcto redireccionamiento al enlace de pago: "
                        + medioPago
        );

        ReportHooks.registrarPaso(
                "Se verifica el correcto redireccionamiento al enlace de pago: "
                        + medioPago
        );
    }


    /**
     * Ejecución individual de Nequi.
     */
    public static Performable nequi() {

        return instrumented(
                DireccionamientoMediosPago.class,
                NEQUI
        );
    }


    /**
     * Ejecución individual de Tarjeta.
     */
    public static Performable tarjeta() {

        return instrumented(
                DireccionamientoMediosPago.class,
                TARJETA_DEBIDO_O_CREDITO
        );
    }


    /**
     * Ejecución individual de PSE.
     */
    public static Performable pse() {

        return instrumented(
                DireccionamientoMediosPago.class,
                PSE
        );
    }
}