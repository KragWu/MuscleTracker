package fr.kragwu.muscletracker.userapi.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class CipherTest {

    private LocalDateTime now = LocalDateTime.now();

    @Test
    void encrypt() {
        String passwordGiven = "p45s-w0Rd";
        String passwordExpected = "¢dg§Z§c";
        
        String passwordEncrypt = Cipher.encrypt(passwordGiven, now.toString());
        assertEquals(passwordExpected, passwordEncrypt);
    }

    @Test
    void decrypt() {
        String passwordGiven = "¢dg§Z§c";
        String passwordExpected = "p45s-w0Rd";
        String passwordEncrypt = Cipher.decrypt(passwordGiven, now.toString());
        assertEquals(passwordExpected, passwordEncrypt);
    }
}
