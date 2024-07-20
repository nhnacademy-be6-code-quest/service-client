package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientDeliveryAddressResponseDtoTest {

    @Test
    void testBuilder() {
        Long clientDeliveryAddressId = 1L;
        String clientDeliveryAddress = "123 Main St";
        String clientDeliveryAddressDetail = "Apt 4B";
        String clientDeliveryAddressNickname = "Home";
        int clientDeliveryZipCode = 12345;

        ClientDeliveryAddressResponseDto dto = ClientDeliveryAddressResponseDto.builder()
                .clientDeliveryAddressId(clientDeliveryAddressId)
                .clientDeliveryAddress(clientDeliveryAddress)
                .clientDeliveryAddressDetail(clientDeliveryAddressDetail)
                .clientDeliveryAddressNickname(clientDeliveryAddressNickname)
                .clientDeliveryZipCode(clientDeliveryZipCode)
                .build();

        assertThat(dto.getClientDeliveryAddressId()).isEqualTo(clientDeliveryAddressId);
        assertThat(dto.getClientDeliveryAddress()).isEqualTo(clientDeliveryAddress);
        assertThat(dto.getClientDeliveryAddressDetail()).isEqualTo(clientDeliveryAddressDetail);
        assertThat(dto.getClientDeliveryAddressNickname()).isEqualTo(clientDeliveryAddressNickname);
        assertThat(dto.getClientDeliveryZipCode()).isEqualTo(clientDeliveryZipCode);
    }

    @Test
    void testSettersAndGetters() {
        ClientDeliveryAddressResponseDto dto = ClientDeliveryAddressResponseDto.builder().build();
        dto.setClientDeliveryAddressId(1L);
        dto.setClientDeliveryAddress("123 Main St");
        dto.setClientDeliveryAddressDetail("Apt 4B");
        dto.setClientDeliveryAddressNickname("Home");
        dto.setClientDeliveryZipCode(12345);

        assertThat(dto.getClientDeliveryAddressId()).isEqualTo(1L);
        assertThat(dto.getClientDeliveryAddress()).isEqualTo("123 Main St");
        assertThat(dto.getClientDeliveryAddressDetail()).isEqualTo("Apt 4B");
        assertThat(dto.getClientDeliveryAddressNickname()).isEqualTo("Home");
        assertThat(dto.getClientDeliveryZipCode()).isEqualTo(12345);
    }

    @Test
    void testNoArgsConstructor() {
        ClientDeliveryAddressResponseDto dto = ClientDeliveryAddressResponseDto.builder().build();
        assertThat(dto).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        Long clientDeliveryAddressId = 1L;
        String clientDeliveryAddress = "123 Main St";
        String clientDeliveryAddressDetail = "Apt 4B";
        String clientDeliveryAddressNickname = "Home";
        int clientDeliveryZipCode = 12345;

        ClientDeliveryAddressResponseDto dto = new ClientDeliveryAddressResponseDto(
                clientDeliveryAddressId,
                clientDeliveryAddress,
                clientDeliveryAddressDetail,
                clientDeliveryAddressNickname,
                clientDeliveryZipCode
        );

        assertThat(dto.getClientDeliveryAddressId()).isEqualTo(clientDeliveryAddressId);
        assertThat(dto.getClientDeliveryAddress()).isEqualTo(clientDeliveryAddress);
        assertThat(dto.getClientDeliveryAddressDetail()).isEqualTo(clientDeliveryAddressDetail);
        assertThat(dto.getClientDeliveryAddressNickname()).isEqualTo(clientDeliveryAddressNickname);
        assertThat(dto.getClientDeliveryZipCode()).isEqualTo(clientDeliveryZipCode);
    }

    @Test
    void testEqualsAndHashCode() {
        ClientDeliveryAddressResponseDto dto1 = ClientDeliveryAddressResponseDto.builder()
                .clientDeliveryAddressId(1L)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        ClientDeliveryAddressResponseDto dto2 = ClientDeliveryAddressResponseDto.builder()
                .clientDeliveryAddressId(1L)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).hasSameHashCodeAs(dto2.hashCode());
    }

    @Test
    void testToString() {
        ClientDeliveryAddressResponseDto dto = ClientDeliveryAddressResponseDto.builder()
                .clientDeliveryAddressId(1L)
                .clientDeliveryAddress("123 Main St")
                .clientDeliveryAddressDetail("Apt 4B")
                .clientDeliveryAddressNickname("Home")
                .clientDeliveryZipCode(12345)
                .build();

        String expectedToString = "ClientDeliveryAddressResponseDto(clientDeliveryAddressId=1, clientDeliveryAddress=123 Main St, clientDeliveryAddressDetail=Apt 4B, clientDeliveryAddressNickname=Home, clientDeliveryZipCode=12345)";
        assertThat(dto.toString()).hasToString(expectedToString);
    }
}
