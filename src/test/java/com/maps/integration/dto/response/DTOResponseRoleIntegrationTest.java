package com.maps.integration.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.integration.base.BaseIntegrationTest;
import com.maps.integration.config.IntegrationTestConfiguration;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.response.DTOResponseRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de integração para DTOResponseRole
 * Testa serialização, mapeamento de dados e estrutura JSON
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: DTOResponseRole")
class DTOResponseRoleIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve serializar DTOResponseRole para JSON corretamente")
    @Rollback
    void shouldSerializeDTOResponseRoleToJsonCorrectly() throws Exception {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("READ_USERS");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("WRITE_USERS");
        Set<Privilege> privileges = new HashSet<>(Set.of(privilege1, privilege2));
        
        DTOResponseRole dto = new DTOResponseRole(
            UUID.randomUUID(),
            "ADMIN",
            privileges
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert
        assertThat(json).isNotNull();
        assertThat(json).contains("ADMIN");
        assertThat(json).contains("READ_USERS");
        assertThat(json).contains("WRITE_USERS");
    }

    @Test
    @DisplayName("Deve deserializar JSON para DTOResponseRole corretamente")
    @Rollback
    void shouldDeserializeJsonToDTOResponseRoleCorrectly() throws Exception {
        // Arrange
        String json = """
            {
                "id": "123e4567-e89b-12d3-a456-426614174000",
                "name": "MODERATOR",
                "privilege": [
                    {
                        "id": "987fcdeb-51a2-43d1-b789-123456789xyz",
                        "name": "MODERATE_CONTENT"
                    }
                ]
            }
            """;

        // Act
        DTOResponseRole dto = objectMapper.readValue(json, DTOResponseRole.class);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo("MODERATOR");
        assertThat(dto.getPrivilege()).hasSize(1);
        assertThat(dto.getPrivilege().iterator().next().getName()).isEqualTo("MODERATE_CONTENT");
    }

    @Test
    @DisplayName("Deve manter todos os campos após serialização/deserialização")
    @Rollback
    void shouldMaintainAllFieldsAfterSerializationDeserialization() throws Exception {
        // Arrange
        Privilege privilege1 = IntegrationTestConfiguration.createTestPrivilege("CREATE_REPORTS");
        Privilege privilege2 = IntegrationTestConfiguration.createTestPrivilege("DELETE_REPORTS");
        Privilege privilege3 = IntegrationTestConfiguration.createTestPrivilege("VIEW_REPORTS");
        Set<Privilege> privileges = new HashSet<>(Set.of(privilege1, privilege2, privilege3));
        
        DTOResponseRole original = new DTOResponseRole(
            UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
            "SUPER_ADMIN",
            privileges
        );

        // Act
        String json = objectMapper.writeValueAsString(original);
        DTOResponseRole deserialized = objectMapper.readValue(json, DTOResponseRole.class);

        // Assert
        assertThat(deserialized.getId()).isEqualTo(original.getId());
        assertThat(deserialized.getName()).isEqualTo(original.getName());
        assertThat(deserialized.getPrivilege()).hasSize(3);
        
        Set<String> deserializedPrivilegeNames = deserialized.getPrivilege().stream()
            .map(Privilege::getName)
            .collect(java.util.stream.Collectors.toSet());
        assertThat(deserializedPrivilegeNames)
            .containsExactlyInAnyOrder("CREATE_REPORTS", "DELETE_REPORTS", "VIEW_REPORTS");
    }

    @Test
    @DisplayName("Deve tratar valores nulos corretamente")
    @Rollback
    void shouldHandleNullValuesCorrectly() throws Exception {
        // Arrange
        DTOResponseRole dto = new DTOResponseRole(null, null, null);

        // Act
        String json = objectMapper.writeValueAsString(dto);
        DTOResponseRole deserialized = objectMapper.readValue(json, DTOResponseRole.class);

        // Assert
        assertThat(deserialized.getId()).isNull();
        assertThat(deserialized.getName()).isNull();
        assertThat(deserialized.getPrivilege()).isNull();
    }

    @Test
    @DisplayName("Deve tratar conjunto vazio de privilégios")
    @Rollback
    void shouldHandleEmptyPrivilegeSet() throws Exception {
        // Arrange
        DTOResponseRole dto = new DTOResponseRole(
            UUID.randomUUID(),
            "EMPTY_ROLE",
            new HashSet<>()
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);
        DTOResponseRole deserialized = objectMapper.readValue(json, DTOResponseRole.class);

        // Assert
        assertThat(deserialized.getName()).isEqualTo("EMPTY_ROLE");
        assertThat(deserialized.getPrivilege()).isEmpty();
    }

    @Test
    @DisplayName("Deve herdar de RepresentationModel corretamente")
    @Rollback
    void shouldInheritFromRepresentationModelCorrectly() {
        // Arrange
        DTOResponseRole dto = new DTOResponseRole(
            UUID.randomUUID(),
            "HATEOS_ROLE",
            new HashSet<>()
        );

        // Act & Assert
        assertThat(dto).isInstanceOf(org.springframework.hateoas.RepresentationModel.class);
        assertThat(dto.getLinks()).isNotNull();
        assertThat(dto.getLinks()).isEmpty(); // Inicialmente sem links
    }

    @Test
    @DisplayName("Deve validar getter methods")
    @Rollback
    void shouldValidateGetterMethods() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "GETTER_ROLE";
        Set<Privilege> privileges = new HashSet<>(Set.of(
            IntegrationTestConfiguration.createTestPrivilege("TEST_PRIVILEGE")
        ));
        
        DTOResponseRole dto = new DTOResponseRole(id, name, privileges);

        // Act & Assert
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getPrivilege()).isEqualTo(privileges);
        assertThat(dto.getPrivilege()).hasSize(1);
    }

    @Test
    @DisplayName("Deve manter integridade referencial dos privilégios")
    @Rollback
    void shouldMaintainPrivilegeReferentialIntegrity() throws Exception {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("REFERENCE_PRIVILEGE");
        privilege.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        
        DTOResponseRole dto = new DTOResponseRole(
            UUID.randomUUID(),
            "REFERENCE_ROLE",
            new HashSet<>(Set.of(privilege))
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);
        DTOResponseRole deserialized = objectMapper.readValue(json, DTOResponseRole.class);

        // Assert
        assertThat(deserialized.getPrivilege()).hasSize(1);
        Privilege deserializedPrivilege = deserialized.getPrivilege().iterator().next();
        assertThat(deserializedPrivilege.getId()).isEqualTo(privilege.getId());
        assertThat(deserializedPrivilege.getName()).isEqualTo(privilege.getName());
    }

    @Test
    @DisplayName("Deve validar inicialização padrão do conjunto de privilégios")
    @Rollback
    void shouldValidateDefaultPrivilegeSetInitialization() {
        // Arrange & Act
        DTOResponseRole dto = new DTOResponseRole(
            UUID.randomUUID(),
            "DEFAULT_ROLE",
            new HashSet<>() // Explicitamente vazio
        );

        // Assert
        assertThat(dto.getPrivilege()).isNotNull();
        assertThat(dto.getPrivilege()).isEmpty();
        assertThat(dto.getPrivilege()).isInstanceOf(HashSet.class);
    }

    @Test
    @DisplayName("Deve validar estrutura JSON final")
    @Rollback
    void shouldValidateFinalJsonStructure() throws Exception {
        // Arrange
        Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("JSON_PRIVILEGE");
        privilege.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        
        DTOResponseRole dto = new DTOResponseRole(
            UUID.fromString("12345678-1234-1234-1234-123456789abc"),
            "JSON_ROLE",
            new HashSet<>(Set.of(privilege))
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert - Verificar estrutura específica do JSON
        assertThat(json).contains("\"id\":\"12345678-1234-1234-1234-123456789abc\"");
        assertThat(json).contains("\"name\":\"JSON_ROLE\"");
        assertThat(json).contains("\"privilege\":");
        assertThat(json).contains("JSON_PRIVILEGE");
        assertThat(json).contains("22222222-2222-2222-2222-222222222222");
    }

    @Test
    @DisplayName("Deve manter consistência com múltiplos privilégios")
    @Rollback
    void shouldMaintainConsistencyWithMultiplePrivileges() throws Exception {
        // Arrange
        Set<Privilege> privileges = new HashSet<>();
        for (int i = 1; i <= 5; i++) {
            Privilege privilege = IntegrationTestConfiguration.createTestPrivilege("PRIVILEGE_" + i);
            privilege.setId(UUID.fromString(String.format("%08d-0000-0000-0000-000000000000", i)));
            privileges.add(privilege);
        }
        
        DTOResponseRole dto = new DTOResponseRole(
            UUID.randomUUID(),
            "MULTI_PRIVILEGE_ROLE",
            privileges
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);
        DTOResponseRole deserialized = objectMapper.readValue(json, DTOResponseRole.class);

        // Assert
        assertThat(deserialized.getPrivilege()).hasSize(5);
        Set<String> privilegeNames = deserialized.getPrivilege().stream()
            .map(Privilege::getName)
            .collect(java.util.stream.Collectors.toSet());
        
        for (int i = 1; i <= 5; i++) {
            assertThat(privilegeNames).contains("PRIVILEGE_" + i);
        }
    }
}