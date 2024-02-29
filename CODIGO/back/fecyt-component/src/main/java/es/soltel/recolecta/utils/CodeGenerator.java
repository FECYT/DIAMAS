package es.soltel.recolecta.utils;

import java.security.SecureRandom;

public class CodeGenerator {

    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 8;

    public static String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            code.append(ALPHANUMERIC_CHARACTERS.charAt(randomIndex));
        }

        return code.toString();
    }

    public static void main(String[] args) {
        String generatedCode = generateCode();
        System.out.println("Generated Code: " + generatedCode);
    }
}