package stepDefinitions;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.WhatsAppPage.BTN_MAS_OPCIONES;
import static userinterfaces.WhatsAppPage.LBL_WHATSAPP;
import static utils.Constantes.*;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import freemarker.log.Logger;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.WaitFor;
import interactions.Validaciones.ValidarTexto;
import interactions.comunes.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import questions.ValidarElemento;
import hooks.ReportHooks;
import tasks.*;
import tasks.CompraTusPaquetes.PaqTodoIncluido;
import tasks.CompraWhatsApp.CompraWhatsApp;
import tasks.CompraWhatsApp.MenuCompraWhatsApp;
import tasks.HazTusRecargas.DireccionamientoMediosPago;
import tasks.HazTusRecargas.HazTusRecargas;
import tasks.HazTusRecargas.SeleccionarValorRecargas;
import tasks.OtrasOpciones.*;
import tasks.TodoSobreTuLinea.*;
import utils.CapturaDePantallaMovil;
import utils.WordAppium;

import java.io.File;
import java.util.List;

public class WhatsappDefinitions {

    User addCredentials;
    private static final Logger LOGGER = Logger.getLogger(WordAppium.class.getName());


    @Before
    public void initScenario(Scenario scenario) {
        OnStage.setTheStage(new OnlineCast());
        LOGGER.info("Limpiando carpeta de capturas...");
        WordAppium.inicializarPlantillaReporte();
        File folder = new File("Capturas");
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".jpg")) {
                    file.delete();
                }
            }
        }
    }

    @Given("Ingresar a WhatsAPP")
    public void ingresarWhatsapp() {
        theActorCalled(" ").attemptsTo
                (WaitForResponse.withText(PREGUNTAR_META)
                );
        theActorInTheSpotlight().attemptsTo(
                ValidarTexto.validarTexto(PREGUNTAR_META)
        );
        theActorInTheSpotlight().should(seeThat(
                ValidarElemento.esVisible(LBL_WHATSAPP))
        );
    }

    @When("^Validar Version de la App$")
    public void validarVersionApp() {
        theActorInTheSpotlight().attemptsTo(
                ValidarVersionApp.validarVersionApp()
        );
    }

    @And("^Buscar el chat de Claro Colombia$")
    public void buscarChatClaro() {
        theActorInTheSpotlight().attemptsTo(
                BuscarChatClaro.buscarChatClaro()
        );
    }

    @And("^Iniciar el chat con Claro Colombia$")
    public void iniciarChatClaro(List<User> credentials) {
        theActorInTheSpotlight().attemptsTo(
                IniciarChatClaro.iniciarChatClaro(credentials.get(0))
        );
        ReportHooks.setLinea(credentials.get(0).getNumero());
    }

    @And("^Seleccionar linea de consulta$")
    public void seleccionarLineaConsulta(List<User> credentials) {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarLineaConsulta.seleccionarLineaConsulta(credentials.get(0))
        );
        ReportHooks.setLinea(credentials.get(0).getNumero());
    }

    @And("^Validar politica de tratamientos de datos$")
    public void validarPoliticaTratamiendoDatos() {
        theActorInTheSpotlight().attemptsTo(
                ValidarTratamientoDatos.validarTratamientoDatos()
        );
    }

    @Then("^Consultar saldo de la linea$")
    public void consultarSaldoLinea() {
        theActorInTheSpotlight().attemptsTo(
                ConsultarSaldoLinea.consultarSaldoLinea()
        );
    }

    @And("^Seleccionar menu principal$")
    public void seleccionarMenuPrinciapl(List<User> credentials) {
        theActorInTheSpotlight().attemptsTo(
                MenuPrincipal.menuPrincipal(credentials.get(0))
        );
    }

    @And("^Validar menu principal$")
    public void validarMenuPrincipal() {
        theActorInTheSpotlight().attemptsTo(
                ValidarMenuPrincipal.validarMenuPrincipal()
        );
    }

    @And("^Ingresar al menu todo sobre tu linea$")
    public void ingresarMenuTodoSobreTuLinea() {
        theActorInTheSpotlight().attemptsTo(
                MenuTodoSobreTuLinea.menuTodoSobreTuLinea()
        );
    }

    @Then("^Consultar los consumos$")
    public void consultarLosConsumos() {
        theActorInTheSpotlight().attemptsTo(
                ConsultarConsumos.ConsultarConsumos()
        );
    }

    @Then("^Conoce o mejora tu plan$")
    public void conoceOMejoraTuPlan() {
        theActorInTheSpotlight().attemptsTo(
                ConoceMejoraTuPlan.conoceMejoraTuPlan()
        );
    }

    @Then("^Consulta otra linea$")
    public void consultaOtraLinea() {
        theActorInTheSpotlight().attemptsTo(
                ConsultaOtraLinea.consultaOtraLinea()
        );
    }


    @And("^Ingresar a la opcion tu lealtad merece mas$")
    public void opcionTuLealtadMereceMas() {
        theActorInTheSpotlight().attemptsTo(
                TuLealtadMereceMas.tuLealtadMereceMas()
        );
    }

    @And("^Seleccionar la opcion claro musica$")
    public void seleccionarClaroMusica() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_MUSICA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Música");
        ReportHooks.registrarPaso("Seleccionar Claro Música");

        theActorInTheSpotlight().attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withText(URL_CLARO_MUSICA)
        );
    }


    @And("^Seleccionar la opcion claro Drive$")
    public void seleccionarClaroDrive() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_DRIVE)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");

        theActorInTheSpotlight().attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitFor.aTime(3000)
        );
    }

    @Then("^Validar direccionamiento claro musica$")
    public void validarDireccionamientoClaroMusica() {
        theActorInTheSpotlight().attemptsTo(
                ValidarClaroMusica.validarClaroMusica()
        );
    }

    @Then("^Validar direccionamiento claro Drive$")
    public void validarDireccionamientoClaroDrive() {
        theActorInTheSpotlight().attemptsTo(
                ValidarClaroDrive.validarClaroDrive()
        );
    }

    @And("^Ingresar a haz tus recargas$")
    public void ingresarHazTusRecargas() {
        theActorInTheSpotlight().attemptsTo(
                HazTusRecargas.hazTusRecargas()
        );
    }

    @And("^Seleccionar el valor de la recarga$")
    public void seleccionarValorRecarga(List<User> credentials) {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarValorRecargas.seleccionarValorRecargas(credentials.get(0))
        );
    }

    @Then("^Validar direccionamiento al medio de pago$")
    public void validarDireccionamientoMedioPago() {
        theActorInTheSpotlight().attemptsTo(
                DireccionamientoMediosPago.direccionamientoMediosPago()
        );
        ReportHooks.registrarPaso("seleccionar_medio_pago");
        ReportHooks.registrarPaso("validar_redireccion_link_pago");
    }

    @And("^Vaciar chat$")
    public void vaciarChat() {
        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_MAS_OPCIONES),
                ClickTextoQueContengaX.elTextoContiene(MAS),
                ClickTextoQueContengaX.elTextoContiene(VACIAR_CHAT),
                ClickTextoQueContengaX.elTextoContiene(VACIAR_CHAT)
        );

    }

    @And("^Ingreso a otras opciones$")
    public void ingresarOtrasOpciones() {
        theActorInTheSpotlight().attemptsTo(
                OtrasOpciones.otrasOpciones()
        );
    }

    @Then("^Validar el menu otras opciones$")
    public void validarMenuOtrasOpciones() {
        theActorInTheSpotlight().attemptsTo(
                ValidarMenuOtrasOpciones.validarMenuOtrasOpciones()
        );
    }

    @Then("^Renueva tu sim$")
    public void renuevaTuSIM() {
        theActorInTheSpotlight().attemptsTo(
                RenuevaTuSIM.renuevaTuSIM()
        );
    }

    @Then("^Cambiate a postpago$")
    public void CambiateaPost() {
        theActorInTheSpotlight().attemptsTo(
                CambiateaPost.cambiateaPost()
        );
    }

    @Then("^Tus servicios LDI$")
    public void ServiciosLDI() {
        theActorInTheSpotlight().attemptsTo(
                ServiciosLDI.serviciosLDI()
        );
    }

    @Then("^Validar menu LDI$")
    public void ValidarMenuLDI() {
        theActorInTheSpotlight().attemptsTo(
                ValidarMenuLDI.validarMenuLDI()
        );
    }

    @And("^Ingresa al menu Compra por WhatsApp$")
    public void ingresarMenuCompraWhatsApp() {
        theActorInTheSpotlight().attemptsTo(
                CompraWhatsApp.compraWhatsApp()
        );
    }

    @And("^Clic boton selecciona$")
    public void botonSelecciona() {
        final String MENSAJE_CAPTURA = "Validar mensaje que contenga el botón Selecciona";

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SELECCIONA)
        );
    }

    @And("^Validar el menu Compra por WhatsApp$")
    public void validarMenuCompraWhatsApp() {
        theActorInTheSpotlight().attemptsTo(
                MenuCompraWhatsApp.menuCompraWhatsApp()
        );

    }

    @And("^Ingresar al menu compra tus paquetes$")
    public void ingresarMenuCompraTusPaquetes() {
        final String MENSAJE_CAPTURA = "Seleccionar menu Compra tus paquetes y enviar";

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRA_TUS_PAQUETES)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        theActorInTheSpotlight().attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForResponse.withText(SELECCIONA)
        );
    }

    @And("^Validar los paquetes todo incluido disponibles$")
    public void validarPaqTodoIncluido() {
        theActorInTheSpotlight().attemptsTo(
                PaqTodoIncluido.seleccionarTipoPaquete());
    }


}