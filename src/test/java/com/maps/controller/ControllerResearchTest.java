package com.maps.controller;

import com.maps.persistence.model.Research;
import com.maps.persistence.payload.request.DTORequestResearch;
import com.maps.persistence.payload.response.DTOResponseResearch;
import com.maps.service.ServiceResearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerResearchTest {

    @Mock
    private ServiceResearch serviceResearch;

    @InjectMocks
    private ControllerResearch controllerResearch;

    @Test
    void testGetEntityClass_ShouldReturnResearchClass() {
        assertEquals(Research.class, controllerResearch.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestResearch request = mock(DTORequestResearch.class);
        DTOResponseResearch response = mock(DTOResponseResearch.class);
        when(serviceResearch.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseResearch> result = controllerResearch.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceResearch).create(request);
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseResearch response = mock(DTOResponseResearch.class);
        when(serviceResearch.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseResearch> result = controllerResearch.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceResearch).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestResearch request = mock(DTORequestResearch.class);
        DTOResponseResearch response = mock(DTOResponseResearch.class);
        when(serviceResearch.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseResearch> result = controllerResearch.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceResearch).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseResearch response = mock(DTOResponseResearch.class);
        when(serviceResearch.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseResearch> result = controllerResearch.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceResearch).delete(id);
    }
}