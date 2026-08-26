package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

public class WordAppium {

  private static final Logger LOGGER = Logger.getLogger(WordAppium.class.getName());

  private static final String TEMPLATE_PATH =
      System.getProperty("user.dir")
          + File.separator
          + "ruta"
          + File.separator
          + "PlantillaInforme.docx";
  private static final String CAPTURAS_DIR = "Capturas/";
  private static final String REPORTES_DIR =
      System.getProperty("user.dir") + File.separator + "reportes";

  // Captura del momento del fallo: la deja ErrorScreenshotHooks. Ese hook corre ANTES
  // que el informe (mayor 'order' en un @After = se ejecuta primero), así que cuando
  // llegamos aquí el archivo ya existe.
  private static final String ERROR_DIR = "Error";
  private static final String ERROR_FILE = "error.png";

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

  private static final ResourceBundle messages = ResourceBundle.getBundle("messages");
  private static final Properties STEP_MESSAGES = new Properties();

  static {
    try (InputStream input =
        WordAppium.class.getClassLoader().getResourceAsStream("messages.properties")) {
      if (input != null) {
        STEP_MESSAGES.load(input);
        LOGGER.info("messages.properties cargado correctamente.");
      } else {
        LOGGER.warning("messages.properties no encontrado.");
      }
    } catch (IOException e) {
      LOGGER.warning("Error al cargar messages.properties: " + e.getMessage());
    }
  }

  public static void generarReporte(
      String nombreEscenario,
      String[] pasosEjecutados,
      String numero,
      String duracionFormato,
      String pasoFallido,
      String estadoFinal,
      String motivoFallo) {
    boolean fallo = "FAILED".equalsIgnoreCase(estadoFinal);

    File[] listado = new File(CAPTURAS_DIR).listFiles();
    File[] capturas = listado == null ? new File[0] : listado;

    // Sin capturas y sin fallo no hay nada que documentar. Pero si FALLÓ, el informe se
    // genera igual: antes se salía por aquí y una prueba que se caía antes de la primera
    // captura no dejaba ningún documento — justo el caso en el que hace falta.
    if (capturas.length == 0 && !fallo) {
      LOGGER.warning("No hay capturas para procesar.");
      return;
    }

    new File(REPORTES_DIR).mkdirs();
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String nombreArchivo =
        "Prueba_" + nombreEscenario.replaceAll("\\s+", "_") + "_" + timestamp + ".docx";
    String rutaDestino = REPORTES_DIR + File.separator + nombreArchivo;
    System.out.println("📄 Guardando en: " + rutaDestino);
    try (FileInputStream fis = new FileInputStream(TEMPLATE_PATH);
        XWPFDocument doc = new XWPFDocument(fis);
        FileOutputStream fos = new FileOutputStream(rutaDestino)) {

      reemplazarTexto(doc, "{{ESCENARIO}}", nombreEscenario);
      reemplazarTexto(doc, "{{FECHA}}", FORMATTER.format(LocalDateTime.now()));
      reemplazarTexto(doc, "{{LINEA}}", numero);
      reemplazarTexto(doc, "{{DURACION}}", duracionFormato);
      // La plantilla reserva una sección "CONCLUSIÓN DE PRUEBAS"; sin esto el marcador
      // salía tal cual ("{{CONCLUSION}}") en todos los informes.
      reemplazarTexto(
          doc,
          "{{CONCLUSION}}",
          construirConclusion(estadoFinal, pasoFallido, motivoFallo, pasosEjecutados.length));
      agregarPasosYCapturas(doc, pasosEjecutados, capturas);
      agregarResultado(doc, estadoFinal, pasoFallido, motivoFallo);

      doc.write(fos);
      LOGGER.info("Reporte generado correctamente: " + rutaDestino);

    } catch (IOException | InvalidFormatException e) {
      LOGGER.severe("Error generando el reporte Word: " + e.getMessage());
    }

    eliminarCapturas(capturas);
  }

