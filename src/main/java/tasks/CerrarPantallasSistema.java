package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import hooks.ReportHooks;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;

public class CerrarPantallasSistema implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        try {

            // Popup USB
            boolean dialogoUsbVisible =
                    AndroidObject.androidDriver(actor)
                            .findElementsByAndroidUIAutomator(
                                    "new UiSelector().textContains(\"USB para\")"
                            )
                            .size() > 0;

            if (dialogoUsbVisible) {

                ReportHooks.registrarPaso(
                        "Se detectó ventana del sistema 'Usar USB para'. Se procede a cerrarla."
                );

                AndroidObject.androidDriver(actor)
                        .findElementByAndroidUIAutomator(
                                "new UiSelector().text(\"CANCELAR\")"
                        )
                        .click();

                Thread.sleep(1000);
            }

            // Popup SIM Claro
            boolean popupSimClaroVisible =
                    AndroidObject.androidDriver(actor)
                            .findElementsByAndroidUIAutomator(
                                    "new UiSelector().textContains(\"SIM Claro\")"
                            )
                            .size() > 0;

            if (popupSimClaroVisible) {

                ReportHooks.registrarPaso(
                        "Se detectó popup 'SIM Claro'. Se procede a cancelarlo."
                );

                AndroidObject.androidDriver(actor)
                        .findElementByAndroidUIAutomator(
                                "new UiSelector().textMatches(\"(?i)cancelar\")"
                        )
                        .click();

                Thread.sleep(1000);
            }

        } catch (Exception e) {

            System.out.println(
                    "No se detectaron ventanas del sistema pendientes."
            );
        }
    }

    public static CerrarPantallasSistema verificar() {
        return instrumented(CerrarPantallasSistema.class);
    }
}