package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientNumberTest {

    @Test
    void testNoArgsConstructor() {
        ClientNumber clientNumber = new ClientNumber();
        assertNotNull(clientNumber);
    }

    @Test
    void testAllArgsConstructor() {
        Client client = new Client();
        ClientNumber clientNumber = new ClientNumber(
                1L,
                client,
                "01012345678"
        );

        assertAll(
                () -> assertEquals(1L, clientNumber.getClientNumberId()),
                () -> assertEquals(client, clientNumber.getClient()),
                () -> assertEquals("01012345678", clientNumber.getClientPhoneNumber())
        );
    }

    @Test
    void testBuilder() {
        Client client = new Client();
        ClientNumber clientNumber = ClientNumber.builder()
                .clientNumberId(1L)
                .client(client)
                .clientPhoneNumber("01012345678")
                .build();

        assertAll(
                () -> assertEquals(1L, clientNumber.getClientNumberId()),
                () -> assertEquals(client, clientNumber.getClient()),
                () -> assertEquals("01012345678", clientNumber.getClientPhoneNumber())
        );
    }

    @Test
    void testSettersAndGetters() {
        ClientNumber clientNumber = new ClientNumber();
        Client client = new Client();

        clientNumber.setClientNumberId(1L);
        clientNumber.setClient(client);
        clientNumber.setClientPhoneNumber("01012345678");

        assertAll(
                () -> assertEquals(1L, clientNumber.getClientNumberId()),
                () -> assertEquals(client, clientNumber.getClient()),
                () -> assertEquals("01012345678", clientNumber.getClientPhoneNumber())
        );
    }
}
