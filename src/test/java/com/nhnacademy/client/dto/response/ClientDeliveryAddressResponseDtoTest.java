package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientDeliveryAddressResponseDtoTest {

    @Test
    void testBuilder() {
        // Given
        Long clientDeliveryAddressId = 1L;
        String clientDeliveryAddress = "123 Main St";
        String clientDeliveryAddressDetail = "Apt 4B";
        String clientDeliveryAddressNickname = "Home";
        int clientDeliveryZipCode = 12345;

        // When
        ClientDeliveryAddressResponseDto dto = ClientDeliveryAddressResponseDto.builder()
                .clientDeliveryAddressId(clientDeliveryAddressId)
                .clientDeliveryAddress(clientDeliveryAddress)
                .clientDeliveryAddressDetail(clientDeliveryAddressDetail)
                .clientDeliveryAddressNickname(clientDeliveryAddressNickname)
                .clientDeliveryZipCode(clientDeliveryZipCode)
                .build();

        // Then
        assertThat(dto.getClientDeliveryAddressId()).isEqualTo(clientDeliveryAddressId);
        assertThat(dto.getClientDeliveryAddress()).isEqualTo(clientDeliveryAddress);
        assertThat(dto.getClientDeliveryAddressDetail()).isEqualTo(clientDeliveryAddressDetail);
        assertThat(dto.getClientDeliveryAddressNickname()).isEqualTo(clientDeliveryAddressNickname);
        assertThat(dto.getClientDeliveryZipCode()).isEqualTo(clientDeliveryZipCode);
    }

    @Test
    void testGetterAndSetter() {
        // Given
        Long clientDeliveryAddressId = 1L;
        String clientDeliveryAddress = "123 Main St";
        String clientDeliveryAddressDetail = "Apt 4B";
        String clientDeliveryAddressNickname = "Home";
        int clientDeliveryZipCode = 12345;

        // When
        ClientDeliveryAddressResponseDto dto = ClientDeliveryAddressResponseDto.builder()
                .clientDeliveryAddressId(clientDeliveryAddressId)
                .clientDeliveryAddress(clientDeliveryAddress)
                .clientDeliveryAddressDetail(clientDeliveryAddressDetail)
                .clientDeliveryAddressNickname(clientDeliveryAddressNickname)
                .clientDeliveryZipCode(clientDeliveryZipCode)
                .build();

        // Then
        assertThat(dto.getClientDeliveryAddressId()).isEqualTo(clientDeliveryAddressId);
        assertThat(dto.getClientDeliveryAddress()).isEqualTo(clientDeliveryAddress);
        assertThat(dto.getClientDeliveryAddressDetail()).isEqualTo(clientDeliveryAddressDetail);
        assertThat(dto.getClientDeliveryAddressNickname()).isEqualTo(clientDeliveryAddressNickname);
        assertThat(dto.getClientDeliveryZipCode()).isEqualTo(clientDeliveryZipCode);
    }
}
