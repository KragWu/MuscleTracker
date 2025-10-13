package fr.kragwu.muscletracker.userapi.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class CipherTest {

    final private LocalDate localDate = LocalDate.of(2024, 1, 1);

    @Test
    void encrypt() {
        String passwordGiven = "p45s-w0Rd";
        String passwordExpected = "¢dg§Z§a";
        System.out.println(localDate);
        String passwordEncrypt = MyCipher.encrypt(passwordGiven, localDate.toString());
        assertEquals(passwordExpected, passwordEncrypt);
    }

    @Test
    void decrypt() {
        String passwordGiven = "¢dg§Z§a";
        String passwordExpected = "p45s-w0Rd";
        System.out.println(localDate);
        String passwordEncrypt = MyCipher.decrypt(passwordGiven, localDate.toString());
        assertEquals(passwordExpected, passwordEncrypt);
    }

    @Test
    void encryptHex() {
        String passwordGiven = "p45s-w0Rd";
        String passwordExpected = "QgQHRwBHAX9U";
        String passwordNextExpected = "ZQFkfGcSeilsOw9n";
        String uuid = "4f545e8a-c62a-4609-a087-4b8a6b14decc";
        System.out.println(localDate);
        System.out.println(uuid);
        String passwordEncrypt = MyCipher.encryptHex(passwordGiven, localDate.toString());
        String passwordNextEncrypt = MyCipher.encryptHex(passwordEncrypt, uuid);
        assertEquals(passwordExpected, passwordEncrypt);
        assertEquals(passwordNextExpected, passwordNextEncrypt);
    }

    @Test
    void decryptHex() {
        String passwordEncryptGiven = "ZQFkfGcSeilsOw9n";
        String passwordExpected = "QgQHRwBHAX9U";
        String passwordNextExpected = "p45s-w0Rd";
        String uuid = "4f545e8a-c62a-4609-a087-4b8a6b14decc";
        System.out.println(localDate);
        System.out.println(uuid);
        String passwordDecrypt = MyCipher.decryptHex(passwordEncryptGiven, uuid);
        String passwordNextEncrypt = MyCipher.decryptHex(passwordDecrypt, localDate.toString());
        assertEquals(passwordExpected, passwordDecrypt);
        assertEquals(passwordNextExpected, passwordNextEncrypt);
    }
}
