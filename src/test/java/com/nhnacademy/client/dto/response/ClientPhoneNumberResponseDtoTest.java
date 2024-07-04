package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientPhoneNumberResponseDtoTest {

    @Test
    void testBuilder() {
        Long clientNumberId = 1L;
        String clientPhoneNumber = "123-456-7890";

        ClientPhoneNumberResponseDto dto = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(clientNumberId)
                .clientPhoneNumber(clientPhoneNumber)
                .build();

        assertThat(dto.getClientNumberId()).isEqualTo(clientNumberId);
        assertThat(dto.getClientPhoneNumber()).isEqualTo(clientPhoneNumber);
    }

    @Test
    void testSettersAndGetters() {
        ClientPhoneNumberResponseDto dto = ClientPhoneNumberResponseDto.builder().build();
        dto.setClientNumberId(1L);
        dto.setClientPhoneNumber("123-456-7890");

        assertThat(dto.getClientNumberId()).isEqualTo(1L);
        assertThat(dto.getClientPhoneNumber()).isEqualTo("123-456-7890");
    }

    @Test
    void testEqualsAndHashCode() {
        ClientPhoneNumberResponseDto dto1 = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(1L)
                .clientPhoneNumber("123-456-7890")
                .build();

        ClientPhoneNumberResponseDto dto2 = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(1L)
                .clientPhoneNumber("123-456-7890")
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).hasSameHashCodeAs(dto2.hashCode());
    }

    @Test
    void testToString() {
        ClientPhoneNumberResponseDto dto = ClientPhoneNumberResponseDto.builder()
                .clientNumberId(1L)
                .clientPhoneNumber("123-456-7890")
                .build();

        String expectedToString = "ClientPhoneNumberResponseDto(clientNumberId=1, clientPhoneNumber=123-456-7890)";
        assertThat(dto.toString()).hasToString(expectedToString);
    }
}
