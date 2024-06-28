package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientPhoneNumberResponseDtoTest {

    @Test
    void testBuilder() {
        // Given
        Long clientNumberId = 1L;
        String clientPhoneNumber = "123-456-7890";

        // When
        ClientPhoneNumberResponseDto dto = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(clientNumberId)
                .clientPhoneNumber(clientPhoneNumber)
                .build();

        // Then
        assertThat(dto.getClientNumberId()).isEqualTo(clientNumberId);
        assertThat(dto.getClientPhoneNumber()).isEqualTo(clientPhoneNumber);
    }

    @Test
    void testGetterAndSetter() {
        // Given
        Long clientNumberId = 1L;
        String clientPhoneNumber = "123-456-7890";

        // When
        ClientPhoneNumberResponseDto dto = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(clientNumberId)
                .clientPhoneNumber(clientPhoneNumber)
                .build();

        // Then
        assertThat(dto.getClientNumberId()).isEqualTo(clientNumberId);
        assertThat(dto.getClientPhoneNumber()).isEqualTo(clientPhoneNumber);
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long clientNumberId = 1L;
        String clientPhoneNumber = "123-456-7890";

        // When
        ClientPhoneNumberResponseDto dto = new ClientPhoneNumberResponseDto(clientNumberId, clientPhoneNumber);

        // Then
        assertThat(dto.getClientNumberId()).isEqualTo(clientNumberId);
        assertThat(dto.getClientPhoneNumber()).isEqualTo(clientPhoneNumber);
    }

    @Test
    void testNoArgsConstructor() {
        // When
        ClientPhoneNumberResponseDto dto = ClientPhoneNumberResponseDto.builder().build();

        // Then
        assertThat(dto.getClientNumberId()).isNull();
        assertThat(dto.getClientPhoneNumber()).isNull();
    }
}
