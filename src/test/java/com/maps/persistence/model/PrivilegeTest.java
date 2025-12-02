package com.maps.persistence.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class PrivilegeTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCreatePrivilege_WithValidName_ShouldBeValid() {
        Privilege privilege = new Privilege("VALID_PRIVILEGE");

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        assertTrue(violations.isEmpty());
        assertEquals("VALID_PRIVILEGE", privilege.getName());
    }

    @Test
    void testCreatePrivilege_WithNoArgsConstructor_ShouldCreateInstance() {
        Privilege privilege = new Privilege();

        assertNotNull(privilege);
        assertNull(privilege.getName());
    }

    @Test
    void testCreatePrivilege_WithAllArgsConstructor_ShouldSetName() {
        String name = "ADMIN_PRIVILEGE";
        Privilege privilege = new Privilege(name);

        assertEquals(name, privilege.getName());
    }

    @Test
    void testCreatePrivilege_WithNullName_ShouldBeInvalid() {
        Privilege privilege = new Privilege(null);

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testCreatePrivilege_WithBlankName_ShouldBeInvalid() {
        Privilege privilege = new Privilege("   ");

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        // A validação @NotBlank só é acionada se o valor não for null
        // Um valor com apenas espaços em branco deve falhar na validação @NotBlank
        assertFalse(violations.isEmpty());
        boolean hasBlankViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        assertTrue(hasBlankViolation, "Should have violation on 'name' field");
    }

    @Test
    void testCreatePrivilege_WithEmptyName_ShouldBeInvalid() {
        Privilege privilege = new Privilege("");

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        assertFalse(violations.isEmpty());
    }

    @Test
    void testPrivilege_ExtendsGenericAuditEntity() {
        Privilege privilege = new Privilege("TEST");

        assertTrue(privilege instanceof GenericAuditEntity);
    }

    @Test
    void testPrivilege_GetName_ShouldReturnCorrectValue() {
        String name = "READ_PRIVILEGE";
        Privilege privilege = new Privilege(name);

        assertEquals(name, privilege.getName());
    }

    @Test
    void testPrivilege_SetName_ShouldUpdateValue() {
        Privilege privilege = new Privilege("OLD_NAME");
        String newName = "NEW_NAME";

        privilege.setName(newName);

        assertEquals(newName, privilege.getName());
    }

    @Test
    void testPrivilege_SetId_ShouldUpdateValue() {
        Privilege privilege = new Privilege("TEST");
        UUID id = UUID.randomUUID();

        privilege.setId(id);

        assertEquals(id, privilege.getId());
    }

    @Test
    void testPrivilege_GetId_WhenNotSet_ShouldReturnNull() {
        Privilege privilege = new Privilege("TEST");

        assertNull(privilege.getId());
    }

    @Test
    void testPrivilege_IsAudited_ShouldHaveAuditAnnotation() {
        Class<Privilege> clazz = Privilege.class;

        assertTrue(clazz.isAnnotationPresent(org.hibernate.envers.Audited.class));
    }

    @Test
    void testPrivilege_IsEntity_ShouldHaveEntityAnnotation() {
        Class<Privilege> clazz = Privilege.class;

        assertTrue(clazz.isAnnotationPresent(jakarta.persistence.Entity.class));
    }

    @Test
    void testPrivilege_HasTableAnnotation_WithCorrectConstraints() {
        Class<Privilege> clazz = Privilege.class;

        assertTrue(clazz.isAnnotationPresent(jakarta.persistence.Table.class));
        jakarta.persistence.Table tableAnnotation = clazz.getAnnotation(jakarta.persistence.Table.class);
        assertEquals(1, tableAnnotation.uniqueConstraints().length);
        assertEquals(1, tableAnnotation.indexes().length);
    }

    @Test
    void testPrivilege_WithSpecialCharactersInName_ShouldBeValid() {
        Privilege privilege = new Privilege("PRIVILEGE_123-TEST");

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        assertTrue(violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().equals("name") &&
                        (v.getMessage().contains("null") || v.getMessage().contains("blank"))));
    }

    @Test
    void testPrivilege_WithLongName_ShouldBeValid() {
        String longName = "A".repeat(255);
        Privilege privilege = new Privilege(longName);

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        assertTrue(violations.stream()
                .noneMatch(v -> v.getPropertyPath().toString().equals("name") &&
                        (v.getMessage().contains("null") || v.getMessage().contains("blank"))));
    }

    @Test
    void testPrivilege_ToString_ShouldNotThrowException() {
        Privilege privilege = new Privilege("TEST");

        assertDoesNotThrow(() -> privilege.toString());
    }

    @Test
    void testPrivilege_HashCode_ShouldNotThrowException() {
        Privilege privilege = new Privilege("TEST");

        assertDoesNotThrow(() -> privilege.hashCode());
    }

    @Test
    void testPrivilege_Equals_WithSameObject_ShouldReturnTrue() {
        Privilege privilege = new Privilege("TEST");

        assertEquals(privilege, privilege);
    }

    @Test
    void testPrivilege_Equals_WithNull_ShouldReturnFalse() {
        Privilege privilege = new Privilege("TEST");

        assertNotEquals(privilege, null);
    }

    @Test
    void testPrivilege_SetName_WithNull_ThenValidate_ShouldBeInvalid() {
        Privilege privilege = new Privilege("VALID");
        privilege.setName(null);

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        assertFalse(violations.isEmpty());
    }

    @Test
    void testPrivilege_SetName_WithBlank_ThenValidate_ShouldBeInvalid() {
        Privilege privilege = new Privilege("VALID");
        privilege.setName("  ");

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        assertFalse(violations.isEmpty());
    }
}
