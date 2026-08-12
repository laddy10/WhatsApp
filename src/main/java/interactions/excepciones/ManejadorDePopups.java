package interactions.excepciones;

import hooks.ReportHooks;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManejadorDePopups {

    private ManejadorDePopups() {}

    /**
     * Busca rápidamente si existe el popup del sistema (ej. SIM Claro) y si existe, hace clic en Cancelar.
     */
    public static void cerrarPopupSiExiste(Actor actor) {
        AndroidDriver driver = getAndroidDriver(actor);
        
        // Guardamos el timeout original implícito de Appium para restaurarlo luego (usualmente 3000ms en el proyecto)
        long currentTimeout = 3000; 

        try {
            // Cambiamos el timeout a 0 para que la búsqueda sea instantánea y no perdamos tiempo si el popup no existe
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

            // Verificamos si existe el mensaje de SIM Claro o el botón genérico Cancelar
            String queryPopup = "new UiSelector().textContains(\"SIM Claro\").or(new UiSelector().textContains(\"Cancelar\"))";
            List<WebElement> popupElements = driver.findElements(MobileBy.AndroidUIAutomator(queryPopup));

            if (!popupElements.isEmpty()) {
                ReportHooks.registrarPaso("Alerta interceptada en pantalla. Intentando cerrar el popup...");
                
                // Buscar el botón 'Cancelar' (nativo)
                String queryBoton = "new UiSelector().textMatches(\"(?i)Cancelar\")";
                List<WebElement> botonesCancelar = driver.findElements(MobileBy.AndroidUIAutomator(queryBoton));
                
                if (!botonesCancelar.isEmpty()) {
                    botonesCancelar.get(0).click();
                    ReportHooks.registrarPaso("Popup cerrado exitosamente dándole a 'Cancelar'.");
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo cerrar el popup o no existía: " + e.getMessage());
        } finally {
            // Restauramos el timeout original de 3 segundos definido en serenity.properties
            driver.manage().timeouts().implicitlyWait(currentTimeout, TimeUnit.MILLISECONDS);
        }
    }

    @SuppressWarnings("unchecked")
    private static AndroidDriver getAndroidDriver(Actor actor) {
        return (AndroidDriver) ((WebDriverFacade) net.serenitybdd.screenplay.abilities.BrowseTheWeb.as(actor).getDriver()).getProxiedDriver();
    }
}
