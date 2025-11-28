package com.maps.integration.model;

import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Token;
import com.maps.persistence.repository.RepositoryToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de integração para modelo Token
 * Testa persistência, relacionamentos e validações com banco H2
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: Modelo Token")
class TokenModelIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RepositoryToken tokenRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve persistir token com dados válidos")
    @Rollback
    void shouldPersistValidToken() {
        // Arrange
        Token token = IntegrationTestConfiguration.createTestToken("550e8400-e29b-41d4-a716-446655440000");

        // Act
        Token savedToken = tokenRepository.save(token);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(savedToken.getId()).isNotNull();
        assertThat(savedToken.getRefreshToken()).isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        assertThat(savedToken.isActive()).isTrue();
        assertThat(savedToken.getCreatedAt()).isNotNull();
        assertThat(savedToken.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar token existente")
    @Rollback
    void shouldUpdateExistingToken() {
        // Arrange
        Token token = IntegrationTestConfiguration.createTestToken("550e8400-e29b-41d4-a716-446655440001");
        Token savedToken = tokenRepository.save(token);
        entityManager.flush();
        entityManager.clear();

        // Act
        Token tokenToUpdate = tokenRepository.findById(savedToken.getId()).orElseThrow();
        tokenToUpdate.setActive(false);
        Token updatedToken = tokenRepository.save(tokenToUpdate);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Token reloadedToken = tokenRepository.findById(updatedToken.getId()).orElseThrow();
        assertThat(reloadedToken.isActive()).isFalse();
        assertThat(reloadedToken.getRefreshToken()).isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
    }

    @Test
    @DisplayName("Deve deletar token")
    @Rollback
    void shouldDeleteToken() {
        // Arrange
        Token token = IntegrationTestConfiguration.createTestToken("550e8400-e29b-41d4-a716-446655440002");
        Token savedToken = tokenRepository.save(token);
        entityManager.flush();
        entityManager.clear();

        // Act
        tokenRepository.delete(savedToken);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertThat(tokenRepository.findById(savedToken.getId())).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar auditoria automática")
    @Rollback
    void shouldHaveAutomaticAuditing() {
        // Arrange & Act
        Token token = IntegrationTestConfiguration.createTestToken("550e8400-e29b-41d4-a716-446655440003");
        Token savedToken = tokenRepository.save(token);
        entityManager.flush();

        // Assert
        assertThat(savedToken.getCreatedAt()).isNotNull();
        assertThat(savedToken.getUpdatedAt()).isNotNull();
        // Em testes sem usuário autenticado, campos de auditoria de usuário são null
        assertThat(savedToken.getCreatedBy()).isNull();
        assertThat(savedToken.getModifiedBy()).isNull();
    }

    @Test
    @DisplayName("Deve buscar token por ID")
    @Rollback
    void shouldFindTokenById() {
        // Arrange
        Token token = IntegrationTestConfiguration.createTestToken("550e8400-e29b-41d4-a716-446655440004");
        Token savedToken = tokenRepository.save(token);
        entityManager.flush();
        entityManager.clear();

        // Act
        Token found = tokenRepository.findById(savedToken.getId()).orElse(null);

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getRefreshToken()).isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"));
        assertThat(found.isActive()).isTrue();
    }
}