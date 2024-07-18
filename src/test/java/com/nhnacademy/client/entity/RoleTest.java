package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testNoArgsConstructor() {
        Role role = new Role();
        assertNotNull(role);
    }

    @Test
    void testAllArgsConstructor() {
        Role role = new Role(
                1,
                "ADMIN"
        );

        assertAll(
                () -> assertEquals(1, role.getRoleId()),
                () -> assertEquals("ADMIN", role.getRoleName())
        );
    }

    @Test
    void testBuilder() {
        Role role = Role.builder()
                .roleId(1)
                .roleName("ADMIN")
                .build();

        assertAll(
                () -> assertEquals(1, role.getRoleId()),
                () -> assertEquals("ADMIN", role.getRoleName())
        );
    }

    @Test
    void testSettersAndGetters() {
        Role role = new Role();

        role.setRoleId(1);
        role.setRoleName("ADMIN");

        assertAll(
                () -> assertEquals(1, role.getRoleId()),
                () -> assertEquals("ADMIN", role.getRoleName())
        );
    }
}
