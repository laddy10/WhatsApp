package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import io.appium.java_client.MobileBy;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;

public class ClickOpcionMenu implements Interaction {

  private final String textoMenu;

  public ClickOpcionMenu(String textoMenu) {
    this.textoMenu = textoMenu;
  }

  @Override
  @Step("Clickea la opción de menú que contiene '#textoMenu' asegurando que sea el elemento clickeable")
  public <T extends Actor> void performAs(T actor) {
    // Busca el elemento que contiene el texto y obtiene su ancestro clickeable más cercano
    // Esto evita clickear burbujas de chat estáticas que contengan el mismo texto
    Target opcion = Target.the("Opción de menú: " + textoMenu)
        .located(MobileBy.xpath("//*[contains(@text, '" + textoMenu + "')]/ancestor-or-self::*[@clickable='true'][1]"));
    
    actor.attemptsTo(Click.on(opcion));
  }

  public static Interaction conTexto(String textoMenu) {
    return instrumented(ClickOpcionMenu.class, textoMenu);
  }
}
