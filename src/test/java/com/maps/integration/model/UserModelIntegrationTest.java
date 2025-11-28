package com.maps.integration.model;

import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.model.User;
import com.maps.persistence.repository.RepositoryPrivilege;
import com.maps.persistence.repository.RepositoryRole;
import com.maps.persistence.repository.RepositoryUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.hibernate.exception.ConstraintViolationException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes de integração para modelo User
 * Testa persistência, relacionamentos e validações com banco H2
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: Modelo User")
class UserModelIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RepositoryUser userRepository;

    @Autowired
    private RepositoryRole roleRepository;

    @Autowired
    private RepositoryPrivilege privilegeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve persistir usuário com dados válidos")
    @Rollback
    void shouldPersistValidUser() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("READ_USERS");
        Role role = IntegrationTestConfiguration.createTestRole("USER", Set.of(privilege));
        User user = IntegrationTestConfiguration.createTestUser("testuser", "test@example.com", Set.of(role));

        // Act
        User savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getAttempt()).isEqualTo(0);
        assertThat(savedUser.getActive()).isTrue();
        assertThat(savedUser.getRole()).hasSize(1);
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar usuário por username")
    @Rollback
    void shouldFindUserByUsername() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("finduser", "find@example.com", Set.of());
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<User> found = userRepository.findByUsername("finduser");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("finduser");
        assertThat(found.get().getEmail()).isEqualTo("find@example.com");
    }

    @Test
    @DisplayName("Deve falhar ao salvar usuário com username nulo")
    @Rollback
    void shouldFailWithNullUsername() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser(null, "nulluser@example.com", Set.of());

        // Act & Assert
        assertThatThrownBy(() -> {
            userRepository.save(user);
            entityManager.flush();
        }).isInstanceOf(jakarta.validation.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve falhar ao salvar usuário com username vazio")
    @Rollback
    void shouldFailWithBlankUsername() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("", "blankuser@example.com", Set.of());

        // Act & Assert
        assertThatThrownBy(() -> {
            userRepository.save(user);
            entityManager.flush();
        }).isInstanceOf(jakarta.validation.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve falhar ao salvar usuário com username duplicado")
    @Rollback
    void shouldFailWithDuplicateUsername() {
        // Arrange
        User user1 = IntegrationTestConfiguration.createTestUser("duplicateuser", "user1@example.com", Set.of());
        User user2 = IntegrationTestConfiguration.createTestUser("duplicateuser", "user2@example.com", Set.of());

        // Act
        userRepository.save(user1);
        entityManager.flush();

        // Assert
        assertThatThrownBy(() -> {
            userRepository.save(user2);
            entityManager.flush();
        }).isInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve manter relacionamentos com roles após persistência")
    @Rollback
    void shouldMaintainRoleRelationships() {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("READ_DATA");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("WRITE_DATA");
        
        // Persistir privilégios primeiro
        privilege1 = privilegeRepository.save(privilege1);
        privilege2 = privilegeRepository.save(privilege2);
        
        Set<Privilege> adminPrivileges = new HashSet<>();
        adminPrivileges.add(privilege1);
        adminPrivileges.add(privilege2);
        Role adminRole = IntegrationTestConfiguration.createTestRole("ADMIN", adminPrivileges);
        
        Set<Privilege> userPrivileges = new HashSet<>();
        userPrivileges.add(privilege1);
        Role userRole = IntegrationTestConfiguration.createTestRole("USER", userPrivileges);
        
        // Persistir roles
        adminRole = roleRepository.save(adminRole);
        userRole = roleRepository.save(userRole);
        
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(adminRole);
        userRoles.add(userRole);
        User user = IntegrationTestConfiguration.createTestUser("multiroleuser", "multirole@example.com", userRoles);

        // Act
        User savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Assert
        User reloadedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertThat(reloadedUser.getRole()).hasSize(2);
        assertThat(reloadedUser.getRole().stream().map(Role::getName))
            .containsExactlyInAnyOrder("ADMIN", "USER");
    }

    @Test
    @DisplayName("Deve atualizar usuário existente")
    @Rollback
    void shouldUpdateExistingUser() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("updateuser", "update@example.com", Set.of());
        User savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        User userToUpdate = userRepository.findById(savedUser.getId()).orElseThrow();
        userToUpdate.setEmail("updated@example.com");
        userToUpdate.setAttempt(3);
        User updatedUser = userRepository.save(userToUpdate);
        entityManager.flush();
        entityManager.clear();

        // Assert
        User reloadedUser = userRepository.findById(updatedUser.getId()).orElseThrow();
        assertThat(reloadedUser.getEmail()).isEqualTo("updated@example.com");
        assertThat(reloadedUser.getAttempt()).isEqualTo(3);
        assertThat(reloadedUser.getUsername()).isEqualTo("updateuser"); // Deve manter username original
    }

    @Test
    @DisplayName("Deve deletar usuário e manter integridade referencial")
    @Rollback
    void shouldDeleteUserMaintainingReferentialIntegrity() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("DELETE_TEST");
        Role role = IntegrationTestConfiguration.createTestRole("DELETE_ROLE", Set.of(privilege));
        roleRepository.save(role);
        
        User user = IntegrationTestConfiguration.createTestUser("deleteuser", "delete@example.com", Set.of(role));
        User savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        userRepository.delete(savedUser);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
        // Role deve continuar existindo
        assertThat(roleRepository.findById(role.getId())).isPresent();
    }

    @Test
    @DisplayName("Deve verificar auditoria automática")
    @Rollback
    void shouldHaveAutomaticAuditing() {
        // Arrange & Act
        User user = IntegrationTestConfiguration.createTestUser("audituser", "audit@example.com", Set.of());
        User savedUser = userRepository.save(user);
        entityManager.flush();

        // Assert
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getUpdatedAt()).isNotNull();
        // Em testes sem usuário autenticado, campos de auditoria de usuário são null
        assertThat(savedUser.getCreatedBy()).isNull();
        assertThat(savedUser.getModifiedBy()).isNull();
    }
}
