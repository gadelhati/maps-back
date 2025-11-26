package com.maps.controller;

import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestRole;
import com.maps.persistence.payload.response.DTOResponseRole;
import com.maps.service.ServiceRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ControllerRole
 * Focando nos endpoints CRUD herdados do ControllerGeneric
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerRoleTest {

    @Mock
    private ServiceRole serviceRole;

    @InjectMocks
    private ControllerRole controllerRole;

    @Test
    void testGetEntityClass_ShouldReturnRoleClass() {
        // Act
        Class<Role> entityClass = controllerRole.getEntityClass();

        // Assert
        assertEquals(Role.class, entityClass);
    }

    @Test
    void testCreate_WithValidRequest_ShouldReturnCreatedResponse() {
        // Arrange
        DTORequestRole createRequest = mock(DTORequestRole.class);
        DTOResponseRole mockResponse = mock(DTOResponseRole.class);
        UUID roleId = UUID.randomUUID();
        
        when(mockResponse.getId()).thenReturn(roleId);
        when(serviceRole.create(createRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseRole> response = controllerRole.create(createRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        verify(serviceRole).create(createRequest);
    }

    @Test
    void testCreate_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        DTORequestRole createRequest = mock(DTORequestRole.class);
        
        when(serviceRole.create(createRequest))
            .thenThrow(new RuntimeException("Role creation failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerRole.create(createRequest);
        });

        verify(serviceRole).create(createRequest);
    }

    @Test
    void testRetrieveAll_WithDefaultParameters_ShouldReturnOkResponse() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        DTOResponseRole mockResponse1 = mock(DTOResponseRole.class);
        DTOResponseRole mockResponse2 = mock(DTOResponseRole.class);
        Page<DTOResponseRole> mockPage = new PageImpl<>(Arrays.asList(mockResponse1, mockResponse2));
        
        when(serviceRole.retrieve(pageable, "", Role.class)).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<DTOResponseRole>> response = controllerRole.retrieve("", pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(serviceRole).retrieve(pageable, "", Role.class);
    }

    @Test
    void testRetrieveAll_WithSearchValue_ShouldReturnFilteredResults() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        String searchValue = "ADMIN";
        DTOResponseRole mockResponse = mock(DTOResponseRole.class);
        Page<DTOResponseRole> mockPage = new PageImpl<>(Arrays.asList(mockResponse));
        
        when(serviceRole.retrieve(pageable, searchValue, Role.class)).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<DTOResponseRole>> response = controllerRole.retrieve(searchValue, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(serviceRole).retrieve(pageable, searchValue, Role.class);
    }

    @Test
    void testRetrieveById_WithValidId_ShouldReturnOkResponse() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        DTOResponseRole mockResponse = mock(DTOResponseRole.class);
        
        when(serviceRole.retrieve(roleId)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseRole> response = controllerRole.retrieve(roleId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceRole).retrieve(roleId);
    }

    @Test
    void testRetrieveById_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        
        when(serviceRole.retrieve(roleId))
            .thenThrow(new RuntimeException("Role not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerRole.retrieve(roleId);
        });

        verify(serviceRole).retrieve(roleId);
    }

    @Test
    void testUpdate_WithValidData_ShouldReturnAcceptedResponse() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        DTORequestRole updateRequest = mock(DTORequestRole.class);
        DTOResponseRole mockResponse = mock(DTOResponseRole.class);
        
        when(serviceRole.update(roleId, updateRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseRole> response = controllerRole.update(roleId, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceRole).update(roleId, updateRequest);
    }

    @Test
    void testUpdate_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        DTORequestRole updateRequest = mock(DTORequestRole.class);
        
        when(serviceRole.update(roleId, updateRequest))
            .thenThrow(new RuntimeException("Role update failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerRole.update(roleId, updateRequest);
        });

        verify(serviceRole).update(roleId, updateRequest);
    }

    @Test
    void testDelete_WithValidId_ShouldReturnAcceptedResponse() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        DTOResponseRole mockResponse = mock(DTOResponseRole.class);
        
        when(serviceRole.delete(roleId)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseRole> response = controllerRole.delete(roleId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceRole).delete(roleId);
    }

    @Test
    void testDelete_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        UUID roleId = UUID.randomUUID();
        
        when(serviceRole.delete(roleId))
            .thenThrow(new RuntimeException("Role deletion failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerRole.delete(roleId);
        });

        verify(serviceRole).delete(roleId);
    }

    @Test
    void testCreate_WithNullRequest_ShouldCallService() {
        // Arrange
        DTOResponseRole mockResponse = mock(DTOResponseRole.class);
        when(serviceRole.create(null)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseRole> response = controllerRole.create(null);

        // Assert
        assertNotNull(response);
        verify(serviceRole).create(null);
    }

    @Test
    void testConstructor_ShouldInitializeCorrectly() {
        // Arrange
        ServiceRole mockService = mock(ServiceRole.class);

        // Act
        ControllerRole controller = new ControllerRole(mockService);

        // Assert
        assertNotNull(controller);
        assertEquals(Role.class, controller.getEntityClass());
    }
}