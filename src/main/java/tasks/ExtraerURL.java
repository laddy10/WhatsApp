package tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraerURL {

    public static String desdeTexto(String texto) {
        Pattern pattern = Pattern.compile("(clro\\.co/\\S+)");
        Matcher matcher = pattern.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1); // devuelve la primera coincidencia
        }
        throw new RuntimeException("No se encontró una URL válida en el texto.");
    }
}
