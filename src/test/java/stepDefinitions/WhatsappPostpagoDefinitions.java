package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import questions.ValidarElemento;
import tasks.OtrasOpciones.AdministarRoaming;
import tasks.OtrasOpciones.ComprarRoaming;
import tasks.OtrasOpciones.PaquetesYRecargasPost;
import tasks.Postpago.SeleccionarLineaPostpago;
import tasks.Postpago.TodoSobreTuPlan.*;
import tasks.Postpago.TratamientoDatosPostpago;
import tasks.Postpago.TusPagosYFacturas.*;
import tasks.Postpago.ValidarMenuPrincipalPost;
import utils.CapturaDePantallaMovil;
import utils.TestDataProvider;


import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static tasks.CompraTusPaquetes.Constants_Paquetes.COMPRAR_ROAMING;
import static userinterfaces.WhatsAppPage.*;
import static userinterfaces.WhatsAppPostpagoPage.BTN_MENU_PRINCIPAL;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;
import static utils.ConstantesPost.TUS_PQR_RADICADOS;

public class WhatsappPostpagoDefinitions {


    private final User user = TestDataProvider.getRealUser();


    // Agregar estos métodos al archivo stepDefinitions/WhatsappDefinitions.java existente

    @And("^Seleccionar linea de consulta postpago$")
    public void seleccionarLineaConsultaPostpago() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarLineaPostpago.seleccionarLineaPostpago()
        );
        ReportHooks.setLinea(user.getNumeroPost());
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
                ValidarTextoQueContengaX.elTextoContiene(OTRAS_OPCIONES_2),
               // ValidarTextoQueContengaX.elTextoContiene(VER_Y_PAGAR_FACTURA),
               // ValidarTextoQueContengaX.elTextoContiene(DIAGNOSTICO_MOVIL),
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

    @And("Ingresar al menu de tus pagos y facturas")
    public void ingresarAlMenuDeTusPagosYFacturas() {
        theActorInTheSpotlight().attemptsTo(
                IngresarTusPagosYFacturas.ingresarTusPagosYFacturas()
        );
    }

    @And("Clic Tu factura")
    public void clicTuFactura() {
        theActorInTheSpotlight().attemptsTo(
                ClicTuFactura.clicTuFactura()
        );
    }

    @And("Ingresar el codigo que llega en el mensaje de texto")
    public void ingresarElCodigoQueLlegaEnElMensajeDeTexto() {
        theActorInTheSpotlight().attemptsTo(
                IngresarCodigoVerificacion.ingresarCodigoVerificacion()
        );
    }

    @And("Abrir factura")
    public void abrirFactura() {
        theActorInTheSpotlight().attemptsTo(
                AbrirFactura.abrirFactura()
        );
    }

    @And("Seleccionar otras facturas claro")
    public void seleccionarOtrasFacturasClaro() {
        theActorInTheSpotlight().attemptsTo(
                OtrasFacturasClaro.otrasFacturasClaro()
        );
    }

    @And("Seleccionar Tu historial")
    public void tuHistorial() {
        theActorInTheSpotlight().attemptsTo(
                ClicTuHistorial.clicTuHistorial()
        );
    }

    @Then("Validar historial de facturas completo")
    public void historialFacturaCompleto() {
        theActorInTheSpotlight().attemptsTo(
                TuHistorialFacturas.tuHistorialFacturas()
        );
    }

    @And("Seleccionar Mensaje de cobranza")
    public void mensajeCobranza() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(MENSAJES_COBRANZA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar Mensajes de cobranza");
        ReportHooks.registrarPaso("Seleccionar Mensajes de cobranza");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withText(FINALIZAR_CHAT)
        );

    }

    @Then("Validar mensaje casa cobranza completo")
    public void validarmensajeCasaCobranza() {
        theActorInTheSpotlight().attemptsTo(
                ValidarMensajesCobranza.validarMensajesCobranza()
        );
    }

    @And("Seleccionar Productos financiados y validar mensaje")
    public void seleccionarProductosFinanciadosYValidar() {
        theActorInTheSpotlight().attemptsTo(
                ProductosFinanciados.productosFinanciados()
        );
    }

    @And("Seleccionar Programa tus pagos")
    public void programaTusPagos() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PROGRAMA_TUS_PAGOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Programa tus pagos'");
        ReportHooks.registrarPaso("Seleccionar 'Programa tus pagos'");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForResponse.withText(FINALIZAR_CHAT)
        );
    }

    @And("Validar direccionamiento Programar pagos")
    public void direccionamientoProgramarPagos() {
        theActorInTheSpotlight().attemptsTo(
                ProgramaTusPagos.programaTusPagos()
        );
    }

    @And("Seleccionar Soporte y servicio")
    public void seleccionarSoporteServicio() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SOPORTE_Y_SERVICIO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Soporte y servicio'");
        ReportHooks.registrarPaso("Seleccionar 'Soporte y servicio'");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(SELECCIONA)
        );
    }

    @And("Ingresar al boton selecciona de soporte")
    public void botonSeleccionaSoporte() {
        CapturaDePantallaMovil.tomarCapturaPantalla("Validar boton Selecciona");
        ReportHooks.registrarPaso("Validar boton Selecciona");

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SELECCIONA),
                ValidarTextoQueContengaX.elTextoContiene(ESTADO_SERVICIOS),
                ValidarTextoQueContengaX.elTextoContiene(REPORTE_ROBO_PERDIDA),
                ValidarTextoQueContengaX.elTextoContiene(TUS_PQR_RADICADOS),
                ValidarTextoQueContengaX.elTextoContiene(TUS_EQUIPOS_SOPORTE)

        );
    }

    @And("Estado de servicios")
    public void estadoServicios() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ESTADO_SERVICIOS)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Estado de servicios'");
        ReportHooks.registrarPaso("Seleccionar 'Estado de servicios'");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(SERVICIOS)
        );

    }


    @And("Seleccionar No tengo servicio")
    public void seleccionarNotengoServicio() {
        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'No tengo servicio'");
        ReportHooks.registrarPaso("Seleccionar 'No tengo servicio'");

        theActorInTheSpotlight().attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),
                ValidarTextoQueContengaX.elTextoContiene(DATOS_FALLAS),
                ValidarTextoQueContengaX.elTextoContiene(LLAMADAS_FALLAS),
                ValidarTextoQueContengaX.elTextoContiene(OTROS_SERVICIOS_FALLAS)
        );
    }


    @Then("Validar fallas en tu navegacion Datos")
    public void fallasNavegacionDatos() {
        theActorInTheSpotlight().attemptsTo(
                FallasDatos.fallasDatos()
        );
    }

    @Then("Validar fallas en hacer o recibir llamadas")
    public void fallasLlamadas() {
        theActorInTheSpotlight().attemptsTo(
                FallasLlamadas.fallasLlamadas()
        );
    }

    @Then("Validar fallas otros servicios")
    public void fallasOtrosServicios() {
        theActorInTheSpotlight().attemptsTo(
                FallasOtrosServicios.fallasOtrosServicios()
        );
    }

    @Then("Validar permita seleccionar opción Reporte robo o perdida")
    public void reporteRoboOPerdida() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(REPORTE_ROBO_PERDIDA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar que permita seleccionar opción Reporte robo o perdida");
        ReportHooks.registrarPaso("Validar que permita seleccionar opción Reporte robo o perdida");

        // Solo se valida hasta aqui porque al enviar se direciona a asesor
        theActorInTheSpotlight().should(seeThat(
                ValidarElemento.esVisible(BTN_ENVIAR_2))
        );

        theActorInTheSpotlight().attemptsTo(
                Atras.irAtras(),
                Enter.theValue(SALIR).into(TXT_ENVIAR_MENSAJE),
                Click.on(BTN_ENVIAR),
                WaitForResponse.withText(ABANDONAR_CONVERSACION)
        );

    }

    @Then("Validar PQR radicados y direccionamiento pagina Claro")
    public void validarPQRRadicados() {
        theActorInTheSpotlight().attemptsTo(
                TusPQRRadicados.tusPQRRadicados()
        );
    }

    @Then("Validar tus equipos en soporte y direccionamiento pagina Claro")
    public void validarTusEquiposSoporte() {
        theActorInTheSpotlight().attemptsTo(
                TusEquiposEnSoporte.tusEquiposEnSoporte()
        );
    }

    @And("Seleccionar otras opciones")
    public void seleccionarOtrasOpciones() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(OTRAS_OPCIONES_POST)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Otras opciones'");
        ReportHooks.registrarPaso("Seleccionar 'Otras opciones'");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withTextContains(SELECCIONA)
        );
    }


    @And("Seleccionar registra tu equipo")
    public void seleccionarRegistraTuEquipo() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(REGISTRA_TU_EQUIPO)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Registra tu equipo'");
        ReportHooks.registrarPaso("Seleccionar 'Registra tu equipo'");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(PROCESO_RAPIDO, IMEI_REGISTRADO)
        );
    }

    @Then("Realizar el regitro del equipo")
    public void realizarRegistroDelEquipo() {
        theActorInTheSpotlight().attemptsTo(
                RegistraTuEquipo.registraTuEquipo()
        );
    }

    @Then("Aceptar declaracion y confirmar datos")
    public void declaracionYConformirmarDatos() {
        theActorInTheSpotlight().attemptsTo(
                ConfirmarDatosRegistraEquipo.confirmarDatosRegistraEquipo()
        );
    }

    @Then("Realizar el proceso de regsitro")
    public void realizarProcesoRegistro() {
        theActorInTheSpotlight().attemptsTo(
                RealizarProcesoRegistro.realizarProcesoRegistro()


        );
    }

    @And("^Seleccionar Roaming internacional$")
    public void roamingInternacional() {
        final String MENSAJE_CAPTURA = "Seleccionar menu Roaming Internacional";

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ROAMING_INTERNACIONAL)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        theActorInTheSpotlight().attemptsTo(
                ClickElementByText.clickElementByText(ENVIAR),
                WaitForTextContains.withTextContains(COMPRAR_ROAMING)
        );
    }

    @Then("Ingresar a la opcion Comprar Roaming")
    public void ingresarComprarRoaming() {
        theActorInTheSpotlight().attemptsTo(
                ComprarRoaming.comprarRoaming()
        );
    }

    @And("^Seleccionar opción Administrar Roaming$")
    public void administrarRoaming() {
        final String MENSAJE_CAPTURA = "Dar clic Administrar Roaming";
        CapturaDePantallaMovil.tomarCapturaPantalla(MENSAJE_CAPTURA);
        ReportHooks.registrarPaso(MENSAJE_CAPTURA);

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ADMINISTRAR_ROAMING),
                WaitForTextContains.withTextContains(TEXTO_CODIGO_SEGURIDAD)
        );
    }

    @Then("Validar el estado actual del servicio Roaming internacional")
    public void estadoRomaing() {
        theActorInTheSpotlight().attemptsTo(
                AdministarRoaming.administarRoaming()
        );
    }

    @Then("Validar direccionamiento de Paquetes y recargas")
    public void direccionamientoPaquetesYRecargas() {
        theActorInTheSpotlight().attemptsTo(
                PaquetesYRecargasPost.paquetesYRecargasPost()
        );
    }

    @Then("Validar el menu modifica tu plan")
    public void validarMenuModificaTuPlan() {
        theActorInTheSpotlight().attemptsTo(
                ModificaTuPlan.modificaTuPlan()
        );
    }

}
