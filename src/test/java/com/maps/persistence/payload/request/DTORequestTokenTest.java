package com.maps.persistence.payload.request;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for DTORequestToken record including field validation,
 * custom constructor testing, and proper record behavior verification.
 * 
 * @author Marcelo Ribeiro Gadelha
 */
class DTORequestTokenTest {

    @Test
    void testRecordCreation_WithAllFields_ShouldCreateCorrectly() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String tokenType = "Bearer";
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

        // When
        DTORequestToken request = new DTORequestToken(id, tokenType, accessToken, refreshToken);

        // Then
        assertNotNull(request);
        assertEquals(id, request.id());
        assertEquals(tokenType, request.tokenType());
        assertEquals(accessToken, request.accessToken());
        assertEquals(refreshToken, request.refreshToken());
    }

    @Test
    void testRecordCreation_WithCustomConstructor_ShouldDefaultTokenType() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String accessToken = "test.token.value";

        // When
        DTORequestToken request = new DTORequestToken(id, accessToken, refreshToken);

        // Then
        assertNotNull(request);
        assertEquals(id, request.id());
        assertEquals("Bearer ", request.tokenType());
        assertEquals(accessToken, request.accessToken());
        assertEquals(refreshToken, request.refreshToken());
    }

    @Test
    void testRecordAccessors_ShouldReturnCorrectValues() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String tokenType = "Bearer";
        String accessToken = "access.token.example";

        // When
        DTORequestToken request = new DTORequestToken(id, tokenType, accessToken, refreshToken);

        // Then
        assertEquals(id, request.id());
        assertEquals(tokenType, request.tokenType());
        assertEquals(accessToken, request.accessToken());
        assertEquals(refreshToken, request.refreshToken());
    }

    @Test
    void testRecordEquality_SameValues_ShouldBeEqual() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String tokenType = "Bearer";
        String accessToken = "same.token.value";

        // When
        DTORequestToken request1 = new DTORequestToken(id, tokenType, accessToken, refreshToken);
        DTORequestToken request2 = new DTORequestToken(id, tokenType, accessToken, refreshToken);

        // Then
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testRecordEquality_DifferentValues_ShouldNotBeEqual() {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String tokenType = "Bearer";
        String accessToken = "token.value";

        // When
        DTORequestToken request1 = new DTORequestToken(id1, tokenType, accessToken, refreshToken);
        DTORequestToken request2 = new DTORequestToken(id2, tokenType, accessToken, refreshToken);

        // Then
        assertNotEquals(request1, request2);
    }

    @Test
    void testRecordToString_ShouldContainFieldValues() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String tokenType = "Bearer";
        String accessToken = "token123";

        // When
        DTORequestToken request = new DTORequestToken(id, tokenType, accessToken, refreshToken);
        String toString = request.toString();

        // Then
        assertTrue(toString.contains("DTORequestToken"));
        assertTrue(toString.contains(id.toString()));
        assertTrue(toString.contains(tokenType));
        assertTrue(toString.contains(accessToken));
        assertTrue(toString.contains(refreshToken.toString()));
    }

    @Test
    void testRealWorldJWT_ShouldHandleCorrectly() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String realJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        // When
        DTORequestToken request = new DTORequestToken(id, "Bearer", realJWT, refreshToken);

        // Then
        assertNotNull(request);
        assertEquals(realJWT, request.accessToken());
        assertTrue(request.accessToken().contains("."));
    }

    @Test
    void testDifferentTokenTypes_ShouldWork() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();
        String accessToken = "token123";

        // When & Then
        DTORequestToken bearerToken = new DTORequestToken(id, "Bearer", accessToken, refreshToken);
        DTORequestToken basicToken = new DTORequestToken(id, "Basic", accessToken, refreshToken);
        DTORequestToken customToken = new DTORequestToken(id, "Custom", accessToken, refreshToken);

        assertEquals("Bearer", bearerToken.tokenType());
        assertEquals("Basic", basicToken.tokenType());
        assertEquals("Custom", customToken.tokenType());
    }

    @Test
    void testEmptyStrings_ShouldBeAllowed() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();

        // When
        DTORequestToken request = new DTORequestToken(id, "", "", refreshToken);

        // Then
        assertNotNull(request);
        assertEquals("", request.tokenType());
        assertEquals("", request.accessToken());
    }

    @Test
    void testRecordImplementsDTORequestIdentifiable() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();

        // When
        DTORequestToken request = new DTORequestToken(id, "Bearer", "token", refreshToken);

        // Then
        assertTrue(request instanceof DTORequestIdentifiable);
        assertEquals(id, request.id());
    }

    @Test
    void testRecordImmutability_ShouldNotBeModifiable() {
        // Given
        UUID id = UUID.randomUUID();
        UUID refreshToken = UUID.randomUUID();

        // When
        DTORequestToken request = new DTORequestToken(id, "Bearer", "token", refreshToken);

        // Then - Records are inherently immutable
        assertNotNull(request.id());
        assertNotNull(request.tokenType());
        assertNotNull(request.accessToken());
        assertNotNull(request.refreshToken());
        // No setter methods should exist (records provide only getters)
    }
}