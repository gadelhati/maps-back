package com.maps.service;

import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.GenericAuditEntity;
import com.maps.persistence.payload.request.DTORequestIdentifiable;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.utils.Information;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ServiceGenericTest {

    @Mock
    private Information information;

    @Mock
    private RepositoryGeneric<TestEntity> repositoryGeneric;

    @Mock
    private MapperInterface<TestEntity, TestRequest, TestResponse> mapperInterface;

    private TestService testService;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(8080);
        request.setContextPath("");
        ServletRequestAttributes attrs = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attrs);

        testService = new TestService(information, repositoryGeneric, mapperInterface);
    }

    @Test
    void testCreate_ShouldSaveEntityAndReturnDTO() {
        TestRequest request = new TestRequest(null, "NewEntity");
        TestEntity entity = new TestEntity(UUID.randomUUID(), "NewEntity");
        TestResponse response = new TestResponse(entity.getId(), "NewEntity");

        when(information.getCurrentUser()).thenReturn(Optional.of("testUser"));
        when(mapperInterface.toObject(request)).thenReturn(entity);
        when(repositoryGeneric.save(entity)).thenReturn(entity);
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        TestResponse result = testService.create(request);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals("NewEntity", result.getName());
        verify(mapperInterface).toObject(request);
        verify(repositoryGeneric).save(entity);
        verify(mapperInterface).toDTO(entity);
    }

    @Test
    void testCreate_ShouldLogCurrentUser() {
        TestRequest request = new TestRequest(null, "Test");
        TestEntity entity = new TestEntity(UUID.randomUUID(), "Test");
        TestResponse response = new TestResponse(entity.getId(), "Test");

        when(information.getCurrentUser()).thenReturn(Optional.of("testUser"));
        when(mapperInterface.toObject(request)).thenReturn(entity);
        when(repositoryGeneric.save(entity)).thenReturn(entity);
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        testService.create(request);

        verify(information).getCurrentUser();
    }

    @Test
    void testRetrieveById_WhenEntityExists_ShouldReturnDTO() {
        UUID id = UUID.randomUUID();
        TestEntity entity = new TestEntity(id, "ExistingEntity");
        TestResponse response = new TestResponse(id, "ExistingEntity");

        when(repositoryGeneric.findById(id)).thenReturn(Optional.of(entity));
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        TestResponse result = testService.retrieve(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("ExistingEntity", result.getName());
        verify(repositoryGeneric).findById(id);
        verify(mapperInterface).toDTO(entity);
    }

    @Test
    void testRetrieveById_WhenEntityDoesNotExist_ShouldThrowEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(repositoryGeneric.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            testService.retrieve(id);
        });

        assertTrue(exception.getMessage().contains("TestEntity"));
        assertTrue(exception.getMessage().contains(id.toString()));
        verify(repositoryGeneric).findById(id);
        verify(mapperInterface, never()).toDTO(any());
    }

    @Test
    void testRetrieveWithPagination_WhenNoValue_ShouldReturnAllEntities() {
        Pageable pageable = PageRequest.of(0, 10);
        TestEntity entity1 = new TestEntity(UUID.randomUUID(), "Entity1");
        TestEntity entity2 = new TestEntity(UUID.randomUUID(), "Entity2");
        Page<TestEntity> entityPage = new PageImpl<>(Arrays.asList(entity1, entity2));
        TestResponse response1 = new TestResponse(entity1.getId(), "Entity1");
        TestResponse response2 = new TestResponse(entity2.getId(), "Entity2");

        when(repositoryGeneric.findAll(eq(pageable))).thenReturn(entityPage);
        when(mapperInterface.toDTO(entity1)).thenReturn(response1);
        when(mapperInterface.toDTO(entity2)).thenReturn(response2);

        Page<TestResponse> result = testService.retrieve(pageable, "", TestEntity.class);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(repositoryGeneric).findAll(pageable);
    }

    @Test
    void testRetrieveWithPagination_WhenSearchingByValidUUID_ShouldFindById() {
        UUID id = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        TestEntity entity = new TestEntity(id, "FoundEntity");
        Page<TestEntity> entityPage = new PageImpl<>(Collections.singletonList(entity));
        TestResponse response = new TestResponse(id, "FoundEntity");

        when(repositoryGeneric.findById(eq(pageable), eq(id))).thenReturn(entityPage);
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        Page<TestResponse> result = testService.retrieve(pageable, id.toString(), TestEntity.class);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(id, result.getContent().get(0).getId());
        verify(repositoryGeneric).findById(pageable, id);
    }

    @Test
    void testRetrieveWithPagination_WhenSearchingByInvalidUUID_ShouldFallbackToPropertySearch() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        String invalidUUID = "not-a-uuid";
        TestEntity entity = new TestEntity(UUID.randomUUID(), "SearchedName");
        Page<TestEntity> entityPage = new PageImpl<>(Collections.singletonList(entity));
        TestResponse response = new TestResponse(entity.getId(), "SearchedName");

        when(repositoryGeneric.findAll(any(Example.class), eq(pageable))).thenReturn(entityPage);
        when(mapperInterface.toDTO(any())).thenReturn(response);

        Page<TestResponse> result = testService.retrieve(pageable, invalidUUID, TestEntity.class);

        assertNotNull(result);
        verify(repositoryGeneric).findAll(any(Example.class), eq(pageable));
    }

    @Test
    void testRetrieveWithPagination_WhenSearchByPropertyFails_ShouldReturnAllEntities() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nonExistentField"));
        TestEntity entity = new TestEntity(UUID.randomUUID(), "Entity");
        Page<TestEntity> entityPage = new PageImpl<>(Collections.singletonList(entity));
        TestResponse response = new TestResponse(entity.getId(), "Entity");

        when(repositoryGeneric.findAll(eq(pageable))).thenReturn(entityPage);
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        Page<TestResponse> result = testService.retrieve(pageable, "someValue", TestEntity.class);

        assertNotNull(result);
        verify(repositoryGeneric).findAll(pageable);
    }

    @Test
    void testUpdate_ShouldSaveUpdatedEntityAndReturnDTO() {
        UUID id = UUID.randomUUID();
        TestRequest request = new TestRequest(id, "UpdatedEntity");
        TestEntity entity = new TestEntity(id, "UpdatedEntity");
        TestResponse response = new TestResponse(id, "UpdatedEntity");

        when(information.getCurrentUser()).thenReturn(Optional.of("testUser"));
        when(mapperInterface.toObject(request)).thenReturn(entity);
        when(repositoryGeneric.save(entity)).thenReturn(entity);
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        TestResponse result = testService.update(id, request);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("UpdatedEntity", result.getName());
        verify(mapperInterface).toObject(request);
        verify(repositoryGeneric).save(entity);
        verify(mapperInterface).toDTO(entity);
    }

    @Test
    void testUpdate_ShouldLogCurrentUser() {
        UUID id = UUID.randomUUID();
        TestRequest request = new TestRequest(id, "Updated");
        TestEntity entity = new TestEntity(id, "Updated");
        TestResponse response = new TestResponse(id, "Updated");

        when(information.getCurrentUser()).thenReturn(Optional.of("testUser"));
        when(mapperInterface.toObject(request)).thenReturn(entity);
        when(repositoryGeneric.save(entity)).thenReturn(entity);
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        testService.update(id, request);

        verify(information).getCurrentUser();
    }

    @Test
    void testDelete_WhenEntityExists_ShouldDeleteAndReturnDTO() {
        UUID id = UUID.randomUUID();
        TestEntity entity = new TestEntity(id, "ToDelete");
        TestResponse response = new TestResponse(id, "ToDelete");

        when(information.getCurrentUser()).thenReturn(Optional.of("testUser"));
        when(repositoryGeneric.findById(id)).thenReturn(Optional.of(entity));
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        TestResponse result = testService.delete(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repositoryGeneric).findById(id);
        verify(repositoryGeneric).delete(entity);
        verify(mapperInterface).toDTO(entity);
    }

    @Test
    void testDelete_WhenEntityDoesNotExist_ShouldThrowEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(repositoryGeneric.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            testService.delete(id);
        });

        assertTrue(exception.getMessage().contains("Cannot delete"));
        assertTrue(exception.getMessage().contains("TestEntity"));
        assertTrue(exception.getMessage().contains(id.toString()));
        verify(repositoryGeneric).findById(id);
        verify(repositoryGeneric, never()).delete(any());
    }

    @Test
    void testDelete_ShouldLogCurrentUser() {
        UUID id = UUID.randomUUID();
        TestEntity entity = new TestEntity(id, "Delete");
        TestResponse response = new TestResponse(id, "Delete");

        when(information.getCurrentUser()).thenReturn(Optional.of("testUser"));
        when(repositoryGeneric.findById(id)).thenReturn(Optional.of(entity));
        when(mapperInterface.toDTO(entity)).thenReturn(response);

        testService.delete(id);

        verify(information).getCurrentUser();
    }

    @Test
    void testAddHateoas_ShouldAddSelfLink() {
        UUID id = UUID.randomUUID();
        TestEntity entity = new TestEntity(id, "HateoasEntity");
        TestResponse response = new TestResponse(id, "HateoasEntity");

        when(mapperInterface.toDTO(entity)).thenReturn(response);

        TestResponse result = testService.addHateoas(entity);

        assertNotNull(result);
        assertFalse(result.getLinks().isEmpty());
        assertTrue(result.hasLink("self"));
        assertTrue(result.getLink("self").get().getHref().contains(id.toString()));
        assertTrue(result.getLink("self").get().getHref().contains("testEntity"));
    }

    @Test
    void testAddHateoas_ShouldUseLowerCaseEntityName() {
        UUID id = UUID.randomUUID();
        TestEntity entity = new TestEntity(id, "Test");
        TestResponse response = new TestResponse(id, "Test");

        when(mapperInterface.toDTO(entity)).thenReturn(response);

        TestResponse result = testService.addHateoas(entity);

        String href = result.getLink("self").get().getHref();
        assertTrue(href.contains("/testEntity/"));
        assertFalse(href.contains("/TestEntity/"));
    }

    // Classes auxiliares para teste
    static class TestEntity extends GenericAuditEntity {
        private String name;

        public TestEntity() {}

        public TestEntity(UUID id, String name) {
            this.setId(id);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
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

    static class TestService extends ServiceGeneric<TestEntity, TestRequest, TestResponse> {
        public TestService(Information information, RepositoryGeneric<TestEntity> repo,
                           MapperInterface<TestEntity, TestRequest, TestResponse> mapper) {
            super(TestEntity.class, information, repo, mapper);
        }
    }
}
