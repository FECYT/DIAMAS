package es.soltel.recolecta.utils;


import java.util.Base64;

public class Base64Util {

    // Método para convertir una cadena a Base64 seguro para URL
    public static String convertirABase64(String textoOriginal) {
        byte[] bytesCodificados = Base64.getUrlEncoder().encode(textoOriginal.getBytes());
        return new String(bytesCodificados);
    }

    // Método para decodificar una cadena desde Base64 seguro para URL
    public static String decodificarDesdeBase64(String textoEnBase64) {
        byte[] bytesDecodificados = Base64.getUrlDecoder().decode(textoEnBase64.getBytes());
        return new String(bytesDecodificados);
    }

}
