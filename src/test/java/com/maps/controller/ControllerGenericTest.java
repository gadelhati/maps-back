package com.maps.controller;

import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.payload.request.DTORequestIdentifiable;
import com.maps.service.ServiceGeneric;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;
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
class ControllerGenericTest {

    @Mock
    private ServiceGeneric<TestEntity, TestRequest, TestResponse> serviceGeneric;

    private TestController testController;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        request.setContextPath("");
        request.setRequestURI("/testEntity");
        ServletRequestAttributes attrs = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attrs);

        testController = new TestController(serviceGeneric);
    }

    @Test
    void testCreate_ShouldReturnCreatedWithLocationHeader() {
        UUID id = UUID.randomUUID();
        TestRequest request = new TestRequest(null, "Test");
        TestResponse response = new TestResponse(id, "Test");

        when(serviceGeneric.create(request)).thenReturn(response);

        ResponseEntity<TestResponse> result = testController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getHeaders().getLocation());
        assertTrue(result.getHeaders().getLocation().toString().contains(id.toString()));
        assertEquals(response, result.getBody());
        verify(serviceGeneric).create(request);
    }

    @Test
    void testRetrieveWithPagination_ShouldReturnOkWithPagedResults() {
        Pageable pageable = PageRequest.of(0, 10);
        TestResponse response = new TestResponse(UUID.randomUUID(), "Test");
        Page<TestResponse> page = new PageImpl<>(Collections.singletonList(response));

        when(serviceGeneric.retrieve(eq(pageable), eq(""), eq(TestEntity.class))).thenReturn(page);

        ResponseEntity<Page<TestResponse>> result = testController.retrieve("", pageable);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().getTotalElements());
        assertEquals(response, result.getBody().getContent().get(0));
        verify(serviceGeneric).retrieve(pageable, "", TestEntity.class);
    }

    @Test
    void testRetrieveWithSearchValue_ShouldPassValueToService() {
        Pageable pageable = PageRequest.of(0, 10);
        String searchValue = "searchTerm";
        Page<TestResponse> emptyPage = new PageImpl<>(Collections.emptyList());

        when(serviceGeneric.retrieve(eq(pageable), eq(searchValue), eq(TestEntity.class))).thenReturn(emptyPage);

        ResponseEntity<Page<TestResponse>> result = testController.retrieve(searchValue, pageable);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getBody().getTotalElements());
        verify(serviceGeneric).retrieve(pageable, searchValue, TestEntity.class);
    }

    @Test
    void testRetrieveById_ShouldReturnOkWithEntity() {
        UUID id = UUID.randomUUID();
        TestResponse response = new TestResponse(id, "Test");

        when(serviceGeneric.retrieve(id)).thenReturn(response);

        ResponseEntity<TestResponse> result = testController.retrieve(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(serviceGeneric).retrieve(id);
    }

    @Test
    void testUpdate_ShouldReturnAcceptedWithUpdatedEntity() {
        UUID id = UUID.randomUUID();
        TestRequest request = new TestRequest(id, "Updated");
        TestResponse response = new TestResponse(id, "Updated");

        when(serviceGeneric.update(id, request)).thenReturn(response);

        ResponseEntity<TestResponse> result = testController.update(id, request);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(serviceGeneric).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnAcceptedWithDeletedEntity() {
        UUID id = UUID.randomUUID();
        TestResponse response = new TestResponse(id, "Deleted");

        when(serviceGeneric.delete(id)).thenReturn(response);

        ResponseEntity<TestResponse> result = testController.delete(id);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(serviceGeneric).delete(id);
    }

    // Classes auxiliares para teste
    static class TestEntity extends GenericAuditEntity {
        private String name;
    }

    record TestRequest(UUID id, String name) implements DTORequestIdentifiable {}

    static class TestResponse extends RepresentationModel<TestResponse> {
        private final UUID id;
        private final String name;

        public TestResponse(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    static class TestController extends ControllerGeneric<TestEntity, TestRequest, TestResponse> {
        public TestController(ServiceGeneric<TestEntity, TestRequest, TestResponse> service) {
            super(service);
        }

        @Override
        protected Class<TestEntity> getEntityClass() {
            return TestEntity.class;
        }
    }
}
