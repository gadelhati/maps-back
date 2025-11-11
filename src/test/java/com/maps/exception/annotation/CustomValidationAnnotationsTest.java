package com.maps.exception.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for custom validation logic
 * Tests validation patterns and rules without full validation framework
 */
@Slf4j
class CustomValidationAnnotationsTest {

    @Test
    @DisplayName("Should validate digit presence correctly")
    void shouldValidateDigitPresenceCorrectly() {
        log.info("Testing digit presence validation logic");

        // Valid cases - contain at least one digit
        assertTrue(containsDigit("password123"));
        assertTrue(containsDigit("abc1def"));
        assertTrue(containsDigit("1"));
        assertTrue(containsDigit("Test123!@#"));

        // Invalid cases - no digits
        assertFalse(containsDigit("password"));
        assertFalse(containsDigit("abcdef"));
        assertFalse(containsDigit("!@#$%"));
        assertFalse(containsDigit(""));

        // Null should be handled gracefully
        assertFalse(containsDigit(null));

        log.info("Digit presence validation test completed successfully");
    }

    @Test
    @DisplayName("Should validate string length correctly")
    void shouldValidateStringLengthCorrectly() {
        log.info("Testing string length validation logic");

        // Test length validation with min=3, max=10
        assertTrue(isValidLength("abc", 3, 10));
        assertTrue(isValidLength("abcdefghij", 3, 10));
        assertTrue(isValidLength("middle", 3, 10));

        // Invalid cases - too short
        assertFalse(isValidLength("ab", 3, 10));

        // Invalid cases - too long
        assertFalse(isValidLength("abcdefghijk", 3, 10));

        // Null handling
        assertFalse(isValidLength(null, 3, 10));

        log.info("String length validation test completed successfully");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "user@domain.co.uk", "valid.email@test.org"})
    @DisplayName("Should validate valid email formats")
    void shouldValidateValidEmailFormats(String validEmail) {
        log.info("Testing valid email format: {}", validEmail);

        assertTrue(isValidEmailFormat(validEmail));

        log.info("Valid email format test passed for: {}", validEmail);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "@domain.com", "user@", "user@domain", ""})
    @DisplayName("Should reject invalid email formats")
    void shouldRejectInvalidEmailFormats(String invalidEmail) {
        log.info("Testing invalid email format: {}", invalidEmail);

        assertFalse(isValidEmailFormat(invalidEmail));

        log.info("Invalid email format test passed for: {}", invalidEmail);
    }

    @Test
    @DisplayName("Should validate exception values correctly")
    void shouldValidateExceptionValuesCorrectly() {
        log.info("Testing exception values validation logic");

        String[] validValues = {"ADMIN", "USER", "GUEST"};

        // Valid cases
        assertTrue(isValidExceptionValue("ADMIN", validValues));
        assertTrue(isValidExceptionValue("USER", validValues));
        assertTrue(isValidExceptionValue("GUEST", validValues));

        // Invalid cases
        assertFalse(isValidExceptionValue("INVALID", validValues));
        assertFalse(isValidExceptionValue("admin", validValues)); // case sensitive
        assertFalse(isValidExceptionValue(null, validValues));

        log.info("Exception values validation test completed successfully");
    }

    @Test
    @DisplayName("Should handle null values appropriately")
    void shouldHandleNullValuesAppropriately() {
        log.info("Testing null value handling in validation logic");

        // Most validators should handle null gracefully
        assertFalse(containsDigit(null));
        assertFalse(isValidLength(null, 1, 10));
        assertFalse(isValidEmailFormat(null));
        assertFalse(isValidExceptionValue(null, new String[]{"VALID"}));

        log.info("Null value handling test completed successfully");
    }

    @Test
    @DisplayName("Should handle edge cases in validation")
    void shouldHandleEdgeCasesInValidation() {
        log.info("Testing edge cases in validation");

        // Edge cases for digit validation
        assertTrue(containsDigit("0")); // Single digit
        assertTrue(containsDigit("!@#1$%^")); // Digit among special chars
        assertFalse(containsDigit("  ")); // Whitespace only
        assertFalse(containsDigit("")); // Empty string

        // Edge cases for length validation
        assertTrue(isValidLength("a", 1, 1)); // Exact boundaries
        assertFalse(isValidLength("", 1, 5)); // Empty string

        log.info("Edge cases validation test completed successfully");
    }

    @Test
    @DisplayName("Should validate password complexity requirements")
    void shouldValidatePasswordComplexityRequirements() {
        log.info("Testing password complexity validation combination");

        // Valid password - has digit and proper length
        assertTrue(isComplexPassword("MyPass123"));

        // Invalid - no digit
        assertFalse(isComplexPassword("MyPassword"));

        // Invalid - too short
        assertFalse(isComplexPassword("Pass1"));

        // Invalid - both issues
        assertFalse(isComplexPassword("Pass"));

        log.info("Password complexity validation test completed successfully");
    }

    // Helper methods that implement the validation logic
    
    private boolean containsDigit(String value) {
        if (value == null) return false;
        return value.matches(".*\\d.*");
    }

    private boolean isValidLength(String value, int min, int max) {
        if (value == null) return false;
        return value.length() >= min && value.length() <= max;
    }

    private boolean isValidEmailFormat(String email) {
        if (email == null || email.isEmpty()) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidExceptionValue(String value, String[] validValues) {
        if (value == null) return false;
        for (String validValue : validValues) {
            if (validValue.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private boolean isComplexPassword(String password) {
        return containsDigit(password) && isValidLength(password, 8, 50);
    }
}