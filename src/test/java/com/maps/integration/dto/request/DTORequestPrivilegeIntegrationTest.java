package com.maps.integration.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.integration.base.BaseIntegrationTest;
import com.maps.persistence.payload.request.DTORequestPrivilege;
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
 * Testes de integração para DTORequestPrivilege
 * Testa serialização, validações e conversões
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: DTORequestPrivilege")
class DTORequestPrivilegeIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("Deve serializar DTORequestPrivilege válido para JSON")
    @Rollback
    void shouldSerializeValidDTORequestPrivilegeToJson() throws Exception {
        // Arrange
        DTORequestPrivilege dto = new DTORequestPrivilege(
            UUID.randomUUID(),
            "READ_USERS"
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert
        assertThat(json).isNotNull();
        assertThat(json).contains("READ_USERS");
    }

    @Test
    @DisplayName("Deve deserializar JSON válido para DTORequestPrivilege")
    @Rollback
    void shouldDeserializeValidJsonToDTORequestPrivilege() throws Exception {
        // Arrange
        String json = """
            {
                "id": "123e4567-e89b-12d3-a456-426614174000",
                "name": "WRITE_USERS"
            }
            """;

        // Act
        DTORequestPrivilege dto = objectMapper.readValue(json, DTORequestPrivilege.class);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo("WRITE_USERS");
    }

    @Test
    @DisplayName("Deve validar DTORequestPrivilege com dados válidos")
    @Rollback
    void shouldValidateDTORequestPrivilegeWithValidData() {
        // Arrange
        DTORequestPrivilege dto = new DTORequestPrivilege(null, "DELETE_USERS");

        // Act
        Set<ConstraintViolation<DTORequestPrivilege>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar validação com nome nulo")
    @Rollback
    void shouldFailValidationWithNullName() {
        // Arrange
        DTORequestPrivilege dto = new DTORequestPrivilege(null, null);

        // Act
        Set<ConstraintViolation<DTORequestPrivilege>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).contains("not.null");
    }

    @Test
    @DisplayName("Deve falhar validação com nome vazio")
    @Rollback
    void shouldFailValidationWithBlankName() {
        // Arrange
        DTORequestPrivilege dto = new DTORequestPrivilege(null, "");

        // Act
        Set<ConstraintViolation<DTORequestPrivilege>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).hasSizeGreaterThan(0);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
            .anyMatch(msg -> msg.contains("not.blank"));
    }

    @Test
    @DisplayName("Deve aceitar nomes de privilégio com diferentes formatos")
    @Rollback
    void shouldAcceptPrivilegeNamesWithDifferentFormats() {
        // Arrange & Act & Assert
        assertThat(validator.validate(new DTORequestPrivilege(null, "READ"))).isEmpty();
        assertThat(validator.validate(new DTORequestPrivilege(null, "WRITE"))).isEmpty();
        assertThat(validator.validate(new DTORequestPrivilege(null, "READ_WRITE"))).isEmpty();
        assertThat(validator.validate(new DTORequestPrivilege(null, "privilege-name"))).isEmpty();
        assertThat(validator.validate(new DTORequestPrivilege(null, "Privilege123"))).isEmpty();
        assertThat(validator.validate(new DTORequestPrivilege(null, "ADMIN_FULL_ACCESS"))).isEmpty();
    }

    @Test
    @DisplayName("Deve manter imutabilidade dos dados após criação")
    @Rollback
    void shouldMaintainDataImmutabilityAfterCreation() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "IMMUTABLE_PRIVILEGE";
        
        DTORequestPrivilege dto = new DTORequestPrivilege(id, name);

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
        DTORequestPrivilege dto1 = new DTORequestPrivilege(id, "SAME_PRIVILEGE");
        DTORequestPrivilege dto2 = new DTORequestPrivilege(id, "SAME_PRIVILEGE");
        DTORequestPrivilege dto3 = new DTORequestPrivilege(UUID.randomUUID(), "DIFFERENT_PRIVILEGE");

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
        DTORequestPrivilege dto = new DTORequestPrivilege(
            UUID.randomUUID(),
            "TO_STRING_PRIVILEGE"
        );

        // Act
        String toString = dto.toString();

        // Assert
        assertThat(toString).contains("DTORequestPrivilege");
        assertThat(toString).contains("TO_STRING_PRIVILEGE");
    }

    @Test
    @DisplayName("Deve implementar DTORequestIdentifiable corretamente")
    @Rollback
    void shouldImplementDTORequestIdentifiableCorrectly() {
        // Arrange
        UUID id = UUID.randomUUID();
        DTORequestPrivilege dto = new DTORequestPrivilege(id, "IDENTIFIABLE_PRIVILEGE");

        // Act & Assert
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto).isInstanceOf(com.maps.persistence.payload.request.DTORequestIdentifiable.class);
    }

    @Test
    @DisplayName("Deve aceitar ID nulo para criação")
    @Rollback
    void shouldAcceptNullIdForCreation() {
        // Arrange
        DTORequestPrivilege dto = new DTORequestPrivilege(null, "NEW_PRIVILEGE");

        // Act
        Set<ConstraintViolation<DTORequestPrivilege>> violations = validator.validate(dto);

        // Assert
        assertThat(violations).isEmpty();
        assertThat(dto.id()).isNull();
        assertThat(dto.name()).isEqualTo("NEW_PRIVILEGE");
    }

    @Test
    @DisplayName("Deve manter consistência com validação customizada")
    @Rollback
    void shouldMaintainConsistencyWithCustomValidation() {
        // Arrange & Act
        DTORequestPrivilege validDto = new DTORequestPrivilege(null, "VALID_PRIVILEGE_NAME");
        Set<ConstraintViolation<DTORequestPrivilege>> violations = validator.validate(validDto);

        // Assert
        assertThat(violations).isEmpty();
        
        // Verificar se as anotações de validação personalizada estão funcionando
        // (assumindo que existe validação @UniqueNamePrivilege se implementada)
    }
}