package com.maps.integration.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestUser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de integração para DTORequestUser
 * Testa serialização, validações e conversões
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: DTORequestUser")
class DTORequestUserIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("Deve serializar DTORequestUser válido para JSON")
    @Rollback
    void shouldSerializeValidDTORequestUserToJson() throws Exception {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("USER", Set.of());
        DTORequestUser dto = new DTORequestUser(
            UUID.randomUUID(),
            "testuser",
            "test@example.com",
            Set.of(role)
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert
        assertThat(json).isNotNull();
        assertThat(json).contains("testuser");
        assertThat(json).contains("test@example.com");
        assertThat(json).contains("USER");
    }

    @Test
    @DisplayName("Deve deserializar JSON válido para DTORequestUser")
    @Rollback
    void shouldDeserializeValidJsonToDTORequestUser() throws Exception {
        // Arrange
        String json = """
            {
                "id": "123e4567-e89b-12d3-a456-426614174000",
                "username": "deserializeuser",
                "email": "deserialize@example.com",
                "role": []
            }
            """;

        // Act
        DTORequestUser dto = objectMapper.readValue(json, DTORequestUser.class);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.username()).isEqualTo("deserializeuser");
        assertThat(dto.email()).isEqualTo("deserialize@example.com");
        assertThat(dto.role()).isEmpty();
    }

    @Test
    @DisplayName("Deve validar DTORequestUser com dados válidos")
    @Rollback
    void shouldValidateDTORequestUserWithValidData() {
        // Arrange
        DTORequestUser dto = new DTORequestUser(
            null,
            "validuser",
            "valid@example.com",
            Set.of()
        );

        // Act
        Set<ConstraintViolation<DTORequestUser>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar validação com username nulo")
    @Rollback
    void shouldFailValidationWithNullUsername() {
        // Arrange
        DTORequestUser dto = new DTORequestUser(
            null,
            null,
            "test@example.com",
            Set.of()
        );

        // Act
        Set<ConstraintViolation<DTORequestUser>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("not.null");
    }

    @Test
    @DisplayName("Deve falhar validação com username vazio")
    @Rollback
    void shouldFailValidationWithBlankUsername() {
        // Arrange
        DTORequestUser dto = new DTORequestUser(
            null,
            "",
            "test@example.com",
            Set.of()
        );

        // Act
        Set<ConstraintViolation<DTORequestUser>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSizeGreaterThan(0);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
            .anyMatch(msg -> msg.contains("not.blank"));
    }

    @Test
    @DisplayName("Deve falhar validação com email inválido")
    @Rollback
    void shouldFailValidationWithInvalidEmail() {
        // Arrange
        DTORequestUser dto = new DTORequestUser(
            null,
            "testuser",
            "invalid-email",
            Set.of()
        );

        // Act
        Set<ConstraintViolation<DTORequestUser>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSizeGreaterThan(0);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
            .anyMatch(msg -> msg.contains("Email") || msg.contains("email"));
    }

    @Test
    @DisplayName("Deve falhar validação com email muito longo")
    @Rollback
    void shouldFailValidationWithTooLongEmail() {
        // Arrange
        String longEmail = "a".repeat(50) + "@example.com"; // > 50 caracteres
        DTORequestUser dto = new DTORequestUser(
            null,
            "testuser",
            longEmail,
            Set.of()
        );

        // Act
        Set<ConstraintViolation<DTORequestUser>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSizeGreaterThan(0);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
            .anyMatch(msg -> msg.contains("size") || msg.contains("Size"));
    }

    @Test
    @DisplayName("Deve validar DTORequestUser com roles válidas")
    @Rollback
    void shouldValidateDTORequestUserWithValidRoles() {
        // Arrange
        Role adminRole = IntegrationTestConfiguration.createTestRole("ADMIN", Set.of());
        Role userRole = IntegrationTestConfiguration.createTestRole("USER", Set.of());
        
        DTORequestUser dto = new DTORequestUser(
            UUID.randomUUID(),
            "multiroleuser",
            "multirole@example.com",
            Set.of(adminRole, userRole)
        );

        // Act
        Set<ConstraintViolation<DTORequestUser>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
        assertThat(dto.role()).hasSize(2);
    }

    @Test
    @DisplayName("Deve manter imutabilidade dos dados após criação")
    @Rollback
    void shouldMaintainDataImmutabilityAfterCreation() {
        // Arrange
        UUID id = UUID.randomUUID();
        String username = "immutableuser";
        String email = "immutable@example.com";
        Set<Role> roles = Set.of();
        
        DTORequestUser dto = new DTORequestUser(id, username, email, roles);

        // Act & Assert
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.username()).isEqualTo(username);
        assertThat(dto.email()).isEqualTo(email);
        assertThat(dto.role()).isEqualTo(roles);
    }

    @Test
    @DisplayName("Deve implementar equals e hashCode corretamente")
    @Rollback
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Arrange
        UUID id = UUID.randomUUID();
        DTORequestUser dto1 = new DTORequestUser(id, "sameuser", "same@example.com", Set.of());
        DTORequestUser dto2 = new DTORequestUser(id, "sameuser", "same@example.com", Set.of());
        DTORequestUser dto3 = new DTORequestUser(UUID.randomUUID(), "differentuser", "different@example.com", Set.of());

        // Act & Assert
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    @DisplayName("Deve implementar toString corretamente")
    @Rollback
    void shouldImplementToStringCorrectly() {
        // Arrange
        DTORequestUser dto = new DTORequestUser(
            UUID.randomUUID(),
            "toStringUser",
            "toString@example.com",
            Set.of()
        );

        // Act
        String toString = dto.toString();

        // Assert
        assertThat(toString).contains("DTORequestUser");
        assertThat(toString).contains("toStringUser");
        assertThat(toString).contains("toString@example.com");
    }
}