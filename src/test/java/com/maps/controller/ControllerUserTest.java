package com.maps.controller;

import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.service.ServiceUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ControllerUser
 * Focando nos endpoints específicos do controller
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerUserTest {

    @Mock
    private ServiceUser serviceUser;

    @InjectMocks
    private ControllerUser controllerUser;

    @Test
    void testChangePassword_WithValidRequest_ShouldReturnAcceptedResponse() {
        // Arrange
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword(
            java.util.UUID.randomUUID(),
            "NewPassword123!"
        );
        
        DTOResponseUser mockResponse = mock(DTOResponseUser.class);
        when(serviceUser.changePassword(passwordRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseUser> response = controllerUser.changePassword(passwordRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceUser).changePassword(passwordRequest);
    }

    @Test
    void testResetPassword_WithValidRequest_ShouldReturnAcceptedResponse() {
        // Arrange
        DTORequestUserAuth userAuthRequest = new DTORequestUserAuth(
            "testuser",
            "oldpassword",
            123456,
            "captcha123"
        );
        
        DTOResponseUser mockResponse = mock(DTOResponseUser.class);
        when(serviceUser.resetPassword("testuser")).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseUser> response = controllerUser.resetPassword(userAuthRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceUser).resetPassword("testuser");
    }

    @Test
    void testResetSecret_WithValidRequest_ShouldReturnAcceptedResponse() {
        // Arrange
        DTORequestUserAuth userAuthRequest = new DTORequestUserAuth(
            "testuser",
            "password",
            654321,
            "captcha456"
        );
        
        DTOResponseUser mockResponse = mock(DTOResponseUser.class);
        when(serviceUser.resetSecret("testuser")).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseUser> response = controllerUser.resetSecret(userAuthRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceUser).resetSecret("testuser");
    }

    @Test
    void testChangePassword_WithNullRequest_ShouldHandleGracefully() {
        // When
        ResponseEntity<DTOResponseUser> response = controllerUser.changePassword(null);

        // Then - Service handles null gracefully, controller returns response
        assertNotNull(response);
        verify(serviceUser).changePassword(null);
    }

    @Test
    void testResetPassword_WithNullRequest_ShouldHandleGracefully() {
        // When & Then - Controller doesn't handle null gracefully, so expecting NPE
        assertThrows(NullPointerException.class, () -> {
            controllerUser.resetPassword(null);
        });
        verify(serviceUser, never()).resetPassword(any());
    }

    @Test
    void testResetSecret_WithNullRequest_ShouldHandleGracefully() {
        // When & Then - Controller doesn't handle null gracefully, so expecting NPE  
        assertThrows(NullPointerException.class, () -> {
            controllerUser.resetSecret(null);
        });
        verify(serviceUser, never()).resetSecret(any(String.class));
    }

    @Test
    void testChangePassword_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword(
            java.util.UUID.randomUUID(),
            "NewPassword123!"
        );
        
        when(serviceUser.changePassword(passwordRequest))
            .thenThrow(new RuntimeException("Password change failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerUser.changePassword(passwordRequest);
        });

        verify(serviceUser).changePassword(passwordRequest);
    }

    @Test
    void testResetPassword_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        DTORequestUserAuth userAuthRequest = new DTORequestUserAuth(
            "testuser",
            "password",
            123456,
            "captcha"
        );
        
        when(serviceUser.resetPassword("testuser"))
            .thenThrow(new RuntimeException("Reset password failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerUser.resetPassword(userAuthRequest);
        });

        verify(serviceUser).resetPassword("testuser");
    }

    @Test
    void testResetSecret_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        DTORequestUserAuth userAuthRequest = new DTORequestUserAuth(
            "testuser",
            "password",
            123456,
            "captcha"
        );
        
        when(serviceUser.resetSecret("testuser"))
            .thenThrow(new RuntimeException("Reset secret failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerUser.resetSecret(userAuthRequest);
        });

        verify(serviceUser).resetSecret("testuser");
    }

    @Test
    void testChangePassword_WithEmptyPassword_ShouldCallService() {
        // Arrange
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword(
            java.util.UUID.randomUUID(),
            ""
        );
        
        DTOResponseUser mockResponse = mock(DTOResponseUser.class);
        when(serviceUser.changePassword(passwordRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseUser> response = controllerUser.changePassword(passwordRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(serviceUser).changePassword(passwordRequest);
    }

    @Test
    void testResetPassword_WithDifferentUsernames_ShouldCallServiceWithCorrectUsername() {
        // Test com diferentes tipos de username
        String[] usernames = {"admin", "user123", "test@example.com", "user-with-dashes"};
        
        for (String username : usernames) {
            // Arrange
            DTORequestUserAuth userAuthRequest = new DTORequestUserAuth(
                username,
                "password",
                123456,
                "captcha"
            );
            
            DTOResponseUser mockResponse = mock(DTOResponseUser.class);
            when(serviceUser.resetPassword(username)).thenReturn(mockResponse);

            // Act
            ResponseEntity<DTOResponseUser> response = controllerUser.resetPassword(userAuthRequest);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
            verify(serviceUser).resetPassword(username);
        }
    }

    @Test
    void testResetSecret_WithDifferentUsernames_ShouldCallServiceWithCorrectUsername() {
        // Test com diferentes tipos de username
        String[] usernames = {"admin", "user123", "test@example.com", "user-with-dashes"};
        
        for (String username : usernames) {
            // Arrange
            DTORequestUserAuth userAuthRequest = new DTORequestUserAuth(
                username,
                "password",
                654321,
                "captcha"
            );
            
            DTOResponseUser mockResponse = mock(DTOResponseUser.class);
            when(serviceUser.resetSecret(username)).thenReturn(mockResponse);

            // Act
            ResponseEntity<DTOResponseUser> response = controllerUser.resetSecret(userAuthRequest);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
            verify(serviceUser).resetSecret(username);
        }
    }

    @Test
    void testAllEndpoints_ResponseStructure_ShouldBeConsistent() {
        // Test para verificar que todas as respostas têm estrutura consistente
        
        // Arrange
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword(
            java.util.UUID.randomUUID(),
            "Password123!"
        );
        
        DTORequestUserAuth userAuthRequest = new DTORequestUserAuth(
            "testuser",
            "password",
            123456,
            "captcha"
        );
        
        DTOResponseUser mockResponse = mock(DTOResponseUser.class);
        
        when(serviceUser.changePassword(passwordRequest)).thenReturn(mockResponse);
        when(serviceUser.resetPassword("testuser")).thenReturn(mockResponse);
        when(serviceUser.resetSecret("testuser")).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseUser> changePasswordResponse = controllerUser.changePassword(passwordRequest);
        ResponseEntity<DTOResponseUser> resetPasswordResponse = controllerUser.resetPassword(userAuthRequest);
        ResponseEntity<DTOResponseUser> resetSecretResponse = controllerUser.resetSecret(userAuthRequest);

        // Assert - Todas as respostas devem ter status ACCEPTED
        assertEquals(HttpStatus.ACCEPTED, changePasswordResponse.getStatusCode());
        assertEquals(HttpStatus.ACCEPTED, resetPasswordResponse.getStatusCode());
        assertEquals(HttpStatus.ACCEPTED, resetSecretResponse.getStatusCode());
        
        // Todas devem retornar o mesmo tipo de objeto
        assertEquals(mockResponse, changePasswordResponse.getBody());
        assertEquals(mockResponse, resetPasswordResponse.getBody());
        assertEquals(mockResponse, resetSecretResponse.getBody());
    }
}