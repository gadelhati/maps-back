package com.maps.persistence.repository;

import com.maps.persistence.model.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TestRepositoryPrivilege {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private RepositoryPrivilege repositoryPrivilege;

    private Privilege readPrivilege;
    private Privilege writePrivilege;
    private Privilege deletePrivilege;

    @BeforeEach
    void setUp() {
        readPrivilege = new Privilege();
        readPrivilege.setName("READ_PRIVILEGE");
        entityManager.persistAndFlush(readPrivilege);

        writePrivilege = new Privilege();
        writePrivilege.setName("WRITE_PRIVILEGE");
        entityManager.persistAndFlush(writePrivilege);

        deletePrivilege = new Privilege();
        deletePrivilege.setName("DELETE_PRIVILEGE");
        entityManager.persistAndFlush(deletePrivilege);

        entityManager.clear();
    }

    @Test
    void findByName_shouldReturnPrivileges_whenPrivilegeExists() {
        Set<Privilege> foundPrivileges = repositoryPrivilege.findByName("READ_PRIVILEGE");

        assertNotNull(foundPrivileges);
        assertEquals(1, foundPrivileges.size());
        
        Privilege privilege = foundPrivileges.iterator().next();
        assertEquals("READ_PRIVILEGE", privilege.getName());
        assertEquals(readPrivilege.getId(), privilege.getId());
    }

    @Test
    void findByName_shouldReturnEmptySet_whenPrivilegeDoesNotExist() {
        Set<Privilege> foundPrivileges = repositoryPrivilege.findByName("NONEXISTENT_PRIVILEGE");

        assertNotNull(foundPrivileges);
        assertTrue(foundPrivileges.isEmpty());
    }

    @Test
    void findById_shouldReturnPrivilege_whenPrivilegeExists() {
        Optional<Privilege> foundPrivilege = repositoryPrivilege.findById(readPrivilege.getId());

        assertTrue(foundPrivilege.isPresent());
        assertEquals("READ_PRIVILEGE", foundPrivilege.get().getName());
        assertEquals(readPrivilege.getId(), foundPrivilege.get().getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenPrivilegeDoesNotExist() {
        Optional<Privilege> foundPrivilege = repositoryPrivilege.findById(UUID.randomUUID());

        assertFalse(foundPrivilege.isPresent());
    }

    @Test
    void existsByNameIgnoreCase_shouldReturnTrue_whenPrivilegeExists() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCase("read_privilege");

        assertTrue(exists);
    }

    @Test
    void existsByNameIgnoreCase_shouldReturnTrue_whenPrivilegeExistsWithDifferentCase() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCase("READ_PRIVILEGE");

        assertTrue(exists);
    }

    @Test
    void existsByNameIgnoreCase_shouldReturnFalse_whenPrivilegeDoesNotExist() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCase("NONEXISTENT_PRIVILEGE");

        assertFalse(exists);
    }

    @Test
    void existsByNameIgnoreCaseAndIdNot_shouldReturnFalse_whenSamePrivilege() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCaseAndIdNot("READ_PRIVILEGE", readPrivilege.getId());

        assertFalse(exists);
    }

    @Test
    void existsByNameIgnoreCaseAndIdNot_shouldReturnTrue_whenDifferentPrivilegeWithSameName() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCaseAndIdNot("READ_PRIVILEGE", writePrivilege.getId());

        assertTrue(exists);
    }

    @Test
    void existsByNameIgnoreCaseAndIdNot_shouldReturnFalse_whenPrivilegeDoesNotExist() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCaseAndIdNot("NONEXISTENT_PRIVILEGE", readPrivilege.getId());

        assertFalse(exists);
    }

    @Test
    void save_shouldPersistNewPrivilege() {
        Privilege executePrivilege = new Privilege();
        executePrivilege.setName("EXECUTE_PRIVILEGE");

        Privilege savedPrivilege = repositoryPrivilege.save(executePrivilege);

        assertNotNull(savedPrivilege.getId());
        assertEquals("EXECUTE_PRIVILEGE", savedPrivilege.getName());

        Set<Privilege> foundPrivileges = repositoryPrivilege.findByName("EXECUTE_PRIVILEGE");
        assertEquals(1, foundPrivileges.size());
        assertEquals("EXECUTE_PRIVILEGE", foundPrivileges.iterator().next().getName());
    }

    @Test
    void save_shouldUpdateExistingPrivilege() {
        readPrivilege.setName("FULL_READ_PRIVILEGE");
        
        Privilege updatedPrivilege = repositoryPrivilege.save(readPrivilege);

        assertEquals("FULL_READ_PRIVILEGE", updatedPrivilege.getName());
        assertEquals(readPrivilege.getId(), updatedPrivilege.getId());

        Set<Privilege> foundPrivileges = repositoryPrivilege.findByName("FULL_READ_PRIVILEGE");
        assertEquals(1, foundPrivileges.size());
        assertEquals(readPrivilege.getId(), foundPrivileges.iterator().next().getId());
    }

    @Test
    void delete_shouldRemovePrivilege() {
        UUID privilegeId = deletePrivilege.getId();

        repositoryPrivilege.delete(deletePrivilege);
        entityManager.flush();

        Optional<Privilege> deletedPrivilege = repositoryPrivilege.findById(privilegeId);
        assertFalse(deletedPrivilege.isPresent());

        Set<Privilege> foundPrivileges = repositoryPrivilege.findByName("DELETE_PRIVILEGE");
        assertTrue(foundPrivileges.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllPrivileges() {
        Iterable<Privilege> allPrivileges = repositoryPrivilege.findAll();

        assertNotNull(allPrivileges);
        
        long count = 0;
        for (Privilege privilege : allPrivileges) {
            count++;
        }
        
        assertEquals(3, count);
    }

    @Test
    void count_shouldReturnCorrectNumber() {
        long count = repositoryPrivilege.count();

        assertEquals(3, count);
    }

    @Test
    void findByName_shouldBeCaseSensitive() {
        Set<Privilege> foundPrivileges = repositoryPrivilege.findByName("read_privilege");

        assertTrue(foundPrivileges.isEmpty());
    }

    @Test
    void existsByNameIgnoreCase_shouldWorkWithLowerCase() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCase("write_privilege");

        assertTrue(exists);
    }

    @Test
    void existsByNameIgnoreCase_shouldWorkWithMixedCase() {
        boolean exists = repositoryPrivilege.existsByNameIgnoreCase("Delete_Privilege");

        assertTrue(exists);
    }
}
