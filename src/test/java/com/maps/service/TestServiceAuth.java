package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Token;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestToken;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.response.DTOResponseToken;
import com.maps.persistence.repository.RepositoryToken;
import com.maps.persistence.repository.RepositoryUser;
import com.maps.configuration.security.ConfigurationJWT;
import com.maps.utils.E2EE;
import com.maps.utils.Information;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestServiceAuth {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private ConfigurationJWT configurationJWT;
    @Mock private RepositoryToken repositoryToken;
    @Mock private RepositoryUser repositoryUser;
    @Mock private MapperInterface<Token, DTORequestToken, DTOResponseToken> mapperInterface;
    @Mock private ServiceCustomUserDetails serviceCustomUserDetails;
    @Mock private ServiceTOTP serviceTOTP;
    @Mock private ServiceEmail serviceEmail;
    @Mock private ServiceUser serviceUser;
    @Mock private Information information;
    @Mock private ServiceRecaptcha serviceRecaptcha;
    @Mock private E2EE e2EE;

    @InjectMocks private ServiceAuth serviceAuth;

    private User testUser;
    private DTORequestUserAuth authRequest;
    private Authentication authentication;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setActive(true);
        testUser.setAttempt(0);
        testUser.setSecret("encryptedSecret");

        authRequest = new DTORequestUserAuth() {
            private String username = "testuser";
            private String password = "password123";
            private Integer totpKey = 123456;
            private String captchaToken = "validCaptcha";
            
            @Override
            public String getUsername() { return username; }
            
            @Override
            public String getPassword() { return password; }
            
            @Override
            public Integer getTotpKey() { return totpKey; }
            
            @Override
            public String getCaptchaToken() { return captchaToken; }
        };

        authentication = new UsernamePasswordAuthenticationToken(
            "testuser", 
            "password123", 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        userDetails = org.springframework.security.core.userdetails.User.builder()
            .username("testuser")
            .password("password123")
            .authorities("ROLE_USER")
            .build();
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        when(serviceCustomUserDetails.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        doNothing().when(serviceTOTP).validateTOTP("testuser", 123456);
        when(configurationJWT.generateToken("testuser")).thenReturn("generated-jwt-token");
        when(repositoryToken.save(any(Token.class))).thenReturn(new Token());

        DTOResponseToken result = serviceAuth.login(authRequest);

        assertNotNull(result);
        assertEquals("generated-jwt-token", result.getAccessToken());
        assertNotNull(result.getRefreshToken());
        assertTrue(result.getRole().contains("ROLE_USER"));
        
        verify(serviceCustomUserDetails, times(1)).loadUserByUsername("testuser");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(serviceTOTP, times(1)).validateTOTP("testuser", 123456);
        verify(configurationJWT, times(1)).generateToken("testuser");
        verify(repositoryToken, times(1)).save(any(Token.class));
    }

    @Test
    void login_shouldThrowException_whenInvalidTOTP() {
        when(serviceCustomUserDetails.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        doThrow(new RuntimeException("Invalid TOTP")).when(serviceTOTP).validateTOTP("testuser", 123456);

        assertThrows(RuntimeException.class, () -> serviceAuth.login(authRequest));
        
        verify(serviceTOTP, times(1)).validateTOTP("testuser", 123456);
        verify(configurationJWT, never()).generateToken(anyString());
    }

    @Test
    void refresh_shouldReturnNewToken_whenTokenValid() {
        UUID refreshToken = UUID.randomUUID();
        DTORequestToken tokenRequest = new DTORequestToken() {
            @Override
            public String getAccessToken() { return "valid-access-token"; }
            
            @Override
            public UUID getRefreshToken() { return refreshToken; }
        };

        when(repositoryToken.existsByRefreshToken(refreshToken)).thenReturn(true);
        when(configurationJWT.validateJWT("valid-access-token")).thenReturn(true);
        when(configurationJWT.getUsernameFromJWT("valid-access-token")).thenReturn("testuser");
        when(serviceCustomUserDetails.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(configurationJWT.generateToken("testuser")).thenReturn("new-jwt-token");

        DTOResponseToken result = serviceAuth.refresh(tokenRequest);

        assertNotNull(result);
        assertEquals("new-jwt-token", result.getAccessToken());
        assertEquals(refreshToken, result.getRefreshToken());
        assertTrue(result.getRole().contains("ROLE_USER"));
        
        verify(repositoryToken, times(1)).existsByRefreshToken(refreshToken);
        verify(configurationJWT, times(1)).validateJWT("valid-access-token");
        verify(configurationJWT, times(2)).getUsernameFromJWT("valid-access-token");
        verify(serviceCustomUserDetails, times(1)).loadUserByUsername("testuser");
        verify(configurationJWT, times(1)).generateToken("testuser");
    }

    @Test
    void refresh_shouldReturnNull_whenTokenInvalid() {
        UUID refreshToken = UUID.randomUUID();
        DTORequestToken tokenRequest = new DTORequestToken() {
            @Override
            public String getAccessToken() { return "invalid-access-token"; }
            
            @Override
            public UUID getRefreshToken() { return refreshToken; }
        };
        
        Token token = new Token(refreshToken, true);
        token.setId(UUID.randomUUID());
        DTOResponseToken responseToken = new DTOResponseToken("token", refreshToken, Collections.singleton("ROLE_USER"));

        when(repositoryToken.existsByRefreshToken(refreshToken)).thenReturn(false);
        when(repositoryToken.findByRefreshToken(refreshToken)).thenReturn(Optional.of(token));
        when(mapperInterface.toDTO(token)).thenReturn(responseToken);

        DTOResponseToken result = serviceAuth.refresh(tokenRequest);

        assertNull(result);
        verify(repositoryToken, times(1)).existsByRefreshToken(refreshToken);
        verify(repositoryToken, times(1)).deleteById(token.getId());
    }

    @Test
    void logout_shouldDeleteToken_whenTokenExists() {
        UUID refreshToken = UUID.randomUUID();
        Token token = new Token(refreshToken, true);
        token.setId(UUID.randomUUID());
        DTOResponseToken responseToken = new DTOResponseToken("token", refreshToken, Collections.singleton("ROLE_USER"));

        when(repositoryToken.findByRefreshToken(refreshToken)).thenReturn(Optional.of(token));
        when(mapperInterface.toDTO(token)).thenReturn(responseToken);

        DTOResponseToken result = serviceAuth.logout(refreshToken);

        assertNotNull(result);
        assertEquals(refreshToken, result.getRefreshToken());
        
        verify(repositoryToken, times(1)).findByRefreshToken(refreshToken);
        verify(repositoryToken, times(1)).deleteById(token.getId());
        verify(mapperInterface, times(1)).toDTO(token);
    }

    @Test
    void logout_shouldThrowException_whenTokenNotFound() {
        UUID refreshToken = UUID.randomUUID();
        when(repositoryToken.findByRefreshToken(refreshToken)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceAuth.logout(refreshToken));
        
        verify(repositoryToken, times(1)).findByRefreshToken(refreshToken);
        verify(repositoryToken, never()).deleteById(any());
    }

    @Test
    void addAttempt_shouldIncrementAttempt_whenUserExists() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(repositoryUser.save(any(User.class))).thenReturn(testUser);

        serviceAuth.addAttempt(authRequest);

        assertEquals(1, testUser.getAttempt());
        verify(repositoryUser, times(1)).findByUsername("testuser");
        verify(repositoryUser, times(1)).save(testUser);
    }

    @Test
    void addAttempt_shouldBlockUser_whenMaxAttemptsReached() {
        testUser.setAttempt(4);
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(repositoryUser.save(any(User.class))).thenReturn(testUser);

        assertThrows(RuntimeException.class, () -> serviceAuth.addAttempt(authRequest));
        
        assertEquals(5, testUser.getAttempt());
        assertFalse(testUser.getActive());
        verify(repositoryUser, times(1)).save(testUser);
    }

    @Test
    void addAttempt_shouldThrowException_whenUserNotFound() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceAuth.addAttempt(authRequest));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
        verify(repositoryUser, never()).save(any());
    }

    @Test
    void validUser_shouldPass_whenUserActiveAndExists() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        assertDoesNotThrow(() -> serviceAuth.validUser("testuser"));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
    }

    @Test
    void validUser_shouldThrowException_whenUserInactive() {
        testUser.setActive(false);
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        assertThrows(RuntimeException.class, () -> serviceAuth.validUser("testuser"));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
    }

    @Test
    void validUser_shouldThrowException_whenUserNotFound() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceAuth.validUser("testuser"));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
    }

    @Test
    void register_shouldCreateUser() {
        DTORequestUser userRequest = new DTORequestUser("newuser", "new@example.com");
        when(serviceUser.create(any(DTORequestUser.class))).thenReturn(mock(com.maps.persistence.payload.response.DTOResponseUser.class));

        assertDoesNotThrow(() -> serviceAuth.register("newuser", "new@example.com"));
        
        verify(serviceUser, times(1)).create(any(DTORequestUser.class));
    }

    @Test
    void resetPassword_shouldSendEmail_whenUserExists() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        assertDoesNotThrow(() -> serviceAuth.resetPassword("testuser"));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
        verify(serviceEmail, times(1)).sendSimpleMessage(
            testUser.getEmail(), 
            "Recovery password", 
            testUser.getPassword()
        );
    }

    @Test
    void resetPassword_shouldThrowException_whenUserNotFound() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceAuth.resetPassword("testuser"));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
        verify(serviceEmail, never()).sendSimpleMessage(anyString(), anyString(), anyString());
    }

    @Test
    void resetTotp_shouldSendEmail_whenUserExists() throws Exception {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(e2EE.decrypt("encryptedSecret")).thenReturn("decryptedSecret");

        assertDoesNotThrow(() -> serviceAuth.resetTotp("testuser"));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
        verify(e2EE, times(1)).decrypt("encryptedSecret");
        verify(serviceEmail, times(1)).sendSimpleMessage(
            testUser.getEmail(), 
            "Recovery totp", 
            "decryptedSecret"
        );
    }

    @Test
    void resetTotp_shouldThrowException_whenUserNotFound() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceAuth.resetTotp("testuser"));
        
        verify(repositoryUser, times(1)).findByUsername("testuser");
        verify(serviceEmail, never()).sendSimpleMessage(anyString(), anyString(), anyString());
    }

    @Test
    void captchaTest_shouldPass_whenCaptchaValid() {
        when(serviceRecaptcha.validateCaptcha("validCaptcha")).thenReturn(true);

        assertDoesNotThrow(() -> serviceAuth.captchaTest("validCaptcha"));
        
        verify(serviceRecaptcha, times(1)).validateCaptcha("validCaptcha");
    }

    @Test
    void captchaTest_shouldThrowException_whenCaptchaInvalid() {
        when(serviceRecaptcha.validateCaptcha("invalidCaptcha")).thenReturn(false);

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class, 
            () -> serviceAuth.captchaTest("invalidCaptcha")
        );
        
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Invalid or suspicious CAPTCHA", exception.getReason());
        verify(serviceRecaptcha, times(1)).validateCaptcha("invalidCaptcha");
    }
}
