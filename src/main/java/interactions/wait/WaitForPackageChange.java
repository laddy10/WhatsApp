package interactions.wait;

import java.util.concurrent.TimeUnit;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import utils.AndroidObject;

public class WaitForPackageChange implements Interaction {

  private static final long POLL_MILLIS = 250;
  private final String originalPackage;
  private final int timeoutSeconds;

  public WaitForPackageChange(String originalPackage, int timeoutSeconds) {
    this.originalPackage = originalPackage;
    this.timeoutSeconds = timeoutSeconds;
  }

  public static WaitForPackageChange from(String packageName, int timeoutSeconds) {
    return Tasks.instrumented(WaitForPackageChange.class, packageName, timeoutSeconds);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeoutSeconds);

    while (System.nanoTime() < deadline) {
      String currentPackage = AndroidObject.androidDriver(actor).getCurrentPackage();
      if (!originalPackage.equals(currentPackage)) {
        return;
      }
      sleep();
    }

    throw new RuntimeException(
        "La aplicacion externa no se abrio en "
            + timeoutSeconds
            + " segundos. Paquete activo: "
            + AndroidObject.androidDriver(actor).getCurrentPackage());
  }

  private void sleep() {
    try {
      Thread.sleep(POLL_MILLIS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Espera de cambio de aplicacion interrumpida", e);
    }
  }
}