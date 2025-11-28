package com.maps.integration.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.integration.base.BaseIntegrationTest;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de integração para DTOResponsePrivilege
 * Testa serialização, mapeamento de dados e estrutura JSON
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@DisplayName("Testes de Integração: DTOResponsePrivilege")
class DTOResponsePrivilegeIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve serializar DTOResponsePrivilege para JSON corretamente")
    @Rollback
    void shouldSerializeDTOResponsePrivilegeToJsonCorrectly() throws Exception {
        // Arrange
        DTOResponsePrivilege dto = new DTOResponsePrivilege(
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
    @DisplayName("Deve deserializar JSON para DTOResponsePrivilege corretamente")
    @Rollback
    void shouldDeserializeJsonToDTOResponsePrivilegeCorrectly() throws Exception {
        // Arrange
        String json = """
            {
                "id": "123e4567-e89b-12d3-a456-426614174000",
                "name": "WRITE_USERS"
            }
            """;

        // Act
        DTOResponsePrivilege dto = objectMapper.readValue(json, DTOResponsePrivilege.class);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo("WRITE_USERS");
        assertThat(dto.getId()).isEqualTo(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    @DisplayName("Deve manter todos os campos após serialização/deserialização")
    @Rollback
    void shouldMaintainAllFieldsAfterSerializationDeserialization() throws Exception {
        // Arrange
        DTOResponsePrivilege original = new DTOResponsePrivilege(
            UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
            "DELETE_REPORTS"
        );

        // Act
        String json = objectMapper.writeValueAsString(original);
        DTOResponsePrivilege deserialized = objectMapper.readValue(json, DTOResponsePrivilege.class);

        // Assert
        assertThat(deserialized.getId()).isEqualTo(original.getId());
        assertThat(deserialized.getName()).isEqualTo(original.getName());
    }

    @Test
    @DisplayName("Deve tratar valores nulos corretamente")
    @Rollback
    void shouldHandleNullValuesCorrectly() throws Exception {
        // Arrange
        DTOResponsePrivilege dto = new DTOResponsePrivilege(null, null);

        // Act
        String json = objectMapper.writeValueAsString(dto);
        DTOResponsePrivilege deserialized = objectMapper.readValue(json, DTOResponsePrivilege.class);

        // Assert
        assertThat(deserialized.getId()).isNull();
        assertThat(deserialized.getName()).isNull();
    }

    @Test
    @DisplayName("Deve herdar de RepresentationModel corretamente")
    @Rollback
    void shouldInheritFromRepresentationModelCorrectly() {
        // Arrange
        DTOResponsePrivilege dto = new DTOResponsePrivilege(
            UUID.randomUUID(),
            "HATEOS_PRIVILEGE"
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
        String name = "GETTER_PRIVILEGE";
        
        DTOResponsePrivilege dto = new DTOResponsePrivilege(id, name);

        // Act & Assert
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Deve aceitar diferentes formatos de nomes de privilégio")
    @Rollback
    void shouldAcceptDifferentPrivilegeNameFormats() throws Exception {
        // Arrange & Act & Assert
        
        // Nome em maiúsculas
        DTOResponsePrivilege dto1 = new DTOResponsePrivilege(UUID.randomUUID(), "READ_WRITE_DELETE");
        String json1 = objectMapper.writeValueAsString(dto1);
        DTOResponsePrivilege result1 = objectMapper.readValue(json1, DTOResponsePrivilege.class);
        assertThat(result1.getName()).isEqualTo("READ_WRITE_DELETE");
        
        // Nome em minúsculas
        DTOResponsePrivilege dto2 = new DTOResponsePrivilege(UUID.randomUUID(), "view_content");
        String json2 = objectMapper.writeValueAsString(dto2);
        DTOResponsePrivilege result2 = objectMapper.readValue(json2, DTOResponsePrivilege.class);
        assertThat(result2.getName()).isEqualTo("view_content");
        
        // Nome com números
        DTOResponsePrivilege dto3 = new DTOResponsePrivilege(UUID.randomUUID(), "LEVEL_1_ACCESS");
        String json3 = objectMapper.writeValueAsString(dto3);
        DTOResponsePrivilege result3 = objectMapper.readValue(json3, DTOResponsePrivilege.class);
        assertThat(result3.getName()).isEqualTo("LEVEL_1_ACCESS");
        
        // Nome com hífen
        DTOResponsePrivilege dto4 = new DTOResponsePrivilege(UUID.randomUUID(), "special-privilege");
        String json4 = objectMapper.writeValueAsString(dto4);
        DTOResponsePrivilege result4 = objectMapper.readValue(json4, DTOResponsePrivilege.class);
        assertThat(result4.getName()).isEqualTo("special-privilege");
    }

    @Test
    @DisplayName("Deve validar estrutura JSON final")
    @Rollback
    void shouldValidateFinalJsonStructure() throws Exception {
        // Arrange
        DTOResponsePrivilege dto = new DTOResponsePrivilege(
            UUID.fromString("12345678-1234-1234-1234-123456789abc"),
            "JSON_STRUCTURE_PRIVILEGE"
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert - Verificar estrutura específica do JSON
        assertThat(json).contains("\"id\":\"12345678-1234-1234-1234-123456789abc\"");
        assertThat(json).contains("\"name\":\"JSON_STRUCTURE_PRIVILEGE\"");
    }

    @Test
    @DisplayName("Deve manter consistência com IDs UUID válidos")
    @Rollback
    void shouldMaintainConsistencyWithValidUUIDs() throws Exception {
        // Arrange
        UUID[] testUUIDs = {
            UUID.fromString("00000000-0000-0000-0000-000000000000"),
            UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"),
            UUID.randomUUID(),
            UUID.randomUUID()
        };
        
        for (int i = 0; i < testUUIDs.length; i++) {
            // Act
            DTOResponsePrivilege dto = new DTOResponsePrivilege(testUUIDs[i], "PRIVILEGE_" + i);
            String json = objectMapper.writeValueAsString(dto);
            DTOResponsePrivilege deserialized = objectMapper.readValue(json, DTOResponsePrivilege.class);
            
            // Assert
            assertThat(deserialized.getId()).isEqualTo(testUUIDs[i]);
            assertThat(deserialized.getName()).isEqualTo("PRIVILEGE_" + i);
        }
    }

    @Test
    @DisplayName("Deve validar campos imutáveis após criação")
    @Rollback
    void shouldValidateImmutableFieldsAfterCreation() {
        // Arrange
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        String name = "IMMUTABLE_PRIVILEGE";
        
        DTOResponsePrivilege dto = new DTOResponsePrivilege(id, name);

        // Act & Assert - Verificar que os campos não podem ser alterados
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        
        // Tentativa de verificar imutabilidade (getter sempre retorna o mesmo valor)
        UUID retrievedId = dto.getId();
        String retrievedName = dto.getName();
        
        assertThat(dto.getId()).isSameAs(retrievedId);
        assertThat(dto.getName()).isSameAs(retrievedName);
    }

    @Test
    @DisplayName("Deve validar comportamento com caracteres especiais no nome")
    @Rollback
    void shouldValidateBehaviorWithSpecialCharactersInName() throws Exception {
        // Arrange
        String[] specialNames = {
            "PRIVILEGE_WITH_SPACES AND SYMBOLS",
            "Privilégio_com_acentos_çãñ",
            "privilege@with#symbols$%&*",
            "привилегия_с_кириллицей",
            "特权_with_unicode_字符"
        };
        
        for (String specialName : specialNames) {
            // Act
            DTOResponsePrivilege dto = new DTOResponsePrivilege(UUID.randomUUID(), specialName);
            String json = objectMapper.writeValueAsString(dto);
            DTOResponsePrivilege deserialized = objectMapper.readValue(json, DTOResponsePrivilege.class);
            
            // Assert
            assertThat(deserialized.getName()).isEqualTo(specialName);
        }
    }
}