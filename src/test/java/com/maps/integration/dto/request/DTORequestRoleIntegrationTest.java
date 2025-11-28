package com.maps.integration.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.integration.base.BaseIntegrationTest;
import com.maps.persistence.payload.request.DTORequestRole;
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
 * Testes de integração para DTORequestRole
 * Testa serialização, validações e conversões
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: DTORequestRole")
class DTORequestRoleIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("Deve serializar DTORequestRole válido para JSON")
    @Rollback
    void shouldSerializeValidDTORequestRoleToJson() throws Exception {
        // Arrange
        DTORequestRole dto = new DTORequestRole(
            UUID.randomUUID(),
            "ADMIN"
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert
        assertThat(json).isNotNull();
        assertThat(json).contains("ADMIN");
    }

    @Test
    @DisplayName("Deve deserializar JSON válido para DTORequestRole")
    @Rollback
    void shouldDeserializeValidJsonToDTORequestRole() throws Exception {
        // Arrange
        String json = """
            {
                "id": "123e4567-e89b-12d3-a456-426614174000",
                "name": "MODERATOR"
            }
            """;

        // Act
        DTORequestRole dto = objectMapper.readValue(json, DTORequestRole.class);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo("MODERATOR");
    }

    @Test
    @DisplayName("Deve validar DTORequestRole com dados válidos")
    @Rollback
    void shouldValidateDTORequestRoleWithValidData() {
        // Arrange
        DTORequestRole dto = new DTORequestRole(null, "USER");

        // Act
        Set<ConstraintViolation<DTORequestRole>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar validação com nome nulo")
    @Rollback
    void shouldFailValidationWithNullName() {
        // Arrange
        DTORequestRole dto = new DTORequestRole(null, null);

        // Act
        Set<ConstraintViolation<DTORequestRole>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("not.null");
    }

    @Test
    @DisplayName("Deve falhar validação com nome vazio")
    @Rollback
    void shouldFailValidationWithBlankName() {
        // Arrange
        DTORequestRole dto = new DTORequestRole(null, "");

        // Act
        Set<ConstraintViolation<DTORequestRole>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSizeGreaterThan(0);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
            .anyMatch(msg -> msg.contains("not.blank"));
    }

    @Test
    @DisplayName("Deve falhar validação com nome apenas espaços")
    @Rollback
    void shouldFailValidationWithWhitespaceName() {
        // Arrange
        DTORequestRole dto = new DTORequestRole(null, "   ");

        // Act
        Set<ConstraintViolation<DTORequestRole>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSizeGreaterThan(0);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
            .anyMatch(msg -> msg.contains("not.blank"));
    }

    @Test
    @DisplayName("Deve aceitar nomes de role com diferentes formatos")
    @Rollback
    void shouldAcceptRoleNamesWithDifferentFormats() {
        // Arrange & Act & Assert
        assertThat(validator.validate(new DTORequestRole(null, "ADMIN"))).isEmpty();
        assertThat(validator.validate(new DTORequestRole(null, "User"))).isEmpty();
        assertThat(validator.validate(new DTORequestRole(null, "SUPER_ADMIN"))).isEmpty();
        assertThat(validator.validate(new DTORequestRole(null, "role-name"))).isEmpty();
        assertThat(validator.validate(new DTORequestRole(null, "Role123"))).isEmpty();
    }

    @Test
    @DisplayName("Deve manter imutabilidade dos dados após criação")
    @Rollback
    void shouldMaintainDataImmutabilityAfterCreation() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "IMMUTABLE_ROLE";
        
        DTORequestRole dto = new DTORequestRole(id, name);

        // Act & Assert
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.name()).isEqualTo(name);
    }

    @Test
    @DisplayName("Deve implementar equals e hashCode corretamente")
    @Rollback
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Arrange
        UUID id = UUID.randomUUID();
        DTORequestRole dto1 = new DTORequestRole(id, "SAME_ROLE");
        DTORequestRole dto2 = new DTORequestRole(id, "SAME_ROLE");
        DTORequestRole dto3 = new DTORequestRole(UUID.randomUUID(), "DIFFERENT_ROLE");

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
        DTORequestRole dto = new DTORequestRole(
            UUID.randomUUID(),
            "TO_STRING_ROLE"
        );

        // Act
        String toString = dto.toString();

        // Assert
        assertThat(toString).contains("DTORequestRole");
        assertThat(toString).contains("TO_STRING_ROLE");
    }

    @Test
    @DisplayName("Deve implementar DTORequestIdentifiable corretamente")
    @Rollback
    void shouldImplementDTORequestIdentifiableCorrectly() {
        // Arrange
        UUID id = UUID.randomUUID();
        DTORequestRole dto = new DTORequestRole(id, "IDENTIFIABLE_ROLE");

        // Act & Assert
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto).isInstanceOf(com.maps.persistence.payload.request.DTORequestIdentifiable.class);
    }

    @Test
    @DisplayName("Deve aceitar ID nulo para criação")
    @Rollback
    void shouldAcceptNullIdForCreation() {
        // Arrange
        DTORequestRole dto = new DTORequestRole(null, "NEW_ROLE");

        // Act
        Set<ConstraintViolation<DTORequestRole>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
        assertThat(dto.id()).isNull();
        assertThat(dto.name()).isEqualTo("NEW_ROLE");
    }
}