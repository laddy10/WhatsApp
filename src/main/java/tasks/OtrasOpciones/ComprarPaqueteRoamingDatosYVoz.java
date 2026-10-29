package tasks.OtrasOpciones;

import static tasks.CompraTusPaquetes.Constants_Paquetes.PQ_1;
import static utils.ConstantesPost.ESPAÑA;

import net.serenitybdd.screenplay.Performable;

public class ComprarPaqueteRoamingDatosYVoz {

    public static Performable comprarPaquete() {
        return ComprarPaqueteRoaming.para(ESPAÑA, PQ_1);
    }
}
