package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import questions.ValidarElemento;
import tasks.*;
import tasks.CompraTusPaquetes.*;
import tasks.CompraWhatsApp.CompraWhatsApp;
import tasks.CompraWhatsApp.MenuCompraWhatsApp;
import tasks.ConsultarOtraCuenta.ConsultarOtraCuenta;
import tasks.HazTusRecargas.DireccionamientoMediosPago;
import tasks.HazTusRecargas.HazTusRecargas;
import tasks.HazTusRecargas.SeleccionarValorRecargas;
import tasks.HazTusRecargas.ValidarMenuRecargas;
import tasks.OtrasOpciones.*;
import tasks.TodoSobreTuLinea.*;
import tasks.TusEquipos.MenuTusEquipos;
import tasks.TusEquipos.TusEquipos;
import tasks.TusEquipos.TusEquiposEnSoporte;
import tasks.TusEquipos.VerPuntosFisicos;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.TestDataProvider;
import utils.WordAppium;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.WhatsAppPage.*;
import static utils.Constantes.*;

public class WhatsappDefinitions {

    private final User user = TestDataProvider.getRealUser();

    @Before
    public void initScenario(Scenario scenario) {
        OnStage.setTheStage(new OnlineCast());
        WordAppium.inicializarPlantillaReporte();
        EvidenciaUtils.reiniciarContador(); // Reinicia el conteo de pasos para este escenario
    }


    @Given("Ingresar a WhatsAPP")
    public void ingresarWhatsapp() {
        theActorCalled(" ").attemptsTo(
                IngresarWhatsApp.ingresar()
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
    public void iniciarChatClaro() {
        theActorInTheSpotlight().attemptsTo(
                IniciarChatClaro.iniciarChatClaro()
        );
        ReportHooks.setLinea(user.getNumeroPre());
    }

    @And("^Seleccionar linea de consulta$")
    public void seleccionarLineaConsulta() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarLineaConsulta.seleccionarLineaConsulta()
        );
        ReportHooks.setLinea(user.getNumeroPre());
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
    public void seleccionarMenuPrinciapl() {
        theActorInTheSpotlight().attemptsTo(
                MenuPrincipal.menuPrincipal()
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

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Claro Drive");
        ReportHooks.registrarPaso("Seleccionar Claro Drive");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitFor.aTime(6000)
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
    public void seleccionarValorRecarga() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarValorRecargas.seleccionarValorRecargas()
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
                Click.on(BTN_VACIAR_CHAT)
                //ClickTextoQueContengaX.elTextoContiene(VACIAR_CHAT)
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


    @And("^Validar los paquetes de voz disponibles$")
    public void validarPaqDeVoz() {
        theActorInTheSpotlight().attemptsTo(
                PaqDeVoz.validarPaqDeVoz());
    }

    @And("^Validar los paquetes de datos disponibles$")
    public void validarPaqDeDatos() {
        theActorInTheSpotlight().attemptsTo(
                PaqDeDatos.validarPaqDeDatos());
    }

    @And("^Validar los paquetes de apps disponibles$")
    public void validarPaqDeApps() {
        theActorInTheSpotlight().attemptsTo(
                PaqDeApps.validarPaqDeApps());
    }

    @And("^Validar los paquetes internacionales disponibles$")
    public void validarPaqInternacionales() {
        theActorInTheSpotlight().attemptsTo(
                PaqInternacionales.validarPaqInternacionales());
    }

    @And("^Validar los paquetes comunidad sorda disponibles$")
    public void validarPaqComunidadSorda() {
        theActorInTheSpotlight().attemptsTo(
                PaqComunidadSorda.validarPaqComunidadSorda());
    }

    @And("^Validar regala un paquete y redireccion portal pagos$")
    public void validarRegalaUnPaquete() {
        theActorInTheSpotlight().attemptsTo(
                RegalaUnPaquete.validarRegalaUnPaquete());
    }


    @And("^Validar flujo de pago de paquete seleccionado$")
    public void validarFlujoPagoPaquete() {
        theActorInTheSpotlight().attemptsTo(
                ValidarPagoPaquete.validarPagoPaquete());
    }

    @And("^Validar menu de haz tus recargas$")
    public void validarMenuHazTusRecargas() {
        theActorInTheSpotlight().attemptsTo(
                ValidarMenuRecargas.validarMenuRecargas());
    }

    @And("^Seleccionar Tus equipos$")
    public void seleccionarTusEquipos() {
        theActorInTheSpotlight().attemptsTo(
                TusEquipos.tusEquipos()
        );
    }

    @And("^Seleccionar Tus equipos en soporte$")
    public void seleccionarTusEquiposEnSoporte() {
        theActorInTheSpotlight().attemptsTo(
                TusEquiposEnSoporte.tusEquiposEnSoporte()
        );
    }

    @Then("^Ingresar a Ver puntos fisicos$")
    public void IngresarVerPuntosFisicos() {
        theActorInTheSpotlight().attemptsTo(
                VerPuntosFisicos.verPuntosFisicos()
        );
    }

    @Then("^Validar el menu de tus equipos$")
    public void validarMenuTusEquipos() {
        theActorInTheSpotlight().attemptsTo(
                MenuTusEquipos.menuTusEquipos()
        );
    }

    @Then("^Seleccionar tus PQRS radicados$")
    public void tusPqrRadicados() {
        theActorInTheSpotlight().attemptsTo(
                TusPQRSRadicados.tusPQRSRadicados()
        );
    }


    @Then("^Validar consultar otra cuenta$")
    public void validarConsultarOtraCuenta() {
        theActorInTheSpotlight().attemptsTo(
                ConsultarOtraCuenta.consultarOtraCuenta()
        );
    }

}