package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testRoleEntity() {
        // Given
        Role role = new Role();
        role.setRoleName("ADMIN");

        // When
        Role savedRole = entityManager.persistAndFlush(role);

        // Then
        assertThat(savedRole.getRoleId()).isNotNull();
        assertThat(savedRole.getRoleName()).isEqualTo("ADMIN");
    }

    @Test
    void testUpdateRoleEntity() {
        // Given
        Role role = new Role();
        role.setRoleName("ADMIN");
        Role savedRole = entityManager.persistAndFlush(role);

        // When
        savedRole.setRoleName("USER");
        Role updatedRole = entityManager.persistAndFlush(savedRole);

        // Then
        assertThat(updatedRole.getRoleId()).isEqualTo(savedRole.getRoleId());
        assertThat(updatedRole.getRoleName()).isEqualTo("USER");
    }

    @Test
    void testDeleteRoleEntity() {
        // Given
        Role role = new Role();
        role.setRoleName("ADMIN");
        Role savedRole = entityManager.persistAndFlush(role);

        // When
        entityManager.remove(savedRole);
        entityManager.flush();
        Role foundRole = entityManager.find(Role.class, savedRole.getRoleId());

        // Then
        assertThat(foundRole).isNull();
    }

    @Test
    void testRoleConstructorAndGetters() {
        // Given
        Role role = new Role(1, "ADMIN");

        // Then
        assertThat(role.getRoleId()).isEqualTo(1);
        assertThat(role.getRoleName()).isEqualTo("ADMIN");
    }

    @Test
    void testRoleSetters() {
        // Given
        Role role = new Role();
        role.setRoleId(2);
        role.setRoleName("USER");

        // Then
        assertThat(role.getRoleId()).isEqualTo(2);
        assertThat(role.getRoleName()).isEqualTo("USER");
    }

    @Test
    void testRoleEqualsAndHashCode() {
        // Given
        Role role1 = new Role(1, "ADMIN");
        Role role2 = new Role(1, "ADMIN");
        Role role3 = new Role(2, "USER");

        // Then
        assertThat(role1).isEqualTo(role2);
        assertThat(role1).isNotEqualTo(role3);
        assertThat(role1.hashCode()).hasSameHashCodeAs(role2.hashCode());
        assertThat(role1.hashCode()).isNotEqualTo(role3.hashCode());
    }

    @Test
    void testRoleToString() {
        // Given
        Role role = new Role(1, "ADMIN");

        // When
        String roleString = role.toString();

        // Then
        assertThat(roleString).contains("Role(roleId=1", "roleName=ADMIN");
    }
}
