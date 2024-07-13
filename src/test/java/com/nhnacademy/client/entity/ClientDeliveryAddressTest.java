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
class ClientDeliveryAddressTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testClientDeliveryAddressEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20L);
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

        ClientDeliveryAddress clientDeliveryAddress = ClientDeliveryAddress.builder()
                .client(client)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        // When
        ClientDeliveryAddress savedClientDeliveryAddress = entityManager.persistAndFlush(clientDeliveryAddress);

        // Then
        assertThat(savedClientDeliveryAddress.getClientDeliveryAddressId()).isNotNull();
        assertThat(savedClientDeliveryAddress.getClient()).isEqualTo(client);
        assertThat(savedClientDeliveryAddress.getClientDeliveryAddress()).isEqualTo("123 Main St");
        assertThat(savedClientDeliveryAddress.getClientDeliveryAddressDetail()).isEqualTo("Apt 4B");
        assertThat(savedClientDeliveryAddress.getClientDeliveryAddressNickname()).isEqualTo("Home");
        assertThat(savedClientDeliveryAddress.getClientDeliveryZipCode()).isEqualTo(12345);
    }

    @Test
    void testUpdateClientDeliveryAddressEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20L);
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

        ClientDeliveryAddress clientDeliveryAddress = ClientDeliveryAddress.builder()
                .client(client)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();
        ClientDeliveryAddress savedClientDeliveryAddress = entityManager.persistAndFlush(clientDeliveryAddress);

        // When
        savedClientDeliveryAddress.setClientDeliveryAddress("456 Elm St");
        savedClientDeliveryAddress.setClientDeliveryAddressDetail("Suite 5C");
        savedClientDeliveryAddress.setClientDeliveryAddressNickname("Office");
        savedClientDeliveryAddress.setClientDeliveryZipCode(67890);
        ClientDeliveryAddress updatedClientDeliveryAddress = entityManager.persistAndFlush(savedClientDeliveryAddress);

        // Then
        assertThat(updatedClientDeliveryAddress.getClientDeliveryAddressId()).isEqualTo(savedClientDeliveryAddress.getClientDeliveryAddressId());
        assertThat(updatedClientDeliveryAddress.getClientDeliveryAddress()).isEqualTo("456 Elm St");
        assertThat(updatedClientDeliveryAddress.getClientDeliveryAddressDetail()).isEqualTo("Suite 5C");
        assertThat(updatedClientDeliveryAddress.getClientDeliveryAddressNickname()).isEqualTo("Office");
        assertThat(updatedClientDeliveryAddress.getClientDeliveryZipCode()).isEqualTo(67890);
    }

    @Test
    void testDeleteClientDeliveryAddressEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20L);
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

        ClientDeliveryAddress clientDeliveryAddress = ClientDeliveryAddress.builder()
                .client(client)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();
        ClientDeliveryAddress savedClientDeliveryAddress = entityManager.persistAndFlush(clientDeliveryAddress);

        // When
        entityManager.remove(savedClientDeliveryAddress);
        entityManager.flush();
        ClientDeliveryAddress foundClientDeliveryAddress = entityManager.find(ClientDeliveryAddress.class, savedClientDeliveryAddress.getClientDeliveryAddressId());

        // Then
        assertThat(foundClientDeliveryAddress).isNull();
    }

    @Test
    void testClientDeliveryAddressConstructorAndGetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15L);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientDeliveryAddress clientDeliveryAddress = new ClientDeliveryAddress(1L, client, "123 Main St", "Apt 4B", "Home", 12345);

        // Then
        assertThat(clientDeliveryAddress.getClientDeliveryAddressId()).isEqualTo(1L);
        assertThat(clientDeliveryAddress.getClient()).isEqualTo(client);
        assertThat(clientDeliveryAddress.getClientDeliveryAddress()).isEqualTo("123 Main St");
        assertThat(clientDeliveryAddress.getClientDeliveryAddressDetail()).isEqualTo("Apt 4B");
        assertThat(clientDeliveryAddress.getClientDeliveryAddressNickname()).isEqualTo("Home");
        assertThat(clientDeliveryAddress.getClientDeliveryZipCode()).isEqualTo(12345);
    }

    @Test
    void testClientDeliveryAddressSetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15L);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientDeliveryAddress clientDeliveryAddress = new ClientDeliveryAddress();
        clientDeliveryAddress.setClientDeliveryAddressId(2L);
        clientDeliveryAddress.setClient(client);
        clientDeliveryAddress.setClientDeliveryAddress("456 Elm St");
        clientDeliveryAddress.setClientDeliveryAddressDetail("Suite 5C");
        clientDeliveryAddress.setClientDeliveryAddressNickname("Office");
        clientDeliveryAddress.setClientDeliveryZipCode(67890);

        // Then
        assertThat(clientDeliveryAddress.getClientDeliveryAddressId()).isEqualTo(2L);
        assertThat(clientDeliveryAddress.getClient()).isEqualTo(client);
        assertThat(clientDeliveryAddress.getClientDeliveryAddress()).isEqualTo("456 Elm St");
        assertThat(clientDeliveryAddress.getClientDeliveryAddressDetail()).isEqualTo("Suite 5C");
        assertThat(clientDeliveryAddress.getClientDeliveryAddressNickname()).isEqualTo("Office");
        assertThat(clientDeliveryAddress.getClientDeliveryZipCode()).isEqualTo(67890);
    }

    @Test
    void testClientDeliveryAddressEqualsAndHashCode() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15L);
        Client client = new Client(1L, clientGrade, "email1@example.com", "password1", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientDeliveryAddress clientDeliveryAddress1 = new ClientDeliveryAddress(1L, client, "123 Main St", "Apt 4B", "Home", 12345);
        ClientDeliveryAddress clientDeliveryAddress2 = new ClientDeliveryAddress(1L, client, "123 Main St", "Apt 4B", "Home", 12345);
        ClientDeliveryAddress clientDeliveryAddress3 = new ClientDeliveryAddress(2L, client, "456 Elm St", "Suite 5C", "Office", 67890);

        // Then
        assertThat(clientDeliveryAddress1).isEqualTo(clientDeliveryAddress2);
        assertThat(clientDeliveryAddress1).isNotEqualTo(clientDeliveryAddress3);
        assertThat(clientDeliveryAddress1.hashCode()).hasSameHashCodeAs(clientDeliveryAddress2.hashCode());
        assertThat(clientDeliveryAddress1.hashCode()).isNotEqualTo(clientDeliveryAddress3.hashCode());
    }

    @Test
    void testClientDeliveryAddressToString() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15L);
        Client client = new Client(1L, clientGrade, "email@example.com", "password", "John Doe", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        ClientDeliveryAddress clientDeliveryAddress = new ClientDeliveryAddress(1L, client, "123 Main St", "Apt 4B", "Home", 12345);

        // When
        String clientDeliveryAddressString = clientDeliveryAddress.toString();

        // Then
        assertThat(clientDeliveryAddressString).contains("ClientDeliveryAddress(clientDeliveryAddressId=1", "client=Client(clientId=1", "clientDeliveryAddress=123 Main St", "clientDeliveryAddressDetail=Apt 4B", "clientDeliveryAddressNickname=Home", "clientDeliveryZipCode=12345");
    }
}