package com.maps.controller;

import com.maps.persistence.model.InternationalChart;
import com.maps.persistence.payload.request.DTORequestInternationalChart;
import com.maps.persistence.payload.response.DTOResponseInternationalChart;
import com.maps.service.ServiceInternationalChart;
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
class ControllerInternationalChartTest {

    @Mock
    private ServiceInternationalChart serviceInternationalChart;

    @InjectMocks
    private ControllerInternationalChart controllerInternationalChart;

    @Test
    void testGetEntityClass_ShouldReturnInternationalChartClass() {
        assertEquals(InternationalChart.class, controllerInternationalChart.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestInternationalChart request = mock(DTORequestInternationalChart.class);
        DTOResponseInternationalChart response = mock(DTOResponseInternationalChart.class);
        when(serviceInternationalChart.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseInternationalChart> result = controllerInternationalChart.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceInternationalChart).create(request);
    }

    @Test
    void testRetrieveAll_ShouldReturnOkResponse() {
        Page<DTOResponseInternationalChart> page = new PageImpl<>(Arrays.asList(mock(DTOResponseInternationalChart.class)));
        when(serviceInternationalChart.retrieve(any(), eq(""), eq(InternationalChart.class))).thenReturn(page);

        ResponseEntity<Page<DTOResponseInternationalChart>> result = controllerInternationalChart.retrieve("", PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceInternationalChart).retrieve(any(), eq(""), eq(InternationalChart.class));
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseInternationalChart response = mock(DTOResponseInternationalChart.class);
        when(serviceInternationalChart.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseInternationalChart> result = controllerInternationalChart.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceInternationalChart).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestInternationalChart request = mock(DTORequestInternationalChart.class);
        DTOResponseInternationalChart response = mock(DTOResponseInternationalChart.class);
        when(serviceInternationalChart.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseInternationalChart> result = controllerInternationalChart.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceInternationalChart).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseInternationalChart response = mock(DTOResponseInternationalChart.class);
        when(serviceInternationalChart.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseInternationalChart> result = controllerInternationalChart.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceInternationalChart).delete(id);
    }
}