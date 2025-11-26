package com.maps.controller;

import com.maps.persistence.model.GaugeStation;
import com.maps.persistence.payload.request.DTORequestGaugeStation;
import com.maps.persistence.payload.response.DTOResponseGaugeStation;
import com.maps.service.ServiceGaugeStation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerGaugeStationTest {

    @Mock
    private ServiceGaugeStation serviceGaugeStation;

    @InjectMocks
    private ControllerGaugeStation controllerGaugeStation;

    @Test
    void testGetEntityClass_ShouldReturnGaugeStationClass() {
        assertEquals(GaugeStation.class, controllerGaugeStation.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestGaugeStation request = mock(DTORequestGaugeStation.class);
        DTOResponseGaugeStation response = mock(DTOResponseGaugeStation.class);
        when(serviceGaugeStation.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseGaugeStation> result = controllerGaugeStation.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceGaugeStation).create(request);
    }

    @Test
    void testRetrieveAll_ShouldReturnOkResponse() {
        Page<DTOResponseGaugeStation> page = new PageImpl<>(Arrays.asList(mock(DTOResponseGaugeStation.class)));
        when(serviceGaugeStation.retrieve(any(), eq(""), eq(GaugeStation.class))).thenReturn(page);

        ResponseEntity<Page<DTOResponseGaugeStation>> result = controllerGaugeStation.retrieve("", PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceGaugeStation).retrieve(any(), eq(""), eq(GaugeStation.class));
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseGaugeStation response = mock(DTOResponseGaugeStation.class);
        when(serviceGaugeStation.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseGaugeStation> result = controllerGaugeStation.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceGaugeStation).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestGaugeStation request = mock(DTORequestGaugeStation.class);
        DTOResponseGaugeStation response = mock(DTOResponseGaugeStation.class);
        when(serviceGaugeStation.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseGaugeStation> result = controllerGaugeStation.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceGaugeStation).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseGaugeStation response = mock(DTOResponseGaugeStation.class);
        when(serviceGaugeStation.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseGaugeStation> result = controllerGaugeStation.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceGaugeStation).delete(id);
    }
}