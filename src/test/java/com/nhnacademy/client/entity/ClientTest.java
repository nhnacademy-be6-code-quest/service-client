package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class ClientTest {

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void testClientEntity() {
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

        // When
        Client savedClient = entityManager.persistAndFlush(client);

        // Then
        assertThat(savedClient.getClientId()).isNotNull();
        assertThat(savedClient.getClientGrade()).isEqualTo(clientGrade);
        assertThat(savedClient.getClientEmail()).isEqualTo("test@example.com");
        assertThat(savedClient.getClientPassword()).isEqualTo("password");
        assertThat(savedClient.getClientName()).isEqualTo("John Doe");
        assertThat(savedClient.getClientBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(savedClient.getClientCreatedAt()).isNotNull();
        assertThat(savedClient.getLastLoginDate()).isNotNull();
        assertThat(savedClient.isDeleted()).isFalse();
        assertThat(savedClient.getClientDeleteDate()).isNull();
    }

    @Test
    void testUpdateClientEntity() {
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
        Client savedClient = entityManager.persistAndFlush(client);

        // When
        savedClient.setClientEmail("updated@example.com");
        savedClient.setClientPassword("newpassword");
        savedClient.setClientName("Jane Doe");
        savedClient.setClientBirth(LocalDate.of(1991, 2, 2));
        savedClient.setDeleted(true);
        savedClient.setClientDeleteDate(LocalDateTime.now());
        Client updatedClient = entityManager.persistAndFlush(savedClient);

        // Then
        assertThat(updatedClient.getClientId()).isEqualTo(savedClient.getClientId());
        assertThat(updatedClient.getClientEmail()).isEqualTo("updated@example.com");
        assertThat(updatedClient.getClientPassword()).isEqualTo("newpassword");
        assertThat(updatedClient.getClientName()).isEqualTo("Jane Doe");
        assertThat(updatedClient.getClientBirth()).isEqualTo(LocalDate.of(1991, 2, 2));
        assertThat(updatedClient.isDeleted()).isTrue();
        assertThat(updatedClient.getClientDeleteDate()).isNotNull();
    }

    @Test
    void testDeleteClientEntity() {
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
        Client savedClient = entityManager.persistAndFlush(client);

        // When
        entityManager.remove(savedClient);
        entityManager.flush();
        Client foundClient = entityManager.find(Client.class, savedClient.getClientId());

        // Then
        assertThat(foundClient).isNull();
    }

    @Test
    void testClientConstructorAndGetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);

        // Then
        assertThat(client.getClientId()).isEqualTo(1L);
        assertThat(client.getClientGrade()).isEqualTo(clientGrade);
        assertThat(client.getClientEmail()).isEqualTo("email@example.com");
        assertThat(client.getClientPassword()).isEqualTo("password");
        assertThat(client.getClientName()).isEqualTo("John Doe");
        assertThat(client.getClientBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(client.getClientCreatedAt()).isNotNull();
        assertThat(client.getLastLoginDate()).isNotNull();
        assertThat(client.isDeleted()).isFalse();
        assertThat(client.getClientDeleteDate()).isNull();
    }

    @Test
    void testClientSetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client();
        client.setClientId(2L);
        client.setClientGrade(clientGrade);
        client.setClientEmail("set@example.com");
        client.setClientPassword("setpassword");
        client.setClientName("Set Name");
        client.setClientBirth(LocalDate.of(1991, 2, 2));
        client.setClientCreatedAt(LocalDateTime.now());
        client.setLastLoginDate(LocalDateTime.now());
        client.setDeleted(true);
        client.setClientDeleteDate(LocalDateTime.now());

        // Then
        assertThat(client.getClientId()).isEqualTo(2L);
        assertThat(client.getClientGrade()).isEqualTo(clientGrade);
        assertThat(client.getClientEmail()).isEqualTo("set@example.com");
        assertThat(client.getClientPassword()).isEqualTo("setpassword");
        assertThat(client.getClientName()).isEqualTo("Set Name");
        assertThat(client.getClientBirth()).isEqualTo(LocalDate.of(1991, 2, 2));
        assertThat(client.getClientCreatedAt()).isNotNull();
        assertThat(client.getLastLoginDate()).isNotNull();
        assertThat(client.isDeleted()).isTrue();
        assertThat(client.getClientDeleteDate()).isNotNull();
    }

    @Test
    void testClientEqualsAndHashCode() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client1 = new Client(1L, clientGrade, "email1@example.com", "password1", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().atStartOfDay(), false, null);
        Client client2 = new Client(1L, clientGrade, "email1@example.com", "password1", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().atStartOfDay(), false, null);
        Client client3 = new Client(2L, clientGrade, "email2@example.com", "password2", "Jane Doe", LocalDate.of(1991, 2, 2), LocalDateTime.now().toLocalDate().atStartOfDay(), LocalDateTime.now().toLocalDate().atStartOfDay(), true, LocalDateTime.now().toLocalDate().atStartOfDay());

        // Then
        assertThat(client1).isEqualTo(client2);
        assertThat(client1).isNotEqualTo(client3);
        assertThat(client1.hashCode()).isEqualTo(client2.hashCode());
        assertThat(client1.hashCode()).isNotEqualTo(client3.hashCode());
    }

    @Test
    void testClientToString() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);

        // When
        String clientString = client.toString();

        // Then
        assertThat(clientString).contains("Client(clientId=1", "clientGrade=ClientGrade(clientGradeId=1", "clientEmail=email@example.com", "clientPassword=password", "clientName=John Doe", "clientBirth=1990-01-01", "clientCreatedAt=", "lastLoginDate=", "isDeleted=false", "clientDeleteDate=null");
    }
}
