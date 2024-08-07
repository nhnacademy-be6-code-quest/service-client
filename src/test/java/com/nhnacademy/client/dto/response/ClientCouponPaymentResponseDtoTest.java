package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientCouponPaymentResponseDtoTest {

    @Test
    void testBuilder() {
        Long clientId = 1L;
        String clientName = "John Doe";
        String clientEmail = "john.doe@example.com";

        ClientCouponPaymentResponseDto dto = ClientCouponPaymentResponseDto.builder()
                .clientId(clientId)
                .clientName(clientName)
                .clientEmail(clientEmail)
                .build();

        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
    }

    @Test
    void testSettersAndGetters() {
        ClientCouponPaymentResponseDto dto = new ClientCouponPaymentResponseDto();
        dto.setClientId(1L);
        dto.setClientName("John Doe");
        dto.setClientEmail("john.doe@example.com");

        assertThat(dto.getClientId()).isEqualTo(1L);
        assertThat(dto.getClientName()).isEqualTo("John Doe");
        assertThat(dto.getClientEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testNoArgsConstructor() {
        ClientCouponPaymentResponseDto dto = new ClientCouponPaymentResponseDto();
        assertThat(dto).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        Long clientId = 1L;
        String clientName = "John Doe";
        String clientEmail = "john.doe@example.com";

        ClientCouponPaymentResponseDto dto = new ClientCouponPaymentResponseDto(clientId, clientName, clientEmail);

        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
    }

    @Test
    void testEqualsAndHashCode() {
        ClientCouponPaymentResponseDto dto1 = ClientCouponPaymentResponseDto.builder()
                .clientId(1L)
                .clientName("John Doe")
                .clientEmail("john.doe@example.com")
                .build();

        ClientCouponPaymentResponseDto dto2 = ClientCouponPaymentResponseDto.builder()
                .clientId(1L)
                .clientName("John Doe")
                .clientEmail("john.doe@example.com")
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).hasSameHashCodeAs(dto2.hashCode());
    }

    @Test
    void testToString() {
        ClientCouponPaymentResponseDto dto = ClientCouponPaymentResponseDto.builder()
                .clientId(1L)
                .clientName("John Doe")
                .clientEmail("john.doe@example.com")
                .build();

        String expectedToString = "ClientCouponPaymentResponseDto(clientId=1, clientName=John Doe, clientEmail=john.doe@example.com)";
        assertThat(dto.toString()).hasToString(expectedToString);
    }
}
