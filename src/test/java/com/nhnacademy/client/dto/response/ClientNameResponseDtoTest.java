package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientNameResponseDtoTest {

    @Test
    void testBuilder() {
        String clientName = "John Doe";

        ClientNameResponseDto dto = ClientNameResponseDto.builder()
                .clientName(clientName)
                .build();

        assertThat(dto.getClientName()).isEqualTo(clientName);
    }

    @Test
    void testNoArgsConstructor() {
        ClientNameResponseDto dto = new ClientNameResponseDto();
        dto.setClientName("John Doe");

        assertThat(dto.getClientName()).isEqualTo("John Doe");
    }

    @Test
    void testSettersAndGetters() {
        ClientNameResponseDto dto = new ClientNameResponseDto();
        String clientName = "Jane Doe";
        dto.setClientName(clientName);

        assertThat(dto.getClientName()).isEqualTo(clientName);
    }

    @Test
    void testAllArgsConstructor() {
        ClientNameResponseDto dto = new ClientNameResponseDto("John Doe");

        assertThat(dto.getClientName()).isEqualTo("John Doe");
    }
}
