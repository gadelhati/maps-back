package com.maps.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes simples de validação
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SimpleValidationTest {

    // Pattern para validação de email
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    // Pattern para validação de senha
    private static final Pattern PASSWORD_DIGIT = Pattern.compile(".*\\d.*");
    private static final Pattern PASSWORD_UPPER = Pattern.compile(".*[A-Z].*");
    private static final Pattern PASSWORD_LOWER = Pattern.compile(".*[a-z].*");
    private static final Pattern PASSWORD_LETTER = Pattern.compile(".*[a-zA-Z].*");

    @Test
    void testEmailValidation() {
        // Emails válidos
        assertTrue(EMAIL_PATTERN.matcher("test@example.com").matches());
        assertTrue(EMAIL_PATTERN.matcher("user.name@domain.co.uk").matches());
        assertTrue(EMAIL_PATTERN.matcher("test123@test.org").matches());
        
        // Emails inválidos
        assertFalse(EMAIL_PATTERN.matcher("invalid-email").matches());
        assertFalse(EMAIL_PATTERN.matcher("@example.com").matches());
        assertFalse(EMAIL_PATTERN.matcher("test@").matches());
        assertFalse(EMAIL_PATTERN.matcher("test@.com").matches());
    }

    @Test
    void testPasswordValidation() {
        String validPassword = "Password123!";
        String invalidPassword1 = "password"; // sem maiúscula
        String invalidPassword2 = "PASSWORD"; // sem minúscula
        String invalidPassword3 = "Password"; // sem dígito
        String invalidPassword4 = "123456789"; // sem letra

        // Senha válida
        assertTrue(PASSWORD_DIGIT.matcher(validPassword).matches());
        assertTrue(PASSWORD_UPPER.matcher(validPassword).matches());
        assertTrue(PASSWORD_LOWER.matcher(validPassword).matches());
        assertTrue(PASSWORD_LETTER.matcher(validPassword).matches());

        // Senhas inválidas
        assertFalse(PASSWORD_UPPER.matcher(invalidPassword1).matches());
        assertFalse(PASSWORD_LOWER.matcher(invalidPassword2).matches());
        assertFalse(PASSWORD_DIGIT.matcher(invalidPassword3).matches());
        assertFalse(PASSWORD_LETTER.matcher(invalidPassword4).matches());
    }

    @Test
    void testLengthValidation() {
        String shortString = "ab";
        String normalString = "hello world";
        String longString = "a".repeat(256);

        // Validação de tamanho mínimo (exemplo: 3 caracteres)
        assertTrue(normalString.length() >= 3);
        assertFalse(shortString.length() >= 3);

        // Validação de tamanho máximo (exemplo: 255 caracteres)
        assertTrue(normalString.length() <= 255);
        assertFalse(longString.length() <= 255);
    }

    @Test
    void testNotBlankValidation() {
        String validString = "test";
        String nullString = null;
        String emptyString = "";
        String blankString = "   ";

        // String válida
        assertNotNull(validString);
        assertFalse(validString.trim().isEmpty());

        // Strings inválidas
        assertTrue(nullString == null || nullString.trim().isEmpty());
        assertTrue(emptyString.trim().isEmpty());
        assertTrue(blankString.trim().isEmpty());
    }

    @Test
    void testNumericValidation() {
        String validNumber = "12345";
        String invalidNumber = "abc123";
        String emptyNumber = "";

        // Número válido
        assertTrue(validNumber.matches("\\d+"));

        // Números inválidos
        assertFalse(invalidNumber.matches("\\d+"));
        assertFalse(emptyNumber.matches("\\d+"));
    }
}