package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientOrderResponseDtoTest {

    @Test
    void testBuilder() {
        // Given
        Long clientId = 1L;
        String clientName = "John Doe";
        List<String> clientNumbers = List.of("123-456-7890");
        List<ClientDeliveryAddressResponseDto> deliveryAddresses = List.of(
                ClientDeliveryAddressResponseDto.builder()
                        .clientDeliveryAddressId(1L)
                        .clientDeliveryAddress("123 Main St")
                        .clientDeliveryAddressDetail("Apt 4B")
                        .clientDeliveryAddressNickname("Home")
                        .clientDeliveryZipCode(12345)
                        .build()
        );

        // When
        ClientOrderResponseDto dto = ClientOrderResponseDto.builder()
                .clientId(clientId)
                .clientName(clientName)
                .clientNumbers(clientNumbers)
                .deliveryAddresses(deliveryAddresses)
                .build();

        // Then
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientNumbers()).isEqualTo(clientNumbers);
        assertThat(dto.getDeliveryAddresses()).isEqualTo(deliveryAddresses);
    }

    @Test
    void testGetterAndSetter() {
        // Given
        Long clientId = 1L;
        String clientName = "John Doe";
        List<String> clientNumbers = List.of("123-456-7890");
        List<ClientDeliveryAddressResponseDto> deliveryAddresses = List.of(
                ClientDeliveryAddressResponseDto.builder()
                        .clientDeliveryAddressId(1L)
                        .clientDeliveryAddress("123 Main St")
                        .clientDeliveryAddressDetail("Apt 4B")
                        .clientDeliveryAddressNickname("Home")
                        .clientDeliveryZipCode(12345)
                        .build()
        );

        // When
        ClientOrderResponseDto dto = ClientOrderResponseDto.builder()
                .clientId(clientId)
                .clientName(clientName)
                .clientNumbers(clientNumbers)
                .deliveryAddresses(deliveryAddresses)
                .build();

        // Then
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientNumbers()).isEqualTo(clientNumbers);
        assertThat(dto.getDeliveryAddresses()).isEqualTo(deliveryAddresses);
    }
}
