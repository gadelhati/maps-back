package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.persistence.repository.RepositoryRole;
import com.maps.persistence.repository.RepositoryUser;
import com.maps.utils.E2EE;
import com.maps.utils.Information;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestServiceUser {

    @Mock private Information information;
    @Mock private RepositoryUser repositoryUser;
    @Mock private RepositoryRole repositoryRole;
    @Mock private ServiceTOTP serviceTOTP;
    @Mock private ServiceEmail serviceEmail;
    @Mock private Environment env;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private E2EE e2EE;
    @Mock private MapperInterface<User, DTORequestUser, DTOResponseUser> mapperInterface;
    @Mock private RepositoryGeneric<User> repositoryGeneric;

    @InjectMocks private ServiceUser serviceUser;

    private User user;
    private DTORequestUser dtoRequestUser;
    private DTOResponseUser dtoResponseUser;
    private Role testRole;
    private Set<Privilege> privileges;

    @BeforeEach
    void setUp() {
        // Setup test data
        Privilege privilege = new Privilege();
        privilege.setName("READ_PRIVILEGE");
        privileges = new HashSet<>();
        privileges.add(privilege);

        testRole = new Role();
        testRole.setName("VIEWER");
        testRole.setPrivilege(privileges);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setActive(true);
        user.setAttempt(0);
        user.setSecret("encryptedSecret");
        Set<Role> roles = new HashSet<>();
        roles.add(testRole);
        user.setRole(roles);

        dtoRequestUser = new DTORequestUser("testuser", "test@example.com");
        dtoResponseUser = new DTOResponseUser(UUID.randomUUID(), "testuser", "test@example.com", 0, true, Collections.singleton(testRole));
    }

    @Test
    void create_shouldCreateUserAndSendEmail() throws Exception {
        when(mapperInterface.toObject(any(DTORequestUser.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(serviceTOTP.generateSecret()).thenReturn("generatedSecret");
        when(e2EE.encrypt(anyString())).thenReturn("encryptedSecret");
        when(repositoryRole.findByName("VIEWER")).thenReturn(testRole);
        when(repositoryUser.save(any(User.class))).thenReturn(user);
        when(mapperInterface.toDTO(any(User.class))).thenReturn(dtoResponseUser);
        when(env.getRequiredProperty(anyString())).thenReturn("maps");
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));

        DTOResponseUser result = serviceUser.create(dtoRequestUser);

        assertNotNull(result);
        assertEquals(dtoResponseUser.getUsername(), result.getUsername());
        assertEquals(dtoResponseUser.getEmail(), result.getEmail());
        
        verify(repositoryUser, times(1)).save(any(User.class));
        verify(serviceEmail, times(1)).sendHtmlMessageWithAttachment(
                anyString(), anyString(), anyString(),
                any(byte[].class), anyString(), anyString()
        );
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(serviceTOTP, times(1)).generateSecret();
        verify(e2EE, times(1)).encrypt(anyString());
    }

    @Test
    void create_shouldThrowException_whenEmailFails() throws Exception {
        when(mapperInterface.toObject(any(DTORequestUser.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(serviceTOTP.generateSecret()).thenReturn("generatedSecret");
        when(e2EE.encrypt(anyString())).thenReturn("encryptedSecret");
        when(repositoryRole.findByName("VIEWER")).thenReturn(testRole);
        when(env.getRequiredProperty(anyString())).thenReturn("maps");
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));
        
        doThrow(new RuntimeException("Email service failure")).when(serviceEmail)
                .sendHtmlMessageWithAttachment(anyString(), anyString(), anyString(), any(byte[].class), anyString(), anyString());

        assertThrows(BadCredentialsException.class, () -> serviceUser.create(dtoRequestUser));
    }

    @Test
    void update_shouldUpdateUser_whenUserExists() {
        UUID userId = UUID.randomUUID();
        when(repositoryUser.findById(userId)).thenReturn(Optional.of(user));
        when(repositoryUser.save(any(User.class))).thenReturn(user);
        when(mapperInterface.toDTO(any(User.class))).thenReturn(dtoResponseUser);
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));

        DTOResponseUser result = serviceUser.update(userId, dtoRequestUser);

        assertNotNull(result);
        verify(repositoryUser, times(1)).findById(userId);
        verify(repositoryUser, times(1)).save(any(User.class));
    }

    @Test
    void update_shouldThrowException_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(repositoryUser.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceUser.update(userId, dtoRequestUser));
    }

    @Test
    void existsByUsername_shouldReturnTrue_whenUserExists() {
        when(repositoryUser.existsByUsernameIgnoreCase("testuser")).thenReturn(true);

        boolean result = serviceUser.existsByUsername("testuser");

        assertTrue(result);
        verify(repositoryUser, times(1)).existsByUsernameIgnoreCase("testuser");
    }

    @Test
    void existsByUsername_shouldThrowException_whenValueIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByUsername(""));
        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByUsername(null));
    }

    @Test
    void existsByEmail_shouldReturnTrue_whenEmailExists() {
        when(repositoryUser.existsByEmailIgnoreCase("test@example.com")).thenReturn(true);

        boolean result = serviceUser.existsByEmail("test@example.com");

        assertTrue(result);
        verify(repositoryUser, times(1)).existsByEmailIgnoreCase("test@example.com");
    }

    @Test
    void existsByEmail_shouldThrowException_whenValueIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByEmail(""));
        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByEmail(null));
    }

    @Test
    void existsByUsernameAndIdNot_shouldReturnTrue_whenDifferentUserExists() {
        UUID userId = UUID.randomUUID();
        when(repositoryUser.existsByUsernameIgnoreCaseAndIdNot("testuser", userId)).thenReturn(true);

        boolean result = serviceUser.existsByUsernameAndIdNot("testuser", userId);

        assertTrue(result);
        verify(repositoryUser, times(1)).existsByUsernameIgnoreCaseAndIdNot("testuser", userId);
    }

    @Test
    void existsByUsernameAndIdNot_shouldThrowException_whenParametersInvalid() {
        UUID userId = UUID.randomUUID();
        
        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByUsernameAndIdNot("", userId));
        assertThrows(IllegalArgumentException.class, () -> serviceUser.existsByUsernameAndIdNot("testuser", null));
    }

    @Test
    void existsByEmailAndIdNot_shouldReturnTrue_whenDifferentUserExists() {
        UUID userId = UUID.randomUUID();
        when(repositoryUser.existsByEmailIgnoreCaseAndIdNot("test@example.com", userId)).thenReturn(true);

        boolean result = serviceUser.existsByEmailAndIdNot("test@example.com", userId);

        assertTrue(result);
        verify(repositoryUser, times(1)).existsByEmailIgnoreCaseAndIdNot("test@example.com", userId);
    }

    @Test
    void changePassword_shouldUpdatePasswordAndSendEmail() throws Exception {
        UUID userId = UUID.randomUUID();
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword() {
            private UUID id = userId;
            private String password = "newPassword";
            
            @Override
            public UUID getId() { return id; }
            
            @Override
            public String getPassword() { return password; }
        };

        User adminUser = new User();
        adminUser.setUsername("admin");
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);

        when(repositoryUser.findById(userId)).thenReturn(Optional.of(user));
        when(repositoryUser.findByUsername("admin")).thenReturn(Optional.of(adminUser));
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(e2EE.decrypt("encryptedSecret")).thenReturn("decryptedSecret");
        when(env.getRequiredProperty(anyString())).thenReturn("maps");
        when(repositoryUser.save(any(User.class))).thenReturn(user);
        when(mapperInterface.toDTO(any(User.class))).thenReturn(dtoResponseUser);

        DTOResponseUser result = serviceUser.changePassword(passwordRequest);

        assertNotNull(result);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(repositoryUser, times(1)).save(any(User.class));
        verify(serviceEmail, times(1)).sendHtmlMessageWithAttachment(
                anyString(), anyString(), anyString(),
                any(byte[].class), anyString(), anyString()
        );
    }

    @Test
    void resetPassword_shouldGenerateNewPasswordAndSendEmail() throws Exception {
        User targetUser = new User();
        targetUser.setUsername("testuser");
        targetUser.setEmail("test@example.com");
        targetUser.setSecret("encryptedSecret");

        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(targetUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(e2EE.decrypt("encryptedSecret")).thenReturn("decryptedSecret");
        when(env.getRequiredProperty(anyString())).thenReturn("maps");
        when(repositoryUser.save(any(User.class))).thenReturn(targetUser);
        when(mapperInterface.toDTO(any(User.class))).thenReturn(dtoResponseUser);
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));

        DTOResponseUser result = serviceUser.resetPassword("testuser");

        assertNotNull(result);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(repositoryUser, times(1)).save(any(User.class));
        verify(serviceEmail, times(1)).sendHtmlMessageWithAttachment(
                anyString(), anyString(), anyString(),
                any(byte[].class), anyString(), anyString()
        );
    }

    @Test
    void resetSecret_shouldGenerateNewSecretAndSendEmail() throws Exception {
        User targetUser = new User();
        targetUser.setUsername("testuser");
        targetUser.setEmail("test@example.com");
        targetUser.setSecret("oldEncryptedSecret");

        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(targetUser));
        when(serviceTOTP.generateSecret()).thenReturn("newSecret");
        when(e2EE.encrypt("newSecret")).thenReturn("newEncryptedSecret");
        when(e2EE.decrypt("newEncryptedSecret")).thenReturn("newSecret");
        when(env.getRequiredProperty(anyString())).thenReturn("maps");
        when(repositoryUser.save(any(User.class))).thenReturn(targetUser);
        when(mapperInterface.toDTO(any(User.class))).thenReturn(dtoResponseUser);
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));

        DTOResponseUser result = serviceUser.resetSecret("testuser");

        assertNotNull(result);
        verify(serviceTOTP, times(1)).generateSecret();
        verify(e2EE, times(1)).encrypt("newSecret");
        verify(repositoryUser, times(1)).save(any(User.class));
        verify(serviceEmail, times(1)).sendHtmlMessageWithAttachment(
                anyString(), anyString(), anyString(),
                any(byte[].class), anyString(), anyString()
        );
    }

    @Test
    void generateSecurePassword_shouldReturnValidPassword() {
        String password = serviceUser.generateSecurePassword();

        assertNotNull(password);
        assertTrue(password.length() >= 8);
        assertTrue(password.matches(".*[A-Z].*")); // Contains uppercase
        assertTrue(password.matches(".*[a-z].*")); // Contains lowercase
        assertTrue(password.matches(".*\\d.*")); // Contains digit
        assertTrue(password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?].*")); // Contains special char
    }

    @Test
    void isValidToChange_withId_shouldReturnUser_whenUserIsSameOrAdmin() {
        UUID userId = UUID.randomUUID();
        user.setUsername("testuser");

        User currentUser = new User();
        currentUser.setUsername("testuser");
        currentUser.setRole(Collections.singleton(testRole));

        when(repositoryUser.findById(userId)).thenReturn(Optional.of(user));
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(currentUser));
        when(information.getCurrentUser()).thenReturn(Optional.of("testuser"));

        User result = serviceUser.isValidToChange(userId);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void isValidToChange_withId_shouldThrowException_whenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(repositoryUser.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceUser.isValidToChange(userId));
    }

    @Test
    void isValidToChange_withUsername_shouldReturnUser_whenUserExists() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.of(user));

        User result = serviceUser.isValidToChange("testuser");

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void isValidToChange_withUsername_shouldThrowException_whenUserNotFound() {
        when(repositoryUser.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceUser.isValidToChange("testuser"));
    }
}

