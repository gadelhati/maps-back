package com.maps.persistence.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Token
 * Testando getters, setters e comportamento da entidade
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TokenUnitTest {

    @Test
    void testTokenCreation_NoArgsConstructor_ShouldCreateEmptyToken() {
        // Act
        Token token = new Token();

        // Assert
        assertNotNull(token);
        assertNull(token.getRefreshToken());
        assertFalse(token.isActive());
    }

    @Test
    void testTokenCreation_AllArgsConstructor_ShouldCreateTokenWithValues() {
        // Arrange
        UUID refreshToken = UUID.randomUUID();
        boolean active = true;

        // Act
        Token token = new Token(refreshToken, active);

        // Assert
        assertNotNull(token);
        assertEquals(refreshToken, token.getRefreshToken());
        assertTrue(token.isActive());
    }

    @Test
    void testSettersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        Token token = new Token();
        UUID refreshToken = UUID.randomUUID();
        boolean active = true;

        // Act
        token.setRefreshToken(refreshToken);
        token.setActive(active);

        // Assert
        assertEquals(refreshToken, token.getRefreshToken());
        assertTrue(token.isActive());
    }

    @Test
    void testSetRefreshToken_NullValue_ShouldAcceptNull() {
        // Arrange
        Token token = new Token();

        // Act
        token.setRefreshToken(null);

        // Assert
        assertNull(token.getRefreshToken());
    }

    @Test
    void testSetActive_FalseValue_ShouldSetToFalse() {
        // Arrange
        Token token = new Token();
        token.setActive(true); // Primeiro definir como true

        // Act
        token.setActive(false);

        // Assert
        assertFalse(token.isActive());
    }

    @Test
    void testTokenEquality_SameRefreshToken_ShouldBeEqual() {
        // Arrange
        UUID refreshToken = UUID.randomUUID();
        Token token1 = new Token(refreshToken, true);
        Token token2 = new Token(refreshToken, false);

        // Act & Assert
        assertEquals(token1.getRefreshToken(), token2.getRefreshToken());
    }

    @Test
    void testTokenDifference_DifferentRefreshTokens_ShouldBeDifferent() {
        // Arrange
        UUID refreshToken1 = UUID.randomUUID();
        UUID refreshToken2 = UUID.randomUUID();
        Token token1 = new Token(refreshToken1, true);
        Token token2 = new Token(refreshToken2, true);

        // Act & Assert
        assertNotEquals(token1.getRefreshToken(), token2.getRefreshToken());
    }

    @Test
    void testActiveFlag_ToggleValue_ShouldChangeCorrectly() {
        // Arrange
        Token token = new Token();
        
        // Act & Assert
        assertFalse(token.isActive()); // Valor padrão
        
        token.setActive(true);
        assertTrue(token.isActive());
        
        token.setActive(false);
        assertFalse(token.isActive());
    }

    @Test
    void testRefreshTokenGeneration_UniqueValues_ShouldBeDifferent() {
        // Arrange
        Token token1 = new Token();
        Token token2 = new Token();

        // Act
        token1.setRefreshToken(UUID.randomUUID());
        token2.setRefreshToken(UUID.randomUUID());

        // Assert
        assertNotNull(token1.getRefreshToken());
        assertNotNull(token2.getRefreshToken());
        assertNotEquals(token1.getRefreshToken(), token2.getRefreshToken());
    }

    @Test
    void testTokenState_MultipleChanges_ShouldMaintainCorrectState() {
        // Arrange
        Token token = new Token();
        UUID refreshToken1 = UUID.randomUUID();
        UUID refreshToken2 = UUID.randomUUID();

        // Act & Assert - Primeira mudança
        token.setRefreshToken(refreshToken1);
        token.setActive(true);
        assertEquals(refreshToken1, token.getRefreshToken());
        assertTrue(token.isActive());

        // Act & Assert - Segunda mudança
        token.setRefreshToken(refreshToken2);
        token.setActive(false);
        assertEquals(refreshToken2, token.getRefreshToken());
        assertFalse(token.isActive());
    }
}