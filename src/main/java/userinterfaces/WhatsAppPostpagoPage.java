package userinterfaces;

import io.appium.java_client.MobileBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class WhatsAppPostpagoPage {

    public static final Target BTN_MENU_PRINCIPAL =
            Target.the("Boton Menú principal")
                    .located(By.xpath("//android.widget.TextView[@text=\"Menú principal\"]"));
    public static final Target LOGO_CLARO_VIDEO=
            Target.the("Logo clarovideo")
                    .located(By.xpath("(//android.widget.Image[@text=\"clarovideo\"])[1]"));

    public static final Target BTN_ACCEDER_CLARO_MUSICA =
            Target.the("Boton Acceder a Claro música")
                    .located(By.xpath("//*[@text='Acceder a Claro música']"));

    public static final Target BTN_CLOSE =
            Target.the("Boton Close")
                    .located(By.xpath("//android.widget.Button[@text=\"Close\"]"));

    public static final Target BTN_CLOSE_2 =
            Target.the("Boton Close")
                    .located(By.xpath("//android.widget.Button[@text=\"Close\"]"));

    public static final Target LINK_TRATAMIENTO_DATOS =
            Target.the("Link tratamiento de datos")
                    .located(By.xpath("//android.view.View[@content-desc=\"https://www.claro.com.co/personas/autogestion/whatsapp/\"]"));

    public static final Target BTN_SI_AUTORIZO =
            Target.the("Boton Si, autorizo")
                    .located(By.xpath("//*[@text='Si, autorizo']"));





    // Elementos Portal de Pagos
    public static final Target LBL_FACTURA_POSTPAGO_LISTA =
            Target.the("Factura postpago lista")
                    .located(By.xpath("//*[contains(@text, '¡Tu factura postpago está lista!')]"));

    public static final Target SELECTOR_MEDIO_PAGO =
            Target.the("Selector medio de pago")
                    .located(By.xpath("//android.view.View[@resource-id=\"select\"]"));

    public static final Target BTN_CONTINUAR_PAGO =
            Target.the("Botón Continuar pago")
                    .located(By.xpath("//*[@text='Continuar']"));

    // Elementos Formulario Tarjeta
    public static final Target TXT_NUMERO_TARJETA =
            Target.the("Campo número de tarjeta")
                    .located(By.xpath("//android.widget.EditText[@resource-id=\"NUMERO_TARJETA\"]"));

    public static final Target TXT_NOMBRE_APELLIDO =
            Target.the("Campo nombre y apellido")
                    .located(By.xpath("//android.widget.EditText[@resource-id=\"NOMBRE_TARJETA\"]"));

    public static final Target SELECT_TIPO_DOCUMENTO =
            Target.the("Selector tipo documento")
                    .located(By.xpath("//android.view.View[@resource-id=\"TIPO_DOCUMENTO\"]"));

    public static final Target TXT_NUMERO_DOCUMENTO =
            Target.the("Campo número documento")
                    .located(By.xpath("//android.widget.EditText[@resource-id=\"NUMERO_DOCUMENTO\"]"));

    public static final Target SELECT_MES_EXPIRACION =
            Target.the("Selector mes expiración")
                    .located(By.xpath("//android.view.View[@resource-id=\"FECHA_VENC_MES\"]"));

    public static final Target SELECT_ANIO_EXPIRACION =
            Target.the("Selector año expiración")
                    .located(By.xpath("//android.view.View[@resource-id=\"FECHA_VENC_ANNO\"]"));

    public static final Target TXT_CVC =
            Target.the("Campo CVC")
                    .located(By.xpath("//android.widget.EditText[@resource-id=\"CODIGO_SEGURIDAD\"]"));

    public static final Target TXT_CORREO_ELECTRONICO =
            Target.the("Campo correo electrónico")
                    .located(By.xpath("//android.widget.EditText[@resource-id=\"EMAIL\"]"));

    public static final Target TXT_NUMERO_CELULAR =
            Target.the("Campo número celular")
                    .located(By.xpath("//android.widget.EditText[@resource-id=\"TELEFONO\"]"));

    public static final Target SELECT_NUMERO_CUOTAS =
            Target.the("Selector número cuotas")
                    .located(By.xpath("//android.view.View[@resource-id=\"CUOTAS\"]"));

    public static final Target TOGGLE_GUARDAR_TARJETA =
            Target.the("Toggle guardar tarjeta")
                    .located(By.xpath("//android.view.View[@text=\"Si\"]"));

    public static final Target TOGGLE_GUARDAR_TARJETA_NO =
            Target.the("Toggle guardar tarjeta opcion No")
                    .located(By.xpath("//android.view.View[@text=\"¿Guardar esta tarjeta? No\"]"));

    public static final Target BTN_CONFIRMAR_FINAL =
            Target.the("Botón Confirmar final")
                    .located(By.xpath("//*[@text='Confirmar']"));

    public static final Target BTN_CANCELAR_FINAL =
            Target.the("Botón Cancelar final")
                    .located(By.xpath("//*[@text='Cancelar']"));

    public static final Target BTN_TARJETA_CREDITO =
            Target.the("Tarjeta crédito")
                    .located(By.xpath("//android.widget.TextView[@text=\"Tarjeta crédito\"]"));
    public static final Target TXT_RECHAZAR = Target.the("Texto Rechazar")
            .located(MobileBy.xpath("//*[@text='Rechazar']"));

    public static final Target TXT_MARCAR_COMO_LEIDO = Target.the("Texto MARCAR COMO LEÍDO")
            .located(MobileBy.xpath("//android.widget.Button[@content-desc=\"Marcar como leído\"]"));

    public static final Target LBL_MAS =
            Target.the("Texto Más")
                    .located(By.xpath("//*[@text='Más']"));
    public static final Target BTN_LECTOR_PDF_DRIVE =
            Target.the("Boton Lector de PDF de Drive")
                    .located(By.xpath("//*[@text='Lector de PDF de Drive']"));

    public static final Target TXT_CONTRASENA_FACTURA =
            Target.the("Campo contraseña factura")
                    .located(By.id("com.google.android.apps.docs:id/password"));

    public static final Target URL_FACTURA_DESCARGADA =
            Target.the("URL factura descargada")
                    .located(By.id("com.android.chrome:id/url_bar"));

    public static final Target IMAGEN_CLARO_VIDEO =
            Target.the("Foto como registrarse Claro Video")
                    .located(By.id("com.whatsapp:id/image"));

    public static final Target IMAGEN_CLARO_MUSICA =
            Target.the("Foto como registrarse Claro Musica")
                    .located(By.id("com.whatsapp:id/image"));

    public static final Target BTN_CLARO_VIDEO =
            Target.the("Boton Claro Video")
                    .located(By.xpath("//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\" and @text=\"Claro video\"]"));

    public static final Target BTN_CLARO_MUSICA =
            Target.the("Boton Claro Musica")
                    .located(By.xpath("//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\" and @text=\"Claro música\"]"));
    public static final Target LBL_EQUIPO_REGISTRADO =
            Target.the("Texto La consulta realizada indica que tu dispositivo se encuentra registrado")
                    .located(By.xpath("//android.widget.TextView[@resource-id=\"com.whatsapp:id/bottom_message\" and @text=\"\uD83D\uDE0A La consulta realizada indica que tu dispositivo se encuentra registrado\"]"));
    public static final Target LBL_IMEI_REGISTRADO =
            Target.the("Texto Tu IMEI ya se encuentra registrado.")
                    .located(By.xpath("//*[contains(@text, 'Tu IMEI ya se encuentra registrado.')]"));


}
