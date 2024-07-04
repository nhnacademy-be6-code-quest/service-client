package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientRole;
import com.nhnacademy.client.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("devH2")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRoleRepositoryTest {

    @Autowired
    private ClientRoleRepository clientRoleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        Role userRole = roleRepository.save(new Role(null, "ROLE_USER"));
        Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN"));

        client = clientRepository.save(Client.builder()
                .clientEmail("test@example.com")
                .clientPassword("password")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .isDeleted(false)
                .build());

        clientRoleRepository.save(ClientRole.builder().client(client).role(userRole).build());
        clientRoleRepository.save(ClientRole.builder().client(client).role(adminRole).build());
    }

    @Test
    void testFindRolesByClient() {
        List<Role> roles = clientRoleRepository.findRolesByClient(client);

        assertThat(roles).isNotNull();
        assertThat(roles).hasSize(2);
        assertThat(roles).extracting("roleName").containsExactlyInAnyOrder("ROLE_USER", "ROLE_ADMIN");
    }
}
