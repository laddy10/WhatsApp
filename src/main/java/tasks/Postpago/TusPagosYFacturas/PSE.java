package tasks.Postpago.TusPagosYFacturas;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.Scroll;
import interactions.scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.CapturaDePantallaMovil;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

import static userinterfaces.WhatsAppPostpagoPage.*;
import static utils.ConstantesPost.*;

public class PSE implements Task {

    private static final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar Tarjeta de Crédito
        actor.attemptsTo(
                Click.on(BTN_PSE),
                WaitForResponse.withText(CONTINUAR_BUTTON)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Tarjeta de Crédito y continuar");
        ReportHooks.registrarPaso("Seleccionar Tarjeta de Crédito y continuar");

        actor.attemptsTo(
                Click.on(BTN_CONTINUAR_PAGO)
        );

        // VALIDAR DIRECCIONAMIENTO Y FORMULARIO PSE

        actor.attemptsTo(
                WaitForTextContains.withAnyTextContains(PORTAL_PAGOS_Y_RECARGAS)
        );

        // SELECCIONAR BANCO
        actor.attemptsTo(
                WaitFor.aTime(3000),
                Click.on(DROPDOWN_BANCO),
                WaitFor.aTime(2000)
        );


        // Hacer scroll para validar diferentes bancos y seleccionar uno
        actor.attemptsTo(
                ValidarTexto.validarTexto(SELECCIONA_TU_BANCO),
                ValidarTexto.validarTexto(ALIANZA_FIDUCIARIA),
                ValidarTexto.validarTexto(BAN100),
                ValidarTexto.validarTexto(BANCAMIA_SA),
                ValidarTexto.validarTexto(BANCO_AGRARIO),
                ValidarTexto.validarTexto(BANCO_AV_VILLAS),
                ValidarTexto.validarTexto(BANCO_BBVA_COLOMBIA),
                ScrollHastaTexto.conTexto(BANCO_DE_BOGOTA),
                ValidarTexto.validarTexto(BANCO_DE_BOGOTA)
        );


        // Seleccionar Banco Popular
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(BANCO_AV_VILLAS),
                WaitFor.aTime(1000)
        );

        //Nomber titular
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(CONFIRMAR),
                Enter.theValue("Pepito Perez").into(TXT_NOMBRE_TITULAR_PSE)

        );

        // VALIDAR TIPO DE CLIENTE
        actor.attemptsTo(
                Click.on(DROPDOWN_TIPO_CLIENTE),
                WaitFor.aTime(1000)
        );


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PERSONA_JURIDICA),
                WaitFor.aTime(1000),
                Click.on(DROPDOWN_TIPO_CLIENTE),
                ClickTextoQueContengaX.elTextoContiene(PERSONA_NATURAL),
                WaitFor.aTime(1000)
        );


        // VALIDAR CORREO ELECTRÓNICO
        actor.attemptsTo(
                Click.on(SELECT_TIPO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(CEDULA_CIUDADANIA),
                Enter.theValue("12345674").into(TXT_NUMERO_DOCUMENTO),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPost()),
                Enter.theValue("Calle 26 # 65- 06").into(TXT_DIRECCION),
                Enter.theValue("pruebasexperienciaclaro@gmail.com").into(TXT_EMAIL)
        );

        // HACER CLIC EN PAGAR
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONFIRMAR),
                WaitFor.aTime(7000),
                WaitForTextContains.withAnyTextContains("Volver al comercio")
        );


        // VALIDAR REDIRECCIÓN AL PORTAL DE PAGOS
        actor.should(seeThat(the(URL_BANCO), isPresent()));


    }

    public static Performable validarRedireccion() {
        return instrumented(PSE.class);
    }
}
