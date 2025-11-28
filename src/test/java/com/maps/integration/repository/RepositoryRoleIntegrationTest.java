package com.maps.integration.repository;

import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.repository.RepositoryRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Testes de integração para RepositoryRole
 * Testa queries, relacionamentos e operações CRUD com banco H2
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: RepositoryRole")
@Transactional
class RepositoryRoleIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RepositoryRole roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve salvar role com sucesso")
    @Rollback
    void shouldSaveRoleSuccessfully() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("READ_DATA");
        Role role = IntegrationTestConfiguration.createTestRole("ADMIN", Set.of(privilege));

        // Act
        Role savedRole = roleRepository.save(role);
        entityManager.flush();

        // Assert
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getName()).isEqualTo("ADMIN");
        assertThat(savedRole.getPrivilege()).hasSize(1);
    }

    @Test
    @DisplayName("Deve buscar role por ID")
    @Rollback
    void shouldFindRoleById() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("USER", Set.of());
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<Role> found = roleRepository.findById(savedRole.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("USER");
    }

    @Test
    @DisplayName("Deve buscar role por nome")
    @Rollback
    void shouldFindRoleByName() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("MODERATOR", Set.of());
        roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Act
        Role found = roleRepository.findByName("MODERATOR");

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("MODERATOR");
    }

    @Test
    @DisplayName("Deve verificar existência por nome ignorando case")
    @Rollback
    void shouldCheckExistenceByNameIgnoreCase() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("CaseSensitiveRole", Set.of());
        roleRepository.save(role);
        entityManager.flush();

        // Act & Assert
        assertThat(roleRepository.existsByNameIgnoreCase("casesensitiverole")).isTrue();
        assertThat(roleRepository.existsByNameIgnoreCase("CASESENSITIVEROLE")).isTrue();
        assertThat(roleRepository.existsByNameIgnoreCase("CaseSensitiveRole")).isTrue();
        assertThat(roleRepository.existsByNameIgnoreCase("NonExistentRole")).isFalse();
    }

    @Test
    @DisplayName("Deve verificar existência por nome ignorando case e ID diferente")
    @Rollback
    void shouldCheckExistenceByNameIgnoreCaseAndIdNot() {
        // Arrange
        Role role1 = IntegrationTestConfiguration.createTestRole("ExistingRole", Set.of());
        Role role2 = IntegrationTestConfiguration.createTestRole("AnotherRole", Set.of());
        Role savedRole1 = roleRepository.save(role1);
        Role savedRole2 = roleRepository.save(role2);
        entityManager.flush();

        // Act & Assert
        assertThat(roleRepository.existsByNameIgnoreCaseAndIdNot("existingrole", savedRole2.getId())).isTrue();
        assertThat(roleRepository.existsByNameIgnoreCaseAndIdNot("existingrole", savedRole1.getId())).isFalse();
    }

    @Test
    @DisplayName("Deve buscar todas as roles")
    @Rollback
    void shouldFindAllRoles() {
        // Arrange
        Role role1 = IntegrationTestConfiguration.createTestRole("ROLE1", Set.of());
        Role role2 = IntegrationTestConfiguration.createTestRole("ROLE2", Set.of());
        Role role3 = IntegrationTestConfiguration.createTestRole("ROLE3", Set.of());
        
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<Role> allRoles = roleRepository.findAll();

        // Assert
        assertThat(allRoles).hasSize(3);
        assertThat(allRoles.stream().map(Role::getName))
            .containsExactlyInAnyOrder("ROLE1", "ROLE2", "ROLE3");
    }

    @Test
    @DisplayName("Deve atualizar role existente")
    @Rollback
    void shouldUpdateExistingRole() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("ORIGINAL_ROLE", Set.of());
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Act
        Role roleToUpdate = roleRepository.findById(savedRole.getId()).orElseThrow();
        roleToUpdate.setName("UPDATED_ROLE");
        Role updatedRole = roleRepository.save(roleToUpdate);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Role reloadedRole = roleRepository.findById(updatedRole.getId()).orElseThrow();
        assertThat(reloadedRole.getName()).isEqualTo("UPDATED_ROLE");
    }

    @Test
    @DisplayName("Deve deletar role")
    @Rollback
    void shouldDeleteRole() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("DELETE_ROLE", Set.of());
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Act
        roleRepository.delete(savedRole);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(roleRepository.findById(savedRole.getId())).isEmpty();
        assertThat(roleRepository.findByName("DELETE_ROLE")).isNull();
    }

    @Test
    @DisplayName("Deve manter relacionamentos com privilégios")
    @Rollback
    void shouldMaintainPrivilegeRelationships() {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("READ_DATA");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("WRITE_DATA");
        Role role = IntegrationTestConfiguration.createTestRole("ADMIN_ROLE", Set.of(privilege1, privilege2));
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Act
        Role reloadedRole = roleRepository.findById(savedRole.getId()).orElseThrow();

        // Assert
        assertThat(reloadedRole.getPrivilege()).hasSize(2);
        assertThat(reloadedRole.getPrivilege().stream().map(Privilege::getName))
            .containsExactlyInAnyOrder("READ_DATA", "WRITE_DATA");
    }

    @Test
    @DisplayName("Deve contar total de roles")
    @Rollback
    void shouldCountTotalRoles() {
        // Arrange
        for (int i = 1; i <= 5; i++) {
            Role role = IntegrationTestConfiguration.createTestRole("COUNT_ROLE_" + i, Set.of());
            roleRepository.save(role);
        }
        entityManager.flush();

        // Act
        long count = roleRepository.count();

        // Assert
        assertThat(count).isEqualTo(5);
    }

    @Test
    @DisplayName("Deve verificar existência por ID")
    @Rollback
    void shouldCheckExistenceById() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("EXISTS_ROLE", Set.of());
        Role savedRole = roleRepository.save(role);
        entityManager.flush();

        // Act & Assert
        assertThat(roleRepository.existsById(savedRole.getId())).isTrue();
        assertThat(roleRepository.existsById(UUID.randomUUID())).isFalse();
    }
}