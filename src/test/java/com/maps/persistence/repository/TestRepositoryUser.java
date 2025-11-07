package com.maps.persistence.repository;

import com.maps.persistence.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TestRepositoryUser {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private RepositoryUser repositoryUser;

    private User user1;
    private User user2;
    private User inactiveUser;

    @BeforeEach
    void setUp() {
        // Active user 1 - without roles to simplify
        user1 = new User();
        user1.setUsername("testuser1");
        user1.setEmail("test1@example.com");
        user1.setPassword("password123");
        user1.setActive(true);
        user1.setAttempt(0);
        entityManager.persistAndFlush(user1);

        // Active user 2
        user2 = new User();
        user2.setUsername("testuser2");
        user2.setEmail("test2@example.com");
        user2.setPassword("password456");
        user2.setActive(true);
        user2.setAttempt(0);
        entityManager.persistAndFlush(user2);

        // Inactive user
        inactiveUser = new User();
        inactiveUser.setUsername("inactiveuser");
        inactiveUser.setEmail("inactive@example.com");
        inactiveUser.setPassword("password789");
        inactiveUser.setActive(false);
        inactiveUser.setAttempt(3);
        entityManager.persistAndFlush(inactiveUser);

        entityManager.clear();
    }

    @Test
    void findByUsername_shouldReturnUser_whenUserExists() {
        Optional<User> foundUser = repositoryUser.findByUsername("testuser1");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser1", foundUser.get().getUsername());
        assertEquals("test1@example.com", foundUser.get().getEmail());
        assertTrue(foundUser.get().getActive());
    }

    @Test
    void findByUsername_shouldReturnEmpty_whenUserDoesNotExist() {
        Optional<User> foundUser = repositoryUser.findByUsername("nonexistent");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByUsername_shouldBeCaseInsensitive_ifConfigured() {
        Optional<User> foundUser = repositoryUser.findByUsername("TESTUSER1");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByEmail_shouldReturnUser_whenEmailExists() {
        Optional<User> foundUser = repositoryUser.findByEmail("test1@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser1", foundUser.get().getUsername());
    }

    @Test
    void findByEmail_shouldReturnEmpty_whenEmailDoesNotExist() {
        Optional<User> foundUser = repositoryUser.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> users = repositoryUser.findAll(pageable);

        assertEquals(3, users.getTotalElements());
        assertTrue(users.getContent().stream()
                .anyMatch(user -> "testuser1".equals(user.getUsername())));
        assertTrue(users.getContent().stream()
                .anyMatch(user -> "testuser2".equals(user.getUsername())));
        assertTrue(users.getContent().stream()
                .anyMatch(user -> "inactiveuser".equals(user.getUsername())));
    }

    @Test
    void findByActive_shouldReturnOnlyActiveUsers() {
        Page<User> activeUsers = repositoryUser.findByActive(true, PageRequest.of(0, 10));

        assertEquals(2, activeUsers.getTotalElements());
        assertTrue(activeUsers.getContent().stream()
                .allMatch(User::getActive));
    }

    @Test
    void existsByUsername_shouldReturnTrue_whenUserExists() {
        boolean exists = repositoryUser.existsByUsername("testuser1");

        assertTrue(exists);
    }

    @Test
    void existsByUsername_shouldReturnFalse_whenUserDoesNotExist() {
        boolean exists = repositoryUser.existsByUsername("nonexistent");

        assertFalse(exists);
    }

    @Test
    void existsByEmail_shouldReturnTrue_whenEmailExists() {
        boolean exists = repositoryUser.existsByEmail("test1@example.com");

        assertTrue(exists);
    }

    @Test
    void existsByEmail_shouldReturnFalse_whenEmailDoesNotExist() {
        boolean exists = repositoryUser.existsByEmail("nonexistent@example.com");

        assertFalse(exists);
    }

    @Test
    void existsByEmailIgnoreCase_shouldReturnTrue_whenEmailExistsWithDifferentCase() {
        boolean exists = repositoryUser.existsByEmailIgnoreCase("TEST1@EXAMPLE.COM");

        assertTrue(exists);
    }

    @Test
    void existsByUsernameIgnoreCase_shouldReturnTrue_whenUsernameExistsWithDifferentCase() {
        boolean exists = repositoryUser.existsByUsernameIgnoreCase("TESTUSER1");

        assertTrue(exists);
    }

    @Test
    void existsByEmailIgnoreCaseAndIdNot_shouldReturnFalse_whenSameUserEmail() {
        boolean exists = repositoryUser.existsByEmailIgnoreCaseAndIdNot("test1@example.com", user1.getId());

        assertFalse(exists);
    }

    @Test
    void existsByEmailIgnoreCaseAndIdNot_shouldReturnTrue_whenDifferentUserEmail() {
        boolean exists = repositoryUser.existsByEmailIgnoreCaseAndIdNot("test1@example.com", user2.getId());

        assertTrue(exists);
    }

    @Test
    void existsByUsernameIgnoreCaseAndIdNot_shouldReturnFalse_whenSameUserUsername() {
        boolean exists = repositoryUser.existsByUsernameIgnoreCaseAndIdNot("testuser1", user1.getId());

        assertFalse(exists);
    }

    @Test
    void existsByUsernameIgnoreCaseAndIdNot_shouldReturnTrue_whenDifferentUserUsername() {
        boolean exists = repositoryUser.existsByUsernameIgnoreCaseAndIdNot("testuser1", user2.getId());

        assertTrue(exists);
    }

    @Test
    void save_shouldPersistNewUser() {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");
        newUser.setPassword("newpassword");
        newUser.setActive(true);
        newUser.setAttempt(0);

        User savedUser = repositoryUser.save(newUser);

        assertNotNull(savedUser.getId());
        assertEquals("newuser", savedUser.getUsername());

        Optional<User> foundUser = repositoryUser.findByUsername("newuser");
        assertTrue(foundUser.isPresent());
    }

    @Test
    void delete_shouldRemoveUser() {
        UUID userId = user1.getId();

        repositoryUser.delete(user1);
        entityManager.flush();

        Optional<User> deletedUser = repositoryUser.findById(userId);
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void findById_shouldReturnUserPage_whenUserExists() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = repositoryUser.findById(pageable, user1.getId());

        assertEquals(1, userPage.getTotalElements());
        assertEquals(user1.getId(), userPage.getContent().get(0).getId());
    }
}
