package com.maps.persistence.repository;

import com.maps.persistence.model.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para RepositoryGeneric usando Privilege como implementação concreta
 */
@DataJpaTest
@ActiveProfiles("test")
class RepositoryGenericTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RepositoryPrivilege repositoryPrivilege; // Usando Privilege como implementação

    @BeforeEach
    void setUp() {
        repositoryPrivilege.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testSave_ShouldPersistEntity() {
        Privilege privilege = new Privilege("GENERIC_SAVE_TEST");

        Privilege saved = repositoryPrivilege.save(privilege);
        entityManager.flush();

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("GENERIC_SAVE_TEST", saved.getName());
    }

    @Test
    void testFindById_WhenExists_ShouldReturnEntity() {
        Privilege privilege = new Privilege("GENERIC_FIND");
        entityManager.persist(privilege);
        entityManager.flush();

        Optional<Privilege> found = repositoryPrivilege.findById(privilege.getId());

        assertTrue(found.isPresent());
        assertEquals(privilege.getId(), found.get().getId());
    }

    @Test
    void testFindById_WhenNotExists_ShouldReturnEmpty() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<Privilege> found = repositoryPrivilege.findById(nonExistentId);

        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll_ShouldReturnAllEntities() {
        repositoryPrivilege.save(new Privilege("GENERIC_ALL_1"));
        repositoryPrivilege.save(new Privilege("GENERIC_ALL_2"));
        repositoryPrivilege.save(new Privilege("GENERIC_ALL_3"));
        entityManager.flush();

        List<Privilege> all = repositoryPrivilege.findAll();

        assertEquals(3, all.size());
    }

    @Test
    void testFindAll_WithPageable_ShouldReturnPaginatedResults() {
        for (int i = 1; i <= 15; i++) {
            repositoryPrivilege.save(new Privilege("GENERIC_PAGE_" + i));
        }
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 5);
        Page<Privilege> page = repositoryPrivilege.findAll(pageable);

        assertEquals(5, page.getContent().size());
        assertEquals(15, page.getTotalElements());
        assertEquals(3, page.getTotalPages());
    }

    @Test
    void testFindAll_WithSort_ShouldReturnSortedResults() {
        repositoryPrivilege.save(new Privilege("GENERIC_CHARLIE"));
        repositoryPrivilege.save(new Privilege("GENERIC_ALPHA"));
        repositoryPrivilege.save(new Privilege("GENERIC_BRAVO"));
        entityManager.flush();

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        List<Privilege> sorted = repositoryPrivilege.findAll(sort);

        assertEquals("GENERIC_ALPHA", sorted.get(0).getName());
        assertEquals("GENERIC_BRAVO", sorted.get(1).getName());
        assertEquals("GENERIC_CHARLIE", sorted.get(2).getName());
    }

    @Test
    void testDelete_ShouldRemoveEntity() {
        Privilege privilege = new Privilege("GENERIC_DELETE");
        entityManager.persist(privilege);
        entityManager.flush();
        UUID id = privilege.getId();

        repositoryPrivilege.delete(privilege);
        entityManager.flush();

        assertFalse(repositoryPrivilege.existsById(id));
    }

    @Test
    void testDeleteById_ShouldRemoveEntity() {
        Privilege privilege = new Privilege("GENERIC_DELETE_BY_ID");
        entityManager.persist(privilege);
        entityManager.flush();
        UUID id = privilege.getId();

        repositoryPrivilege.deleteById(id);
        entityManager.flush();

        assertFalse(repositoryPrivilege.existsById(id));
    }

    @Test
    void testExistsById_WhenExists_ShouldReturnTrue() {
        Privilege privilege = new Privilege("GENERIC_EXISTS");
        entityManager.persist(privilege);
        entityManager.flush();

        boolean exists = repositoryPrivilege.existsById(privilege.getId());

        assertTrue(exists);
    }

    @Test
    void testExistsById_WhenNotExists_ShouldReturnFalse() {
        UUID nonExistentId = UUID.randomUUID();

        boolean exists = repositoryPrivilege.existsById(nonExistentId);

        assertFalse(exists);
    }

    @Test
    void testCount_ShouldReturnCorrectCount() {
        repositoryPrivilege.save(new Privilege("GENERIC_COUNT_1"));
        repositoryPrivilege.save(new Privilege("GENERIC_COUNT_2"));
        entityManager.flush();

        long count = repositoryPrivilege.count();

        assertEquals(2, count);
    }

    @Test
    void testSaveAll_ShouldPersistMultipleEntities() {
        List<Privilege> privileges = List.of(
                new Privilege("GENERIC_BULK_1"),
                new Privilege("GENERIC_BULK_2"),
                new Privilege("GENERIC_BULK_3")
        );

        repositoryPrivilege.saveAll(privileges);
        entityManager.flush();

        assertEquals(3, repositoryPrivilege.count());
    }

    @Test
    void testDeleteAll_ShouldRemoveAllEntities() {
        repositoryPrivilege.save(new Privilege("GENERIC_DELETE_ALL_1"));
        repositoryPrivilege.save(new Privilege("GENERIC_DELETE_ALL_2"));
        entityManager.flush();

        repositoryPrivilege.deleteAll();
        entityManager.flush();

        assertEquals(0, repositoryPrivilege.count());
    }

    @Test
    void testFindAllById_ShouldReturnMatchingEntities() {
        Privilege p1 = repositoryPrivilege.save(new Privilege("GENERIC_FIND_ALL_1"));
        Privilege p2 = repositoryPrivilege.save(new Privilege("GENERIC_FIND_ALL_2"));
        repositoryPrivilege.save(new Privilege("GENERIC_FIND_ALL_3"));
        entityManager.flush();

        List<UUID> ids = List.of(p1.getId(), p2.getId());
        List<Privilege> found = repositoryPrivilege.findAllById(ids);

        assertEquals(2, found.size());
    }

    @Test
    void testSaveAndFlush_ShouldPersistImmediately() {
        Privilege privilege = new Privilege("GENERIC_SAVE_FLUSH");

        Privilege saved = repositoryPrivilege.saveAndFlush(privilege);

        assertNotNull(saved.getId());
        assertTrue(repositoryPrivilege.existsById(saved.getId()));
    }

    @Test
    void testFlush_ShouldSynchronizeChanges() {
        Privilege privilege = new Privilege("GENERIC_FLUSH");
        repositoryPrivilege.save(privilege);

        repositoryPrivilege.flush();

        assertTrue(repositoryPrivilege.existsById(privilege.getId()));
    }

    @Test
    void testFindById_WithPageable_ShouldReturnPagedResult() {
        Privilege privilege = new Privilege("GENERIC_FIND_ID_PAGE");
        entityManager.persist(privilege);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Privilege> page = repositoryPrivilege.findById(pageable, privilege.getId());

        assertEquals(1, page.getTotalElements());
        assertEquals(privilege.getId(), page.getContent().get(0).getId());
    }

    @Test
    void testUpdate_ShouldModifyEntity() {
        Privilege privilege = new Privilege("GENERIC_UPDATE_OLD");
        entityManager.persist(privilege);
        entityManager.flush();
        entityManager.clear();

        Privilege managed = repositoryPrivilege.findById(privilege.getId()).orElseThrow();
        managed.setName("GENERIC_UPDATE_NEW");
        repositoryPrivilege.save(managed);
        entityManager.flush();

        Privilege updated = repositoryPrivilege.findById(privilege.getId()).orElseThrow();
        assertEquals("GENERIC_UPDATE_NEW", updated.getName());
    }

    @Test
    void testAuditFields_ShouldBePopulatedOnSave() {
        Privilege privilege = new Privilege("GENERIC_AUDIT");

        Privilege saved = repositoryPrivilege.save(privilege);
        entityManager.flush();

        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void testPagination_WithMultiplePages_ShouldNavigateCorrectly() {
        for (int i = 1; i <= 25; i++) {
            repositoryPrivilege.save(new Privilege("GENERIC_MULTI_" + i));
        }
        entityManager.flush();

        Pageable firstPage = PageRequest.of(0, 10);
        Pageable secondPage = PageRequest.of(1, 10);

        Page<Privilege> page1 = repositoryPrivilege.findAll(firstPage);
        Page<Privilege> page2 = repositoryPrivilege.findAll(secondPage);

        assertEquals(10, page1.getContent().size());
        assertEquals(10, page2.getContent().size());
        assertEquals(25, page1.getTotalElements());
        assertEquals(3, page1.getTotalPages());
    }
}