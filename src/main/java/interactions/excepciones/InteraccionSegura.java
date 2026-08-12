package interactions.excepciones;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class InteraccionSegura implements Interaction {

    private final Performable interaccionOriginal;

    public InteraccionSegura(Performable interaccionOriginal) {
        this.interaccionOriginal = interaccionOriginal;
    }

    @Override
    @Step("{0} ejecuta la interacción de forma segura protegiendo contra popups del sistema")
    public <T extends Actor> void performAs(T actor) {
        try {
            // Intento 1: Ejecutar la interacción original normalmente
            actor.attemptsTo(interaccionOriginal);
        } catch (Exception e) {
            System.out.println("La interacción original falló. Verificando si existe un popup intrusivo...");
            
            // Si falla, activamos el "escudo" para quitar cualquier popup bloqueante
            ManejadorDePopups.cerrarPopupSiExiste(actor);
            
            // Intento 2: Volvemos a intentar la interacción original (si falla aquí, la prueba falla formalmente)
            System.out.println("Reintentando la interacción original tras la verificación de popup.");
            actor.attemptsTo(interaccionOriginal);
        }
    }

    public static Performable ejecutar(Performable interaccionOriginal) {
        return instrumented(InteraccionSegura.class, interaccionOriginal);
    }
}
