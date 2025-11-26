package com.maps.controller;

import com.maps.persistence.model.City;
import com.maps.persistence.payload.request.DTORequestCity;
import com.maps.persistence.payload.response.DTOResponseCity;
import com.maps.service.ServiceCity;
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
class ControllerCityTest {

    @Mock
    private ServiceCity serviceCity;

    @InjectMocks
    private ControllerCity controllerCity;

    @Test
    void testGetEntityClass_ShouldReturnCityClass() {
        assertEquals(City.class, controllerCity.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestCity request = mock(DTORequestCity.class);
        DTOResponseCity response = mock(DTOResponseCity.class);
        when(serviceCity.create(request)).thenReturn(response);

        ResponseEntity<DTOResponseCity> result = controllerCity.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(serviceCity).create(request);
    }

    @Test
    void testRetrieveAll_ShouldReturnOkResponse() {
        Page<DTOResponseCity> page = new PageImpl<>(Arrays.asList(mock(DTOResponseCity.class)));
        when(serviceCity.retrieve(any(), eq(""), eq(City.class))).thenReturn(page);

        ResponseEntity<Page<DTOResponseCity>> result = controllerCity.retrieve("", PageRequest.of(0, 20));

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceCity).retrieve(any(), eq(""), eq(City.class));
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseCity response = mock(DTOResponseCity.class);
        when(serviceCity.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponseCity> result = controllerCity.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(serviceCity).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestCity request = mock(DTORequestCity.class);
        DTOResponseCity response = mock(DTOResponseCity.class);
        when(serviceCity.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponseCity> result = controllerCity.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceCity).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponseCity response = mock(DTOResponseCity.class);
        when(serviceCity.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponseCity> result = controllerCity.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(serviceCity).delete(id);
    }
}