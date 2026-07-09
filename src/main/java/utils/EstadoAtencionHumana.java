package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Estado compartido y persistente para no iniciar nuevos saludos si hay asesor pendiente. */
public final class EstadoAtencionHumana {

  private static volatile boolean casoEnColaOConAsesor;
  private static volatile boolean bloqueadoPorTimeout;
  private static final Path ARCHIVO_ESTADO = Paths.get(
      System.getProperty("whatsapp.asesor.estado.path", ".runtime/asesor-pendiente.flag"));

  private EstadoAtencionHumana() {}

  public static boolean requiereRecuperacion() {
    return casoEnColaOConAsesor || existeMarcaPersistente();
  }

  public static boolean estaBloqueadoPorTimeout() {
    return bloqueadoPorTimeout || existeMarcaPersistente();
  }

  public static void marcarEnCola() {
    casoEnColaOConAsesor = true;
    escribirMarca("EN_COLA_O_ASESOR");
  }

  public static void marcarTimeoutSinAsignacion() {
    casoEnColaOConAsesor = true;
    bloqueadoPorTimeout = true;
    escribirMarca("TIMEOUT_SIN_ASIGNACION");
  }

  public static void marcarTimeoutSinCierre() {
    casoEnColaOConAsesor = true;
    bloqueadoPorTimeout = true;
    escribirMarca("TIMEOUT_SIN_CIERRE");
  }

  public static void marcarCerrado() {
    casoEnColaOConAsesor = false;
    bloqueadoPorTimeout = false;
    eliminarMarca();
  }

  public static String rutaMarcaPersistente() {
    return ARCHIVO_ESTADO.toAbsolutePath().toString();
  }

  private static boolean existeMarcaPersistente() {
    return Files.exists(ARCHIVO_ESTADO);
  }

  private static void escribirMarca(String estado) {
    try {
      Path parent = ARCHIVO_ESTADO.toAbsolutePath().getParent();
      if (parent != null) {
        Files.createDirectories(parent);
      }
      Files.write(
          ARCHIVO_ESTADO,
          (estado + System.lineSeparator() + "timestamp=" + System.currentTimeMillis())
              .getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new IllegalStateException(
          "No se pudo persistir el estado de atencion humana en "
              + ARCHIVO_ESTADO.toAbsolutePath(), e);
    }
  }

  private static void eliminarMarca() {
    try {
      Files.deleteIfExists(ARCHIVO_ESTADO);
    } catch (IOException e) {
      throw new IllegalStateException(
          "No se pudo limpiar el estado de atencion humana en "
              + ARCHIVO_ESTADO.toAbsolutePath(), e);
    }
  }
}
