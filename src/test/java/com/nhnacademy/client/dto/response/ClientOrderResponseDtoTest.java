package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientOrderResponseDtoTest {

    @Test
    void testBuilder() {
        Long clientId = 1L;
        String clientName = "Test User";
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

        ClientOrderResponseDto dto = ClientOrderResponseDto.builder()
                .clientId(clientId)
                .clientName(clientName)
                .clientNumbers(clientNumbers)
                .deliveryAddresses(deliveryAddresses)
                .build();

        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientNumbers()).isEqualTo(clientNumbers);
        assertThat(dto.getDeliveryAddresses()).isEqualTo(deliveryAddresses);
    }

    @Test
    void testSettersAndGetters() {
        ClientOrderResponseDto dto = ClientOrderResponseDto.builder().build();
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

        dto.setClientId(1L);
        dto.setClientName("Test User");
        dto.setClientNumbers(clientNumbers);
        dto.setDeliveryAddresses(deliveryAddresses);

        assertThat(dto.getClientId()).isEqualTo(1L);
        assertThat(dto.getClientName()).isEqualTo("Test User");
        assertThat(dto.getClientNumbers()).isEqualTo(clientNumbers);
        assertThat(dto.getDeliveryAddresses()).isEqualTo(deliveryAddresses);
    }

    @Test
    void testEqualsAndHashCode() {
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

        ClientOrderResponseDto dto1 = ClientOrderResponseDto.builder()
                .clientId(1L)
                .clientName("Test User")
                .clientNumbers(clientNumbers)
                .deliveryAddresses(deliveryAddresses)
                .build();

        ClientOrderResponseDto dto2 = ClientOrderResponseDto.builder()
                .clientId(1L)
                .clientName("Test User")
                .clientNumbers(clientNumbers)
                .deliveryAddresses(deliveryAddresses)
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).hasSameHashCodeAs(dto2.hashCode());
    }

    @Test
    void testToString() {
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

        ClientOrderResponseDto dto = ClientOrderResponseDto.builder()
                .clientId(1L)
                .clientName("Test User")
                .clientNumbers(clientNumbers)
                .deliveryAddresses(deliveryAddresses)
                .build();

        String expectedToString = "ClientOrderResponseDto(clientId=1, clientName=Test User, clientNumbers=[123-456-7890], deliveryAddresses=[ClientDeliveryAddressResponseDto(clientDeliveryAddressId=1, clientDeliveryAddress=123 Main St, clientDeliveryAddressDetail=Apt 4B, clientDeliveryAddressNickname=Home, clientDeliveryZipCode=12345)])";
        assertThat(dto.toString()).hasToString(expectedToString);
    }
}
