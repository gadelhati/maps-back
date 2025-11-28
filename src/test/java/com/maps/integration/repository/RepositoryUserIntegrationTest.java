package com.maps.integration.repository;

import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.model.User;
import com.maps.persistence.repository.RepositoryUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de integração para RepositoryUser
 * Testa queries, relacionamentos e operações CRUD com banco H2
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: RepositoryUser")
@Transactional
class RepositoryUserIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RepositoryUser userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve salvar usuário com sucesso")
    @Rollback
    void shouldSaveUserSuccessfully() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("saveuser", "save@example.com", Set.of());

        // Act
        User savedUser = userRepository.save(user);
        entityManager.flush();

        // Assert
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("saveuser");
        assertThat(savedUser.getEmail()).isEqualTo("save@example.com");
    }

    @Test
    @DisplayName("Deve buscar usuário por ID")
    @Rollback
    void shouldFindUserById() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("finduser", "find@example.com", Set.of());
        User savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<User> found = userRepository.findById(savedUser.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("finduser");
    }

    @Test
    @DisplayName("Deve buscar usuário por username")
    @Rollback
    void shouldFindUserByUsername() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("uniqueuser", "unique@example.com", Set.of());
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<User> found = userRepository.findByUsername("uniqueuser");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("unique@example.com");
    }

    @Test
    @DisplayName("Deve verificar existência por username ignorando case")
    @Rollback
    void shouldCheckExistenceByUsernameIgnoreCase() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("CaseSensitiveUser", "case@example.com", Set.of());
        userRepository.save(user);
        entityManager.flush();

        // Act & Assert
        assertThat(userRepository.existsByUsernameIgnoreCase("casesensitiveuser")).isTrue();
        assertThat(userRepository.existsByUsernameIgnoreCase("CASESENSITIVEUSER")).isTrue();
        assertThat(userRepository.existsByUsernameIgnoreCase("CaseSensitiveUser")).isTrue();
        assertThat(userRepository.existsByUsernameIgnoreCase("NonExistentUser")).isFalse();
    }

    @Test
    @DisplayName("Deve verificar existência por email ignorando case")
    @Rollback
    void shouldCheckExistenceByEmailIgnoreCase() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("emailuser", "CaseEmail@Example.Com", Set.of());
        userRepository.save(user);
        entityManager.flush();

        // Act & Assert
        assertThat(userRepository.existsByEmailIgnoreCase("caseemail@example.com")).isTrue();
        assertThat(userRepository.existsByEmailIgnoreCase("CASEEMAIL@EXAMPLE.COM")).isTrue();
        assertThat(userRepository.existsByEmailIgnoreCase("CaseEmail@Example.Com")).isTrue();
        assertThat(userRepository.existsByEmailIgnoreCase("nonexistent@example.com")).isFalse();
    }

    @Test
    @DisplayName("Deve verificar existência por username ignorando case e ID diferente")
    @Rollback
    void shouldCheckExistenceByUsernameIgnoreCaseAndIdNot() {
        // Arrange
        User user1 = IntegrationTestConfiguration.createTestUser("ExistingUser", "existing1@example.com", Set.of());
        User user2 = IntegrationTestConfiguration.createTestUser("AnotherUser", "existing2@example.com", Set.of());
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        entityManager.flush();

        // Act & Assert
        assertThat(userRepository.existsByUsernameIgnoreCaseAndIdNot("existinguser", savedUser2.getId())).isTrue();
        assertThat(userRepository.existsByUsernameIgnoreCaseAndIdNot("existinguser", savedUser1.getId())).isFalse();
    }

    @Test
    @DisplayName("Deve verificar existência por email ignorando case e ID diferente")
    @Rollback
    void shouldCheckExistenceByEmailIgnoreCaseAndIdNot() {
        // Arrange
        User user1 = IntegrationTestConfiguration.createTestUser("user1", "Existing@Example.Com", Set.of());
        User user2 = IntegrationTestConfiguration.createTestUser("user2", "another@example.com", Set.of());
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        entityManager.flush();

        // Act & Assert
        assertThat(userRepository.existsByEmailIgnoreCaseAndIdNot("existing@example.com", savedUser2.getId())).isTrue();
        assertThat(userRepository.existsByEmailIgnoreCaseAndIdNot("existing@example.com", savedUser1.getId())).isFalse();
    }

    @Test
    @DisplayName("Deve buscar todos os usuários")
    @Rollback
    void shouldFindAllUsers() {
        // Arrange
        User user1 = IntegrationTestConfiguration.createTestUser("user1", "user1@example.com", Set.of());
        User user2 = IntegrationTestConfiguration.createTestUser("user2", "user2@example.com", Set.of());
        User user3 = IntegrationTestConfiguration.createTestUser("user3", "user3@example.com", Set.of());
        
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<User> allUsers = userRepository.findAll();

        // Assert
        assertThat(allUsers).hasSize(3);
        assertThat(allUsers.stream().map(User::getUsername))
            .containsExactlyInAnyOrder("user1", "user2", "user3");
    }

    @Test
    @DisplayName("Deve buscar usuários com paginação")
    @Rollback
    void shouldFindUsersWithPagination() {
        // Arrange
        for (int i = 1; i <= 10; i++) {
            User user = IntegrationTestConfiguration.createTestUser("pageuser" + i, "page" + i + "@example.com", Set.of());
            userRepository.save(user);
        }
        entityManager.flush();
        entityManager.clear();

        // Act
        Pageable pageable = PageRequest.of(0, 3);
        Page<User> page = userRepository.findAll(pageable);

        // Assert
        assertThat(page.getContent()).hasSize(3);
        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getTotalPages()).isEqualTo(4);
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
        userToUpdate.setAttempt(5);
        userToUpdate.setActive(false);
        User updatedUser = userRepository.save(userToUpdate);
        entityManager.flush();
        entityManager.clear();

        // Assert
        User reloadedUser = userRepository.findById(updatedUser.getId()).orElseThrow();
        assertThat(reloadedUser.getEmail()).isEqualTo("updated@example.com");
        assertThat(reloadedUser.getAttempt()).isEqualTo(5);
        assertThat(reloadedUser.getActive()).isFalse();
        assertThat(reloadedUser.getUsername()).isEqualTo("updateuser"); // Deve manter username
    }

    @Test
    @DisplayName("Deve deletar usuário")
    @Rollback
    void shouldDeleteUser() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("deleteuser", "delete@example.com", Set.of());
        User savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        userRepository.delete(savedUser);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
        assertThat(userRepository.findByUsername("deleteuser")).isEmpty();
    }

    @Test
    @DisplayName("Deve manter relacionamentos com roles")
    @Rollback
    void shouldMaintainRoleRelationships() {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("READ_DATA");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("WRITE_DATA");
        Role adminRole = IntegrationTestConfiguration.createTestRole("ADMIN", Set.of(privilege1, privilege2));
        Role userRole = IntegrationTestConfiguration.createTestRole("USER", Set.of(privilege1));
        
        User user = IntegrationTestConfiguration.createTestUser("multiroleuser", "multirole@example.com", Set.of(adminRole, userRole));
        User savedUser = userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Act
        User reloadedUser = userRepository.findById(savedUser.getId()).orElseThrow();

        // Assert
        assertThat(reloadedUser.getRole()).hasSize(2);
        assertThat(reloadedUser.getRole().stream().map(Role::getName))
            .containsExactlyInAnyOrder("ADMIN", "USER");
        
        // Verificar carregamento EAGER dos privilégios
        reloadedUser.getRole().forEach(role -> {
            assertThat(role.getPrivilege()).isNotEmpty();
            if ("ADMIN".equals(role.getName())) {
                assertThat(role.getPrivilege()).hasSize(2);
            } else {
                assertThat(role.getPrivilege()).hasSize(1);
            }
        });
    }

    @Test
    @DisplayName("Deve buscar usuários por status ativo com paginação")
    @Rollback
    void shouldFindUsersByActiveStatusWithPagination() {
        // Arrange
        User activeUser1 = IntegrationTestConfiguration.createTestUser("activeuser1", "active1@example.com", Set.of());
        activeUser1.setActive(true);
        
        User activeUser2 = IntegrationTestConfiguration.createTestUser("activeuser2", "active2@example.com", Set.of());
        activeUser2.setActive(true);
        
        User inactiveUser = IntegrationTestConfiguration.createTestUser("inactiveuser", "inactive@example.com", Set.of());
        inactiveUser.setActive(false);
        
        userRepository.save(activeUser1);
        userRepository.save(activeUser2);
        userRepository.save(inactiveUser);
        entityManager.flush();
        entityManager.clear();

        // Act
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<User> activeUsers = userRepository.findByActive(true, pageRequest);

        // Assert
        assertThat(activeUsers.getContent()).hasSize(2);
        assertThat(activeUsers.getContent().stream().map(User::getUsername))
            .containsExactlyInAnyOrder("activeuser1", "activeuser2");
    }

    @Test
    @DisplayName("Deve contar total de usuários")
    @Rollback
    void shouldCountTotalUsers() {
        // Arrange
        for (int i = 1; i <= 7; i++) {
            User user = IntegrationTestConfiguration.createTestUser("countuser" + i, "count" + i + "@example.com", Set.of());
            userRepository.save(user);
        }
        entityManager.flush();

        // Act
        long count = userRepository.count();

        // Assert
        assertThat(count).isEqualTo(7);
    }

    @Test
    @DisplayName("Deve verificar existência por ID")
    @Rollback
    void shouldCheckExistenceById() {
        // Arrange
        User user = IntegrationTestConfiguration.createTestUser("existsuser", "exists@example.com", Set.of());
        User savedUser = userRepository.save(user);
        entityManager.flush();

        // Act & Assert
        assertThat(userRepository.existsById(savedUser.getId())).isTrue();
        assertThat(userRepository.existsById(UUID.randomUUID())).isFalse();
    }

    @Test
    @DisplayName("Deve manter auditoria durante operações CRUD")
    @Rollback
    void shouldMaintainAuditingDuringCrudOperations() {
        // Arrange & Create
        User user = IntegrationTestConfiguration.createTestUser("audituser", "audit@example.com", Set.of());
        User savedUser = userRepository.save(user);
        entityManager.flush();
        
        // Assert Creation Audit
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getUpdatedAt()).isNotNull();
        assertThat(savedUser.getCreatedBy()).isNotNull();
        assertThat(savedUser.getModifiedBy()).isNotNull();
        
        var originalUpdatedAt = savedUser.getUpdatedAt();
        entityManager.clear();
        
        // Update
        User userToUpdate = userRepository.findById(savedUser.getId()).orElseThrow();
        userToUpdate.setEmail("updated_audit@example.com");
        User updatedUser = userRepository.save(userToUpdate);
        entityManager.flush();
        
        // Assert Update Audit
        assertThat(updatedUser.getUpdatedAt()).isAfter(originalUpdatedAt);
        assertThat(updatedUser.getCreatedAt()).isEqualTo(savedUser.getCreatedAt()); // Não deve mudar
    }
}