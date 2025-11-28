package com.maps.integration.model;

import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.repository.RepositoryPrivilege;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.hibernate.exception.ConstraintViolationException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes de integração para modelo Privilege
 * Testa persistência, relacionamentos e validações com banco H2
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: Modelo Privilege")
class PrivilegeModelIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RepositoryPrivilege privilegeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve persistir privilégio com dados válidos")
    @Rollback
    void shouldPersistValidPrivilege() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("READ_REPORTS");

        // Act
        Privilege savedPrivilege = privilegeRepository.save(privilege);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(savedPrivilege.getId()).isNotNull();
        assertThat(savedPrivilege.getName()).isEqualTo("READ_REPORTS");
        assertThat(savedPrivilege.getCreatedAt()).isNotNull();
        assertThat(savedPrivilege.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar privilégio por nome")
    @Rollback
    void shouldFindPrivilegeByName() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("WRITE_REPORTS");
        privilegeRepository.save(privilege);
        entityManager.flush();
        entityManager.clear();

        // Act
        Set<Privilege> foundPrivileges = privilegeRepository.findByName("WRITE_REPORTS");

        // Assert
        assertThat(foundPrivileges).hasSize(1);
        Privilege found = foundPrivileges.iterator().next();
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("WRITE_REPORTS");
    }

    @Test
    @DisplayName("Deve falhar ao salvar privilégio com nome nulo")
    @Rollback
    void shouldFailWithNullName() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege(null);

        // Act & Assert
        assertThatThrownBy(() -> {
            privilegeRepository.save(privilege);
            entityManager.flush();
        }).isInstanceOf(jakarta.validation.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve falhar ao salvar privilégio com nome vazio")
    @Rollback
    void shouldFailWithBlankName() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("");

        // Act & Assert
        assertThatThrownBy(() -> {
            privilegeRepository.save(privilege);
            entityManager.flush();
        }).isInstanceOf(jakarta.validation.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve falhar ao salvar privilégio com nome duplicado")
    @Rollback
    void shouldFailWithDuplicateName() {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("DUPLICATE_PRIVILEGE");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("DUPLICATE_PRIVILEGE");

        // Act
        privilegeRepository.save(privilege1);
        entityManager.flush();

        // Assert
        assertThatThrownBy(() -> {
            privilegeRepository.save(privilege2);
            entityManager.flush();
        }).isInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Deve verificar existência por nome ignorando case")
    @Rollback
    void shouldCheckExistenceIgnoringCase() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("TestPrivilege");
        privilegeRepository.save(privilege);
        entityManager.flush();

        // Act & Assert
        assertThat(privilegeRepository.existsByNameIgnoreCase("testprivilege")).isTrue();
        assertThat(privilegeRepository.existsByNameIgnoreCase("TESTPRIVILEGE")).isTrue();
        assertThat(privilegeRepository.existsByNameIgnoreCase("TestPrivilege")).isTrue();
        assertThat(privilegeRepository.existsByNameIgnoreCase("NonExistentPrivilege")).isFalse();
    }

    @Test
    @DisplayName("Deve verificar existência por nome ignorando case e ID diferente")
    @Rollback
    void shouldCheckExistenceIgnoringCaseAndDifferentId() {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("ExistingPrivilege");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("AnotherPrivilege");
        Privilege savedPrivilege1 = privilegeRepository.save(privilege1);
        Privilege savedPrivilege2 = privilegeRepository.save(privilege2);
        entityManager.flush();

        // Act & Assert
        assertThat(privilegeRepository.existsByNameIgnoreCaseAndIdNot("existingprivilege", savedPrivilege2.getId())).isTrue();
        assertThat(privilegeRepository.existsByNameIgnoreCaseAndIdNot("existingprivilege", savedPrivilege1.getId())).isFalse();
    }

    @Test
    @DisplayName("Deve atualizar privilégio existente")
    @Rollback
    void shouldUpdateExistingPrivilege() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("UPDATE_PRIVILEGE");
        Privilege savedPrivilege = privilegeRepository.save(privilege);
        entityManager.flush();
        entityManager.clear();

        // Act
        Privilege privilegeToUpdate = privilegeRepository.findById(savedPrivilege.getId()).orElseThrow();
        privilegeToUpdate.setName("UPDATED_PRIVILEGE");
        Privilege updatedPrivilege = privilegeRepository.save(privilegeToUpdate);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Privilege reloadedPrivilege = privilegeRepository.findById(updatedPrivilege.getId()).orElseThrow();
        assertThat(reloadedPrivilege.getName()).isEqualTo("UPDATED_PRIVILEGE");
    }

    @Test
    @DisplayName("Deve deletar privilégio")
    @Rollback
    void shouldDeletePrivilege() {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("DELETE_TEST_PRIVILEGE");
        Privilege savedPrivilege = privilegeRepository.save(privilege);
        entityManager.flush();
        entityManager.clear();

        // Act
        privilegeRepository.delete(savedPrivilege);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(privilegeRepository.findById(savedPrivilege.getId())).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar auditoria automática")
    @Rollback
    void shouldHaveAutomaticAuditing() {
        // Arrange & Act
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("AUDIT_PRIVILEGE");
        Privilege savedPrivilege = privilegeRepository.save(privilege);
        entityManager.flush();

        // Assert
        assertThat(savedPrivilege.getCreatedAt()).isNotNull();
        assertThat(savedPrivilege.getUpdatedAt()).isNotNull();
        // Em testes sem usuário autenticado, campos de auditoria de usuário são null
        assertThat(savedPrivilege.getCreatedBy()).isNull();
        assertThat(savedPrivilege.getModifiedBy()).isNull();
    }
}
