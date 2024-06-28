package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRegisterResponseDtoTest {

    @Test
    void testAllArgsConstructor() {
        // Given
        String clientEmail = "test@example.com";
        LocalDateTime clientCreatedAt = LocalDateTime.of(2023, 1, 1, 12, 0);

        // When
        ClientRegisterResponseDto dto = new ClientRegisterResponseDto(clientEmail, clientCreatedAt);

        // Then
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientCreatedAt()).isEqualTo(clientCreatedAt);
    }

    @Test
    void testGetter() {
        // Given
        String clientEmail = "test@example.com";
        LocalDateTime clientCreatedAt = LocalDateTime.of(2023, 1, 1, 12, 0);

        // When
        ClientRegisterResponseDto dto = new ClientRegisterResponseDto(clientEmail, clientCreatedAt);

        // Then
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientCreatedAt()).isEqualTo(clientCreatedAt);
    }
}
