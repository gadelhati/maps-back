package com.maps.utils;

import com.maps.utils.E2EE.E2EEException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Isolated unit tests for the E2EE class
 * Testing secure AES encryption functionalities with random IV
 */
class E2EEUnitTest {

    private E2EE e2ee;
    
    @BeforeEach
    void setUp() {
        e2ee = new E2EE();
        // Configure test key directly via reflection
        ReflectionTestUtils.setField(e2ee, "configuredSecretKey", "testSecretKey123456789012345678901234567890");
    }

    @Test
    void testEncryptDecrypt_ValidData_ShouldReturnOriginalData() throws E2EEException {
        // Arrange
        String originalData = "Hello, World!";

        // Act
        String encrypted = e2ee.encrypt(originalData);
        String decrypted = e2ee.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertNotEquals(originalData, encrypted);
        assertEquals(originalData, decrypted);
        // Verify the encrypted data is valid Base64 and has reasonable length
        assertTrue(encrypted.length() > originalData.length());
    }

    @Test
    void testEncrypt_EmptyString_ShouldEncryptSuccessfully() throws E2EEException {
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
    void testEncrypt_LongString_ShouldEncryptSuccessfully() throws E2EEException {
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
    void testEncrypt_SpecialCharacters_ShouldEncryptSuccessfully() throws E2EEException {
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
    void testEncrypt_UnicodeCharacters_ShouldEncryptSuccessfully() throws E2EEException {
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
        E2EEException exception = assertThrows(E2EEException.class, () -> e2ee.encrypt(null));
        assertEquals("Input data cannot be null", exception.getMessage());
    }

    @Test
    void testDecrypt_NullInput_ShouldThrowException() {
        // Act & Assert
        assertThrows(E2EEException.class, () -> e2ee.decrypt(null));
    }

    @Test
    void testDecrypt_EmptyInput_ShouldThrowException() {
        // Act & Assert
        assertThrows(E2EEException.class, () -> e2ee.decrypt(""));
        assertThrows(E2EEException.class, () -> e2ee.decrypt("   "));
    }

    @Test
    void testDecrypt_InvalidEncryptedData_ShouldThrowException() {
        // Arrange
        String invalidEncryptedData = "invalid_encrypted_data";

        // Act & Assert
        assertThrows(E2EEException.class, () -> e2ee.decrypt(invalidEncryptedData));
    }

    @Test
    void testEncryptTwice_SameInput_ShouldProduceDifferentOutputs() throws E2EEException {
        // Arrange
        String data = "Test data for encryption";

        // Act
        String encrypted1 = e2ee.encrypt(data);
        String encrypted2 = e2ee.encrypt(data);

        // Assert - With random IV, same input should produce different encrypted outputs
        assertNotEquals(encrypted1, encrypted2, "Encrypted outputs should be different due to random IV");
        
        // But both should decrypt to the same original data
        assertEquals(data, e2ee.decrypt(encrypted1));
        assertEquals(data, e2ee.decrypt(encrypted2));
    }

    @Test
    void testEncrypt_DifferentInputs_ShouldProduceDifferentOutputs() throws E2EEException {
        // Arrange
        String data1 = "First test data";
        String data2 = "Second test data";

        // Act
        String encrypted1 = e2ee.encrypt(data1);
        String encrypted2 = e2ee.encrypt(data2);

        // Assert
        assertNotEquals(encrypted1, encrypted2);
    }
    
    @Test
    void testIsValidEncryptedData_WithValidData_ShouldReturnTrue() throws E2EEException {
        // Arrange
        String data = "Valid test data";
        String encrypted = e2ee.encrypt(data);
        
        // Act & Assert
        assertTrue(e2ee.isValidEncryptedData(encrypted));
    }
    
    @Test
    void testIsValidEncryptedData_WithInvalidData_ShouldReturnFalse() {
        // Act & Assert
        assertFalse(e2ee.isValidEncryptedData("invalid_data"));
        assertFalse(e2ee.isValidEncryptedData(null));
        assertFalse(e2ee.isValidEncryptedData(""));
    }
}