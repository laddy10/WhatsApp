package tasks.Postpago.TusPagosYFacturas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.Constantes.*;

import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import io.appium.java_client.touch.offset.PointOption;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.WebElement;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

public class AbrirFactura extends AndroidObject implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(WaitForResponse.withText(PDF_FACTURA, 15));
    abrirAdjuntoPdf(actor);

    CapturaDePantallaMovil.tomarCapturaPantalla("Clic en enlace de factura PDF");
    ReportHooks.registrarPaso("Clic en enlace de factura PDF");

    manejarSelectorDeLectorPdf(actor);

    actor.attemptsTo(
        WaitForResponse.withAnyText(ESTE_ARCHIVO_ESTA_PROTEGIDO),
        ValidarTexto.validarTexto(ESTE_ARCHIVO_ESTA_PROTEGIDO),
        Click.on(TXT_CONTRASENA_FACTURA),
        WaitFor.aTime(5000),
        Enter.theValue("99321987").into(TXT_CONTRASENA_FACTURA));

    CapturaDePantallaMovil.tomarCapturaPantalla("Ingresar contrasena");
    ReportHooks.registrarPaso("Ingresar contrasena");

    actor.attemptsTo(
        ClickElementByText.clickElementByText(ABRIR),
        WaitFor.aTime(3000),
        ValidarTextoQueContengaX.elTextoContiene(PDF_FACTURA));

    CapturaDePantallaMovil.tomarCapturaPantalla("Factura validada correctamente");
    ReportHooks.registrarPaso("Factura validada correctamente");

    actor.attemptsTo(Atras.irAtras(), SalirConversacion.salir());
  }

  private <T extends Actor> void abrirAdjuntoPdf(T actor) {
    List<WebElementFacade> adjuntos = ADJUNTO_FACTURA_PDF.resolveAllFor(actor);
    if (!adjuntos.isEmpty()) {
      actor.attemptsTo(Click.on(ADJUNTO_FACTURA_PDF), WaitFor.aTime(3000));
    } else {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PDF_FACTURA), WaitFor.aTime(3000));
    }

    if (textoContiene(actor, PDF_FACTURA)
        && !textoContiene(actor, ESTE_ARCHIVO_ESTA_PROTEGIDO)
        && !textoContiene(actor, SOLO_UNA_VEZ)
        && LBL_MAS.resolveAllFor(actor).isEmpty()) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PDF_FACTURA), WaitFor.aTime(3000));
    }

    if (textoContiene(actor, PDF_FACTURA)
        && !textoContiene(actor, ESTE_ARCHIVO_ESTA_PROTEGIDO)
        && !textoContiene(actor, SOLO_UNA_VEZ)
        && LBL_MAS.resolveAllFor(actor).isEmpty()) {
      tocarAdjuntoPdfPorCoordenadas(actor);
      actor.attemptsTo(WaitFor.aTime(3000));
    }
  }

  private <T extends Actor> void tocarAdjuntoPdfPorCoordenadas(T actor) {
    WebElement pdf =
        androidDriver(actor)
            .findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + PDF_FACTURA + "\")");
    int x = Math.max(30, pdf.getLocation().getX() - 45);
    int y = pdf.getLocation().getY() + (pdf.getSize().getHeight() / 2);
    withAction(actor).tap(PointOption.point(x, y)).perform();
  }

  private <T extends Actor> void manejarSelectorDeLectorPdf(T actor) {
    if (textoContiene(actor, ESTE_ARCHIVO_ESTA_PROTEGIDO)) {
      return;
    }

    if (textoContiene(actor, "Lector")) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Lector"), WaitFor.aTime(3000));
      return;
    }

    List<WebElementFacade> btnLectorPdfDrive = LBL_MAS.resolveAllFor(actor);
    if (!btnLectorPdfDrive.isEmpty()) {
      actor.attemptsTo(Click.on(LBL_MAS), Click.on(BTN_LECTOR_PDF_DRIVE), WaitFor.aTime(2000));
      return;
    }

    if (textoContiene(actor, SOLO_UNA_VEZ)) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(SOLO_UNA_VEZ), WaitFor.aTime(2000));
    }
  }

  public static Performable abrirFactura() {
    return instrumented(AbrirFactura.class);
  }
}
