package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.*;
import static utils.ConstantesPost.*;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.SalirConversacion;
import utils.CapturaDePantallaMovil;

public class TuHistorialFacturas implements Task {

    private static final String MENSAJE_CAPTURA_4 =
            "Validar historial de facturas";

    private static final int CANTIDAD_FACTURAS_ESPERADAS = 6;

    @Override
    public <T extends Actor> void performAs(T actor) {

        /*
         * ============================================================
         * 1. VALIDAR ENCABEZADO DEL HISTORIAL
         * ============================================================
         */
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(
                        FACTURAS_RECIENTES
                ),

                ValidarTextoQueContengaX.elTextoContiene(
                        DESCARGALAS_LINK
                )
        );

        /*
         * ============================================================
         * 2. VALIDAR LAS ÚLTIMAS 6 FACTURAS
         * ============================================================
         *
         * No se validan meses específicos porque cambian
         * dependiendo de la fecha de ejecución.
         *
         * Se valida la estructura real:
         *
         * - Valor: $
         * - enlace de descarga yoiz.me
         */
        validarUltimas6Facturas(actor);

        /*
         * ============================================================
         * 3. EVIDENCIA
         * ============================================================
         */
        CapturaDePantallaMovil.tomarCapturaPantalla(
                MENSAJE_CAPTURA_4
        );

        ReportHooks.registrarPaso(
                MENSAJE_CAPTURA_4
        );

        /*
         * ============================================================
         * 4. CERRAR CONVERSACIÓN
         * ============================================================
         */
        actor.attemptsTo(
                SalirConversacion.salir()
        );
    }


    private void validarUltimas6Facturas(Actor actor) {

        List<WebElementFacade> mensajes =
                LBL_MENSAJES.resolveAllFor(actor);

        int valoresEncontrados = 0;
        int enlacesEncontrados = 0;

        for (WebElementFacade mensaje : mensajes) {

            String texto = mensaje.getText();

            if (texto == null || texto.trim().isEmpty()) {
                continue;
            }

            /*
             * Contar cuántas veces aparece "Valor: $"
             */
            valoresEncontrados +=
                    contarOcurrencias(
                            texto,
                            "Valor: $"
                    );

            /*
             * Contar cuántos links de factura aparecen.
             */
            enlacesEncontrados +=
                    contarOcurrencias(
                            texto,
                            "https://yoiz.me/"
                    );
        }

        ReportHooks.registrarPaso(
                "Facturas detectadas - Valores: "
                        + valoresEncontrados
                        + " | Enlaces: "
                        + enlacesEncontrados
        );

        /*
         * Para considerar válido el historial deben existir
         * mínimo 6 valores y 6 enlaces.
         */
        if (valoresEncontrados < CANTIDAD_FACTURAS_ESPERADAS
                || enlacesEncontrados < CANTIDAD_FACTURAS_ESPERADAS) {

            CapturaDePantallaMovil.tomarCapturaPantalla(
                    "Historial incompleto de facturas"
            );

            throw new IllegalStateException(
                    "El historial no contiene las últimas "
                            + CANTIDAD_FACTURAS_ESPERADAS
                            + " facturas esperadas. "
                            + "Valores encontrados: "
                            + valoresEncontrados
                            + ", enlaces encontrados: "
                            + enlacesEncontrados
            );
        }

        ReportHooks.registrarPaso(
                "Se validaron correctamente las últimas "
                        + CANTIDAD_FACTURAS_ESPERADAS
                        + " facturas del historial."
        );
    }


    private int contarOcurrencias(
            String texto,
            String patron) {

        int contador = 0;
        int indice = 0;

        while ((indice = texto.indexOf(
                patron,
                indice
        )) != -1) {

            contador++;

            indice += patron.length();
        }

        return contador;
    }


    public static Performable tuHistorialFacturas() {

        return instrumented(
                TuHistorialFacturas.class
        );
    }
}