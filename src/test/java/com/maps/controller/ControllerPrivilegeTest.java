package com.maps.controller;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.payload.request.DTORequestPrivilege;
import com.maps.persistence.payload.response.DTOResponsePrivilege;
import com.maps.service.ServicePrivilege;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerPrivilegeTest {

    @Mock
    private ServicePrivilege servicePrivilege;

    @InjectMocks
    private ControllerPrivilege controllerPrivilege;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        request.setContextPath("");
        request.setRequestURI("/privilege");
        ServletRequestAttributes attrs = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attrs);
    }

    @Test
    void testGetEntityClass_ShouldReturnPrivilegeClass() {
        Class<Privilege> result = controllerPrivilege.getEntityClass();

        assertEquals(Privilege.class, result);
    }

    @Test
    void testCreate_WithValidRequest_ShouldReturnCreatedStatus() {
        UUID id = UUID.randomUUID();
        DTORequestPrivilege request = new DTORequestPrivilege(null, "CREATE_PRIVILEGE");
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "CREATE_PRIVILEGE");

        when(servicePrivilege.create(request)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("CREATE_PRIVILEGE", result.getBody().getName());
        assertNotNull(result.getHeaders().getLocation());
        assertTrue(result.getHeaders().getLocation().toString().contains(id.toString()));
        verify(servicePrivilege).create(request);
    }

    @Test
    void testCreate_ShouldCallServiceCreate() {
        DTORequestPrivilege request = new DTORequestPrivilege(null, "TEST");
        DTOResponsePrivilege response = new DTOResponsePrivilege(UUID.randomUUID(), "TEST");

        when(servicePrivilege.create(request)).thenReturn(response);

        controllerPrivilege.create(request);

        verify(servicePrivilege, times(1)).create(request);
    }

    @Test
    void testRetrieveAll_WithPagination_ShouldReturnOkStatus() {
        Pageable pageable = PageRequest.of(0, 10);
        DTOResponsePrivilege response = new DTOResponsePrivilege(UUID.randomUUID(), "PRIVILEGE");
        Page<DTOResponsePrivilege> page = new PageImpl<>(Collections.singletonList(response));

        when(servicePrivilege.retrieve(eq(pageable), eq(""), eq(Privilege.class))).thenReturn(page);

        ResponseEntity<Page<DTOResponsePrivilege>> result = controllerPrivilege.retrieve("", pageable);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getTotalElements());
        verify(servicePrivilege).retrieve(pageable, "", Privilege.class);
    }

    @Test
    void testRetrieveAll_WithSearchValue_ShouldPassValueToService() {
        Pageable pageable = PageRequest.of(0, 10);
        String searchValue = "SEARCH_TERM";
        Page<DTOResponsePrivilege> page = new PageImpl<>(Collections.emptyList());

        when(servicePrivilege.retrieve(eq(pageable), eq(searchValue), eq(Privilege.class))).thenReturn(page);

        controllerPrivilege.retrieve(searchValue, pageable);

        verify(servicePrivilege).retrieve(pageable, searchValue, Privilege.class);
    }

    @Test
    void testRetrieveById_WithValidId_ShouldReturnOkStatus() {
        UUID id = UUID.randomUUID();
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "PRIVILEGE");

        when(servicePrivilege.retrieve(id)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(id, result.getBody().getId());
        assertEquals("PRIVILEGE", result.getBody().getName());
        verify(servicePrivilege).retrieve(id);
    }

    @Test
    void testRetrieveById_ShouldCallServiceRetrieve() {
        UUID id = UUID.randomUUID();
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "TEST");

        when(servicePrivilege.retrieve(id)).thenReturn(response);

        controllerPrivilege.retrieve(id);

        verify(servicePrivilege, times(1)).retrieve(id);
    }

    @Test
    void testUpdate_WithValidData_ShouldReturnAcceptedStatus() {
        UUID id = UUID.randomUUID();
        DTORequestPrivilege request = new DTORequestPrivilege(id, "UPDATED_PRIVILEGE");
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "UPDATED_PRIVILEGE");

        when(servicePrivilege.update(id, request)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("UPDATED_PRIVILEGE", result.getBody().getName());
        verify(servicePrivilege).update(id, request);
    }

    @Test
    void testUpdate_ShouldCallServiceUpdate() {
        UUID id = UUID.randomUUID();
        DTORequestPrivilege request = new DTORequestPrivilege(id, "UPDATE");
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "UPDATE");

        when(servicePrivilege.update(id, request)).thenReturn(response);

        controllerPrivilege.update(id, request);

        verify(servicePrivilege, times(1)).update(id, request);
    }

    @Test
    void testDelete_WithValidId_ShouldReturnAcceptedStatus() {
        UUID id = UUID.randomUUID();
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "DELETED_PRIVILEGE");

        when(servicePrivilege.delete(id)).thenReturn(response);

        ResponseEntity<DTOResponsePrivilege> result = controllerPrivilege.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(id, result.getBody().getId());
        verify(servicePrivilege).delete(id);
    }

    @Test
    void testDelete_ShouldCallServiceDelete() {
        UUID id = UUID.randomUUID();
        DTOResponsePrivilege response = new DTOResponsePrivilege(id, "DELETE");

        when(servicePrivilege.delete(id)).thenReturn(response);

        controllerPrivilege.delete(id);

        verify(servicePrivilege, times(1)).delete(id);
    }

    @Test
    void testControllerPrivilege_ExtendsControllerGeneric() {
        assertTrue(controllerPrivilege instanceof ControllerGeneric);
    }

    @Test
    void testCreate_WithNullRequest_ShouldStillCallService() {
        DTORequestPrivilege request = null;
        DTOResponsePrivilege response = new DTOResponsePrivilege(UUID.randomUUID(), "NULL_TEST");

        when(servicePrivilege.create(request)).thenReturn(response);

        controllerPrivilege.create(request);

        verify(servicePrivilege).create(request);
    }

    @Test
    void testRetrieveAll_WithEmptySearchValue_ShouldUseDefaultValue() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<DTOResponsePrivilege> page = new PageImpl<>(Collections.emptyList());

        when(servicePrivilege.retrieve(eq(pageable), eq(""), eq(Privilege.class))).thenReturn(page);

        controllerPrivilege.retrieve("", pageable);

        verify(servicePrivilege).retrieve(pageable, "", Privilege.class);
    }
}
