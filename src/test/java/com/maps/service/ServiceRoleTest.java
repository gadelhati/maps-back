package com.maps.service;

import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestRole;
import com.maps.persistence.repository.RepositoryRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ServiceRole
 * Focando nos métodos específicos da classe que não são herdados
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ServiceRoleTest {

    @Mock
    private RepositoryRole repositoryRole;

    @InjectMocks
    private ServiceRole serviceRole;

    @Test
    void testExistsByName_WithValidName_ShouldCallRepository() {
        // Arrange
        String roleName = "ADMIN";
        when(repositoryRole.existsByNameIgnoreCase(roleName)).thenReturn(true);

        // Act
        boolean result = serviceRole.existsByName(roleName);

        // Assert
        assertTrue(result);
        verify(repositoryRole).existsByNameIgnoreCase(roleName);
    }

    @Test
    void testExistsByName_WithNonExistentName_ShouldReturnFalse() {
        // Arrange
        String roleName = "NON_EXISTENT_ROLE";
        when(repositoryRole.existsByNameIgnoreCase(roleName)).thenReturn(false);

        // Act
        boolean result = serviceRole.existsByName(roleName);

        // Assert
        assertFalse(result);
        verify(repositoryRole).existsByNameIgnoreCase(roleName);
    }

    @Test
    void testExistsByName_WithNullValue_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serviceRole.existsByName(null)
        );

        assertEquals("Value must not be null or empty.", exception.getMessage());
        verify(repositoryRole, never()).existsByNameIgnoreCase(any());
    }

    @Test
    void testExistsByName_WithEmptyString_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serviceRole.existsByName("")
        );

        assertEquals("Value must not be null or empty.", exception.getMessage());
        verify(repositoryRole, never()).existsByNameIgnoreCase(any());
    }

    @Test
    void testExistsByName_WithWhitespaceOnlyString_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serviceRole.existsByName("   ")
        );

        assertEquals("Value must not be null or empty.", exception.getMessage());
        verify(repositoryRole, never()).existsByNameIgnoreCase(any());
    }

    @Test
    void testExistsByName_WithCaseInsensitiveSearch_ShouldWork() {
        // Arrange
        String roleName = "admin";
        when(repositoryRole.existsByNameIgnoreCase(roleName)).thenReturn(true);

        // Act
        boolean result = serviceRole.existsByName(roleName);

        // Assert
        assertTrue(result);
        verify(repositoryRole).existsByNameIgnoreCase(roleName);
    }

    @Test
    void testExistsByNameAndIdNot_WithValidParameters_ShouldCallRepository() {
        // Arrange
        String roleName = "USER";
        UUID roleId = UUID.randomUUID();
        when(repositoryRole.existsByNameIgnoreCaseAndIdNot(roleName, roleId)).thenReturn(false);

        // Act
        boolean result = serviceRole.existsByNameAndIdNot(roleName, roleId);

        // Assert
        assertFalse(result);
        verify(repositoryRole).existsByNameIgnoreCaseAndIdNot(roleName, roleId);
    }

    @Test
    void testExistsByNameAndIdNot_WithExistingNameDifferentId_ShouldReturnTrue() {
        // Arrange
        String roleName = "MODERATOR";
        UUID roleId = UUID.randomUUID();
        when(repositoryRole.existsByNameIgnoreCaseAndIdNot(roleName, roleId)).thenReturn(true);

        // Act
        boolean result = serviceRole.existsByNameAndIdNot(roleName, roleId);

        // Assert
        assertTrue(result);
        verify(repositoryRole).existsByNameIgnoreCaseAndIdNot(roleName, roleId);
    }

    @Test
    void testExistsByNameAndIdNot_WithNullName_ShouldThrowException() {
        // Arrange
        UUID roleId = UUID.randomUUID();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serviceRole.existsByNameAndIdNot(null, roleId)
        );

        assertEquals("Value must not be null or empty.", exception.getMessage());
        verify(repositoryRole, never()).existsByNameIgnoreCaseAndIdNot(any(), any());
    }

    @Test
    void testExistsByNameAndIdNot_WithEmptyName_ShouldThrowException() {
        // Arrange
        UUID roleId = UUID.randomUUID();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serviceRole.existsByNameAndIdNot("", roleId)
        );

        assertEquals("Value must not be null or empty.", exception.getMessage());
        verify(repositoryRole, never()).existsByNameIgnoreCaseAndIdNot(any(), any());
    }

    @Test
    void testExistsByNameAndIdNot_WithNullId_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serviceRole.existsByNameAndIdNot("ADMIN", null)
        );

        assertEquals("ID must not be null.", exception.getMessage());
        verify(repositoryRole, never()).existsByNameIgnoreCaseAndIdNot(any(), any());
    }

    @Test
    void testExistsByNameAndIdNot_WithWhitespaceNameAndValidId_ShouldThrowException() {
        // Arrange
        UUID roleId = UUID.randomUUID();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serviceRole.existsByNameAndIdNot("   ", roleId)
        );

        assertEquals("Value must not be null or empty.", exception.getMessage());
        verify(repositoryRole, never()).existsByNameIgnoreCaseAndIdNot(any(), any());
    }

    @Test
    void testExistsByNameAndIdNot_WithSameIdAndName_ShouldReturnFalse() {
        // Arrange - Mesmo ID significa que é a mesma entidade sendo atualizada
        String roleName = "VIEWER";
        UUID roleId = UUID.randomUUID();
        when(repositoryRole.existsByNameIgnoreCaseAndIdNot(roleName, roleId)).thenReturn(false);

        // Act
        boolean result = serviceRole.existsByNameAndIdNot(roleName, roleId);

        // Assert
        assertFalse(result);
        verify(repositoryRole).existsByNameIgnoreCaseAndIdNot(roleName, roleId);
    }

    @Test
    void testExistsByNameAndIdNot_WithCaseInsensitiveNameCheck_ShouldWork() {
        // Arrange
        String roleName = "viewer";
        UUID roleId = UUID.randomUUID();
        when(repositoryRole.existsByNameIgnoreCaseAndIdNot(roleName, roleId)).thenReturn(false);

        // Act
        boolean result = serviceRole.existsByNameAndIdNot(roleName, roleId);

        // Assert
        assertFalse(result);
        verify(repositoryRole).existsByNameIgnoreCaseAndIdNot(roleName, roleId);
    }

    @Test
    void testBothMethods_WithDifferentScenarios_ShouldHandleCorrectly() {
        // Cenário: Verificar se nome existe e depois verificar para update
        
        // Arrange
        String roleName = "NEW_ROLE";
        UUID existingId = UUID.randomUUID();
        
        when(repositoryRole.existsByNameIgnoreCase(roleName)).thenReturn(false);
        when(repositoryRole.existsByNameIgnoreCaseAndIdNot(roleName, existingId)).thenReturn(false);

        // Act
        boolean existsForCreation = serviceRole.existsByName(roleName);
        boolean existsForUpdate = serviceRole.existsByNameAndIdNot(roleName, existingId);

        // Assert
        assertFalse(existsForCreation);
        assertFalse(existsForUpdate);
        
        verify(repositoryRole).existsByNameIgnoreCase(roleName);
        verify(repositoryRole).existsByNameIgnoreCaseAndIdNot(roleName, existingId);
    }

    @Test
    void testExistsByName_WithSpecialCharacters_ShouldWork() {
        // Arrange
        String roleNameWithSpecialChars = "SPECIAL_ROLE-123!";
        when(repositoryRole.existsByNameIgnoreCase(roleNameWithSpecialChars)).thenReturn(true);

        // Act
        boolean result = serviceRole.existsByName(roleNameWithSpecialChars);

        // Assert
        assertTrue(result);
        verify(repositoryRole).existsByNameIgnoreCase(roleNameWithSpecialChars);
    }

    @Test
    void testExistsByNameAndIdNot_WithLongRoleName_ShouldWork() {
        // Arrange
        String longRoleName = "VERY_LONG_ROLE_NAME_THAT_EXCEEDS_NORMAL_LENGTH_TO_TEST_EDGE_CASES";
        UUID roleId = UUID.randomUUID();
        when(repositoryRole.existsByNameIgnoreCaseAndIdNot(longRoleName, roleId)).thenReturn(false);

        // Act
        boolean result = serviceRole.existsByNameAndIdNot(longRoleName, roleId);

        // Assert
        assertFalse(result);
        verify(repositoryRole).existsByNameIgnoreCaseAndIdNot(longRoleName, roleId);
    }
}