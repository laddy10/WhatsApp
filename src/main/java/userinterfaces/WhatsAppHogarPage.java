package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class WhatsAppHogarPage {

    public static final Target IMG_RECOMENDACIONES =
            Target.the("Imagen de recomendaciones TV").located(By.id("image"));
    public static final Target LBL_NO =
            Target.the("Texto No").located(By.xpath("//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\" and @text=\"No\"]"));

    public static final Target LBL_NO_2 =
            Target.the("Texto No contactar asesor").located(By.xpath("(//android.widget.TextView[@resource-id=\"com.whatsapp:id/button_content\"])[4]"));
    public static final Target TXT_AUTORIZA_PSE =
            Target.the("Texto Autoriza a PSE").
                    located(By.xpath("//android.widget.Image[@content-desc=\"Autoriza\"]"));
    public static final Target CHECK_TRATAMIENTOS_PERSONALES =
            Target.the("Texto tratamiento de datos personales").
                    located(By.xpath("//android.widget.CheckBox[@resource-id=\"dataConsent\"]"));
    public static final Target CHECK_TERMINOS_CONDICIONES =
            Target.the("Texto términos y condiciones").
                    located(By.xpath("//android.widget.CheckBox[@resource-id=\"termsConditions\"]"));

}
