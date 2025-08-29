package utils;

import interactions.comunes.WaitFor;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.NoSuchElementException;


public class AndroidObject {

    public void HideKeyboard(Actor actor) {
        androidDriver(actor).hideKeyboard();
    }


    //SCROLL
    public void SwipeToElement(Actor actor, String label) {
        androidDriver(actor).findElementByAndroidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().text(\""
                                + label + "\"));")
                .click();
    }


    public void UnScrollArribaInicio(Actor actor) {
        try {
            androidDriver(actor).findElementByAndroidUIAutomator(
                    "new UiScrollable(new UiSelector().resourceIdMatches(\"android:id/list\").scrollable(true)).scrollBackward()");
        } catch (Exception e) {
            e.printStackTrace(); // Agrega esto para ver si hay algún error
        }
    }

    public void UnScrollAbajo(Actor actor) {
        try {
            androidDriver(actor).findElementByAndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()");
        } catch (Exception e) {
        }
    }

    public static void scrollToText(Actor actor, String texto) {
        try {
            androidDriver(actor).findElement(
                    new MobileBy.ByAndroidUIAutomator((
                            "new UiScrollable(new UiSelector().scrollable(true))" +
                                    ".scrollIntoView(new UiSelector().textContains(\"" + texto + "\"))"
                    ))
            );
        } catch (Exception e) {
            throw new RuntimeException("No se encontró el texto al hacer scroll: " + texto, e);
        }
    }

    //VALIDACIONES
    public boolean validarTexto(Actor actor, String text) {
        try {
            return androidDriver(actor)
                    .findElementByAndroidUIAutomator("new UiSelector().text(\"" + text + "\")")
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Texto no encontrado: " + text);
            return false;
        }
    }


    public void ElTextoContiene(Actor actor, String text) {
        androidDriver(actor).findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")")
                .isDisplayed();
    }


    //CLICK
    public void ClickByText(Actor actor, String text) {
        actor.attemptsTo(WaitFor.aTime(1000));
        androidDriver(actor).findElementByAndroidUIAutomator("new UiSelector().text(\"" + text + "\")")
                .click();

    }

    public void ClickElTextoContiene(Actor actor, String text) {
        androidDriver(actor).findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")")
                .click();

    }

    public void Atras(Actor actor) {
        androidDriver(actor).navigate().back();
    }


    public AndroidDriver getAndroidDriver(Actor actor) {
        return androidDriver(actor);
    }


    @SuppressWarnings("unchecked")
    public static AndroidDriver androidDriver(Actor actor) {
        return (AndroidDriver) ((WebDriverFacade) getDriver(actor)).getProxiedDriver();
    }


    private static WebDriverFacade getDriver(Actor actor) {
        return ((WebDriverFacade) BrowseTheWeb.as(actor).getDriver());
    }

    public TouchAction withAction(Actor actor) {
        return new TouchAction(androidDriver(actor));
    }

    public void SwitchtoFrame(Actor actor, int id) {
        androidDriver(actor).switchTo().frame(id);
    }
}