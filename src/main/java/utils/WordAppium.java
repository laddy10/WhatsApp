package utils;

import com.ibm.icu.text.MessageFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class WordAppium {

    private static final Logger LOGGER = Logger.getLogger(WordAppium.class.getName());
    private static final String TEMPLATE_PATH = System.getProperty("user.dir") + File.separator + "ruta" + File.separator + "InformeFinal.docx";
    private static final String CAPTURAS_DIR = "Capturas/";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void generarReporte(String nombreEscenario, String[] pasosEjecutados, String linea, String duracionFormato, String pasoFallido, String estadoFinal) {
        File capturasFolder = new File(CAPTURAS_DIR);
        File[] capturasFiles = capturasFolder.listFiles();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = "Reporte_" + nombreEscenario.replaceAll("\\s+", "_") + "_" + timestamp + ".docx";
        String rutaDestino = System.getProperty("user.dir") + File.separator + "reportes" + File.separator + nombreArchivo;


        if (capturasFiles == null || capturasFiles.length == 0) {
            LOGGER.warning("No hay capturas para procesar.");
            return;
        }

        new File(System.getProperty("user.dir") + File.separator + "reportes").mkdirs();

        try (FileInputStream fis = new FileInputStream(TEMPLATE_PATH);
             XWPFDocument document = new XWPFDocument(fis);
            FileOutputStream fos = new FileOutputStream(rutaDestino)) {


                // Reemplazar marcadores en plantilla
            reemplazarTextoEnDocumento(document, "{{ESCENARIO}}", nombreEscenario);
            reemplazarTextoEnDocumento(document, "{{FECHA}}", FORMATTER.format(LocalDateTime.now()));
            reemplazarTextoEnDocumento(document, "{{LINEA}}", linea);
            reemplazarTextoEnDocumento(document, "{{DURACION}}", duracionFormato);
            reemplazarTextoEnDocumento(document, "{{CONCLUSION}}", generarConclusion(nombreEscenario, pasosEjecutados, linea, pasoFallido, estadoFinal));


        //    agregarEncabezado(document, nombreEscenario, linea, pasosEjecutados);
            agregarPasosYCapturas(document, pasosEjecutados, capturasFiles);


            document.write(fos);
            LOGGER.info("Reporte actualizado correctamente.");

        } catch (IOException | InvalidFormatException e) {
            LOGGER.severe("Error generando el reporte: " + e.getMessage());
        }

        eliminarCapturas(capturasFiles);
    }

    private static void agregarPasosYCapturas(XWPFDocument doc, String[] pasos, File[] capturas)
            throws IOException, InvalidFormatException {

        for (String paso : pasos) {
            XWPFParagraph pasoParrafo = doc.createParagraph();
            pasoParrafo.setSpacingBefore(200);
            pasoParrafo.setAlignment(ParagraphAlignment.BOTH);

            XWPFRun pasoRun = pasoParrafo.createRun();
            pasoRun.setBold(false);
            pasoRun.setFontSize(11);
            pasoRun.setText(" ✅ " + paso);

            XWPFParagraph paragraph = doc.createParagraph();
            XWPFRun run = paragraph.createRun();

            File imagenPaso = buscarImagenDePaso(paso, capturas);
            if (imagenPaso != null) {
                XWPFParagraph imagenParrafo = doc.createParagraph();
                imagenParrafo.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun imagenRun = imagenParrafo.createRun();
                try (FileInputStream is = new FileInputStream(imagenPaso)) {
                    imagenRun.addPicture(is, Document.PICTURE_TYPE_PNG, imagenPaso.getName(),
                            Units.toEMU(150), Units.toEMU(270));
                    imagenRun.addBreak();
                }
            } else {
                XWPFRun noImagen = doc.createParagraph().createRun();
                noImagen.setText("(No se encontró imagen para este paso)");
            }
        }
    }

    private static File buscarImagenDePaso(String paso, File[] capturas) {
        String pasoNormalizado = paso
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "_"); // igual que en CapturaDePantallaMovil

        for (File img : capturas) {
            if (img.getName().toLowerCase().contains(pasoNormalizado)) {
                return img;
            }
        }
        return null;
    }


    /**
     * Genera la conclusión del reporte basada en la ejecución de los pasos
     */
    private static String generarConclusion(String escenario, String[] pasos, String linea, String pasoFallido, String estadoFinal) {
        StringBuilder conclusion = new StringBuilder();
        conclusion.append(iniciarConclusion(linea)).append("\n\n");


        boolean falloDetectado = false;

        for (String paso : pasos) {
            if (falloDetectado) {
                conclusion.append("⏭️ Paso pendiente (no ejecutado): ").append(paso).append(".\n");
                continue;
            }

            String descripcion = procesarPasoEspecifico(paso);
            conclusion.append("").append(descripcion).append("\n");

            if (pasoFallido != null && paso.equalsIgnoreCase(pasoFallido)) {
                conclusion.append("❌ La prueba falló en el paso: ").append(paso).append(".\n");
                falloDetectado = true;
            }

        }

        conclusion.append("\n");

        if ("FAILED".equalsIgnoreCase(estadoFinal)) {
            conclusion.append("⚠️ La prueba finalizó con errores.");
        } else {
            conclusion.append("✅ Todos los pasos fueron completados exitosamente.");
        }

        return conclusion.toString();
    }


    private static final ResourceBundle messages;

    static {
        try {
            // Cargar el ResourceBundle desde el classpath
            messages = ResourceBundle.getBundle("messages");
        } catch (MissingResourceException e) {
            LOGGER.severe("No se pudo cargar el archivo de mensajes: " + e.getMessage());
            throw new RuntimeException("Error crítico: Archivo de recursos no encontrado", e);
        }
    }

    private static String iniciarConclusion(String linea) {
        return MessageFormat.format(messages.getString("report.initial_message"), linea);
    }

    private static String procesarPasoPendiente(String paso) {
        return "⏭️ Paso pendiente (no ejecutado): " + paso + ". ";
    }

    private static final Properties STEP_MESSAGES = new Properties();

    static {
        try (InputStream input = WordAppium.class.getClassLoader().getResourceAsStream("messages.properties")) {
            if (input != null) {
                STEP_MESSAGES.load(input);
                LOGGER.info("Archivo messages.properties cargado correctamente desde classpath.");
            } else {
                LOGGER.warning("No se encontró el archivo messages.properties en el classpath.");
            }
        } catch (IOException e) {
            LOGGER.warning("Error al cargar el archivo messages.properties: " + e.getMessage());
        }
    }




    private static String procesarPasoEspecifico(String paso) {
        String pasoNormalizado = paso.toLowerCase().replaceAll("\\s+", "_");

        // Buscar coincidencia exacta
        if (STEP_MESSAGES.containsKey(pasoNormalizado)) {
            return STEP_MESSAGES.getProperty(pasoNormalizado);
        }

        // Buscar coincidencia parcial (por si el paso contiene más texto que la key)
        for (String key : STEP_MESSAGES.stringPropertyNames()) {
            if (pasoNormalizado.contains(key)) {
                return STEP_MESSAGES.getProperty(key);
            }
        }

        // Si no se encontró, devuelve una descripción genérica
        return "";
    }



    private static String marcarPasoFallido() {
        return "❌ La prueba falló en este paso. ";
    }

    private static String finalizarConclusion(String estadoFinal) {
        return "FAILED".equalsIgnoreCase(estadoFinal)
                ? "⚠️ La prueba finalizó con errores."
                : "✅ Todos los pasos fueron completados exitosamente.";
    }

    private static void eliminarCapturas(File[] capturas) {
        for (File captura : capturas) {
            try {
                Files.deleteIfExists(captura.toPath());
                LOGGER.info("Captura eliminada: " + captura.getName());
            } catch (IOException e) {
                LOGGER.warning("No se pudo eliminar la captura: " + captura.getName());
            }
        }
        LOGGER.info("Capturas eliminadas correctamente.");
    }


    private static void reemplazarTextoEnDocumento(XWPFDocument document, String marcador, String nuevoTexto) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(marcador)) {
                    text = text.replace(marcador, nuevoTexto);
                    run.setText(text, 0);
                }
            }
        }

        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        for (XWPFRun run : paragraph.getRuns()) {
                            String text = run.getText(0);
                            if (text != null && text.contains(marcador)) {
                                text = text.replace(marcador, nuevoTexto);
                                run.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void inicializarPlantillaReporte() {
        String plantillaOriginal = System.getProperty("user.dir") + File.separator + "ruta" + File.separator + "PlantillaInforme.docx";
        String destinoEditable = System.getProperty("user.dir") + File.separator + "ruta" + File.separator + "InformeFinal.docx";

        try {
            Files.copy(Paths.get(plantillaOriginal), Paths.get(destinoEditable), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Se inicializó el archivo InformeFinal.docx con la plantilla base.");
        } catch (IOException e) {
            LOGGER.severe("Error al copiar la plantilla del informe: " + e.getMessage());
        }
    }


}
