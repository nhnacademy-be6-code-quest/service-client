package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientDeliveryAddressTest {

    @Test
    void testNoArgsConstructor() {
        ClientDeliveryAddress clientDeliveryAddress = new ClientDeliveryAddress();
        assertNotNull(clientDeliveryAddress);
    }

    @Test
    void testAllArgsConstructor() {
        Client client = new Client();
        ClientDeliveryAddress clientDeliveryAddress = new ClientDeliveryAddress(
                1L,
                client,
                "123 Main St",
                "Apt 4B",
                "Home",
                12345
        );

        assertAll(
                () -> assertEquals(1L, clientDeliveryAddress.getClientDeliveryAddressId()),
                () -> assertEquals(client, clientDeliveryAddress.getClient()),
                () -> assertEquals("123 Main St", clientDeliveryAddress.getClientDeliveryAddress()),
                () -> assertEquals("Apt 4B", clientDeliveryAddress.getClientDeliveryAddressDetail()),
                () -> assertEquals("Home", clientDeliveryAddress.getClientDeliveryAddressNickname()),
                () -> assertEquals(12345, clientDeliveryAddress.getClientDeliveryZipCode())
        );
    }

    @Test
    void testBuilder() {
        Client client = new Client();
        ClientDeliveryAddress clientDeliveryAddress = ClientDeliveryAddress.builder()
                .clientDeliveryAddressId(1L)
                .client(client)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        assertAll(
                () -> assertEquals(1L, clientDeliveryAddress.getClientDeliveryAddressId()),
                () -> assertEquals(client, clientDeliveryAddress.getClient()),
                () -> assertEquals("123 Main St", clientDeliveryAddress.getClientDeliveryAddress()),
                () -> assertEquals("Apt 4B", clientDeliveryAddress.getClientDeliveryAddressDetail()),
                () -> assertEquals("Home", clientDeliveryAddress.getClientDeliveryAddressNickname()),
                () -> assertEquals(12345, clientDeliveryAddress.getClientDeliveryZipCode())
        );
    }

    @Test
    void testSettersAndGetters() {
        ClientDeliveryAddress clientDeliveryAddress = new ClientDeliveryAddress();
        Client client = new Client();

        clientDeliveryAddress.setClientDeliveryAddressId(1L);
        clientDeliveryAddress.setClient(client);
        clientDeliveryAddress.setClientDeliveryAddress("123 Main St");
        clientDeliveryAddress.setClientDeliveryAddressDetail("Apt 4B");
        clientDeliveryAddress.setClientDeliveryAddressNickname("Home");
        clientDeliveryAddress.setClientDeliveryZipCode(12345);

        assertAll(
                () -> assertEquals(1L, clientDeliveryAddress.getClientDeliveryAddressId()),
                () -> assertEquals(client, clientDeliveryAddress.getClient()),
                () -> assertEquals("123 Main St", clientDeliveryAddress.getClientDeliveryAddress()),
                () -> assertEquals("Apt 4B", clientDeliveryAddress.getClientDeliveryAddressDetail()),
                () -> assertEquals("Home", clientDeliveryAddress.getClientDeliveryAddressNickname()),
                () -> assertEquals(12345, clientDeliveryAddress.getClientDeliveryZipCode())
        );
    }
}
