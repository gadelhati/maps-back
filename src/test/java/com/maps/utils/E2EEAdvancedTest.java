package com.maps.utils;

import com.maps.utils.E2EE.E2EEException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Isolated advanced E2EE tests covering extreme cases,
 * performance scenarios, security validations, and comprehensive encryption scenarios.
 * 
 * @author Marcelo Ribeiro Gadelha
 */
class E2EEAdvancedTest {

    private E2EE e2ee;
    
    @BeforeEach
    void setUp() {
        e2ee = new E2EE();
        // Configurar chave de teste diretamente via reflection
        ReflectionTestUtils.setField(e2ee, "configuredSecretKey", "advancedTestKey123456789012345678901234567890");
    }

    @Test
    void testEncryptDecrypt_WithVeryLongText_ShouldWork() {
        // Given
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longText.append("This is a very long text that will be used to test encryption with large data sets. ");
        }
        String originalText = longText.toString();

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(originalText);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(originalText, decrypted);
            // Verify encryption increases length due to IV and padding
            assertTrue(encrypted.length() > originalText.length());
        });
    }

    @Test
    void testEncryptDecrypt_WithSpecialCharactersAndUnicode_ShouldWork() {
        // Given
        String originalText = "Special chars: @#$%^&*()_+{}|:<>?[];'\\\",./ and Unicode: áéíóú çñü 漢字 🚀";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(originalText);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(originalText, decrypted);
        });
    }

    @Test
    void testEncryptDecrypt_WithNumbersAndSymbols_ShouldWork() {
        // Given
        String originalText = "1234567890!@#$%^&*()-=_+[]{}|;:',.<>/?" + "∞≈π√∑∆∇∂∫±≤≥≠≡⊕⊗";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(originalText);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(originalText, decrypted);
        });
    }

    @Test
    void testEncryptDecrypt_WithSingleCharacter_ShouldWork() {
        // Given
        String originalText = "a";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(originalText);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(originalText, decrypted);
        });
    }

    @Test
    void testEncryptDecrypt_WithSpacesAndNewlines_ShouldWork() {
        // Given
        String originalText = "Line 1\nLine 2\tTabbed\r\nWindows newline   Multiple spaces";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(originalText);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(originalText, decrypted);
        });
    }

    @Test
    void testEncrypt_MultipleTimesWithSameText_ShouldProduceDifferentResults() {
        // Given
        String originalText = "Consistent text for encryption";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted1 = e2ee.encrypt(originalText);
            String encrypted2 = e2ee.encrypt(originalText);
            String encrypted3 = e2ee.encrypt(originalText);
            
            // With random IV, same input should produce DIFFERENT encrypted outputs
            assertNotEquals(encrypted1, encrypted2, "Encrypted outputs should differ due to random IV");
            assertNotEquals(encrypted2, encrypted3, "Encrypted outputs should differ due to random IV");
            assertNotEquals(encrypted1, encrypted3, "Encrypted outputs should differ due to random IV");
            
            // But all should decrypt to same original value
            String decrypted1 = e2ee.decrypt(encrypted1);
            String decrypted2 = e2ee.decrypt(encrypted2);
            String decrypted3 = e2ee.decrypt(encrypted3);
            
            assertEquals(originalText, decrypted1);
            assertEquals(originalText, decrypted2);
            assertEquals(originalText, decrypted3);
        });
    }

    @Test
    void testDecrypt_WithTamperedEncryptedText_ShouldFail() throws E2EEException {
        // Given
        String originalText = "Valid text to encrypt";
        String encrypted = e2ee.encrypt(originalText);

        // Test 1: Tamper by changing a character in the middle
        String tamperedMiddle = encrypted.substring(0, encrypted.length() / 2) +
                "!" +
                encrypted.substring(encrypted.length() / 2 + 1);
        assertThrows(E2EEException.class, () -> e2ee.decrypt(tamperedMiddle),
                "Should throw exception when middle character is tampered");

        // Test 2: Tamper by truncating data (too short)
        String truncated = encrypted.substring(0, 20);
        assertThrows(E2EEException.class, () -> e2ee.decrypt(truncated),
                "Should throw exception when data is truncated");

        // Test 3: Tamper by adding extra data
        String withExtra = encrypted + "AAAA";
        assertThrows(E2EEException.class, () -> e2ee.decrypt(withExtra),
                "Should throw exception when extra data is added");

        // Test 4: Completely invalid Base64
        String invalidBase64 = "This is not Base64!!!";
        assertThrows(E2EEException.class, () -> e2ee.decrypt(invalidBase64),
                "Should throw exception for invalid Base64");
    }
    
    @Test
    void testGenerateKey_ValidKeySizes_ShouldWork() throws E2EEException {
        // Test valid AES key sizes
        SecretKey key128 = e2ee.generateKey(128);
        SecretKey key192 = e2ee.generateKey(192);
        SecretKey key256 = e2ee.generateKey(256);
        
        assertNotNull(key128);
        assertNotNull(key192);
        assertNotNull(key256);
        assertEquals(16, key128.getEncoded().length); // 128 bits = 16 bytes
        assertEquals(24, key192.getEncoded().length); // 192 bits = 24 bytes
        assertEquals(32, key256.getEncoded().length); // 256 bits = 32 bytes
    }
    
    @Test
    void testGenerateKey_InvalidKeySize_ShouldThrowException() {
        // Invalid key sizes should throw exception
        assertThrows(E2EEException.class, () -> e2ee.generateKey(64));
        assertThrows(E2EEException.class, () -> e2ee.generateKey(512));
        assertThrows(E2EEException.class, () -> e2ee.generateKey(100));
    }
    
    @Test
    void testGenerateKeyAsString_ShouldReturnValidBase64() throws E2EEException {
        String keyString = e2ee.generateKeyAsString(128);
        
        assertNotNull(keyString);
        assertFalse(keyString.trim().isEmpty());
        
        // Should be valid Base64
        assertDoesNotThrow(() -> java.util.Base64.getDecoder().decode(keyString));
    }

    @Test
    void testEncryptDecrypt_WithJsonLikeString_ShouldWork() {
        // Given
        String jsonText = "{\"user\":\"admin\",\"permissions\":[\"read\",\"write\"],\"nested\":{\"key\":\"value\"}}";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(jsonText);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(jsonText, decrypted);
        });
    }

    @Test
    void testEncryptDecrypt_WithSQL_ShouldWork() {
        // Given
        String sqlText = "SELECT * FROM users WHERE id = 1 AND name = 'John O''Reilly' ORDER BY created_at DESC;";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(sqlText);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(sqlText, decrypted);
        });
    }

    @Test
    void testEncryptDecrypt_PerformanceTest_ShouldComplete() {
        // Given
        String originalText = "Performance test text that should encrypt and decrypt quickly";
        
        // When & Then
        assertDoesNotThrow(() -> {
            long startTime = System.currentTimeMillis();
            
            for (int i = 0; i < 100; i++) {
                String encrypted = e2ee.encrypt(originalText);
                String decrypted = e2ee.decrypt(encrypted);
                assertEquals(originalText, decrypted);
            }
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            // Should complete 100 cycles in reasonable time (less than 5 seconds)
            assertTrue(duration < 5000, "Encryption/decryption took too long: " + duration + "ms");
        });
    }

    @Test
    void testEncryptDecrypt_WithDifferentTextSizes_ShouldWork() {
        // When & Then
        assertDoesNotThrow(() -> {
            // Test with different size texts
            String small = "Hi";
            String medium = "Medium length text for testing encryption capabilities";
            StringBuilder large = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                large.append("Large text content repeated multiple times ");
            }
            String largeText = large.toString();

            // Encrypt and decrypt all sizes
            assertEquals(small, e2ee.decrypt(e2ee.encrypt(small)));
            assertEquals(medium, e2ee.decrypt(e2ee.encrypt(medium)));
            assertEquals(largeText, e2ee.decrypt(e2ee.encrypt(largeText)));
        });
    }

    @Test
    void testEncryption_ShouldBeReproducibleInDecryption() {
        // Given
        String[] testTexts = {
            "Simple text",
            "Text with numbers 12345",
            "Special chars !@#$%^&*()",
            "Unicode: 漢字 émojis 🎉",
            ""  // Empty string
        };

        // When & Then
        assertDoesNotThrow(() -> {
            for (String text : testTexts) {
                String encrypted = e2ee.encrypt(text);
                String decrypted = e2ee.decrypt(encrypted);
                assertEquals(text, decrypted, "Failed for text: " + text);
            }
        });
    }

    @Test
    void testEncryption_EdgeCase_WithOnlySpaces() {
        // Given
        String spacesOnly = "     ";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(spacesOnly);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(spacesOnly, decrypted);
        });
    }

    @Test
    void testEncryption_EdgeCase_WithRepeatedCharacters() {
        // Given
        String repeated = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(repeated);
            String decrypted = e2ee.decrypt(encrypted);
            assertEquals(repeated, decrypted);
        });
    }
    
    @Test
    void testRandomness_EncryptionShouldProduceDifferentIVs() {
        // Given
        String text = "Test IV randomness";
        
        // When & Then
        assertDoesNotThrow(() -> {
            // Encrypt same text multiple times and collect IVs
            java.util.Set<String> ivs = new java.util.HashSet<>();
            
            for (int i = 0; i < 10; i++) {
                String encrypted = e2ee.encrypt(text);
                // Extract first 24 characters (16 bytes IV in Base64)
                String ivPortion = encrypted.substring(0, Math.min(24, encrypted.length()));
                ivs.add(ivPortion);
            }
            
            // All IVs should be different (very high probability)
            assertEquals(10, ivs.size(), "All IVs should be unique due to randomness");
        });
    }
}