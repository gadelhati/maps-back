package com.maps.integration.model;

import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.repository.RepositoryPrivilege;
import com.maps.persistence.repository.RepositoryRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.Session;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes de integração para modelo Role
 * Testa persistência, relacionamentos e validações com banco H2
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: Modelo Role")
class RoleModelIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RepositoryRole roleRepository;

    @Autowired
    private RepositoryPrivilege privilegeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve persistir role com dados válidos")
    @Rollback
    void shouldPersistValidRole() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("READ_DATA");
        Role role = IntegrationTestConfiguration.createTestRole("ADMIN", Set.of(privilege));

        // Act
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(savedRole.getId()).isNotNull();
        assertThat(savedRole.getName()).isEqualTo("ADMIN");
        assertThat(savedRole.getPrivilege()).hasSize(1);
        assertThat(savedRole.getCreatedAt()).isNotNull();
        assertThat(savedRole.getUpdatedAt()).isNotNull();
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
    @DisplayName("Deve falhar ao salvar role com nome nulo")
    @Rollback
    void shouldFailWithNullName() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole(null, Set.of());

        // Act & Assert
        assertThatThrownBy(() -> {
            roleRepository.save(role);
            entityManager.flush();
        }).isInstanceOf(jakarta.validation.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve falhar ao salvar role com nome vazio")
    @Rollback
    void shouldFailWithBlankName() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("", Set.of());

        // Act & Assert
        assertThatThrownBy(() -> {
            roleRepository.save(role);
            entityManager.flush();
        }).isInstanceOf(jakarta.validation.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve falhar ao salvar role com nome duplicado")
    @Rollback
    void shouldFailWithDuplicateName() {
        // Arrange
        Role role1 = IntegrationTestConfiguration.createTestRole("DUPLICATE_ROLE", Set.of());
        Role role2 = IntegrationTestConfiguration.createTestRole("DUPLICATE_ROLE", Set.of());

        // Act
        roleRepository.save(role1);
        entityManager.flush();

        // Assert
        assertThatThrownBy(() -> {
            roleRepository.save(role2);
            entityManager.flush();
        }).isInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve manter relacionamentos com privilégios após persistência")
    @Rollback
    void shouldMaintainPrivilegeRelationships() {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("READ_USERS");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("WRITE_USERS");
        Privilege privilege3 = IntegrationTestConfiguration.createTestPrivilege("DELETE_USERS");
        
        // Persistir privilégios primeiro
        privilege1 = privilegeRepository.save(privilege1);
        privilege2 = privilegeRepository.save(privilege2);
        privilege3 = privilegeRepository.save(privilege3);
        
        // Criar coleção mutável
        Set<Privilege> privileges = new HashSet<>();
        privileges.add(privilege1);
        privileges.add(privilege2);
        privileges.add(privilege3);
        
        Role role = IntegrationTestConfiguration.createTestRole("SUPER_ADMIN", privileges);

        // Act
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Role reloadedRole = roleRepository.findById(savedRole.getId()).orElseThrow();
        assertThat(reloadedRole.getPrivilege()).hasSize(3);
        assertThat(reloadedRole.getPrivilege().stream().map(Privilege::getName))
            .containsExactlyInAnyOrder("READ_USERS", "WRITE_USERS", "DELETE_USERS");
    }

    @Test
    @DisplayName("Deve verificar existência por nome ignorando case")
    @Rollback
    void shouldCheckExistenceIgnoringCase() {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("TestRole", Set.of());
        roleRepository.save(role);
        entityManager.flush();

        // Act & Assert
        assertThat(roleRepository.existsByNameIgnoreCase("testrole")).isTrue();
        assertThat(roleRepository.existsByNameIgnoreCase("TESTROLE")).isTrue();
        assertThat(roleRepository.existsByNameIgnoreCase("TestRole")).isTrue();
        assertThat(roleRepository.existsByNameIgnoreCase("NonExistentRole")).isFalse();
    }

    @Test
    @DisplayName("Deve verificar existência por nome ignorando case e ID diferente")
    @Rollback
    void shouldCheckExistenceIgnoringCaseAndDifferentId() {
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
    @DisplayName("Deve atualizar role existente")
    @Rollback
    void shouldUpdateExistingRole() {
        // Arrange
        Privilege originalPrivilege = IntegrationTestConfiguration.createTestPrivilege("ORIGINAL_PRIVILEGE");
        Set<Privilege> initialPrivileges = new HashSet<>();
        initialPrivileges.add(originalPrivilege);
        Role role = IntegrationTestConfiguration.createTestRole("UPDATE_ROLE", initialPrivileges);
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Act
        Role roleToUpdate = roleRepository.findById(savedRole.getId()).orElseThrow();
        Privilege newPrivilege = IntegrationTestConfiguration.createTestPrivilege("NEW_PRIVILEGE");
        Set<Privilege> updatedPrivileges = new HashSet<>();
        updatedPrivileges.add(originalPrivilege);
        updatedPrivileges.add(newPrivilege);
        roleToUpdate.setPrivilege(updatedPrivileges);
        Role updatedRole = roleRepository.save(roleToUpdate);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Role reloadedRole = roleRepository.findById(updatedRole.getId()).orElseThrow();
        assertThat(reloadedRole.getPrivilege()).hasSize(2);
        assertThat(reloadedRole.getName()).isEqualTo("UPDATE_ROLE"); // Deve manter nome original
    }

    @Test
    @DisplayName("Deve deletar role e manter integridade referencial")
    @Rollback
    void shouldDeleteRoleMaintainingReferentialIntegrity() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("DELETE_TEST_PRIVILEGE");
        privilegeRepository.save(privilege);
        
        Set<Privilege> rolePrivileges = new HashSet<>();
        rolePrivileges.add(privilege);
        Role role = IntegrationTestConfiguration.createTestRole("DELETE_TEST_ROLE", rolePrivileges);
        Role savedRole = roleRepository.save(role);
        entityManager.flush();
        entityManager.clear();

        // Act
        roleRepository.delete(savedRole);
        entityManager.flush();
        entityManager.clear();

        // Assert - Com soft delete, a role não aparece em consultas normais
        assertThat(roleRepository.findById(savedRole.getId())).isEmpty();
        
        // Verificar que o comando SQL foi executado corretamente (não há erro de execução)
        entityManager.flush(); // Força qualquer comando pendente
        
        // NOTA: Com CascadeType.ALL na relação ManyToMany, os privileges associados também são deletados
        // Este é o comportamento atual da aplicação configurado na entidade Role
        // Em um cenário real, poder-se-ia considerar usar apenas PERSIST, MERGE, REFRESH ao invés de ALL
        assertThat(privilegeRepository.findById(privilege.getId())).isEmpty();
    }
}
