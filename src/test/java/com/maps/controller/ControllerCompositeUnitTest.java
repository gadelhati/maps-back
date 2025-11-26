package com.maps.controller;

import com.maps.persistence.model.CompositePK;
import com.maps.persistence.payload.request.DTORequestCompositeUnit;
import com.maps.persistence.payload.response.DTOResponseCompositeUnit;
import com.maps.service.ServiceCompositeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ControllerCompositeUnit
 * Este controller tem métodos específicos diferentes do padrão CRUD
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerCompositeUnitTest {

    @Mock
    private ServiceCompositeUnit serviceCompositeUnit;

    @InjectMocks
    private ControllerCompositeUnit controllerCompositeUnit;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attrs = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attrs);
    }

    @Test
    void testCreate_WithValidRequest_ShouldReturnCreatedResponse() {
        // Arrange
        DTORequestCompositeUnit createRequest = mock(DTORequestCompositeUnit.class);
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        
        when(serviceCompositeUnit.create(createRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseCompositeUnit> response = controllerCompositeUnit.create(createRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        verify(serviceCompositeUnit).create(createRequest);
    }

    @Test
    void testCreate_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        DTORequestCompositeUnit createRequest = mock(DTORequestCompositeUnit.class);
        
        when(serviceCompositeUnit.create(any(DTORequestCompositeUnit.class)))
            .thenThrow(new RuntimeException("Composite unit creation failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerCompositeUnit.create(createRequest);
        });

        verify(serviceCompositeUnit).create(any(DTORequestCompositeUnit.class));
    }

    @Test
    void testRetrieveComposite_WithAllParameters_ShouldReturnOkResponse() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        String key = "testKey";
        String value = "testValue";
        String name = "testName";
        int number = 123;
        
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        Page<DTOResponseCompositeUnit> mockPage = new PageImpl<>(Arrays.asList(mockResponse));
        
        when(serviceCompositeUnit.retrieveComposite(pageable, key, value, name, number)).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<DTOResponseCompositeUnit>> response = 
            controllerCompositeUnit.retrieve(pageable, key, value, name, number);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(serviceCompositeUnit).retrieveComposite(pageable, key, value, name, number);
    }

    @Test
    void testRetrieveComposite_WithDefaultParameters_ShouldReturnOkResponse() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        Page<DTOResponseCompositeUnit> mockPage = new PageImpl<>(Arrays.asList(mockResponse));
        
        when(serviceCompositeUnit.retrieveComposite(pageable, "", "", null, 0)).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<DTOResponseCompositeUnit>> response = 
            controllerCompositeUnit.retrieve(pageable, "", "", null, 0);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(serviceCompositeUnit).retrieveComposite(pageable, "", "", null, 0);
    }

    @Test
    void testRetrieve_WithKeyAndValue_ShouldReturnOkResponse() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        String key = "testKey";
        String value = "testValue";
        
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        Page<DTOResponseCompositeUnit> mockPage = new PageImpl<>(Arrays.asList(mockResponse));
        
        when(serviceCompositeUnit.retrieve(pageable, key, value)).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<DTOResponseCompositeUnit>> response = 
            controllerCompositeUnit.retrieve(key, value, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(serviceCompositeUnit).retrieve(pageable, key, value);
    }

    @Test
    void testRetrieve_WithDefaultKeyValue_ShouldReturnOkResponse() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        Page<DTOResponseCompositeUnit> mockPage = new PageImpl<>(Arrays.asList(mockResponse));
        
        when(serviceCompositeUnit.retrieve(pageable, "", "")).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<DTOResponseCompositeUnit>> response = 
            controllerCompositeUnit.retrieve("", "", pageable);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(serviceCompositeUnit).retrieve(pageable, "", "");
    }

    @Test
    void testUpdate_WithValidRequest_ShouldReturnAcceptedResponse() {
        // Arrange
        DTORequestCompositeUnit updateRequest = mock(DTORequestCompositeUnit.class);
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        String name = "testName";
        int number = 123;
        
        when(updateRequest.name()).thenReturn(name);
        when(updateRequest.number()).thenReturn(number);
        when(serviceCompositeUnit.update(any(CompositePK.class), eq(updateRequest))).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseCompositeUnit> response = controllerCompositeUnit.update(updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceCompositeUnit).update(any(CompositePK.class), eq(updateRequest));
    }

    @Test
    void testUpdate_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        DTORequestCompositeUnit updateRequest = mock(DTORequestCompositeUnit.class);
        String name = "testName";
        int number = 123;
        
        when(updateRequest.name()).thenReturn(name);
        when(updateRequest.number()).thenReturn(number);
        when(serviceCompositeUnit.update(any(CompositePK.class), eq(updateRequest)))
            .thenThrow(new RuntimeException("Update failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerCompositeUnit.update(updateRequest);
        });

        verify(serviceCompositeUnit).update(any(CompositePK.class), eq(updateRequest));
    }

    @Test
    void testDeleteByNameAndNumber_WithValidParameters_ShouldReturnAcceptedResponse() {
        // Arrange
        String name = "testName";
        int number = 123;
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        
        when(serviceCompositeUnit.delete(any(CompositePK.class))).thenReturn(mockResponse);

        // Act
        ResponseEntity<DTOResponseCompositeUnit> response = controllerCompositeUnit.delete(name, number);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceCompositeUnit).delete(any(CompositePK.class));
    }

    @Test
    void testDeleteByNameAndNumber_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        String name = "testName";
        int number = 123;
        
        when(serviceCompositeUnit.delete(any(CompositePK.class)))
            .thenThrow(new RuntimeException("Delete failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerCompositeUnit.delete(name, number);
        });

        verify(serviceCompositeUnit).delete(any(CompositePK.class));
    }

    @Test
    void testDeleteAll_Success_ShouldReturnAcceptedResponse() {
        // Arrange
        doNothing().when(serviceCompositeUnit).delete();

        // Act
        ResponseEntity<HttpStatus> response = controllerCompositeUnit.delete();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNull(response.getBody());
        verify(serviceCompositeUnit).delete();
    }

    @Test
    void testDeleteAll_ServiceThrowsException_ShouldReturnBadRequest() {
        // Arrange
        doThrow(new RuntimeException("Delete all failed")).when(serviceCompositeUnit).delete();

        // Act
        ResponseEntity<HttpStatus> response = controllerCompositeUnit.delete();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response.getBody());
        verify(serviceCompositeUnit).delete();
    }

    @Test
    void testCompositePK_CreationInUpdate_ShouldUseCorrectNameAndNumber() {
        // Arrange
        DTORequestCompositeUnit updateRequest = mock(DTORequestCompositeUnit.class);
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        String expectedName = "ExpectedName";
        int expectedNumber = 456;
        
        when(updateRequest.name()).thenReturn(expectedName);
        when(updateRequest.number()).thenReturn(expectedNumber);
        when(serviceCompositeUnit.update(any(CompositePK.class), eq(updateRequest))).thenReturn(mockResponse);

        // Act
        controllerCompositeUnit.update(updateRequest);

        // Assert
        verify(serviceCompositeUnit).update(any(CompositePK.class), eq(updateRequest));
    }

    @Test
    void testCompositePK_CreationInDelete_ShouldUseCorrectNameAndNumber() {
        // Arrange
        String expectedName = "ExpectedName";
        int expectedNumber = 789;
        DTOResponseCompositeUnit mockResponse = mock(DTOResponseCompositeUnit.class);
        
        when(serviceCompositeUnit.delete(any(CompositePK.class))).thenReturn(mockResponse);

        // Act
        controllerCompositeUnit.delete(expectedName, expectedNumber);

        // Assert
        verify(serviceCompositeUnit).delete(any(CompositePK.class));
    }
}