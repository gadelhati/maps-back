package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.persistence.repository.RepositoryRole;
import com.maps.persistence.repository.RepositoryUser;
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
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestServiceUser {

    @Mock private Information information;
    @Mock private RepositoryUser repositoryUser;
    @Mock private RepositoryRole repositoryRole;
    @Mock private ServiceUserTOTP serviceUserTOTP;
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
        Privilege readPrivilege = new Privilege();
        readPrivilege.setId(UUID.randomUUID());
        readPrivilege.setName("READ");

        privileges = new HashSet<>();
        privileges.add(readPrivilege);

        testRole = new Role("VIEWER", privileges);
        testRole.setId(UUID.randomUUID());

        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setSecret("encryptedSecret");
        user.setActive(true);
        user.setAttempt(0);

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
        when(serviceUserTOTP.generateSecret()).thenReturn("generatedSecret");
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
        verify(serviceUserTOTP, times(1)).generateSecret();
        verify(e2EE, times(1)).encrypt(anyString());
    }

    @Test
    void create_shouldThrowBadCredentialsException_whenEncryptionFails() throws Exception {
        when(mapperInterface.toObject(any(DTORequestUser.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(serviceUserTOTP.generateSecret()).thenReturn("generatedSecret");
        when(e2EE.encrypt(anyString())).thenThrow(new Exception("Encryption failed"));
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));

        assertThrows(BadCredentialsException.class, () -> serviceUser.create(dtoRequestUser));

        verify(repositoryUser, never()).save(any(User.class));
        verify(serviceEmail, never()).sendHtmlMessageWithAttachment(
                anyString(), anyString(), anyString(),
                any(byte[].class), anyString(), anyString()
        );
    }

    @Test
    void create_shouldThrowException_whenUserMappingFails() {
        when(mapperInterface.toObject(any(DTORequestUser.class)))
                .thenThrow(new RuntimeException("Mapping failed"));
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));
        when(env.getRequiredProperty(anyString())).thenReturn("maps");

        assertThrows(RuntimeException.class, () -> serviceUser.create(dtoRequestUser));

        verify(repositoryUser, never()).save(any(User.class));
        verify(serviceEmail, never()).sendHtmlMessageWithAttachment(
                anyString(), anyString(), anyString(),
                any(byte[].class), anyString(), anyString()
        );
    }

    @Test
    void create_shouldThrowException_whenRepositorySaveFails() throws Exception {
        when(mapperInterface.toObject(any(DTORequestUser.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(serviceUserTOTP.generateSecret()).thenReturn("generatedSecret");
        when(e2EE.encrypt(anyString())).thenReturn("encryptedSecret");
        when(repositoryRole.findByName("VIEWER")).thenReturn(testRole);
        when(repositoryUser.save(any(User.class))).thenThrow(new RuntimeException("Database error"));
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));
        when(env.getRequiredProperty(anyString())).thenReturn("maps");

        assertThrows(RuntimeException.class, () -> serviceUser.create(dtoRequestUser));

        verify(repositoryUser, times(1)).save(any(User.class));
        verify(serviceEmail, never()).sendHtmlMessageWithAttachment(
                anyString(), anyString(), anyString(),
                any(byte[].class), anyString(), anyString()
        );
    }
}