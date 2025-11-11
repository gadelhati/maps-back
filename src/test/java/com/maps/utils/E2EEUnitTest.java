package com.maps.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe E2EE
 * Testando funcionalidades de criptografia AES
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class E2EEUnitTest {

    private E2EE e2ee;

    @BeforeEach
    void setUp() {
        e2ee = new E2EE();
    }

    @Test
    void testEncryptDecrypt_ValidData_ShouldReturnOriginalData() throws Exception {
        // Arrange
        String originalData = "Hello, World!";

        // Act
        String encrypted = e2ee.encrypt(originalData);
        String decrypted = e2ee.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertNotEquals(originalData, encrypted);
        assertEquals(originalData, decrypted);
    }

    @Test
    void testEncrypt_EmptyString_ShouldEncryptSuccessfully() throws Exception {
        // Arrange
        String emptyData = "";

        // Act
        String encrypted = e2ee.encrypt(emptyData);
        String decrypted = e2ee.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertEquals(emptyData, decrypted);
    }

    @Test
    void testEncrypt_LongString_ShouldEncryptSuccessfully() throws Exception {
        // Arrange
        String longData = "A".repeat(1000);

        // Act
        String encrypted = e2ee.encrypt(longData);
        String decrypted = e2ee.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertEquals(longData, decrypted);
    }

    @Test
    void testEncrypt_SpecialCharacters_ShouldEncryptSuccessfully() throws Exception {
        // Arrange
        String specialData = "!@#$%^&*()_+{}|:<>?[]\\;',./`~";

        // Act
        String encrypted = e2ee.encrypt(specialData);
        String decrypted = e2ee.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertEquals(specialData, decrypted);
    }

    @Test
    void testEncrypt_UnicodeCharacters_ShouldEncryptSuccessfully() throws Exception {
        // Arrange
        String unicodeData = "Olá, 世界! 🌍 مرحبا";

        // Act
        String encrypted = e2ee.encrypt(unicodeData);
        String decrypted = e2ee.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertEquals(unicodeData, decrypted);
    }

    @Test
    void testEncrypt_NullInput_ShouldThrowException() {
        // Act & Assert
        assertThrows(Exception.class, () -> e2ee.encrypt(null));
    }

    @Test
    void testDecrypt_NullInput_ShouldThrowException() {
        // Act & Assert
        assertThrows(Exception.class, () -> e2ee.decrypt(null));
    }

    @Test
    void testDecrypt_InvalidEncryptedData_ShouldThrowException() {
        // Arrange
        String invalidEncryptedData = "invalid_encrypted_data";

        // Act & Assert
        assertThrows(Exception.class, () -> e2ee.decrypt(invalidEncryptedData));
    }

    @Test
    void testEncryptTwice_SameInput_ShouldProduceSameOutput() throws Exception {
        // Arrange
        String data = "Test data for encryption";

        // Act
        String encrypted1 = e2ee.encrypt(data);
        String encrypted2 = e2ee.encrypt(data);

        // Assert
        assertEquals(encrypted1, encrypted2);
    }

    @Test
    void testEncrypt_DifferentInputs_ShouldProduceDifferentOutputs() throws Exception {
        // Arrange
        String data1 = "First test data";
        String data2 = "Second test data";

        // Act
        String encrypted1 = e2ee.encrypt(data1);
        String encrypted2 = e2ee.encrypt(data2);

        // Assert
        assertNotEquals(encrypted1, encrypted2);
    }
}