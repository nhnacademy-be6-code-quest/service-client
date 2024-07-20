package com.nhnacademy.client.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ClientUpdatePrivacyRequestDtoTest {

    private ClientUpdatePrivacyRequestDto clientUpdatePrivacyRequestDto;
    private String clientName = "John Doe";
    private LocalDate clientBirth = LocalDate.of(1990, 1, 1);

    @BeforeEach
    void setUp() {
        clientUpdatePrivacyRequestDto = new ClientUpdatePrivacyRequestDto(clientName, clientBirth);
    }

    @Test
    void testGetterAndSetter() {
        // Test getter
        assertThat(clientUpdatePrivacyRequestDto.getClientName()).isEqualTo(clientName);
        assertThat(clientUpdatePrivacyRequestDto.getClientBirth()).isEqualTo(clientBirth);

        // Test setter
        String newClientName = "Jane Doe";
        LocalDate newClientBirth = LocalDate.of(1995, 5, 5);

        clientUpdatePrivacyRequestDto.setClientName(newClientName);
        clientUpdatePrivacyRequestDto.setClientBirth(newClientBirth);

        assertThat(clientUpdatePrivacyRequestDto.getClientName()).isEqualTo(newClientName);
        assertThat(clientUpdatePrivacyRequestDto.getClientBirth()).isEqualTo(newClientBirth);
    }

    @Test
    void testAllArgsConstructor() {
        ClientUpdatePrivacyRequestDto dto = new ClientUpdatePrivacyRequestDto(clientName, clientBirth);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientBirth()).isEqualTo(clientBirth);
    }

    @Test
    void testNoArgsConstructor() {
        ClientUpdatePrivacyRequestDto dto = new ClientUpdatePrivacyRequestDto();
        assertThat(dto.getClientName()).isNull();
        assertThat(dto.getClientBirth()).isNull();
    }
}
