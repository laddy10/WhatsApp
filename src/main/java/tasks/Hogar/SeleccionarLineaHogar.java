package tasks.Hogar;

import interactions.wait.WaitForResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Text;
import utils.ContextoST;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.LBL_MENU_LINEAS;
import static userinterfaces.WhatsAppPage.TXT_CAJA_MENSAJE;
import static utils.Constantes.*;


public class SeleccionarLineaHogar implements Task {

    private final User user = TestDataProvider.getRealUser();

    /** Texto de la opción 1 cuando el menú lista cuentas hogar: "1 - AVCR 36 23A-## ###-###". */
    private static final Pattern OPCION_1 = Pattern.compile("(?m)^\\s*1\\s*-\\s*(.+)$");

    private static final String CUENTA_SIN_IDENTIFICAR = "Cuenta hogar (opción 1 del menú)";

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Contrato st-context: en Hogar no se elige una LÍNEA sino una CUENTA. El menú del
        // bot lista la cuenta hogar como una dirección ("1 - AVCR 36 23A-## ###-###") y
        // este flujo siempre manda la opción 1, así que la dirección se lee del propio
        // menú: informar la cuenta real, no una suposición.
        ContextoST.registrarCuenta(leerCuentaDelMenu(actor));

        actor.attemptsTo(
                Enter.theValue("1").into(TXT_CAJA_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withAnyTextFailingOn(
                        60,
                        List.of(
                                OPCIONES_MOSTRADAS_ANTERIORMENTE,
                                TU_RESPUESTA_NO_ES_VALIDA,
                                INGRESAR_OPCION_VALIDA,
                                NO_ENTENDI_TU_MENSAJE),
                        POLITICA_TRATAMIENTO,
                        MENU_PRINCIPAL));
    }

    /**
     * Dirección de la cuenta hogar tal como la muestra el menú del bot. Nunca lanza: si el
     * menú no está o cambió de formato, devuelve una descripción genérica — este dato es
     * para el informe, no para la prueba.
     */
    private <T extends Actor> String leerCuentaDelMenu(T actor) {
        try {
            Matcher m = OPCION_1.matcher(Text.of(LBL_MENU_LINEAS).viewedBy(actor).asString());
            if (m.find()) {
                String descripcion = m.group(1).trim();
                if (!descripcion.isEmpty()) {
                    return descripcion;
                }
            }
        } catch (Exception e) {
            System.out.println(
                    "[ContextoST] No se pudo leer la cuenta hogar del menú: " + e.getMessage());
        }
        return CUENTA_SIN_IDENTIFICAR;
    }

    public static Performable seleccionarLineaHogar() {
        return instrumented(SeleccionarLineaHogar.class);
    }
}
