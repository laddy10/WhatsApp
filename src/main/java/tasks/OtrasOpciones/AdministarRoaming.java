package tasks.OtrasOpciones;

import hooks.ReportHooks;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR;
import static userinterfaces.WhatsAppPage.TXT_ENVIAR_MENSAJE;
import static userinterfaces.WhatsAppPostpagoPage.TXT_MARCAR_COMO_LEIDO;
import static utils.Constantes.*;
import static utils.ConstantesPost.ESTADO_ROAMING;
import static utils.ConstantesPost.FECHA_DESACTIVACION;

public class AdministarRoaming extends AndroidObject implements Task {

    ArrayList<Character> lista = new ArrayList<>();
    ArrayList<Character> DigitosClave = new ArrayList<>();
    String clave = "";


    @Override
    public <T extends Actor> void performAs(T actor) {

        CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla solicitud código de verificación");
        ReportHooks.registrarPaso("Pantalla solicitud código de verificación");


        lista = LeerMensaje(actor);

        if (!Presence.of(TXT_MARCAR_COMO_LEIDO).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(Click.on(TXT_MARCAR_COMO_LEIDO)
            );
        }

        List<WebElementFacade> btnlectorpdfdrive = TXT_MARCAR_COMO_LEIDO.resolveAllFor(actor);
        if (!btnlectorpdfdrive.isEmpty()) {
            actor.attemptsTo(
                    Click.on(TXT_MARCAR_COMO_LEIDO)
            );

        }

        actor.attemptsTo(Atras.irAtras());


        for (int i = 0; i < lista.size(); i++) {
            DigitarNumeros(actor, lista.get(i).toString());
        }


        for (int i = 0; i < clave.length(); i++) {
            if (Character.isDigit(clave.charAt(i))) {
                DigitosClave.add(clave.charAt(i));
            }
        }

        for (int i = 0; i < DigitosClave.size(); i++) {
            DigitarNumeros(actor, DigitosClave.get(i).toString());
        }

        StringBuilder codigoVerificacion = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            codigoVerificacion.append(lista.get(i));  // Construir el código como una cadena
        }

        try {
            actor.attemptsTo(
                    Enter.theValue(codigoVerificacion.toString()).into(TXT_ENVIAR_MENSAJE),
                    Click.on(BTN_ENVIAR),
                    WaitForTextContains.withAnyTextContains(ESTADO_ROAMING)
            );

        } catch (Exception e) {
            System.out.println("Error al ingresar el código de verificación: " + e.getMessage());
        }

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(ESTADO_ROAMING),
                ValidarTextoQueContengaX.elTextoContiene(FECHA_DESACTIVACION)
        );


        CapturaDePantallaMovil.tomarCapturaPantalla("Validación estado Roaming");
        ReportHooks.registrarPaso("Validación estado Roaming");

        actor.attemptsTo(
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );


    }

    public static Performable administarRoaming() {
        return instrumented(AdministarRoaming.class);
    }
}