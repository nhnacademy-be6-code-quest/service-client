package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientLoginResponseDtoTest {

    @Test
    void testBuilder() {
        // Given
        List<String> role = List.of("ROLE_USER");
        Long clientId = 1L;
        String clientEmail = "test@example.com";
        String clientPassword = "Password1!";
        String clientName = "John Doe";

        // When
        ClientLoginResponseDto dto = ClientLoginResponseDto.builder()
                .role(role)
                .clientId(clientId)
                .clientEmail(clientEmail)
                .clientPassword(clientPassword)
                .clientName(clientName)
                .build();

        // Then
        assertThat(dto.getRole()).isEqualTo(role);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientPassword()).isEqualTo(clientPassword);
        assertThat(dto.getClientName()).isEqualTo(clientName);
    }

    @Test
    void testGetterAndSetter() {
        // Given
        List<String> role = List.of("ROLE_USER");
        Long clientId = 1L;
        String clientEmail = "test@example.com";
        String clientPassword = "Password1!";
        String clientName = "John Doe";

        // When
        ClientLoginResponseDto dto = ClientLoginResponseDto.builder()
                .role(role)
                .clientId(clientId)
                .clientEmail(clientEmail)
                .clientPassword(clientPassword)
                .clientName(clientName)
                .build();

        // Then
        assertThat(dto.getRole()).isEqualTo(role);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientPassword()).isEqualTo(clientPassword);
        assertThat(dto.getClientName()).isEqualTo(clientName);
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        List<String> role = List.of("ROLE_USER");
        Long clientId = 1L;
        String clientEmail = "test@example.com";
        String clientPassword = "Password1!";
        String clientName = "John Doe";

        // When
        ClientLoginResponseDto dto = new ClientLoginResponseDto(role, clientId, clientEmail, clientPassword, clientName);

        // Then
        assertThat(dto.getRole()).isEqualTo(role);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientPassword()).isEqualTo(clientPassword);
        assertThat(dto.getClientName()).isEqualTo(clientName);
    }

    @Test
    void testNoArgsConstructor() {
        // When
        ClientLoginResponseDto dto = ClientLoginResponseDto.builder().build();

        // Then
        assertThat(dto.getRole()).isNull();
        assertThat(dto.getClientId()).isNull();
        assertThat(dto.getClientEmail()).isNull();
        assertThat(dto.getClientPassword()).isNull();
        assertThat(dto.getClientName()).isNull();
    }
}
