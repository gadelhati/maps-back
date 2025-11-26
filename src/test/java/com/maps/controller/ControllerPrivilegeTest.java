package com.maps.controller;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import com.maps.service.ServicePrivilege;
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
class ControllerPrivilegeTest {

    @Mock
    private ServicePrivilege servicePrivilege;

    @InjectMocks
    private ControllerPrivilege controllerPrivilege;

    @Test
    void testGetEntityClass_ShouldReturnPrivilegeClass() {
        assertEquals(Privilege.class, controllerPrivilege.getEntityClass());
    }

    @Test
    void testCreate_ShouldReturnCreatedResponse() {
        DTORequestPrivilege request = mock(DTORequestPrivilege.class);
        DTOResponsePrivilege response = mock(DTOResponsePrivilege.class);
        when(servicePrivilege.create(request)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(servicePrivilege).create(request);
    }

    @Test
    void testRetrieveById_ShouldReturnOkResponse() {
        UUID id = UUID.randomUUID();
        DTOResponsePrivilege response = mock(DTOResponsePrivilege.class);
        when(servicePrivilege.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(servicePrivilege).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTORequestPrivilege request = mock(DTORequestPrivilege.class);
        DTOResponsePrivilege response = mock(DTOResponsePrivilege.class);
        when(servicePrivilege.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(servicePrivilege).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedResponse() {
        UUID id = UUID.randomUUID();
        DTOResponsePrivilege response = mock(DTOResponsePrivilege.class);
        when(servicePrivilege.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        verify(servicePrivilege).delete(id);
    }
}