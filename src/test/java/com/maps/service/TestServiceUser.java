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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceUser {

    @Mock
    private Information information;
    @Mock
    private RepositoryUser repositoryUser;
    @Mock
    private RepositoryRole repositoryRole;
    @Mock
    private ServiceUserTOTP serviceUserTOTP;
    @Mock
    private ServiceEmail serviceEmail;
    @Mock
    private Environment env;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private E2EE e2EE;
    @Mock
    private MapperInterface<User, DTORequestUser, DTOResponseUser>
    mapperInterface;
    @Mock
    private RepositoryGeneric<User> repositoryGeneric;
    @InjectMocks
    private ServiceUser serviceUser;
    @Autowired
    private TestEntityManager entityManager;
    private User user;
    private DTORequestUser dtoRequestUser;
    private DTOResponseUser dtoResponseUser;

    @BeforeEach
    void setUp() {
        // Create privileges
        Privilege readPrivilege = new Privilege();
        readPrivilege.setName("READ");
        entityManager.persistAndFlush(readPrivilege);

        Set<Privilege> privileges = new HashSet<>();
        privileges.add(readPrivilege);

        MockitoAnnotations.openMocks(this);
        // Initialize serviceUser with mocked dependencies
        serviceUser = new ServiceUser(repositoryGeneric, mapperInterface,
        repositoryUser, information, serviceUserTOTP, env, passwordEncoder, e2EE,
        repositoryRole, serviceEmail);
        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setSecret("encryptedSecret");
        user.setActive(true);
        user.setAttempt(0);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("UUID.randomUUID()", privileges));
        user.setRole(roles);
//        dtoRequestUser = new DTORequestUser();
//        dtoRequestUser.setId(user.getId());
//        dtoRequestUser.setUsername("testuser");
//        dtoRequestUser.setEmail("test@example.com");
//        dtoRequestUser.setPassword("rawPassword");
//        dtoRequestUser.setRole(roles);
//        dtoResponseUser = new DTOResponseUser();
//        dtoResponseUser.setId(user.getId());
//        dtoResponseUser.setUsername("testuser");
//        dtoResponseUser.setEmail("test@example.com");
//        dtoResponseUser.setRole(roles);
    }
    @Test
    void create_shouldCreateUserAndSendEmail() throws Exception {
        // Create privileges
        Privilege readPrivilege = new Privilege();
        readPrivilege.setName("READ");
        entityManager.persistAndFlush(readPrivilege);

        Set<Privilege> privileges = new HashSet<>();
        privileges.add(readPrivilege);

        when(mapperInterface.toObject(any(DTORequestUser.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(serviceUserTOTP.generateSecret()).thenReturn("generatedSecret");
        when(e2EE.encrypt(anyString())).thenReturn("encryptedSecret");
        when(repositoryRole.findByName(anyString())).thenReturn(new
        Role("UUID.randomUUID()", privileges));
        when(repositoryUser.save(any(User.class))).thenReturn(user);
        when(mapperInterface.toDTO(any(User.class))).thenReturn(dtoResponseUser);
        when(env.getRequiredProperty(anyString())).thenReturn("maps");
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));
        DTOResponseUser result = serviceUser.create(dtoRequestUser);
        assertNotNull(result);
        assertEquals(dtoResponseUser.getUsername(), result.getUsername());
        verify(repositoryUser, times(1)).save(any(User.class));
        verify(serviceEmail,
        times(1)).sendHtmlMessageWithAttachment(anyString(), anyString(), anyString(),
        any(byte[].class), anyString(), anyString());
    }
    @Test
    void create_shouldThrowBadCredentialsException_whenEncryptionFails() throws Exception {
        when(mapperInterface.toObject(any(DTORequestUser.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(serviceUserTOTP.generateSecret()).thenReturn("generatedSecret");
        when(e2EE.encrypt(anyString())).thenThrow(new Exception("Encryption failed"));
        when(information.getCurrentUser()).thenReturn(Optional.of("admin"));
        assertThrows(BadCredentialsException.class, () ->
        serviceUser.create(dtoRequestUser));
        verify(repositoryUser, never()).save(any(User.class));
        verify(serviceEmail,
        never()).sendHtmlMessageWithAttachment(anyString(), anyString(), anyString(),
        any(byte[].class), anyString(), anyString());
    }
}