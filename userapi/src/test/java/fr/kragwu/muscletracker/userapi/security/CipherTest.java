package fr.kragwu.muscletracker.userapi.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class CipherTest {

    private LocalDate now = LocalDate.of(2024, 1, 1);

    @Test
    void encrypt() {
        String passwordGiven = "p45s-w0Rd";
        String passwordExpected = "¢dg§Z§a";
        System.out.println(now);
        String passwordEncrypt = MyCipher.encrypt(passwordGiven, now.toString());
        assertEquals(passwordExpected, passwordEncrypt);
    }

    @Test
    void decrypt() {
        String passwordGiven = "¢dg§Z§a";
        String passwordExpected = "p45s-w0Rd";
        System.out.println(now);
        String passwordEncrypt = MyCipher.decrypt(passwordGiven, now.toString());
        assertEquals(passwordExpected, passwordEncrypt);
    }
}
