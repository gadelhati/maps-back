package com.maps.controller;

import com.maps.persistence.model.Researcher;
import com.maps.persistence.payload.request.DTORequestResearcher;
import com.maps.persistence.payload.response.DTOResponseResearcher;
import com.maps.service.ServiceResearcher;
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
class ControllerResearcherTest {

    @Mock
    private ServiceResearcher serviceResearcher;

    @InjectMocks
    private ControllerResearcher controllerResearcher;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attrs = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attrs);
    }

    @Test
    void testGetEntityClass_ShouldReturnResearcherClass() {
        assertEquals(Researcher.class, controllerResearcher.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestResearcher request = mock(DTORequestResearcher.class);
        DTOResponseResearcher response = mock(DTOResponseResearcher.class);
        when(serviceResearcher.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseResearcher> result = controllerResearcher.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceResearcher).create(request);
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseResearcher response = mock(DTOResponseResearcher.class);
        when(serviceResearcher.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseResearcher> result = controllerResearcher.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceResearcher).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestResearcher request = mock(DTORequestResearcher.class);
        DTOResponseResearcher response = mock(DTOResponseResearcher.class);
        when(serviceResearcher.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseResearcher> result = controllerResearcher.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceResearcher).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseResearcher response = mock(DTOResponseResearcher.class);
        when(serviceResearcher.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseResearcher> result = controllerResearcher.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceResearcher).delete(id);
    }
}