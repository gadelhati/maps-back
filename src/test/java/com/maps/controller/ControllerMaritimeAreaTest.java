package com.maps.controller;

import com.maps.persistence.model.MaritimeArea;
import com.maps.persistence.payload.request.DTORequestMaritimeArea;
import com.maps.persistence.payload.response.DTOResponseMaritimeArea;
import com.maps.service.ServiceMaritimeArea;
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
class ControllerMaritimeAreaTest {

    @Mock
    private ServiceMaritimeArea serviceMaritimeArea;

    @InjectMocks
    private ControllerMaritimeArea controllerMaritimeArea;

    @Test
    void testGetEntityClass_ShouldReturnMaritimeAreaClass() {
        assertEquals(MaritimeArea.class, controllerMaritimeArea.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestMaritimeArea request = mock(DTORequestMaritimeArea.class);
        DTOResponseMaritimeArea response = mock(DTOResponseMaritimeArea.class);
        when(serviceMaritimeArea.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseMaritimeArea> result = controllerMaritimeArea.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceMaritimeArea).create(request);
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseMaritimeArea response = mock(DTOResponseMaritimeArea.class);
        when(serviceMaritimeArea.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseMaritimeArea> result = controllerMaritimeArea.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceMaritimeArea).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestMaritimeArea request = mock(DTORequestMaritimeArea.class);
        DTOResponseMaritimeArea response = mock(DTOResponseMaritimeArea.class);
        when(serviceMaritimeArea.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseMaritimeArea> result = controllerMaritimeArea.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceMaritimeArea).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseMaritimeArea response = mock(DTOResponseMaritimeArea.class);
        when(serviceMaritimeArea.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseMaritimeArea> result = controllerMaritimeArea.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceMaritimeArea).delete(id);
    }
}