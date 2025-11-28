package com.maps.integration.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Role;
import com.maps.persistence.payload.response.DTOResponseUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de integração para DTOResponseUser
 * Testa serialização, mapeamento de dados e estrutura JSON
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: DTOResponseUser")
class DTOResponseUserIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve serializar DTOResponseUser para JSON corretamente")
    @Rollback
    void shouldSerializeDTOResponseUserToJsonCorrectly() throws Exception {
        // Arrange
        Role role = IntegrationTestConfiguration.createTestRole("USER", Set.of());
        DTOResponseUser dto = new DTOResponseUser(
            UUID.randomUUID(),
            "testuser",
            "test@example.com",
            0,
            true,
            Set.of(role)
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert
        assertThat(json).isNotNull();
        assertThat(json).contains("testuser");
        assertThat(json).contains("test@example.com");
        assertThat(json).contains("\"attempt\":0");
        assertThat(json).contains("\"active\":true");
        assertThat(json).contains("USER");
    }

    @Test
    @DisplayName("Deve deserializar JSON para DTOResponseUser corretamente")
    @Rollback
    void shouldDeserializeJsonToDTOResponseUserCorrectly() throws Exception {
        // Arrange
        String json = """
            {
                "id": "123e4567-e89b-12d3-a456-426614174000",
                "username": "deserializeuser",
                "email": "deserialize@example.com",
                "attempt": 3,
                "active": false,
                "role": []
            }
            """;

        // Act
        DTOResponseUser dto = objectMapper.readValue(json, DTOResponseUser.class);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getUsername()).isEqualTo("deserializeuser");
        assertThat(dto.getEmail()).isEqualTo("deserialize@example.com");
        assertThat(dto.getAttempt()).isEqualTo(3);
        assertThat(dto.getActive()).isFalse();
        assertThat(dto.getRole()).isEmpty();
    }

    @Test
    @DisplayName("Deve manter todos os campos após serialização/deserialização")
    @Rollback
    void shouldMaintainAllFieldsAfterSerializationDeserialization() throws Exception {
        // Arrange
        Role adminRole = IntegrationTestConfiguration.createTestRole("ADMIN", Set.of());
        Role userRole = IntegrationTestConfiguration.createTestRole("USER", Set.of());
        
        DTOResponseUser original = new DTOResponseUser(
            UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
            "roundtripuser",
            "roundtrip@example.com",
            5,
            true,
            Set.of(adminRole, userRole)
        );

        // Act
        String json = objectMapper.writeValueAsString(original);
        DTOResponseUser deserialized = objectMapper.readValue(json, DTOResponseUser.class);

        // Assert
        assertThat(deserialized.getId()).isEqualTo(original.getId());
        assertThat(deserialized.getUsername()).isEqualTo(original.getUsername());
        assertThat(deserialized.getEmail()).isEqualTo(original.getEmail());
        assertThat(deserialized.getAttempt()).isEqualTo(original.getAttempt());
        assertThat(deserialized.getActive()).isEqualTo(original.getActive());
        assertThat(deserialized.getRole()).hasSize(2);
    }

    @Test
    @DisplayName("Deve tratar valores nulos corretamente")
    @Rollback
    void shouldHandleNullValuesCorrectly() throws Exception {
        // Arrange
        DTOResponseUser dto = new DTOResponseUser(
            null,
            null,
            null,
            null,
            null,
            null
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);
        DTOResponseUser deserialized = objectMapper.readValue(json, DTOResponseUser.class);

        // Assert
        assertThat(deserialized.getId()).isNull();
        assertThat(deserialized.getUsername()).isNull();
        assertThat(deserialized.getEmail()).isNull();
        assertThat(deserialized.getAttempt()).isNull();
        assertThat(deserialized.getActive()).isNull();
        assertThat(deserialized.getRole()).isNull();
    }

    @Test
    @DisplayName("Deve herdar de RepresentationModel corretamente")
    @Rollback
    void shouldInheritFromRepresentationModelCorrectly() {
        // Arrange
        DTOResponseUser dto = new DTOResponseUser(
            UUID.randomUUID(),
            "hateosuser",
            "hateos@example.com",
            0,
            true,
            Set.of()
        );

        // Act & Assert
        assertThat(dto).isInstanceOf(org.springframework.hateoas.RepresentationModel.class);
        assertThat(dto.getLinks()).isNotNull();
        assertThat(dto.getLinks()).isEmpty(); // Inicialmente sem links
    }

    @Test
    @DisplayName("Deve aceitar roles com relacionamentos complexos")
    @Rollback
    void shouldAcceptRolesWithComplexRelationships() throws Exception {
        // Arrange
        var privilege1 = IntegrationTestConfiguration.createTestPrivilege("READ_DATA");
        var privilege2 = IntegrationTestConfiguration.createTestPrivilege("WRITE_DATA");
        Role complexRole = IntegrationTestConfiguration.createTestRole("COMPLEX_ROLE", Set.of(privilege1, privilege2));
        
        DTOResponseUser dto = new DTOResponseUser(
            UUID.randomUUID(),
            "complexuser",
            "complex@example.com",
            0,
            true,
            Set.of(complexRole)
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);
        DTOResponseUser deserialized = objectMapper.readValue(json, DTOResponseUser.class);

        // Assert
        assertThat(deserialized.getRole()).hasSize(1);
        Role deserializedRole = deserialized.getRole().iterator().next();
        assertThat(deserializedRole.getName()).isEqualTo("COMPLEX_ROLE");
        assertThat(deserializedRole.getPrivilege()).hasSize(2);
    }

    @Test
    @DisplayName("Deve validar getter methods")
    @Rollback
    void shouldValidateGetterMethods() {
        // Arrange
        UUID id = UUID.randomUUID();
        String username = "getteruser";
        String email = "getter@example.com";
        Integer attempt = 10;
        Boolean active = false;
        Set<Role> roles = Set.of();
        
        DTOResponseUser dto = new DTOResponseUser(id, username, email, attempt, active, roles);

        // Act & Assert
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getUsername()).isEqualTo(username);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getAttempt()).isEqualTo(attempt);
        assertThat(dto.getActive()).isEqualTo(active);
        assertThat(dto.getRole()).isEqualTo(roles);
    }

    @Test
    @DisplayName("Deve manter integridade de dados com diferentes tipos de attempt")
    @Rollback
    void shouldMaintainDataIntegrityWithDifferentAttemptTypes() throws Exception {
        // Arrange & Act & Assert
        
        // Teste com attempt zero
        DTOResponseUser dto1 = new DTOResponseUser(UUID.randomUUID(), "user1", "user1@example.com", 0, true, Set.of());
        String json1 = objectMapper.writeValueAsString(dto1);
        DTOResponseUser result1 = objectMapper.readValue(json1, DTOResponseUser.class);
        assertThat(result1.getAttempt()).isEqualTo(0);
        
        // Teste com attempt negativo (caso de borda)
        DTOResponseUser dto2 = new DTOResponseUser(UUID.randomUUID(), "user2", "user2@example.com", -1, true, Set.of());
        String json2 = objectMapper.writeValueAsString(dto2);
        DTOResponseUser result2 = objectMapper.readValue(json2, DTOResponseUser.class);
        assertThat(result2.getAttempt()).isEqualTo(-1);
        
        // Teste com attempt alto
        DTOResponseUser dto3 = new DTOResponseUser(UUID.randomUUID(), "user3", "user3@example.com", 999999, true, Set.of());
        String json3 = objectMapper.writeValueAsString(dto3);
        DTOResponseUser result3 = objectMapper.readValue(json3, DTOResponseUser.class);
        assertThat(result3.getAttempt()).isEqualTo(999999);
    }

    @Test
    @DisplayName("Deve validar estrutura JSON final")
    @Rollback
    void shouldValidateFinalJsonStructure() throws Exception {
        // Arrange
        DTOResponseUser dto = new DTOResponseUser(
            UUID.fromString("12345678-1234-1234-1234-123456789abc"),
            "structureuser",
            "structure@example.com",
            2,
            true,
            Set.of()
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert - Verificar estrutura específica do JSON
        assertThat(json).contains("\"id\":\"12345678-1234-1234-1234-123456789abc\"");
        assertThat(json).contains("\"username\":\"structureuser\"");
        assertThat(json).contains("\"email\":\"structure@example.com\"");
        assertThat(json).contains("\"attempt\":2");
        assertThat(json).contains("\"active\":true");
        assertThat(json).contains("\"role\":[]");
    }
}