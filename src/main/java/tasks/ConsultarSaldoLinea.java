package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class ConsultarSaldoLinea implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(
                        CONSULTA_TUS_CONSUMOS
                )
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Seleccionar opcion 'Consulta tus consumos'"
        );

        ReportHooks.registrarPaso(
                "Seleccionar opcion 'Consulta tus consumos'"
        );


        /*
         * Esperamos cualquier texto que confirme
         * que la respuesta del bot ya llegó.
         *
         * IMPORTANTE:
         * RECARGA_ACTIVA puede quedar fuera de pantalla,
         * por eso agregamos textos que normalmente quedan
         * visibles en la parte inferior de la respuesta.
         */
        actor.attemptsTo(
                ClickElementByText.clickElementByText(
                        ENVIAR
                ),

                WaitForResponse.withAnyText(
                        SIN_SALDO_DISPONIBLE,
                        RECARGA_ACTIVA,
                        PAQUETE_VENCE,
                        "Datos consumidos",
                        MENU_ANTERIOR
                )
        );


        List<WebElementFacade> lblsinsaldo =
                LBL_SIN_SALDO.resolveAllFor(actor);


        if (!lblsinsaldo.isEmpty()) {

            /*
             * Caso sin saldo / sin recarga activa.
             */
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(
                            SIN_SALDO_DISPONIBLE
                    ),

                    ValidarTexto.validarTexto(
                            COMPRAR_PAQUETE
                    ),

                    ValidarTexto.validarTexto(
                            COMPRAR_RECARGA
                    ),

                    ValidarTexto.validarTexto(
                            MENU_ANTERIOR
                    )
            );

        } else {

            /*
             * Caso con consumos/recargas.
             *
             * La respuesta es larga y WhatsApp queda
             * posicionado en la parte inferior.
             *
             * Hacemos scroll hacia arriba para mostrar
             * el encabezado "Actualmente tienes activa una recarga".
             */
            actor.attemptsTo(
                    ScrollGradual.subir(0.30),
                    WaitFor.aTime(1000)
            );


            AndroidObject androidObject =
                    new AndroidObject();


            /*
             * Después del primer scroll revisamos
             * si ya apareció RECARGA_ACTIVA.
             */
            if (!androidObject.textoContiene(
                    actor,
                    RECARGA_ACTIVA
            )) {

                /*
                 * Si la respuesta es todavía más larga,
                 * hacemos un segundo scroll pequeño.
                 */
                actor.attemptsTo(
                        ScrollGradual.subir(0.25),
                        WaitFor.aTime(1000)
                );
            }


            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(
                            RECARGA_ACTIVA
                    ),

                    ValidarTextoQueContengaX.elTextoContiene(
                            SALDO_VENCE
                    )
            );
        }


        CapturaDePantallaMovil.tomarCapturaPantalla(
                "Se valida el saldo de la linea"
        );

        ReportHooks.registrarPaso(
                "Se valida el saldo de la linea"
        );


        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }


    public static Performable consultarSaldoLinea() {

        return instrumented(
                ConsultarSaldoLinea.class
        );
    }
}