package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientLoginResponseDtoTest {

    @Test
    void testBuilder() {
        List<String> roles = List.of("ROLE_USER");
        Long clientId = 1L;
        String clientEmail = "test@example.com";
        String clientPassword = "password";
        String clientName = "Test User";

        ClientLoginResponseDto dto = ClientLoginResponseDto.builder()
                .role(roles)
                .clientId(clientId)
                .clientEmail(clientEmail)
                .clientPassword(clientPassword)
                .clientName(clientName)
                .build();

        assertThat(dto.getRole()).isEqualTo(roles);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientPassword()).isEqualTo(clientPassword);
        assertThat(dto.getClientName()).isEqualTo(clientName);
    }

    @Test
    void testSettersAndGetters() {
        ClientLoginResponseDto dto = ClientLoginResponseDto.builder().build();
        List<String> roles = List.of("ROLE_USER");
        dto.setRole(roles);
        dto.setClientId(1L);
        dto.setClientEmail("test@example.com");
        dto.setClientPassword("password");
        dto.setClientName("Test User");

        assertThat(dto.getRole()).isEqualTo(roles);
        assertThat(dto.getClientId()).isEqualTo(1L);
        assertThat(dto.getClientEmail()).isEqualTo("test@example.com");
        assertThat(dto.getClientPassword()).isEqualTo("password");
        assertThat(dto.getClientName()).isEqualTo("Test User");
    }

    @Test
    void testAllArgsConstructor() {
        List<String> roles = List.of("ROLE_USER");
        Long clientId = 1L;
        String clientEmail = "test@example.com";
        String clientPassword = "password";
        String clientName = "Test User";

        ClientLoginResponseDto dto = new ClientLoginResponseDto(
                roles, clientId, clientEmail, clientPassword, clientName);

        assertThat(dto.getRole()).isEqualTo(roles);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientPassword()).isEqualTo(clientPassword);
        assertThat(dto.getClientName()).isEqualTo(clientName);
    }

}
