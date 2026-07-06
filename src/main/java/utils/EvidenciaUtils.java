package utils;

import hooks.ReportHooks;
import net.serenitybdd.core.Serenity;

public class EvidenciaUtils {

  private static int contadorPasos = 1;

  public static void registrarCaptura(String paso) {
    String pasoNumerado = contadorPasos++ + ". " + paso;

    // Registrar en tu sistema actual
    ReportHooks.registrarPaso(pasoNumerado);
    CapturaDePantallaMovil.tomarCapturaPantalla(pasoNumerado);

    // La política AFTER_EACH_STEP de Serenity realiza la captura del paso.
    Serenity.recordReportData().withTitle(paso).andContents(pasoNumerado);
  }

  public static void reiniciarContador() {
    contadorPasos = 1;
  }
}
