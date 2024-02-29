package es.soltel.herramientaevaluador.common.base.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.Normalizer;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import es.soltel.herramientaevaluador.common.base.exception.RestException;
import es.soltel.herramientaevaluador.common.config.token.IJWTService;
import io.jsonwebtoken.Claims;

public class Utils {

    public final static String PATTERN_USERNAME = "^\\w{0,255}$";
    public final static String PATTERN_NOMBRE_APELLIDOS = "^[a-zA-ZÀ-ÿ -]{0,255}$";
    public final static String PATTERN_SOLO_LETRAS = "^[a-zA-ZñÑ ]{0,255}$";
    public final static String PATTERN_EMAIL = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.{1}[a-z]{2,4}$";
    public final static String PATTERN_ROLE_NAME = "^[a-zA-ZÀ-ÿ0-9 ]{0,255}$";

    private static IJWTService jwtService;

    @Autowired
    private void setJwtService(IJWTService jwtService) {
        Utils.jwtService = jwtService;
    }

    private static HttpServletRequest servletRequest;

    @Autowired
    private void setServletRequest(HttpServletRequest servletRequest) {
        Utils.servletRequest = servletRequest;
    }

    public static Boolean matchPatter(String pattern, String text) {
        if (text != null && !Pattern.compile(pattern).matcher(text).matches()) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    public static String getTokenOffice365() throws RestException {
        String tokenOffice365 = null;
        try {
            String token = jwtService.getToken(servletRequest);

            Claims claims = jwtService.getClaims(token);

            tokenOffice365 = (String) claims.get("tokenOffice365");

        }
        catch (Exception e) {
         
            e.printStackTrace();
        }
        return tokenOffice365;

    }

    // Esta función no cambia solo las tildes sino también las Ñ por N
    public static String eliminaAcentos(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public static String stripAccents(String s) {
        /* Salvamos las ñ */
        s = s.replace('ñ', '\001');
        s = s.replace('Ñ', '\002');
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        /* Volvemos las ñ a la cadena */
        s = s.replace('\001', 'ñ');
        s = s.replace('\002', 'Ñ');

        return s;
    }

    public static byte[] encrypt(String plainText, PublicKey publicKey) throws Exception {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes());

        return cipherText;
    }

    public static String decrypt(byte[] cipherTextArray, PrivateKey privateKey) throws Exception {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);

        return new String(decryptedTextArray);
    }

}
