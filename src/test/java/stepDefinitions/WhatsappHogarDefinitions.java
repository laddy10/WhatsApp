package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Validaciones.ValidarTextoQueContengaX;
import interactions.scroll.ScrollGradual;
import interactions.wait.EsperarYClickSeleccionaEnUltimoMensaje;
import interactions.wait.WaitForResponse;
import interactions.wait.WaitForTextContains;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import tasks.Hogar.SoporteHogar.*;
import tasks.Hogar.SeleccionarLineaHogar;
import tasks.Hogar.TusPagosYFacturas.AbrirFacturaHogar;
import tasks.Hogar.TusPagosYFacturas.ConsultarFacturaHogar;
import tasks.Hogar.TusPagosYFacturas.IngresarAlLinkDePagoHogar;
import tasks.Hogar.TusPagosYFacturas.IngresarCodigoHogar;
import tasks.Hogar.TusPagosYFacturas.TransaccionTarjetaCreditoHogar;
import tasks.Hogar.TusPagosYFacturas.TransaccionPSEHogar;
import tasks.Hogar.TusPagosYFacturas.TransaccionBancolombiaHogar;
import tasks.Hogar.TusPagosYFacturas.TransaccionDaviplataHogar;
import tasks.Hogar.TusPagosYFacturas.TransaccionMisTarjetasRegistradasHogar;
import tasks.Hogar.TusPagosYFacturas.ValidarDireccionamientoProgramarPagosHogar;
import tasks.SalirConversacion;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.WhatsAppPage.BTN_ENVIAR_2;
import static userinterfaces.WhatsAppPostpagoPage.BTN_MENU_PRINCIPAL;
import static utils.Constantes.*;
import static utils.ConstantesPost.*;

public class WhatsappHogarDefinitions {

    private final User user = TestDataProvider.getRealUser();

    @And("^Seleccionar linea de consulta hogar$")
    public void seleccionarLineaConsultaHogar() {
        theActorInTheSpotlight().attemptsTo(SeleccionarLineaHogar.seleccionarLineaHogar());
    }

