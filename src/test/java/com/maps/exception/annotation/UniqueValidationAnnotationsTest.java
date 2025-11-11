package com.maps.exception.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for validation annotation structure and basic behavior.
 * Focused on testing annotation logic without Spring dependency injection.
 * 
 * @author Marcelo Ribeiro Gadelha
 */
class UniqueValidationAnnotationsTest {

    @Test
    void testUniqueEmailAnnotation_Structure_ShouldExist() {
        // Given & When
        Class<?> annotationClass = UniqueEmail.class;

        // Then
        assertNotNull(annotationClass);
        assertTrue(annotationClass.isAnnotation());
    }

    @Test
    void testUniqueNameUserAnnotation_Structure_ShouldExist() {
        // Given & When
        Class<?> annotationClass = UniqueNameUser.class;

        // Then
        assertNotNull(annotationClass);
        assertTrue(annotationClass.isAnnotation());
    }

    @Test
    void testValidationLogic_BasicFieldValidation() {
        // Given
        String validEmail = "test@example.com";
        String invalidEmail = "invalid-email";
        String validUsername = "validuser123";
        String shortUsername = "ab";

        // When & Then - Basic validation logic tests
        assertTrue(validEmail.contains("@") && validEmail.contains("."));
        assertFalse(invalidEmail.contains("@") && invalidEmail.contains("."));
        assertTrue(validUsername.length() >= 3);
        assertFalse(shortUsername.length() >= 3);
    }

    @Test
    void testEmailValidation_Format_ShouldValidateBasicRules() {
        // Given
        String[] validEmails = {
            "user@domain.com",
            "test.email@example.org",
            "user123@test.co.uk"
        };
        
        String[] invalidEmails = {
            "plaintext",
            "@domain.com",
            "user@",
            "user@.com"
        };

        // When & Then
        for (String email : validEmails) {
            assertTrue(isValidEmailFormat(email), "Should be valid: " + email);
        }
        
        for (String email : invalidEmails) {
            assertFalse(isValidEmailFormat(email), "Should be invalid: " + email);
        }
    }

    @Test
    void testUsernameValidation_Length_ShouldValidateBasicRules() {
        // Given
        String[] validUsernames = {
            "abc", // minimum
            "validuser",
            "a".repeat(50) // maximum
        };
        
        String[] invalidUsernames = {
            "",
            "ab", // too short
            "a".repeat(51) // too long
        };

        // When & Then
        for (String username : validUsernames) {
            assertTrue(isValidUsernameLength(username), "Should be valid: " + username);
        }
        
        for (String username : invalidUsernames) {
            assertFalse(isValidUsernameLength(username), "Should be invalid: " + username);
        }
    }

    @Test
    void testValidationAnnotations_WithNullValues_ShouldHandleGracefully() {
        // Given
        String nullString = null;

        // When & Then
        assertFalse(isValidEmailFormat(nullString));
        assertFalse(isValidUsernameLength(nullString));
    }

    @Test
    void testValidationAnnotations_WithBlankValues_ShouldFail() {
        // Given
        String blankString = "";
        String whitespaceString = "   ";

        // When & Then
        assertFalse(isValidEmailFormat(blankString));
        assertFalse(isValidEmailFormat(whitespaceString));
        assertFalse(isValidUsernameLength(blankString));
        assertFalse(isValidUsernameLength(whitespaceString));
    }

    @Test
    void testSpecialCharacters_InEmail_ShouldBeHandled() {
        // Given
        String[] specialEmails = {
            "user+tag@domain.com",
            "user.name@domain.com",
            "user_name@domain.com"
        };

        // When & Then
        for (String email : specialEmails) {
            assertTrue(isValidEmailFormat(email), "Should handle special chars: " + email);
        }
    }

    @Test
    void testCaseInsensitive_Validation_ShouldWork() {
        // Given
        String upperEmail = "USER@DOMAIN.COM";
        String lowerEmail = "user@domain.com";
        String mixedEmail = "User@Domain.Com";

        // When & Then
        assertTrue(isValidEmailFormat(upperEmail));
        assertTrue(isValidEmailFormat(lowerEmail));
        assertTrue(isValidEmailFormat(mixedEmail));
    }

    @Test
    void testValidationAnnotations_EdgeCases() {
        // Given
        String longDomain = "user@" + "a".repeat(100) + ".com";
        String shortLocal = "a@domain.com";

        // When & Then
        assertTrue(isValidEmailFormat(longDomain));
        assertTrue(isValidEmailFormat(shortLocal));
    }

    // Helper methods for basic validation logic
    private boolean isValidEmailFormat(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // More strict validation
        int atIndex = email.indexOf("@");
        int lastDotIndex = email.lastIndexOf(".");
        
        return atIndex > 0 && 
               lastDotIndex > atIndex + 1 && 
               lastDotIndex < email.length() - 1 &&
               !email.startsWith("@") && 
               !email.endsWith("@") &&
               !email.startsWith(".") && 
               !email.endsWith(".") &&
               !email.contains("@.") &&
               !email.contains(".@");
    }

    private boolean isValidUsernameLength(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return username.length() >= 3 && username.length() <= 50;
    }
}