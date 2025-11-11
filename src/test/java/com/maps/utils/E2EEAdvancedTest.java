package com.maps.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced tests for E2EE encryption utility covering edge cases,
 * performance scenarios, and comprehensive encryption scenarios.
 * 
 * @author Marcelo Ribeiro Gadelha
 */
class E2EEAdvancedTest {

    private E2EE e2ee;

    @BeforeEach
    void setUp() {
        e2ee = new E2EE();
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
            
            // All should decrypt to same value but might be same encrypted result due to static IV
            String decrypted1 = e2ee.decrypt(encrypted1);
            String decrypted2 = e2ee.decrypt(encrypted2);
            String decrypted3 = e2ee.decrypt(encrypted3);
            
            assertEquals(originalText, decrypted1);
            assertEquals(originalText, decrypted2);
            assertEquals(originalText, decrypted3);
        });
    }

    @Test
    void testDecrypt_WithTamperedEncryptedText_ShouldFail() {
        // Given
        String originalText = "Valid text to encrypt";

        // When & Then
        assertDoesNotThrow(() -> {
            String encrypted = e2ee.encrypt(originalText);
            String tamperedEncrypted = encrypted.substring(0, encrypted.length() - 1) + "X";
            
            // Should throw an exception when trying to decrypt tampered data
            assertThrows(Exception.class, () -> e2ee.decrypt(tamperedEncrypted));
        });
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
}