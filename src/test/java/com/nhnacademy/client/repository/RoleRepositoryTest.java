package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("devH2")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository.save(new Role(1, "ROLE_USER"));
        roleRepository.save(new Role(2, "ROLE_ADMIN"));
    }

    @Test
    void testFindByRoleName() {
        Role roleUser = roleRepository.findByRoleName("ROLE_USER");
        assertThat(roleUser).isNotNull();
        assertThat(roleUser.getRoleName()).isEqualTo("ROLE_USER");

        Role roleAdmin = roleRepository.findByRoleName("ROLE_ADMIN");
        assertThat(roleAdmin).isNotNull();
        assertThat(roleAdmin.getRoleName()).isEqualTo("ROLE_ADMIN");

        Role roleNonExistent = roleRepository.findByRoleName("ROLE_NON_EXISTENT");
        assertThat(roleNonExistent).isNull();
    }
}
