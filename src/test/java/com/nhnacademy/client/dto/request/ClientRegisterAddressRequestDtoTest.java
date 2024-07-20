package com.nhnacademy.client.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRegisterAddressRequestDtoTest {

    private ClientRegisterAddressRequestDto clientRegisterAddressRequestDto;

    private String clientDeliveryAddress = "123 Main St";
    private String clientDeliveryAddressDetail = "Apt 4B";
    private String clientDeliveryAddressNickname = "Home";
    private int clientDeliveryZipCode = 12345;

    @BeforeEach
    void setUp() {
        clientRegisterAddressRequestDto = ClientRegisterAddressRequestDto.builder()
                .clientDeliveryAddress(clientDeliveryAddress)
                .clientDeliveryAddressDetail(clientDeliveryAddressDetail)
                .clientDeliveryAddressNickname(clientDeliveryAddressNickname)
                .clientDeliveryZipCode(clientDeliveryZipCode)
                .build();
    }

    @Test
    void testGetterAndSetter() {
        // Test getter
        assertThat(clientRegisterAddressRequestDto.getClientDeliveryAddress()).isEqualTo(clientDeliveryAddress);
        assertThat(clientRegisterAddressRequestDto.getClientDeliveryAddressDetail()).isEqualTo(clientDeliveryAddressDetail);
        assertThat(clientRegisterAddressRequestDto.getClientDeliveryAddressNickname()).isEqualTo(clientDeliveryAddressNickname);
        assertThat(clientRegisterAddressRequestDto.getClientDeliveryZipCode()).isEqualTo(clientDeliveryZipCode);

        // Test setter
        String newClientDeliveryAddress = "456 Another St";
        String newClientDeliveryAddressDetail = "Suite 1A";
        String newClientDeliveryAddressNickname = "Work";
        int newClientDeliveryZipCode = 67890;

        clientRegisterAddressRequestDto.setClientDeliveryAddress(newClientDeliveryAddress);
        clientRegisterAddressRequestDto.setClientDeliveryAddressDetail(newClientDeliveryAddressDetail);
        clientRegisterAddressRequestDto.setClientDeliveryAddressNickname(newClientDeliveryAddressNickname);
        clientRegisterAddressRequestDto.setClientDeliveryZipCode(newClientDeliveryZipCode);

        assertThat(clientRegisterAddressRequestDto.getClientDeliveryAddress()).isEqualTo(newClientDeliveryAddress);
        assertThat(clientRegisterAddressRequestDto.getClientDeliveryAddressDetail()).isEqualTo(newClientDeliveryAddressDetail);
        assertThat(clientRegisterAddressRequestDto.getClientDeliveryAddressNickname()).isEqualTo(newClientDeliveryAddressNickname);
        assertThat(clientRegisterAddressRequestDto.getClientDeliveryZipCode()).isEqualTo(newClientDeliveryZipCode);
    }

    @Test
    void testAllArgsConstructor() {
        ClientRegisterAddressRequestDto dto = new ClientRegisterAddressRequestDto(
                clientDeliveryAddress,
                clientDeliveryAddressDetail,
                clientDeliveryAddressNickname,
                clientDeliveryZipCode
        );

        assertThat(dto.getClientDeliveryAddress()).isEqualTo(clientDeliveryAddress);
        assertThat(dto.getClientDeliveryAddressDetail()).isEqualTo(clientDeliveryAddressDetail);
        assertThat(dto.getClientDeliveryAddressNickname()).isEqualTo(clientDeliveryAddressNickname);
        assertThat(dto.getClientDeliveryZipCode()).isEqualTo(clientDeliveryZipCode);
    }

    @Test
    void testNoArgsConstructor() {
        ClientRegisterAddressRequestDto dto = new ClientRegisterAddressRequestDto();
        assertThat(dto.getClientDeliveryAddress()).isNull();
        assertThat(dto.getClientDeliveryAddressDetail()).isNull();
        assertThat(dto.getClientDeliveryAddressNickname()).isNull();
        assertThat(dto.getClientDeliveryZipCode()).isZero();
    }

    @Test
    void testBuilder() {
        ClientRegisterAddressRequestDto dto = ClientRegisterAddressRequestDto.builder()
                .clientDeliveryAddress(clientDeliveryAddress)
                .clientDeliveryAddressDetail(clientDeliveryAddressDetail)
                .clientDeliveryAddressNickname(clientDeliveryAddressNickname)
                .clientDeliveryZipCode(clientDeliveryZipCode)
                .build();

        assertThat(dto.getClientDeliveryAddress()).isEqualTo(clientDeliveryAddress);
        assertThat(dto.getClientDeliveryAddressDetail()).isEqualTo(clientDeliveryAddressDetail);
        assertThat(dto.getClientDeliveryAddressNickname()).isEqualTo(clientDeliveryAddressNickname);
        assertThat(dto.getClientDeliveryZipCode()).isEqualTo(clientDeliveryZipCode);
    }
}
