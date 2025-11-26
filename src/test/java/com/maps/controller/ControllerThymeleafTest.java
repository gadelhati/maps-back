package com.maps.controller;

import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.response.DTOResponseToken;
import com.maps.service.ServiceAuth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ControllerThymeleaf
 * Focando nos endpoints que retornam views Thymeleaf
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerThymeleafTest {

    @Mock
    private ServiceAuth serviceAuth;

    @Mock
    private Model model;

    @InjectMocks
    private ControllerThymeleaf controllerThymeleaf;

    @Test
    void testRegister_ShouldReturnRegisterView() {
        // Act
        ModelAndView result = controllerThymeleaf.register();

        // Assert
        assertNotNull(result);
        assertEquals("register", result.getViewName());
    }

    @Test
    void testSignUp_WithValidData_ShouldReturnConfirmView() {
        // Arrange
        String username = "testuser";
        String email = "test@example.com";
        
        doNothing().when(serviceAuth).register(username, email);

        // Act
        ModelAndView result = controllerThymeleaf.signUp(username, email);

        // Assert
        assertNotNull(result);
        assertEquals("confirm", result.getViewName());
        verify(serviceAuth).register(username, email);
    }

    @Test
    void testSignUp_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        String username = "testuser";
        String email = "test@example.com";
        
        doThrow(new RuntimeException("Registration failed")).when(serviceAuth).register(username, email);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerThymeleaf.signUp(username, email);
        });

        verify(serviceAuth).register(username, email);
    }

    @Test
    void testLogin_ShouldReturnLoginView() {
        // Act
        ModelAndView result = controllerThymeleaf.login();

        // Assert
        assertNotNull(result);
        assertEquals("login", result.getViewName());
    }

    @Test
    void testSignIn_WithValidCredentials_ShouldReturnUploadViewWithToken() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String totpKey = "123456";
        
        DTOResponseToken mockToken = mock(DTOResponseToken.class);
        DTORequestUserAuth expectedAuthRequest = new DTORequestUserAuth(username, password, Integer.parseInt(totpKey), "captchaToken");
        
        when(serviceAuth.login(any(DTORequestUserAuth.class))).thenReturn(mockToken);

        // Act
        ModelAndView result = controllerThymeleaf.signIn(username, password, totpKey);

        // Assert
        assertNotNull(result);
        assertEquals("upload", result.getViewName());
        assertEquals(mockToken, result.getModel().get("token"));
        verify(serviceAuth).login(any(DTORequestUserAuth.class));
    }

    @Test
    void testSignIn_WithInvalidCredentials_ShouldReturnLoginViewWithError() {
        // Arrange
        String username = "testuser";
        String password = "wrongpassword";
        String totpKey = "123456";
        
        when(serviceAuth.login(any(DTORequestUserAuth.class)))
            .thenThrow(new RuntimeException("Invalid credentials"));

        // Act
        ModelAndView result = controllerThymeleaf.signIn(username, password, totpKey);

        // Assert
        assertNotNull(result);
        assertEquals("login", result.getViewName());
        assertEquals(true, result.getModel().get("loginError"));
        verify(serviceAuth).login(any(DTORequestUserAuth.class));
    }

    @Test
    void testSignIn_WithInvalidTotpFormat_ShouldReturnLoginViewWithError() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String totpKey = "invalid";

        // Act
        ModelAndView result = controllerThymeleaf.signIn(username, password, totpKey);

        // Assert
        assertNotNull(result);
        assertEquals("login", result.getViewName());
        assertEquals(true, result.getModel().get("loginError"));
        verify(serviceAuth, never()).login(any(DTORequestUserAuth.class));
    }

    @Test
    void testRequiredPassword_ShouldReturnResetPasswordView() {
        // Act
        ModelAndView result = controllerThymeleaf.requiredPassword();

        // Assert
        assertNotNull(result);
        assertEquals("resetPassword", result.getViewName());
    }

    @Test
    void testResetPassword_WithValidUsername_ShouldReturnConfirmView() {
        // Arrange
        String username = "testuser";
        
        doNothing().when(serviceAuth).resetPassword(username);

        // Act
        ModelAndView result = controllerThymeleaf.resetPassword(username);

        // Assert
        assertNotNull(result);
        assertEquals("confirm", result.getViewName());
        verify(serviceAuth).resetPassword(username);
    }

    @Test
    void testResetPassword_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        String username = "testuser";
        
        doThrow(new RuntimeException("Reset failed")).when(serviceAuth).resetPassword(username);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerThymeleaf.resetPassword(username);
        });

        verify(serviceAuth).resetPassword(username);
    }

    @Test
    void testRequiredTotp_ShouldReturnResetTotpView() {
        // Act
        ModelAndView result = controllerThymeleaf.requiredTotp();

        // Assert
        assertNotNull(result);
        assertEquals("resetTotp", result.getViewName());
    }

    @Test
    void testResetTotp_WithValidUsername_ShouldReturnConfirmView() throws Exception {
        // Arrange
        String username = "testuser";
        
        doNothing().when(serviceAuth).resetTotp(username);

        // Act
        ModelAndView result = controllerThymeleaf.resetTotp(username);

        // Assert
        assertNotNull(result);
        assertEquals("confirm", result.getViewName());
        verify(serviceAuth).resetTotp(username);
    }

    @Test
    void testResetTotp_ServiceThrowsException_ShouldPropagateException() throws Exception {
        // Arrange
        String username = "testuser";
        
        doThrow(new Exception("TOTP reset failed")).when(serviceAuth).resetTotp(username);

        // Act & Assert
        assertThrows(Exception.class, () -> {
            controllerThymeleaf.resetTotp(username);
        });

        verify(serviceAuth).resetTotp(username);
    }

    @Test
    void testConfirm_ShouldReturnConfirmView() {
        // Act
        ModelAndView result = controllerThymeleaf.confirm();

        // Assert
        assertNotNull(result);
        assertEquals("confirm", result.getViewName());
    }

    @Test
    void testLogout_ShouldReturnLogoutView() {
        // Act
        ModelAndView result = controllerThymeleaf.logout(model);

        // Assert
        assertNotNull(result);
        assertEquals("logout", result.getViewName());
    }

    @Test
    void testSignIn_TotpKeyParsing_ShouldHandleCorrectly() {
        // Test para verificar diferentes valores de TOTP
        String username = "testuser";
        String password = "password123";
        String[] totpKeys = {"123456", "000000", "999999"};
        
        DTOResponseToken mockToken = mock(DTOResponseToken.class);
        when(serviceAuth.login(any(DTORequestUserAuth.class))).thenReturn(mockToken);

        for (String totpKey : totpKeys) {
            ModelAndView result = controllerThymeleaf.signIn(username, password, totpKey);
            
            assertNotNull(result);
            assertEquals("upload", result.getViewName());
            assertEquals(mockToken, result.getModel().get("token"));
        }
        
        verify(serviceAuth, times(totpKeys.length)).login(any(DTORequestUserAuth.class));
    }

    @Test
    void testSignUp_WithDifferentEmailFormats_ShouldCallService() {
        // Test com diferentes formatos de email
        String username = "testuser";
        String[] emails = {"test@example.com", "user.name+tag@domain.co.uk", "simple@domain.org"};
        
        for (String email : emails) {
            controllerThymeleaf.signUp(username, email);
            verify(serviceAuth).register(username, email);
        }
    }

    @Test
    void testAllViewEndpoints_ShouldReturnCorrectViewNames() {
        // Test abrangente para verificar que todos os endpoints de view retornam as views corretas
        assertEquals("register", controllerThymeleaf.register().getViewName());
        assertEquals("login", controllerThymeleaf.login().getViewName());
        assertEquals("resetPassword", controllerThymeleaf.requiredPassword().getViewName());
        assertEquals("resetTotp", controllerThymeleaf.requiredTotp().getViewName());
        assertEquals("confirm", controllerThymeleaf.confirm().getViewName());
        assertEquals("logout", controllerThymeleaf.logout(model).getViewName());
    }
}