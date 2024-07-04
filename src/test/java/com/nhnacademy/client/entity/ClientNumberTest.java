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
class ClientNumberTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testClientNumberEntity() {
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

        ClientNumber clientNumber = ClientNumber.builder()
                .client(client)
                .clientPhoneNumber("123-456-7890")
                .build();

        // When
        ClientNumber savedClientNumber = entityManager.persistAndFlush(clientNumber);

        // Then
        assertThat(savedClientNumber.getClientNumberId()).isNotNull();
        assertThat(savedClientNumber.getClient()).isEqualTo(client);
        assertThat(savedClientNumber.getClientPhoneNumber()).isEqualTo("123-456-7890");
    }

    @Test
    void testUpdateClientNumberEntity() {
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

        ClientNumber clientNumber = ClientNumber.builder()
                .client(client)
                .clientPhoneNumber("123-456-7890")
                .build();
        ClientNumber savedClientNumber = entityManager.persistAndFlush(clientNumber);

        // When
        savedClientNumber.setClientPhoneNumber("098-765-4321");
        ClientNumber updatedClientNumber = entityManager.persistAndFlush(savedClientNumber);

        // Then
        assertThat(updatedClientNumber.getClientNumberId()).isEqualTo(savedClientNumber.getClientNumberId());
        assertThat(updatedClientNumber.getClientPhoneNumber()).isEqualTo("098-765-4321");
    }

    @Test
    void testDeleteClientNumberEntity() {
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

        ClientNumber clientNumber = ClientNumber.builder()
                .client(client)
                .clientPhoneNumber("123-456-7890")
                .build();
        ClientNumber savedClientNumber = entityManager.persistAndFlush(clientNumber);

        // When
        entityManager.remove(savedClientNumber);
        entityManager.flush();
        ClientNumber foundClientNumber = entityManager.find(ClientNumber.class, savedClientNumber.getClientNumberId());

        // Then
        assertThat(foundClientNumber).isNull();
    }

    @Test
    void testClientNumberConstructorAndGetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientNumber clientNumber = new ClientNumber(1L, client, "123-456-7890");

        // Then
        assertThat(clientNumber.getClientNumberId()).isEqualTo(1L);
        assertThat(clientNumber.getClient()).isEqualTo(client);
        assertThat(clientNumber.getClientPhoneNumber()).isEqualTo("123-456-7890");
    }

    @Test
    void testClientNumberSetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientNumber clientNumber = new ClientNumber();
        clientNumber.setClientNumberId(2L);
        clientNumber.setClient(client);
        clientNumber.setClientPhoneNumber("098-765-4321");

        // Then
        assertThat(clientNumber.getClientNumberId()).isEqualTo(2L);
        assertThat(clientNumber.getClient()).isEqualTo(client);
        assertThat(clientNumber.getClientPhoneNumber()).isEqualTo("098-765-4321");
    }

    @Test
    void testClientNumberEqualsAndHashCode() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email1@example.com", "password1", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientNumber clientNumber1 = new ClientNumber(1L, client, "123-456-7890");
        ClientNumber clientNumber2 = new ClientNumber(1L, client, "123-456-7890");
        ClientNumber clientNumber3 = new ClientNumber(2L, client, "098-765-4321");

        // Then
        assertThat(clientNumber1).isEqualTo(clientNumber2);
        assertThat(clientNumber1).isNotEqualTo(clientNumber3);
        assertThat(clientNumber1.hashCode()).hasSameHashCodeAs(clientNumber2.hashCode());
        assertThat(clientNumber1.hashCode()).isNotEqualTo(clientNumber3.hashCode());
    }

    @Test
    void testClientNumberToString() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientNumber clientNumber = new ClientNumber(1L, client, "123-456-7890");

        // When
        String clientNumberString = clientNumber.toString();

        // Then
        assertThat(clientNumberString).contains("ClientNumber(clientNumberId=1", "client=Client(clientId=1", "clientPhoneNumber=123-456-7890");
    }
}