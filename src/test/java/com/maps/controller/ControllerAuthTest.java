package com.maps.controller;

import com.maps.persistence.payload.request.DTORequestToken;
import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.response.DTOResponseToken;
import com.maps.service.ServiceAuth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ControllerAuth
 * Focando nos endpoints de autenticação
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerAuthTest {

    @Mock
    private ServiceAuth serviceAuth;

    @InjectMocks
    private ControllerAuth controllerAuth;

    @Test
    void testLogin_WithValidCredentials_ShouldReturnOkResponse() {
        // Arrange
        DTORequestUserAuth loginRequest = new DTORequestUserAuth(
            "testuser",
            "password123",
            123456,
            "captcha123"
        );
        
        DTOResponseToken mockResponse = mock(DTOResponseToken.class);
        when(serviceAuth.login(loginRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseToken> response = controllerAuth.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceAuth).login(loginRequest);
    }

    @Test
    void testLogin_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        DTORequestUserAuth loginRequest = new DTORequestUserAuth(
            "testuser",
            "wrongpassword",
            123456,
            "captcha"
        );
        
        when(serviceAuth.login(loginRequest))
            .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerAuth.login(loginRequest);
        });

        verify(serviceAuth).login(loginRequest);
    }

    @Test
    void testRefresh_WithValidToken_ShouldReturnAcceptedResponse() {
        // Arrange
        UUID refreshTokenId = UUID.randomUUID();
        DTORequestToken tokenRequest = new DTORequestToken(refreshTokenId, "access-token", refreshTokenId);
        
        DTOResponseToken mockResponse = mock(DTOResponseToken.class);
        when(serviceAuth.refresh(tokenRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseToken> response = controllerAuth.refresh(tokenRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceAuth).refresh(tokenRequest);
    }

    @Test
    void testRefresh_WithInvalidToken_ShouldPropagateException() {
        // Arrange
        UUID invalidTokenId = UUID.randomUUID();
        DTORequestToken tokenRequest = new DTORequestToken(invalidTokenId, "access-token", invalidTokenId);
        
        when(serviceAuth.refresh(tokenRequest))
            .thenThrow(new RuntimeException("Invalid refresh token"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerAuth.refresh(tokenRequest);
        });

        verify(serviceAuth).refresh(tokenRequest);
    }

    @Test
    void testLogout_WithValidRefreshToken_ShouldReturnAcceptedResponse() {
        // Arrange
        UUID refreshToken = UUID.randomUUID();
        
        DTOResponseToken mockResponse = mock(DTOResponseToken.class);
        when(serviceAuth.logout(refreshToken)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseToken> response = controllerAuth.logout(refreshToken);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceAuth).logout(refreshToken);
    }

    @Test
    void testLogout_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        UUID refreshToken = UUID.randomUUID();
        
        when(serviceAuth.logout(refreshToken))
            .thenThrow(new RuntimeException("Token not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerAuth.logout(refreshToken);
        });

        verify(serviceAuth).logout(refreshToken);
    }

    @Test
    void testLogin_WithNullRequest_ShouldCallService() {
        // Arrange
        DTOResponseToken mockResponse = mock(DTOResponseToken.class);
        when(serviceAuth.login(null)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseToken> response = controllerAuth.login(null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceAuth).login(null);
    }

    @Test
    void testRefresh_WithNullRequest_ShouldCallService() {
        // Arrange
        DTOResponseToken mockResponse = mock(DTOResponseToken.class);
        when(serviceAuth.refresh(null)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseToken> response = controllerAuth.refresh(null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(serviceAuth).refresh(null);
    }

    @Test
    void testLogin_WithDifferentUsernames_ShouldCallServiceWithCorrectData() {
        // Test com diferentes tipos de username
        String[] usernames = {"admin", "user123", "test@example.com", "user-with-dashes"};
        
        for (String username : usernames) {
            // Arrange
            DTORequestUserAuth loginRequest = new DTORequestUserAuth(
                username,
                "password",
                123456,
                "captcha"
            );
            
            DTOResponseToken mockResponse = mock(DTOResponseToken.class);
            when(serviceAuth.login(loginRequest)).thenReturn(mockResponse);

            // Act
            ResponseEntity<DTOResponseToken> response = controllerAuth.login(loginRequest);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(serviceAuth).login(loginRequest);
        }
    }

    @Test
    void testAllEndpoints_ResponseStructure_ShouldBeConsistent() {
        // Test para verificar que todas as respostas têm estrutura consistente
        
        // Arrange
        DTORequestUserAuth loginRequest = new DTORequestUserAuth(
            "testuser",
            "password",
            123456,
            "captcha"
        );
        
        UUID refreshTokenId = UUID.randomUUID();
        DTORequestToken tokenRequest = new DTORequestToken(refreshTokenId, "access-token", refreshTokenId);
        
        DTOResponseToken mockResponse = mock(DTOResponseToken.class);
        
        when(serviceAuth.login(loginRequest)).thenReturn(mockResponse);
        when(serviceAuth.refresh(tokenRequest)).thenReturn(mockResponse);
        when(serviceAuth.logout(refreshTokenId)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseToken> loginResponse = controllerAuth.login(loginRequest);
        ResponseEntity<DTOResponseToken> refreshResponse = controllerAuth.refresh(tokenRequest);
        ResponseEntity<DTOResponseToken> logoutResponse = controllerAuth.logout(refreshTokenId);

        // Assert - Login deve ter status OK
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        
        // Refresh e logout devem ter status ACCEPTED
        assertEquals(HttpStatus.ACCEPTED, refreshResponse.getStatusCode());
        assertEquals(HttpStatus.ACCEPTED, logoutResponse.getStatusCode());
        
        // Todas devem retornar o mesmo tipo de objeto
        assertEquals(mockResponse, loginResponse.getBody());
        assertEquals(mockResponse, refreshResponse.getBody());
        assertEquals(mockResponse, logoutResponse.getBody());
    }
}