    @And("^Seleccionar menu principal hogar$")
    public void seleccionarMenuPrincipalHogar() {
        theActorInTheSpotlight().attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(OPCIONES_RAPIDAS_HOGAR),
                ValidarTextoQueContengaX.elTextoContiene(OTRAS_OPCIONES_2)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validación menú de opciones rápidas Hogar");
        ReportHooks.registrarPaso("Validación menú de opciones rápidas Hogar");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_MENU_PRINCIPAL),
                WaitForTextContains.withAnyTextContains(COMPRA_POR_WHATSAPP)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Acceso al Menú Principal Hogar");
        ReportHooks.registrarPaso("Acceso al Menú Principal Hogar");
    }

    @And("^Ingresar en opciones rapidas a soporte hogar$")
    public void opcionesRapidasSoporteHogar() {
        theActorInTheSpotlight().attemptsTo(OpcionesRapidasSoporteHogar.opcionesRapidasSoporteHogar());
    }

    @And("^Ingresar al menu de soporte hogar$")
    public void ingresarMenuSoporteHogar() {
        theActorInTheSpotlight().attemptsTo(MenuSoporteHogar.menuSoporteHogar());
    }

    @And("^Seleccionar el servicio fallas en tu navegacion$")
    public void seleccionarFallasEnTuNavegacion() {
        theActorInTheSpotlight().attemptsTo(FallasEnTuNavegacion.fallasEnTuNavegacion());
    }

    @And("^Validar opcion reiniciar modem$")
    public void validarReiniciarModem() {
        theActorInTheSpotlight().attemptsTo(ReiniciarModem.reiniciarModem());
    }

    @And("^Seleccionar el servicio fallas en tu en tu señal de TV$")
    public void seleccionarFallasEnTuTV() {
        theActorInTheSpotlight().attemptsTo(FallasEnTuSeñalTV.fallasEnTuSeñalTV());
    }

    @Then("^Validar imagen de recomendaciones$")
    public void validarImagenRecomendaciones() {
        theActorInTheSpotlight().attemptsTo(ImagenRecomendaciones.imagenRecomendaciones());
    }

    @Then("^Seleccionar el servicio fallas al hacer o recibir llamadas$")
    public void seleccionarFallasEnTuTelefonia() {
        theActorInTheSpotlight().attemptsTo(FallasEnTuSeñalTelefonia.fallasEnTuSeñalTelefonia());
    }

    @And("^Seleccionar la opcion Consulta tu factura y paga la tuya o la de otros$")
    public void seleccionarConsultaFactura() {
        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTA_FACTURA_PAGA_TUYA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar opción 'Consulta tu factura'");
        ReportHooks.registrarPaso("Seleccionar opción 'Consulta tu factura'");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(VALOR_A_PAGAR, FACTURA_POSTPAGO_LISTA)
        );
    }

    @And("^Validar informacion de la factura$")
    public void validarInformacionFactura() {
        theActorInTheSpotlight().attemptsTo(ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar información de la factura");
        ReportHooks.registrarPaso("Validar información de la factura");
    }

    @And("^Seleccionar la opcion Conoce y descarga tu factura$")
    public void seleccionarDescargaFactura() {
        theActorInTheSpotlight().attemptsTo(
                EsperarYClickSeleccionaEnUltimoMensaje.conTimeout(20),
                ClickTextoQueContengaX.elTextoContiene(CONOCE_Y_DESCARGA_TU_FACTURA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Seleccionar 'Conoce y descarga tu factura'");
        ReportHooks.registrarPaso("Seleccionar 'Conoce y descarga tu factura'");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_ENVIAR_2),
                WaitForTextContains.withAnyTextContains(TEXTO_NECESITO_CONFIRMAR_IDENTIDAD, TEXTO_CODIGO_SEGURIDAD)
        );
    }

    @Then("^Validar mensaje de validacion de identidad$")
    public void validarMensajeIdentidad() {
        theActorInTheSpotlight().attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(TEXTO_NECESITO_CONFIRMAR_IDENTIDAD));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar mensaje confirmación identidad");
        ReportHooks.registrarPaso("Validar mensaje confirmación identidad");
    }

    @And("^Ingresar el codigo de verificacion$")
    public void ingresarCodigoVerificacion() {
        theActorInTheSpotlight().attemptsTo(IngresarCodigoHogar.ingresarCodigoHogar());
    }

    @Then("^Validar mensaje de identidad confirmada con exito$")
    public void validarIdentidadExitosa() {
        theActorInTheSpotlight().attemptsTo(ValidarTextoQueContengaX.elTextoContiene(IDENTIDAD_CONFIRMADA));

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar identidad confirmada con éxito");
        ReportHooks.registrarPaso("Validar identidad confirmada con éxito");
    }

    @Then("^Validar mensaje de identidad confirmada con exito gradual$")
    public void validarIdentidadExitosaGradual() {
        theActorInTheSpotlight().attemptsTo(
               ScrollGradual.subir(0.20),
                ValidarTextoQueContengaX.elTextoContiene(IDENTIDAD_CONFIRMADA)
        );

        CapturaDePantallaMovil.tomarCapturaPantalla("Validar identidad confirmada con éxito gradual");
        ReportHooks.registrarPaso("Validar identidad confirmada con éxito gradual");
    }

    @And("^Ver factura hogar$")
    public void verFacturaHogar() {
        theActorInTheSpotlight().attemptsTo(AbrirFacturaHogar.abrirFacturaHogar());
    }

    @And("^Ingresar al link de pago hogar$")
    public void ingresarLinkPagoHogar() {
        theActorInTheSpotlight().attemptsTo(IngresarAlLinkDePagoHogar.ingresarAlLinkDePagoHogar());
    }

    @And("^Realizar transaccion de pago con tarjeta de credito hogar$")
    public void transaccionTarjetaCreditoHogar() {
        theActorInTheSpotlight().attemptsTo(TransaccionTarjetaCreditoHogar.transaccionTarjetaCreditoHogar());
    }

    @And("^Realizar transaccion de pago con PSE hogar$")
    public void transaccionPSEHogar() {
        theActorInTheSpotlight().attemptsTo(TransaccionPSEHogar.transaccionPSEHogar());
    }

    @And("^Realizar transaccion de pago con Boton Bancolombia hogar$")
    public void transaccionBancolombiaHogar() {
        theActorInTheSpotlight().attemptsTo(TransaccionBancolombiaHogar.transaccionBancolombiaHogar());
    }

    @And("^Realizar transaccion de pago con Daviplata hogar$")
    public void transaccionDaviplataHogar() {
        theActorInTheSpotlight().attemptsTo(TransaccionDaviplataHogar.transaccionDaviplataHogar());
    }

    @And("^Realizar transaccion de pago con Mis tarjetas registradas hogar$")
    public void transaccionMisTarjetasRegistradasHogar() {
        theActorInTheSpotlight().attemptsTo(TransaccionMisTarjetasRegistradasHogar.transaccionMisTarjetasRegistradasHogar());
    }

    @And("^Validar Programar pagos hogar$")
    public void validarProgramarPagosHogar() {
        theActorInTheSpotlight().attemptsTo(ValidarDireccionamientoProgramarPagosHogar.validarDireccionamientoProgramarPagosHogar());
    }

    @And("^Seleccionar Estado servicios y esperar revision hogar$")
    public void estadoServiciosYEsperarRevisionHogar() {
        theActorInTheSpotlight().attemptsTo(EstadoServiciosHogar.estadoServiciosHogar());
    }

    @And("^Validar opciones de Cambia tu plan hogar$")
    public void validarOpcionesCambiaTuPlanHogar() {
        theActorInTheSpotlight().attemptsTo(ConoceMejoraTuPlanHogar.conoceMejoraTuPlanHogar());
    }

    @And("^Validar opcion Servicios adicionales hogar$")
    public void validarOpcionServiciosAdicionalesHogar() {
        theActorInTheSpotlight().attemptsTo(ServiciosAdicionalesHogar.serviciosAdicionalesHogar());
    }

    @And("^Validar T-Resuelve soporte tecnologico hogar$")
    public void validarTResuelveSoporteTecnologicoHogar() {
        theActorInTheSpotlight().attemptsTo(TResuelveOpcionUnoHogar.tResuelveOpcionUnoHogar());
    }

    @And("^Salir conversacion$")
    public void salirConversacion() {
        theActorInTheSpotlight().attemptsTo(SalirConversacion.salir());
    }
}
