package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientRoleResponseDtoTest {
    @Test
    void testGetterSetterClientRole() {
        List<String> roles = List.of("1", "2");

        ClientRoleResponseDto clientRoleResponseDto = new ClientRoleResponseDto(roles);

        assertEquals(roles, clientRoleResponseDto.getRoles());
    }

    @Test
    void testBuilderClientRole() {
        List<String> roles = List.of("1", "2");

        ClientRoleResponseDto clientRoleResponseDto = ClientRoleResponseDto.builder()
                .roles(roles)
                .build();

        assertEquals(roles, clientRoleResponseDto.getRoles());
    }
}