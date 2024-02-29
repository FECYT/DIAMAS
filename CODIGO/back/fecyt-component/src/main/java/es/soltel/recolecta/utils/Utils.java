package es.soltel.recolecta.utils;

import java.text.Normalizer;

public class Utils {

    public static String parsearTildes(String input) {
        // Normalizar y eliminar diacríticos adicionales
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Reemplazar letras con tilde por letras sin tilde
        return normalized.replaceAll("[^\\p{ASCII}]", "");
    }

    public static Double keyCategoryToNumber(){
        return 0.0;
    }

}
