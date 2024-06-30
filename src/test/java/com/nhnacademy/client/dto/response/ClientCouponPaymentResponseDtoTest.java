package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientCouponPaymentResponseDtoTest {

    @Test
    void testBuilder() {
        // Given
        Long clientId = 1L;
        String clientName = "John Doe";
        String clientEmail = "test@example.com";

        // When
        ClientCouponPaymentResponseDto dto = ClientCouponPaymentResponseDto.builder()
                .clientId(clientId)
                .clientName(clientName)
                .clientEmail(clientEmail)
                .build();

        // Then
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
    }

    @Test
    void testGetterAndSetter() {
        // Given
        Long clientId = 1L;
        String clientName = "John Doe";
        String clientEmail = "test@example.com";

        // When
        ClientCouponPaymentResponseDto dto = ClientCouponPaymentResponseDto.builder()
                .clientId(clientId)
                .clientName(clientName)
                .clientEmail(clientEmail)
                .build();

        // Then
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
    }
}
