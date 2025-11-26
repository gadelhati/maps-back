package com.maps.controller;

import com.maps.persistence.model.Country;
import com.maps.persistence.payload.request.DTORequestCountry;
import com.maps.persistence.payload.response.DTOResponseCountry;
import com.maps.service.ServiceCountry;
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
class ControllerCountryTest {

    @Mock
    private ServiceCountry serviceCountry;

    @InjectMocks
    private ControllerCountry controllerCountry;

    @Test
    void testGetEntityClass_ShouldReturnCountryClass() {
        assertEquals(Country.class, controllerCountry.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestCountry request = mock(DTORequestCountry.class);
        DTOResponseCountry response = mock(DTOResponseCountry.class);
        when(serviceCountry.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseCountry> result = controllerCountry.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceCountry).create(request);
    }

    @Test
    void testRetrieveAll_ShouldReturnOkResponse() {
        Page<DTOResponseCountry> page = new PageImpl<>(Arrays.asList(mock(DTOResponseCountry.class)));
        when(serviceCountry.retrieve(any(), eq(""), eq(Country.class))).thenReturn(page);

        ResponseEntity<Page<DTOResponseCountry>> result = controllerCountry.retrieve("", PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceCountry).retrieve(any(), eq(""), eq(Country.class));
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseCountry response = mock(DTOResponseCountry.class);
        when(serviceCountry.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseCountry> result = controllerCountry.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceCountry).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestCountry request = mock(DTORequestCountry.class);
        DTOResponseCountry response = mock(DTOResponseCountry.class);
        when(serviceCountry.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseCountry> result = controllerCountry.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceCountry).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseCountry response = mock(DTOResponseCountry.class);
        when(serviceCountry.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseCountry> result = controllerCountry.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceCountry).delete(id);
    }
}