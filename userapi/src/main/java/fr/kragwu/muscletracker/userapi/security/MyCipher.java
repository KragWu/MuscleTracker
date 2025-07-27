package fr.kragwu.muscletracker.userapi.security;

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
}
