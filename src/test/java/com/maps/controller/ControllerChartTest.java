package com.maps.controller;

import com.maps.persistence.model.Chart;
import com.maps.persistence.payload.request.DTORequestChart;
import com.maps.persistence.payload.response.DTOResponseChart;
import com.maps.service.ServiceChart;
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
 * Testes unitários para ControllerChart
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerChartTest {

    @Mock
    private ServiceChart serviceChart;

    @InjectMocks
    private ControllerChart controllerChart;

    @Test
    void testGetEntityClass_ShouldReturnChartClass() {
        assertEquals(Chart.class, controllerChart.getEntityClass());
    }

    @Test
    void testCreate_WithValidRequest_ShouldReturnCreatedResponse() {
        DTORequestChart createRequest = mock(DTORequestChart.class);
        DTOResponseChart mockResponse = mock(DTOResponseChart.class);
        UUID chartId = UUID.randomUUID();
        
        when(mockResponse.getId()).thenReturn(chartId);
        when(serviceChart.create(createRequest)).thenReturn(mockResponse);

        ResponseEntity<DTOResponseChart> response = controllerChart.create(createRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
        verify(serviceChart).create(createRequest);
    }

    @Test
    void testRetrieveAll_ShouldReturnOkResponse() {
        Pageable pageable = PageRequest.of(0, 20);
        DTOResponseChart mockResponse = mock(DTOResponseChart.class);
        Page<DTOResponseChart> mockPage = new PageImpl<>(Arrays.asList(mockResponse));
        
        when(serviceChart.retrieve(pageable, "", Chart.class)).thenReturn(mockPage);

        ResponseEntity<Page<DTOResponseChart>> response = controllerChart.retrieve("", pageable);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceChart).retrieve(pageable, "", Chart.class);
    }

    @Test
    void testRetrieveById_WithValidId_ShouldReturnOkResponse() {
        UUID chartId = UUID.randomUUID();
        DTOResponseChart mockResponse = mock(DTOResponseChart.class);
        
        when(serviceChart.retrieve(chartId)).thenReturn(mockResponse);

        ResponseEntity<DTOResponseChart> response = controllerChart.retrieve(chartId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(serviceChart).retrieve(chartId);
    }

    @Test
    void testUpdate_WithValidData_ShouldReturnAcceptedResponse() {
        UUID chartId = UUID.randomUUID();
        DTORequestChart updateRequest = mock(DTORequestChart.class);
        DTOResponseChart mockResponse = mock(DTOResponseChart.class);
        
        when(serviceChart.update(chartId, updateRequest)).thenReturn(mockResponse);

        ResponseEntity<DTOResponseChart> response = controllerChart.update(chartId, updateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(serviceChart).update(chartId, updateRequest);
    }

    @Test
    void testDelete_WithValidId_ShouldReturnAcceptedResponse() {
        UUID chartId = UUID.randomUUID();
        DTOResponseChart mockResponse = mock(DTOResponseChart.class);
        
        when(serviceChart.delete(chartId)).thenReturn(mockResponse);

        ResponseEntity<DTOResponseChart> response = controllerChart.delete(chartId);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(serviceChart).delete(chartId);
    }
}