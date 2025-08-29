package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.WaitForResponse;
import interactions.comunes.WaitForTextContains;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import tasks.Postpago.TodoSobreTuPlan.*;
import tasks.Postpago.SeleccionarLineaPostpago;
import tasks.Postpago.TratamientoDatosPostpago;
import tasks.Postpago.TusPagosYFacturas.TusPagosFacturasPostpago;
import tasks.Postpago.ValidarMenuPrincipalPost;
import tasks.ValidarTratamientoDatos;
import utils.CapturaDePantallaMovil;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static userinterfaces.WhatsAppPostpagoPage.BTN_MENU_PRINCIPAL;
import static utils.Constantes.*;
import static utils.Constantes.URL_CLARO_MUSICA;
import static utils.ConstantesPost.*;

public class WhatsappPostpagoDefinitions {

    // Agregar estos métodos al archivo stepDefinitions/WhatsappDefinitions.java existente

    @And("^Seleccionar linea de consulta postpago$")
    public void seleccionarLineaConsultaPostpago(List<User> credentials) {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarLineaPostpago.seleccionarLineaPostpago(credentials.get(0))
        );
        ReportHooks.setLinea(credentials.get(0).getNumeroPost());
    }


    @And("^Validar politica de tratamientos de datos postpago$")
    public void validarPoliticaTratamiendoDatosPost() {
        theActorInTheSpotlight().attemptsTo(
                TratamientoDatosPostpago.tratamientoDatosPostpago()
        );
    }

    @And("^Seleccionar menu principal Post$")
    public void seleccionarMenuPrincipalPost() {
        CapturaDePantallaMovil.tomarCapturaPantalla("Ingresar al Menú principal");
        ReportHooks.registrarPaso("Ingresar al Menú principal");

        theActorInTheSpotlight().attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(OPCIONES_RAPIDAS),
                ValidarTextoQueContengaX.elTextoContiene(VER_Y_PAGAR_FACTURA),
                ValidarTextoQueContengaX.elTextoContiene(DIAGNOSTICO_MOVIL),
                Click.on(BTN_MENU_PRINCIPAL),
                WaitForResponse.withText(COMPRA_WHATSAPP_POST)
        );


    }

    @And("^Validar menu principal Post$")
    public void validarMenuPrincipalPost() {
        theActorInTheSpotlight().attemptsTo(
                ValidarMenuPrincipalPost.validarMenuPrincipalPost()
        );
    }

    @And("^Ingresar al menu todo sobre tu plan postpago$")
    public void ingresarMenuTodoSobreTuPlanPostpago() {
        theActorInTheSpotlight().attemptsTo(
                MenuTodoSobreTuPlan.menuTodoSobreTuPlan()
        );
    }

    @And("^Consultar consumos del plan postpago$")
    public void consultarConsumosPlanPostpago() {
        theActorInTheSpotlight().attemptsTo(
                ConsultaConsumosPostpago.consultaConsumosPostpago()
        );
    }

    @And("^Conoce o mejora tu plan postpago$")
    public void conoceOMejoraTuPlanPostpago() {
        theActorInTheSpotlight().attemptsTo(
                ConoceMejoraPlanPostpago.conoceMejoraPlanPostpago()
        );
    }

    @And("^Ingresar tu lealtad merece mas postpago$")
    public void tuLealtadMereceMaspost() {
        theActorInTheSpotlight().attemptsTo(
                TuLealtadMereceMasPost.tuLealtadMereceMasPost()
        );
    }


    @And("^Seleccionar claro musica$")
    public void claroMusica() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_MUSICA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Música");
        ReportHooks.registrarPaso("Seleccionar Claro Música");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withText(URL_CLARO_MUSICA)
        );
    }


    @And("^Seleccionar claro video$")
    public void claroVideo() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_VIDEO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Video");
        ReportHooks.registrarPaso("Seleccionar Claro Video");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(URL_CLARO_VIDEO)
        );
    }

    @Then("^Validar direccionamiento claro video$")
    public void validarClaroVideo() {
        theActorInTheSpotlight().attemptsTo(
                ValidarClaroVideo.validarClaroVideo()
        );
    }

    @Then("^Validar claro Drive Postpago$")
    public void validarClaroDrivePost() {
        theActorInTheSpotlight().attemptsTo(
                ValidarClaroDrivePost.validarClaroDrivePost()
        );
    }


    @And("^Seleccionar la opcion claro Club$")
    public void claroClub() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_CLUB)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Club");
        ReportHooks.registrarPaso("Seleccionar Claro Club");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(URL_CLARO_CLUB)
        );
    }

    @Then("^Validar claro Club$")
    public void validarClaroClub() {
        theActorInTheSpotlight().attemptsTo(
                ValidarClaroClub.validarClaroClub()
        );
    }

    @And("^Seleccionar la opcion aniversario Claro$")
    public void aniversarioClaro() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ANIVERSARIO_CLARO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Aniversario Claro");
        ReportHooks.registrarPaso("Seleccionar Aniversario Claro");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(URL_ANIVERSARIO_CLARO)
        );
    }


    @And("^Seleccionar la opcion sorpresa de cumpleanos$")
    public void sorpresaDeCumpleaños() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SORPRESA_DE_CUMPLEAÑOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Aniversario Claro");
        ReportHooks.registrarPaso("Seleccionar Aniversario Claro");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(URL_ANIVERSARIO_CLARO)
        );
    }

    @Then("^Validar aniversario Claro$")
    public void validarAniversarioClaro() {
        theActorInTheSpotlight().attemptsTo(
                ValidarAniversarioClaro.validarAniversarioClaro()
        );
    }

    @And("^Ingresar a tus pagos y facturas$")
    public void ingresarTusPagosFacturas() {
        theActorInTheSpotlight().attemptsTo(
                TusPagosFacturasPostpago.tusPagosFacturasPostpago()
        );
    }
}