  private static void agregarPasosYCapturas(XWPFDocument doc, String[] pasos, File[] capturas)
      throws IOException, InvalidFormatException {
    for (String paso : pasos) {
      XWPFParagraph p = doc.createParagraph();
      p.setSpacingBefore(200);
      XWPFRun run = p.createRun();
      run.setText(paso);
      run.setFontSize(12);

      // 👉 Paso 2: Insertar un párrafo vacío como espacio entre texto e imagen
      XWPFParagraph espacio = doc.createParagraph();
      XWPFRun espacioRun = espacio.createRun();
      espacioRun.setText(""); // este es el salto en blanco

      File imagen = buscarCapturaDePaso(paso, capturas);
      if (imagen != null) {
        XWPFParagraph imgP = doc.createParagraph();
        XWPFRun imgRun = imgP.createRun();
        try (FileInputStream is = new FileInputStream(imagen)) {
          imgRun.addPicture(
              is, Document.PICTURE_TYPE_PNG, imagen.getName(), Units.toEMU(150), Units.toEMU(270));
        }
      } else {
        XWPFRun noImgRun = doc.createParagraph().createRun();
        noImgRun.setText("(No se encontró imagen para este paso)");
      }
    }
  }

  /** Resumen de una frase para la sección "CONCLUSIÓN DE PRUEBAS" de la plantilla. */
  private static String construirConclusion(
      String estadoFinal, String pasoFallido, String motivoFallo, int pasosEjecutados) {
    if (!"FAILED".equalsIgnoreCase(estadoFinal)) {
      return pasosEjecutados > 0
          ? "La prueba se ejecutó completamente: "
              + pasosEjecutados
              + " paso(s) validados sin errores."
          : "La prueba finalizó sin errores.";
    }

    StringBuilder conclusion = new StringBuilder("La prueba FALLÓ");
    if (pasoFallido != null && !pasoFallido.trim().isEmpty()) {
      conclusion.append(" en el paso \"").append(pasoFallido.trim()).append("\"");
    }
    conclusion.append(". ");
    if (motivoFallo == null || motivoFallo.trim().isEmpty()) {
      conclusion.append("No se pudo determinar el motivo automáticamente; ver la captura del ");
      conclusion.append("momento del fallo al final del informe.");
    } else {
      conclusion.append("Motivo: ").append(motivoFallo.trim());
    }
    if (pasosEjecutados > 0) {
      conclusion
          .append(" Alcanzó a ejecutar ")
          .append(pasosEjecutados)
          .append(" paso(s) antes del fallo.");
    }
    return conclusion.toString();
  }

  /**
   * Cierra el informe con el resultado. Si la prueba falló documenta en qué paso, por qué (mensaje
   * corto, sin la traza de código) y la captura de la pantalla en ese momento — que es lo que
   * antes había que ir a buscar al reporte de Serenity.
   */
  private static void agregarResultado(
      XWPFDocument doc, String estadoFinal, String pasoFallido, String motivoFallo)
      throws IOException, InvalidFormatException {
    boolean fallo = "FAILED".equalsIgnoreCase(estadoFinal);

    XWPFParagraph titulo = doc.createParagraph();
    titulo.setSpacingBefore(400);
    XWPFRun tituloRun = titulo.createRun();
    tituloRun.setBold(true);
    tituloRun.setFontSize(14);
    tituloRun.setColor(fallo ? "C00000" : "2E7D32");
    tituloRun.setText(fallo ? "RESULTADO: FALLIDO" : "RESULTADO: EXITOSO");

    if (!fallo) {
      return;
    }

    if (pasoFallido != null && !pasoFallido.trim().isEmpty()) {
      agregarParrafo(doc, "Paso donde falló: " + pasoFallido, true);
    }

    String motivo =
        (motivoFallo == null || motivoFallo.trim().isEmpty())
            ? "No se pudo determinar automáticamente (ver el reporte de Serenity)."
            : motivoFallo;
    agregarParrafo(doc, "Motivo del fallo: " + motivo, true);

    File captura = new File(ERROR_DIR, ERROR_FILE);
    if (captura.isFile()) {
      agregarParrafo(doc, "Pantalla en el momento del fallo:", true);
      try (FileInputStream is = new FileInputStream(captura)) {
        XWPFRun imgRun = doc.createParagraph().createRun();
        imgRun.addPicture(
            is, Document.PICTURE_TYPE_PNG, captura.getName(), Units.toEMU(150), Units.toEMU(270));
      }
    } else {
      agregarParrafo(doc, "(No se pudo capturar la pantalla del fallo)", false);
    }
  }

