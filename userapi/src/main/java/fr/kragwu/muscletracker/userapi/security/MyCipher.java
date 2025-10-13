package fr.kragwu.muscletracker.userapi.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MyCipher {
    public static String encrypt(String password, String secretKey) {
        StringBuilder cryptedPassword = new StringBuilder();
        for(int index = 0; index < password.length(); index++) {
            int characterEncodeA = password.charAt(index);
            int characterEncodeB = secretKey.charAt(index % secretKey.length());
            int characterEncode = characterEncodeA + characterEncodeB;
            cryptedPassword.append(Character.toChars(characterEncode));
        }
        return cryptedPassword.toString();
    }

    public static String decrypt(String cryptedPassword, String secretKey) {
        StringBuilder decryptedPassword = new StringBuilder();
        for(int index = 0; index < cryptedPassword.length(); index++) {
            int characterDecodeA = cryptedPassword.charAt(index);
            int characterDencodeB = secretKey.charAt(index % secretKey.length());
            int characterDecode = characterDecodeA - characterDencodeB;
            decryptedPassword.append(Character.toChars(characterDecode));
        }
        return decryptedPassword.toString();
    }

    public static String encryptHex(String password, String secretKey) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[passwordBytes.length];

        for (int i = 0; i < passwordBytes.length; i++) {
            result[i] = (byte) (passwordBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        // Encode en base64 pour un header HTTP safe
        return Base64.getEncoder().encodeToString(result);
    }

    public static String decryptHex(String cryptedPassword, String secretKey) {
        byte[] cryptedBytes = Base64.getDecoder().decode(cryptedPassword);
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[cryptedBytes.length];

        for (int i = 0; i < cryptedBytes.length; i++) {
            result[i] = (byte) (cryptedBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return new String(result, StandardCharsets.UTF_8);
    }
}
