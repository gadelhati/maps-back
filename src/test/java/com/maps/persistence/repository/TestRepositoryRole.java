package com.maps.persistence.repository;

import com.maps.persistence.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@DataJpaTest
@ActiveProfiles("test")
class TestRepositoryRole {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private RepositoryRole repositoryRole;

    private Role adminRole;
    private Role userRole;
    private Role viewerRole;

    @BeforeEach
    void setUp() {
        // Create simple roles without privileges to avoid auditing issues
        adminRole = new Role();
        adminRole.setName("ADMIN");
        entityManager.persistAndFlush(adminRole);

        userRole = new Role();
        userRole.setName("USER");
        entityManager.persistAndFlush(userRole);

        viewerRole = new Role();
        viewerRole.setName("VIEWER");
        entityManager.persistAndFlush(viewerRole);

        entityManager.clear();
    }

    @Test
    void findByName_shouldReturnRole_whenRoleExists() {
        Role foundRole = repositoryRole.findByName("ADMIN");

        assertNotNull(foundRole);
        assertEquals("ADMIN", foundRole.getName());
    }

    @Test
    void findByName_shouldReturnNull_whenRoleDoesNotExist() {
        Role foundRole = repositoryRole.findByName("NONEXISTENT");

        assertNull(foundRole);
    }

    @Test
    void findById_shouldReturnRole_whenRoleExists() {
        Optional<Role> foundRole = repositoryRole.findById(adminRole.getId());

        assertTrue(foundRole.isPresent());
        assertEquals("ADMIN", foundRole.get().getName());
        assertEquals(adminRole.getId(), foundRole.get().getId());
    }

    @Test
    void findById_shouldReturnEmpty_whenRoleDoesNotExist() {
        Optional<Role> foundRole = repositoryRole.findById(UUID.randomUUID());

        assertFalse(foundRole.isPresent());
    }

    @Test
    void existsByNameIgnoreCase_shouldReturnTrue_whenRoleExists() {
        boolean exists = repositoryRole.existsByNameIgnoreCase("admin");

        assertTrue(exists);
    }

    @Test
    void existsByNameIgnoreCase_shouldReturnTrue_whenRoleExistsWithDifferentCase() {
        boolean exists = repositoryRole.existsByNameIgnoreCase("ADMIN");

        assertTrue(exists);
    }

    @Test
    void existsByNameIgnoreCase_shouldReturnFalse_whenRoleDoesNotExist() {
        boolean exists = repositoryRole.existsByNameIgnoreCase("NONEXISTENT");

        assertFalse(exists);
    }

    @Test
    void existsByNameIgnoreCaseAndIdNot_shouldReturnFalse_whenSameRole() {
        boolean exists = repositoryRole.existsByNameIgnoreCaseAndIdNot("ADMIN", adminRole.getId());

        assertFalse(exists);
    }

    @Test
    void existsByNameIgnoreCaseAndIdNot_shouldReturnTrue_whenDifferentRoleWithSameName() {
        boolean exists = repositoryRole.existsByNameIgnoreCaseAndIdNot("ADMIN", userRole.getId());

        assertTrue(exists);
    }

    @Test
    void existsByNameIgnoreCaseAndIdNot_shouldReturnFalse_whenRoleDoesNotExist() {
        boolean exists = repositoryRole.existsByNameIgnoreCaseAndIdNot("NONEXISTENT", adminRole.getId());

        assertFalse(exists);
    }

    @Test
    void save_shouldPersistNewRole() {
        Role moderatorRole = new Role();
        moderatorRole.setName("MODERATOR");

        Role savedRole = repositoryRole.save(moderatorRole);

        assertNotNull(savedRole.getId());
        assertEquals("MODERATOR", savedRole.getName());

        Role foundRole = repositoryRole.findByName("MODERATOR");
        assertNotNull(foundRole);
        assertEquals("MODERATOR", foundRole.getName());
    }

    @Test
    void save_shouldUpdateExistingRole() {
        adminRole.setName("SUPER_ADMIN");
        
        Role updatedRole = repositoryRole.save(adminRole);

        assertEquals("SUPER_ADMIN", updatedRole.getName());
        assertEquals(adminRole.getId(), updatedRole.getId());

        Role foundRole = repositoryRole.findByName("SUPER_ADMIN");
        assertNotNull(foundRole);
        assertEquals(adminRole.getId(), foundRole.getId());
    }

    @Test
    void delete_shouldRemoveRole() {
        UUID roleId = viewerRole.getId();

        repositoryRole.delete(viewerRole);
        entityManager.flush();

        Optional<Role> deletedRole = repositoryRole.findById(roleId);
        assertFalse(deletedRole.isPresent());

        Role foundRole = repositoryRole.findByName("VIEWER");
        assertNull(foundRole);
    }

    @Test
    void findAll_shouldReturnAllRoles() {
        Iterable<Role> allRoles = repositoryRole.findAll();

        assertNotNull(allRoles);
        
        long count = 0;
        Iterator<Role> iterator = allRoles.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        
        assertEquals(3, count);
    }

    @Test
    void count_shouldReturnCorrectNumber() {
        long count = repositoryRole.count();

        assertEquals(3, count);
    }
}
