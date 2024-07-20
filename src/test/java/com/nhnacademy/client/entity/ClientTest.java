package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testNoArgsConstructor() {
        Client client = new Client();
        assertNotNull(client);
    }

    @Test
    void testAllArgsConstructor() {
        ClientGrade clientGrade = new ClientGrade();
        LocalDateTime now = LocalDateTime.now();

        Client client = new Client(
                1L,
                clientGrade,
                "test@example.com",
                "password",
                "Test User",
                LocalDate.of(1990, 1, 1),
                now,
                now,
                false,
                now
        );

        assertAll(
                () -> assertEquals(1L, client.getClientId()),
                () -> assertEquals(clientGrade, client.getClientGrade()),
                () -> assertEquals("test@example.com", client.getClientEmail()),
                () -> assertEquals("password", client.getClientPassword()),
                () -> assertEquals("Test User", client.getClientName()),
                () -> assertEquals(LocalDate.of(1990, 1, 1), client.getClientBirth()),
                () -> assertEquals(now, client.getClientCreatedAt()),
                () -> assertEquals(now, client.getLastLoginDate()),
                () -> assertFalse(client.isDeleted()),
                () -> assertEquals(now, client.getClientDeleteDate())
        );
    }

    @Test
    void testBuilder() {
        ClientGrade clientGrade = new ClientGrade();
        LocalDateTime now = LocalDateTime.now();

        Client client = Client.builder()
                .clientId(1L)
                .clientGrade(clientGrade)
                .clientEmail("test@example.com")
                .clientPassword("password")
                .clientName("Test User")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .clientCreatedAt(now)
                .lastLoginDate(now)
                .isDeleted(false)
                .clientDeleteDate(now)
                .build();

        assertAll(
                () -> assertEquals(1L, client.getClientId()),
                () -> assertEquals(clientGrade, client.getClientGrade()),
                () -> assertEquals("test@example.com", client.getClientEmail()),
                () -> assertEquals("password", client.getClientPassword()),
                () -> assertEquals("Test User", client.getClientName()),
                () -> assertEquals(LocalDate.of(1990, 1, 1), client.getClientBirth()),
                () -> assertEquals(now, client.getClientCreatedAt()),
                () -> assertEquals(now, client.getLastLoginDate()),
                () -> assertFalse(client.isDeleted()),
                () -> assertEquals(now, client.getClientDeleteDate())
        );
    }

    @Test
    void testSettersAndGetters() {
        Client client = new Client();
        ClientGrade clientGrade = new ClientGrade();
        LocalDateTime now = LocalDateTime.now();

        client.setClientId(1L);
        client.setClientGrade(clientGrade);
        client.setClientEmail("test@example.com");
        client.setClientPassword("password");
        client.setClientName("Test User");
        client.setClientBirth(LocalDate.of(1990, 1, 1));
        client.setClientCreatedAt(now);
        client.setLastLoginDate(now);
        client.setDeleted(false);
        client.setClientDeleteDate(now);

        assertAll(
                () -> assertEquals(1L, client.getClientId()),
                () -> assertEquals(clientGrade, client.getClientGrade()),
                () -> assertEquals("test@example.com", client.getClientEmail()),
                () -> assertEquals("password", client.getClientPassword()),
                () -> assertEquals("Test User", client.getClientName()),
                () -> assertEquals(LocalDate.of(1990, 1, 1), client.getClientBirth()),
                () -> assertEquals(now, client.getClientCreatedAt()),
                () -> assertEquals(now, client.getLastLoginDate()),
                () -> assertFalse(client.isDeleted()),
                () -> assertEquals(now, client.getClientDeleteDate())
        );
    }
}
