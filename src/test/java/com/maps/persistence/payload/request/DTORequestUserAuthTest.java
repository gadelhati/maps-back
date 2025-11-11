package com.maps.persistence.payload.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para DTORequestUserAuth (record)
 * Testando a estrutura de dados de requisição de autenticação
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DTORequestUserAuthTest {

    @Test
    void testRecordCreation_WithAllArgs_ShouldCreateCorrectly() {
        // Arrange
        String username = "testuser";
        String password = "TestPass123!";
        Integer totpKey = 123456;
        String captchaToken = "captcha123";

        // Act
        DTORequestUserAuth dto = new DTORequestUserAuth(username, password, totpKey, captchaToken);

        // Assert
        assertNotNull(dto);
        assertEquals(username, dto.username());
        assertEquals(password, dto.password());
        assertEquals(totpKey, dto.totpKey());
        assertEquals(captchaToken, dto.captchaToken());
    }

    @Test
    void testRecordAccessors_ShouldReturnCorrectValues() {
        // Arrange
        DTORequestUserAuth dto = new DTORequestUserAuth("john.doe", "SecurePass123!", 654321, "token123");

        // Act & Assert
        assertEquals("john.doe", dto.username());
        assertEquals("SecurePass123!", dto.password());
        assertEquals(654321, dto.totpKey());
        assertEquals("token123", dto.captchaToken());
    }

    @Test
    void testRecordWithNullValues_ShouldAcceptNulls() {
        // Act
        DTORequestUserAuth dto = new DTORequestUserAuth(null, null, null, null);

        // Assert
        assertNotNull(dto);
        assertNull(dto.username());
        assertNull(dto.password());
        assertNull(dto.totpKey());
        assertNull(dto.captchaToken());
    }

    @Test
    void testRecordEquality_SameValues_ShouldBeEqual() {
        // Arrange
        DTORequestUserAuth dto1 = new DTORequestUserAuth("user", "pass", 123456, "token");
        DTORequestUserAuth dto2 = new DTORequestUserAuth("user", "pass", 123456, "token");

        // Act & Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testRecordEquality_DifferentValues_ShouldNotBeEqual() {
        // Arrange
        DTORequestUserAuth dto1 = new DTORequestUserAuth("user1", "pass", 123456, "token");
        DTORequestUserAuth dto2 = new DTORequestUserAuth("user2", "pass", 123456, "token");

        // Act & Assert
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testRecordToString_ShouldContainFieldValues() {
        // Arrange
        DTORequestUserAuth dto = new DTORequestUserAuth("testuser", "password123", 999999, "captcha");

        // Act
        String toString = dto.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("password123"));
        assertTrue(toString.contains("999999"));
        assertTrue(toString.contains("captcha"));
    }

    @Test
    void testSpecialCharactersInFields_ShouldHandleCorrectly() {
        // Arrange
        String username = "user@domain.com";
        String password = "P@ssw0rd!#$%";
        String captchaToken = "token-with-dashes_and_underscores";

        // Act
        DTORequestUserAuth dto = new DTORequestUserAuth(username, password, 123456, captchaToken);

        // Assert
        assertEquals(username, dto.username());
        assertEquals(password, dto.password());
        assertEquals(captchaToken, dto.captchaToken());
    }

    @Test
    void testEmptyStrings_ShouldBeAllowed() {
        // Arrange
        String emptyUsername = "";
        String emptyPassword = "";
        String emptyCaptcha = "";

        // Act
        DTORequestUserAuth dto = new DTORequestUserAuth(emptyUsername, emptyPassword, 0, emptyCaptcha);

        // Assert
        assertEquals(emptyUsername, dto.username());
        assertEquals(emptyPassword, dto.password());
        assertEquals(0, dto.totpKey());
        assertEquals(emptyCaptcha, dto.captchaToken());
    }

    @Test
    void testTotpKeyRange_ValidValues_ShouldWork() {
        // Arrange & Act & Assert
        // Teste com valor mínimo
        DTORequestUserAuth dto1 = new DTORequestUserAuth("user", "pass", 0, "token");
        assertEquals(0, dto1.totpKey());

        // Teste com valor máximo típico de TOTP (6 dígitos)
        DTORequestUserAuth dto2 = new DTORequestUserAuth("user", "pass", 999999, "token");
        assertEquals(999999, dto2.totpKey());

        // Teste com valor negativo
        DTORequestUserAuth dto3 = new DTORequestUserAuth("user", "pass", -1, "token");
        assertEquals(-1, dto3.totpKey());
    }

    @Test
    void testRecordImmutability_ShouldNotBeModifiable() {
        // Arrange
        DTORequestUserAuth dto = new DTORequestUserAuth("user", "pass", 123456, "token");

        // Act & Assert - Records são imutáveis
        // Não há métodos setter para modificar os valores
        assertEquals("user", dto.username());
        assertEquals("pass", dto.password());
        assertEquals(123456, dto.totpKey());
        assertEquals("token", dto.captchaToken());
    }
}