package com.maps.controller;

import com.maps.persistence.model.State;
import com.maps.persistence.payload.request.DTORequestState;
import com.maps.persistence.payload.response.DTOResponseState;
import com.maps.service.ServiceState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerStateTest {

    @Mock
    private ServiceState serviceState;

    @InjectMocks
    private ControllerState controllerState;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attrs = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attrs);
    }

    @Test
    void testGetEntityClass_ShouldReturnStateClass() {
        assertEquals(State.class, controllerState.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestState request = mock(DTORequestState.class);
        DTOResponseState response = mock(DTOResponseState.class);
        when(serviceState.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseState> result = controllerState.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceState).create(request);
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseState response = mock(DTOResponseState.class);
        when(serviceState.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseState> result = controllerState.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceState).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestState request = mock(DTORequestState.class);
        DTOResponseState response = mock(DTOResponseState.class);
        when(serviceState.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseState> result = controllerState.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceState).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseState response = mock(DTOResponseState.class);
        when(serviceState.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseState> result = controllerState.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceState).delete(id);
    }
}