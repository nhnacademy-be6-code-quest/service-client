package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientRoleTest {

    @Test
    void testNoArgsConstructor() {
        ClientRole clientRole = new ClientRole();
        assertNotNull(clientRole);
    }

    @Test
    void testAllArgsConstructor() {
        Client client = new Client();
        Role role = new Role();
        ClientRole clientRole = new ClientRole(
                1L,
                client,
                role
        );

        assertAll(
                () -> assertEquals(1L, clientRole.getClientRoleId()),
                () -> assertEquals(client, clientRole.getClient()),
                () -> assertEquals(role, clientRole.getRole())
        );
    }

    @Test
    void testBuilder() {
        Client client = new Client();
        Role role = new Role();
        ClientRole clientRole = ClientRole.builder()
                .clientRoleId(1L)
                .client(client)
                .role(role)
                .build();

        assertAll(
                () -> assertEquals(1L, clientRole.getClientRoleId()),
                () -> assertEquals(client, clientRole.getClient()),
                () -> assertEquals(role, clientRole.getRole())
        );
    }

    @Test
    void testSettersAndGetters() {
        ClientRole clientRole = new ClientRole();
        Client client = new Client();
        Role role = new Role();

        clientRole.setClientRoleId(1L);
        clientRole.setClient(client);
        clientRole.setRole(role);

        assertAll(
                () -> assertEquals(1L, clientRole.getClientRoleId()),
                () -> assertEquals(client, clientRole.getClient()),
                () -> assertEquals(role, clientRole.getRole())
        );
    }
}
