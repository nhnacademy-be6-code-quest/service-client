package com.nhnacademy.client.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRegisterPhoneNumberRequestDtoTest {

    private ClientRegisterPhoneNumberRequestDto clientRegisterPhoneNumberRequestDto;
    private String phoneNumber = "123-456-7890";

    @BeforeEach
    void setUp() {
        clientRegisterPhoneNumberRequestDto = new ClientRegisterPhoneNumberRequestDto(phoneNumber);
    }

    @Test
    void testGetterAndSetter() {
        // Test getter
        assertThat(clientRegisterPhoneNumberRequestDto.getPhoneNumber()).isEqualTo(phoneNumber);

        // Test setter
        String newPhoneNumber = "098-765-4321";
        clientRegisterPhoneNumberRequestDto.setPhoneNumber(newPhoneNumber);
        assertThat(clientRegisterPhoneNumberRequestDto.getPhoneNumber()).isEqualTo(newPhoneNumber);
    }

    @Test
    void testAllArgsConstructor() {
        ClientRegisterPhoneNumberRequestDto dto = new ClientRegisterPhoneNumberRequestDto(phoneNumber);
        assertThat(dto.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void testNoArgsConstructor() {
        ClientRegisterPhoneNumberRequestDto dto = new ClientRegisterPhoneNumberRequestDto();
        assertThat(dto.getPhoneNumber()).isNull();
    }
}
