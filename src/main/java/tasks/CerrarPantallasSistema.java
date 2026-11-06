package tasks;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import hooks.ReportHooks;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;

public class CerrarPantallasSistema implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Popup USB
        try {
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

        } catch (Exception e) {
            System.out.println("No se pudo cerrar popup USB: " + e.getMessage());
        }

        // Popup SIM Claro
        try {
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
            System.out.println("No se pudo cerrar popup SIM Claro: " + e.getMessage());
        }

        // Popup actualización del sistema
        try {
            boolean actualizacionVisible =
                    AndroidObject.androidDriver(actor)
                            .findElementsByAndroidUIAutomator(
                                    "new UiSelector().textContains(\"Actualización del sistema\")"
                            )
                            .size() > 0;

            if (actualizacionVisible) {

                ReportHooks.registrarPaso(
                        "Se detectó ventana 'Actualización del sistema'. Se selecciona 'Recordarme más tarde'."
                );

                AndroidObject.androidDriver(actor)
                        .findElementByAndroidUIAutomator(
                                "new UiSelector().text(\"Recordarme más tarde\")"
                        )
                        .click();

                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.out.println(
                    "No se pudo cerrar actualización del sistema: " + e.getMessage()
            );
        }
    }

    public static CerrarPantallasSistema verificar() {
        return instrumented(CerrarPantallasSistema.class);
    }
}