  private static void agregarParrafo(XWPFDocument doc, String texto, boolean etiquetaEnNegrita) {
    XWPFParagraph p = doc.createParagraph();
    p.setSpacingBefore(120);
    int corte = etiquetaEnNegrita ? texto.indexOf(':') : -1;

    if (corte > 0) {
      XWPFRun etiqueta = p.createRun();
      etiqueta.setBold(true);
      etiqueta.setFontSize(12);
      etiqueta.setText(texto.substring(0, corte + 1) + " ");

      XWPFRun valor = p.createRun();
      valor.setFontSize(12);
      valor.setText(texto.substring(corte + 1).trim());
    } else {
      XWPFRun run = p.createRun();
      run.setFontSize(12);
      run.setText(texto);
    }
  }

  private static File buscarCapturaDePaso(String paso, File[] capturas) {
    String normalizado = paso.toLowerCase().replaceAll("[^a-z0-9]", "_");
    for (File f : capturas) {
      if (f.getName().toLowerCase().contains(normalizado)) {
        return f;
      }
    }
    return null;
  }

  private static String generarConclusion(
      String[] pasos, String pasoFallido, String estadoFinal, String linea) {
    StringBuilder conclusion = new StringBuilder();
    //    conclusion.append(messages.getString("report.initial_message").replace("{0}",
    // linea)).append("\n\n");

    boolean fallo = false;
    for (String paso : pasos) {
      if (fallo) {
        conclusion.append("⏭️ Paso pendiente: ").append(paso).append("\n");
        continue;
      }

      String descripcion = obtenerDescripcionPaso(paso);
      conclusion.append(descripcion).append("\n");

      if (pasoFallido != null && paso.equalsIgnoreCase(pasoFallido)) {
        conclusion.append("❌ Falló en el paso: ").append(paso).append("\n");
        fallo = true;
      }
    }

    conclusion.append("\n");
    return "FAILED".equalsIgnoreCase(estadoFinal)
        ? conclusion.append("⚠️ La prueba finalizó con errores.").toString()
        : conclusion.append(" ").toString();
  }

  private static String obtenerDescripcionPaso(String paso) {
    String key = paso.toLowerCase().replaceAll("[^a-z0-9]", "_");
    if (STEP_MESSAGES.containsKey(key)) {
      return STEP_MESSAGES.getProperty(key);
    }
    for (String k : STEP_MESSAGES.stringPropertyNames()) {
      if (key.contains(k)) {
        return STEP_MESSAGES.getProperty(k);
      }
    }
    return paso;
  }

  private static void eliminarCapturas(File[] capturas) {
    for (File captura : capturas) {
      try {
        Files.deleteIfExists(captura.toPath());
      } catch (IOException e) {
        LOGGER.warning("No se pudo eliminar la captura: " + captura.getName());
      }
    }
  }

  private static void reemplazarTexto(XWPFDocument doc, String marcador, String valor) {
    for (XWPFParagraph p : doc.getParagraphs()) {
      for (XWPFRun r : p.getRuns()) {
        String text = r.getText(0);
        if (text != null && text.contains(marcador)) {
          r.setText(text.replace(marcador, valor), 0);
        }
      }
    }

    for (XWPFTable t : doc.getTables()) {
      for (XWPFTableRow row : t.getRows()) {
        for (XWPFTableCell cell : row.getTableCells()) {
          for (XWPFParagraph p : cell.getParagraphs()) {
            for (XWPFRun r : p.getRuns()) {
              String text = r.getText(0);
              if (text != null && text.contains(marcador)) {
                r.setText(text.replace(marcador, valor), 0);
              }
            }
          }
        }
      }
    }
  }

  public static void inicializarPlantillaReporte() {
    try {
      String origen =
          System.getProperty("user.dir")
              + File.separator
              + "ruta"
              + File.separator
              + "PlantillaInforme.docx";
      String destino =
          System.getProperty("user.dir")
              + File.separator
              + "ruta"
              + File.separator
              + "InformeFinal.docx";
      Files.copy(Paths.get(origen), Paths.get(destino), StandardCopyOption.REPLACE_EXISTING);
      LOGGER.info("Plantilla copiada correctamente.");
    } catch (IOException e) {
      LOGGER.severe("Error al copiar la plantilla: " + e.getMessage());
    }
  }
}
