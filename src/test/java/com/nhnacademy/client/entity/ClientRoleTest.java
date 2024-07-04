package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRoleTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testClientRoleEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20);
        entityManager.persist(clientGrade);

        Client client = Client.builder()
                .clientGrade(clientGrade)
                .clientEmail("test@example.com")
                .clientPassword("password")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .isDeleted(false)
                .clientDeleteDate(null)
                .build();
        entityManager.persist(client);

        Role role = new Role();
        role.setRoleName("ADMIN");
        entityManager.persist(role);

        ClientRole clientRole = ClientRole.builder()
                .client(client)
                .role(role)
                .build();

        // When
        ClientRole savedClientRole = entityManager.persistAndFlush(clientRole);

        // Then
        assertThat(savedClientRole.getClientRoleId()).isNotNull();
        assertThat(savedClientRole.getClient()).isEqualTo(client);
        assertThat(savedClientRole.getRole()).isEqualTo(role);
    }

    @Test
    void testUpdateClientRoleEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20);
        entityManager.persist(clientGrade);

        Client client = Client.builder()
                .clientGrade(clientGrade)
                .clientEmail("test@example.com")
                .clientPassword("password")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .isDeleted(false)
                .clientDeleteDate(null)
                .build();
        entityManager.persist(client);

        Role role1 = new Role();
        role1.setRoleName("ADMIN");
        entityManager.persist(role1);

        Role role2 = new Role();
        role2.setRoleName("USER");
        entityManager.persist(role2);

        ClientRole clientRole = ClientRole.builder()
                .client(client)
                .role(role1)
                .build();
        ClientRole savedClientRole = entityManager.persistAndFlush(clientRole);

        // When
        savedClientRole.setRole(role2);
        ClientRole updatedClientRole = entityManager.persistAndFlush(savedClientRole);

        // Then
        assertThat(updatedClientRole.getClientRoleId()).isEqualTo(savedClientRole.getClientRoleId());
        assertThat(updatedClientRole.getRole()).isEqualTo(role2);
    }

    @Test
    void testDeleteClientRoleEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20);
        entityManager.persist(clientGrade);

        Client client = Client.builder()
                .clientGrade(clientGrade)
                .clientEmail("test@example.com")
                .clientPassword("password")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .isDeleted(false)
                .clientDeleteDate(null)
                .build();
        entityManager.persist(client);

        Role role = new Role();
        role.setRoleName("ADMIN");
        entityManager.persist(role);

        ClientRole clientRole = ClientRole.builder()
                .client(client)
                .role(role)
                .build();
        ClientRole savedClientRole = entityManager.persistAndFlush(clientRole);

        // When
        entityManager.remove(savedClientRole);
        entityManager.flush();
        ClientRole foundClientRole = entityManager.find(ClientRole.class, savedClientRole.getClientRoleId());

        // Then
        assertThat(foundClientRole).isNull();
    }

    @Test
    void testClientRoleConstructorAndGetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        Role role = new Role();
        role.setRoleName("ADMIN");
        role.setRoleId(1);
        ClientRole clientRole = new ClientRole(1L, client, role);

        // Then
        assertThat(clientRole.getClientRoleId()).isEqualTo(1L);
        assertThat(clientRole.getClient()).isEqualTo(client);
        assertThat(clientRole.getRole()).isEqualTo(role);
    }

    @Test
    void testClientRoleSetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        Role role1 = new Role();
        role1.setRoleName("ADMIN");
        role1.setRoleId(1);
        Role role2 = new Role();
        role2.setRoleName("USER");
        role2.setRoleId(2);
        ClientRole clientRole = new ClientRole();
        clientRole.setClientRoleId(2L);
        clientRole.setClient(client);
        clientRole.setRole(role2);

        // Then
        assertThat(clientRole.getClientRoleId()).isEqualTo(2L);
        assertThat(clientRole.getClient()).isEqualTo(client);
        assertThat(clientRole.getRole()).isEqualTo(role2);
    }

    @Test
    void testClientRoleEqualsAndHashCode() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email1@example.com", "password1", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        Role role = new Role();
        role.setRoleName("ADMIN");
        role.setRoleId(1);
        ClientRole clientRole1 = new ClientRole(1L, client, role);
        ClientRole clientRole2 = new ClientRole(1L, client, role);
        ClientRole clientRole3 = new ClientRole(2L, client, role);

        // Then
        assertThat(clientRole1).isEqualTo(clientRole2);
        assertThat(clientRole1).isNotEqualTo(clientRole3);
        assertThat(clientRole1.hashCode()).hasSameHashCodeAs(clientRole2.hashCode());
        assertThat(clientRole1.hashCode()).isNotEqualTo(clientRole3.hashCode());
    }

    @Test
    void testClientRoleToString() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        Role role = new Role();
        role.setRoleName("ADMIN");
        role.setRoleId(1);
        ClientRole clientRole = new ClientRole(1L, client, role);

        // When
        String clientRoleString = clientRole.toString();

        // Then
        assertThat(clientRoleString).contains("ClientRole(clientRoleId=1", "client=Client(clientId=1", "role=Role(roleId=1", "roleName=ADMIN");
    }
}
