package com.maps.controller;

import com.maps.persistence.model.ChartArea;
import com.maps.persistence.payload.request.DTORequestChartArea;
import com.maps.persistence.payload.response.DTOResponseChartArea;
import com.maps.service.ServiceChartArea;
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

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerChartAreaTest {

    @Mock
    private ServiceChartArea serviceChartArea;

    @InjectMocks
    private ControllerChartArea controllerChartArea;

    @Test
    void testGetEntityClass_ShouldReturnChartAreaClass() {
        assertEquals(ChartArea.class, controllerChartArea.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestChartArea request = mock(DTORequestChartArea.class);
        DTOResponseChartArea response = mock(DTOResponseChartArea.class);
        when(serviceChartArea.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseChartArea> result = controllerChartArea.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceChartArea).create(request);
    }

    @Test
    void testRetrieveAll_ShouldReturnOkResponse() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<DTOResponseChartArea> page = new PageImpl<>(Arrays.asList(mock(DTOResponseChartArea.class)));
        when(serviceChartArea.retrieve(pageable, "", ChartArea.class)).thenReturn(page);

        ResponseEntity<Page<DTOResponseChartArea>> result = controllerChartArea.retrieve("", pageable);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceChartArea).retrieve(pageable, "", ChartArea.class);
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseChartArea response = mock(DTOResponseChartArea.class);
        when(serviceChartArea.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseChartArea> result = controllerChartArea.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceChartArea).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestChartArea request = mock(DTORequestChartArea.class);
        DTOResponseChartArea response = mock(DTOResponseChartArea.class);
        when(serviceChartArea.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseChartArea> result = controllerChartArea.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceChartArea).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseChartArea response = mock(DTOResponseChartArea.class);
        when(serviceChartArea.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseChartArea> result = controllerChartArea.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceChartArea).delete(id);
    }
}