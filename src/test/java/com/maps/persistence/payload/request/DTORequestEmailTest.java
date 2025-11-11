package com.maps.persistence.payload.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para DTORequestEmail (record)
 * Testando a estrutura de dados de requisição de email
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class DTORequestEmailTest {

    @Test
    void testRecordCreation_WithAllValidFields_ShouldCreateCorrectly() {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test email body content";

        // Act
        DTORequestEmail dto = new DTORequestEmail(to, subject, text);

        // Assert
        assertNotNull(dto);
        assertEquals(to, dto.to());
        assertEquals(subject, dto.subject());
        assertEquals(text, dto.text());
    }

    @Test
    void testRecordAccessors_ShouldReturnCorrectValues() {
        // Arrange
        DTORequestEmail dto = new DTORequestEmail("user@domain.com", "Important Message", "This is the message body");

        // Act & Assert
        assertEquals("user@domain.com", dto.to());
        assertEquals("Important Message", dto.subject());
        assertEquals("This is the message body", dto.text());
    }

    @Test
    void testRecordEquality_SameValues_ShouldBeEqual() {
        // Arrange
        DTORequestEmail dto1 = new DTORequestEmail("test@example.com", "Subject", "Text");
        DTORequestEmail dto2 = new DTORequestEmail("test@example.com", "Subject", "Text");

        // Act & Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testRecordEquality_DifferentValues_ShouldNotBeEqual() {
        // Arrange
        DTORequestEmail dto1 = new DTORequestEmail("test1@example.com", "Subject", "Text");
        DTORequestEmail dto2 = new DTORequestEmail("test2@example.com", "Subject", "Text");

        // Act & Assert
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testRecordToString_ShouldContainFieldValues() {
        // Arrange
        DTORequestEmail dto = new DTORequestEmail("admin@test.com", "Alert", "System notification");

        // Act
        String toString = dto.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("admin@test.com"));
        assertTrue(toString.contains("Alert"));
        assertTrue(toString.contains("System notification"));
    }

    @Test
    void testComplexEmailAddresses_ShouldHandleCorrectly() {
        // Arrange
        String complexEmail = "user+tag@subdomain.example.co.uk";
        String subject = "Complex Email Test";
        String text = "Testing complex email scenarios";

        // Act
        DTORequestEmail dto = new DTORequestEmail(complexEmail, subject, text);

        // Assert
        assertEquals(complexEmail, dto.to());
        assertEquals(subject, dto.subject());
        assertEquals(text, dto.text());
    }

    @Test
    void testSpecialCharactersInContent_ShouldWork() {
        // Arrange
        String to = "test@example.com";
        String subject = "Subject with special chars: àáâãäåæçèéê!@#$%";
        String text = "Text with unicode: 你好世界 and symbols: ★☆♥♦♠♣";

        // Act
        DTORequestEmail dto = new DTORequestEmail(to, subject, text);

        // Assert
        assertEquals(to, dto.to());
        assertEquals(subject, dto.subject());
        assertEquals(text, dto.text());
    }

    @Test
    void testLongContent_ShouldHandleCorrectly() {
        // Arrange
        String to = "test@example.com";
        String subject = "Very long subject line that exceeds normal length to test handling of longer content";
        String text = "This is a very long email body that simulates real-world scenarios where emails can contain extensive content including multiple paragraphs, detailed information, and comprehensive explanations about various topics that users might need to communicate.";

        // Act
        DTORequestEmail dto = new DTORequestEmail(to, subject, text);

        // Assert
        assertEquals(to, dto.to());
        assertEquals(subject, dto.subject());
        assertEquals(text, dto.text());
    }

    @Test
    void testEmptyStrings_ShouldBeAllowed() {
        // Arrange
        String to = "";
        String subject = "";
        String text = "";

        // Act
        DTORequestEmail dto = new DTORequestEmail(to, subject, text);

        // Assert
        assertEquals(to, dto.to());
        assertEquals(subject, dto.subject());
        assertEquals(text, dto.text());
    }

    @Test
    void testNullValues_ShouldBeAllowed() {
        // Act
        DTORequestEmail dto = new DTORequestEmail(null, null, null);

        // Assert
        assertNotNull(dto);
        assertNull(dto.to());
        assertNull(dto.subject());
        assertNull(dto.text());
    }

    @Test
    void testRecordImmutability_ShouldNotBeModifiable() {
        // Arrange
        DTORequestEmail dto = new DTORequestEmail("test@example.com", "Subject", "Text");

        // Act & Assert - Records são imutáveis
        // Não há métodos setter para modificar os valores
        assertEquals("test@example.com", dto.to());
        assertEquals("Subject", dto.subject());
        assertEquals("Text", dto.text());
    }
}