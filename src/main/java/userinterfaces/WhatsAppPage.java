package userinterfaces;

import io.appium.java_client.MobileBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class WhatsAppPage extends PageObject {

    public static final Target LBL_WHATSAPP =
            Target.the("Texto de WhatsApp")
                    .located(By.id("toolbar_logo"));

    public static final Target BTN_ENVIAR_MENSAJE =
            Target.the("Boton enviar mensaje")
                    .located(By.id("fab"));

    public static final Target BTN_LUPA =
            Target.the("Boton lupa")
                    .located(By.id("com.whatsapp:id/search_icon"));

    public static final Target BTN_LUPA_PRINCIPAL =
            Target.the("Boton lupa")
                    .located(By.id("search_icon"));


    public static final Target TXT_BUSCAR_TEXTO =
            Target.the("Caja de texto buscar")
                    .located(By.id("com.whatsapp:id/search_input"));

    public static final Target TXT_BUSCAR_TEXTO_PRINCIPAL =
            Target.the("Caja de texto buscar")
                    .located(By.id("search_input"));

    public static final Target BTN_MENU_ITEM =
            Target.the("Boton Más opciones WhatsApp")
                    .located(By.id("menuitem_overflow"));

    public static final Target TXT_ENVIAR_MENSAJE =
            Target.the("Caja de texto enviar mensaje")
                    .located(By.id("entry"));
    public static final Target BTN_CERRAR =
            Target.the("Bonton X")
                    .located(By.xpath("//*[@class='android.widget.ImageButton']"));
    public static final Target BTN_ENVIAR =
            Target.the("Boton enviar mensaje")
                    .located(By.id("send"));

    public static final Target BTN_SI =
            Target.the("Boton Si")
                    .located(By.id("quick_reply_btn_1"));

    public static final Target BTN_SI2 =
            Target.the("Boton Si")
                    .located(By.xpath("//*[@text='Si']"));

    public static final Target LBL_TRATAMIENTO_DATOS =
            Target.the("¿Autorizas el tratamiento de tus datos personales y aceptas los T&C de nuestro canal de atención digital?")
                    .located(By.xpath("//*[@text='¿Autorizas el tratamiento de tus datos personales y aceptas los T&C de nuestro canal de atención digital?']"));

    public static final Target BTN_VER_MENU_PREPAGO =
            Target.the("Boton Ver menú prepago ")
                    .located(By.xpath("//*[@text='Ver menú prepago']"));

    public static final Target LBL_SIN_SALDO =
            Target.the("Texto Actualmente no tienes saldo disponible")
                    .located(By.xpath("//*[@text='Actualmente no tienes saldo disponible.\n" +
                            "\n" +
                            "Elige una de estas opciones y sigue conectado  \uD83D\uDC47']"));

    public static final Target CHAT_CLARO =
            Target.the("Chat Claro")
                    .located(By.xpath("//*[@text='Claro Colombia']"));

    public static final Target LBL_VISTA_PREVIA =
            Target.the("Texto vista previ del destino ")
                    .located(By.xpath("//*[@text='claro.com.co']"));

    public static final Target LBL_MEJORA_TU_PLAN =
            Target.the("Texto Mejora tu plan")
                    .located(By.xpath("//*[@text='Mejora tu plan']"));

    public static final Target LBL_PAQUETE_ACTIVO =
            Target.the("Texto paquete activo")
                    .located(By.xpath("//*[contains(@text, 'Actualmente tienes activo')]"));

    public static final Target LBL_NOMBRE_PLAN =
            Target.the("Texto Nombre del plan")
                    .located(By.xpath("//*[contains(@text, 'Nombre del plan')]"));
    public static final Target LBL_MENU_LINEAS =
            Target.the("Texto del menú de líneas")
                    .located(By.xpath("//*[contains(@text, '¿Cuál cuenta quieres gestionar?')]"));
    public static final Target TXT_CAJA_MENSAJE =
            Target.the("Caja texto")
                    .located(By.id("entry"));

    public static final Target LBL_CONSUMO_PAQUETES =
            Target.the("Texto Tu consumo de paquetes a la fecha es")
                    .located(By.xpath("//*[contains(@text, 'Tu consumo de paquetes a la fecha es')]"));

    public static final Target BTN_INSTALAR =
            Target.the("Boton Instalar")
                    .located(By.xpath("//*[@text='Instalar']"));
    public static final Target BTN_CANCELAR =
            Target.the("Boton Cancelar")
                    .located(By.xpath("//*[@text='Cancelar']"));
    public static final Target TXT_BIENVENIDO_CLARO =
            Target.the("Texto ¡Bienvenido al nuevo Claro música!")
                    .located(By.xpath("//*[@text='¡Bienvenido al nuevo Claro música!']"));
    public static final Target LOG_CLARO_MUSICA =
            Target.the("Logo Claro Musica")
                    .located(By.xpath("//*[@contentDescription='Cm Logo']"));
    public static final Target TXT_PERMISO_NOTIFICACION =
            Target.the("Texto ¿Permitir que Claro música te envíe notificaciones?")
                    .located(By.xpath("//*[@text='¿Permitir que Claro música te envíe notificaciones?']"));
    public static final Target BTN_NO_PERMITIR =
            Target.the("Boton No permitir")
                    .located(By.xpath("//*[@text='No permitir']"));

    public static final Target BTN_SELECCIONA =
            Target.the("Boton Selecciona del menu tu lealtad merece más ")
                    .located(By.xpath("(//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\"])[3]"));

    public static final Target URL_CLARO_MUSICA2 =
            Target.the("URL Claro Musica")
                    .located(By.xpath("//*[@contentDescription='https://bit.ly/3Dw3dk9']"));

    public static final Target IMAG_CLARO_DRIVE =
            Target.the("Imagen Claro Drive")
                    .located(By.xpath("//android.webkit.WebView[@text=\"Claro drive\"]/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View/android.widget.Image"));

    public static final Target LBL_SIGNO_PRECIO =
            Target.the("Texto precio")
                    .located(By.id("select_list_item_title"));
    public static final Target LBL_MAS_OPCIONES =
            Target.the("Texto Más opciones")
                    .located(By.xpath("//*[@text='Más opciones']"));

    public static final Target BTN_SELECCIONA2 =
            Target.the("Boton Selecciona")
                    .located(By.xpath("(//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\"])[3]"));
    public static final Target BTN_CONTINUAR_RECARGA =
            Target.the("Boton Continuar recarga")
                    .located(By.xpath("//*[@text='Continuar recarga']"));
    public static final Target LBL_TARJETA_DEBITO_CREDITO =
            Target.the("Texto Tarjeta Débito o Crédito")
                    .located(By.xpath("//*[@text='Tarjeta Débito o Crédito']"));
    public static final Target LBL_SIN_CUENTA_NEQUI =
            Target.the("Texto Sin cuenta nequi")
                    .located(By.xpath("//*[@text='\uD83D\uDE25 Parece que este número no tiene una cuenta en Nequi.\n" +
                            "\n" +
                            "Puedes intentarlo de nuevo con otra cuenta Nequi o elegir otro medio de pago \uD83D\uDC47']"));
    public static final Target LBL_CHATS = Target.the("Texto chat con nombre dinámico")
            .locatedBy("//*[@text='{0}']");

    public static final Target BTN_ELIMINAR_CHAT =
            Target.the("Boton Eliminar chat")
                    .located(By.id("menuitem_conversations_delete"));
    public static final Target BTN_MAS_OPCIONES =
            Target.the("Boton Más opciones")
                    .located(By.id("menuitem_overflow"));

    public static final Target LBL_MENSAJES =
            Target.the("Mensajes del chat")
                    .located(MobileBy.AndroidUIAutomator(
                            "new UiSelector().className(\"android.widget.TextView\")"));

    public static final Target LBL_MENU_ANTERIOR =
            Target.the("Menu anterior LDI")
                    .located(MobileBy.xpath(
                            "(//android.widget.FrameLayout[@content-desc=\"Menú anterior\"])[2]"));

    public static final Target LBL_MENU_ANTERIOR2 =
            Target.the("Menu anterior LDI")
                    .located(MobileBy.xpath(
                            "(//android.widget.FrameLayout[@content-desc=\"Menú anterior\"])[3]"));

    public static final Target BTN_ENVIAR_2 = Target.the("Botón Enviar")
            .located(MobileBy.xpath("//*[translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='enviar']"));

    public static final Target BTN_SELECCIONA_PQ_TODO_INCLUIDO =
            Target.the("Boton Selecciona")
                    .located(MobileBy.xpath("(//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\"])[2]"));
    public static final Target BTN_VER_PAQUETES =
            Target.the("Boton Ver paquetes")
                    .located(By.xpath("//*[@text='Ver paquetes']"));

    public static final Target BTN_CONFIRMAR =
            Target.the("Boton Confirmar")
                    .located(By.xpath("//*[@text='Confirmar']"));

    public static final Target BTN_MEDIOS_DE_PAGO =
            Target.the("Boton Medios de pago")
                    .located(By.xpath("//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\" and @text=\"Medios de pago\"]"));
    public static final Target BTN_SELECCIONA_RECARGAS =
            Target.the("Boton Selecciona recargas")
                    .located(By.xpath("(//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\"])[3]"));


    public static final Target LBL_MENSAJE_ACTUALIZACION =
            Target.the("Mensaje de actualización del sistema")
                    .located(By.xpath("//*[contains(@text, 'Nuestro sistema registra un proceso de actualización')]"));
    public static final Target BTN_NO_ACTUALIZACION =
            Target.the("Botón No para rechazar actualización")
                    .located(By.xpath("//*[@text='No']"));

    public static final Target BTN_SELECCIONA_SOPORTE =
            Target.the("Botón Selecciona Soporte")
                    .located(By.xpath("(//android.widget.FrameLayout[@resource-id=\"com.whatsapp:id/button\"])[2]"));


    public static final Target BTN_REPRODUCIR_VIDEO =
            Target.the("Botón reproducir video")
                    .located(By.id("com.whatsapp:id/cancel_download"));
    public static final Target BTN_SELECCIONA_PQ_INTERNACIONALES =
            Target.the("Boton Selecciona sección paquetes internacionales")
                    .located(MobileBy.xpath("//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\"]"));

    public static final Target BTN_SELECCIONA_TU_LEALTAD_2 =
            Target.the("Boton Selecciona del menu tu lealtad merece más ")
                    .located(By.xpath("(//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\"])[4]"));

    public class AppConstants {
        public static final String WHATSAPP_PACKAGE = "com.whatsapp";
    }


}