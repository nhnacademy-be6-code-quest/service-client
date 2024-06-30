package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ClientPrivacyResponseDtoTest {

    @Test
    void testBuilder() {
        // Given
        String clientGrade = "VIP";
        String clientEmail = "test@example.com";
        String clientName = "John Doe";
        LocalDate clientBirth = LocalDate.of(1990, 1, 1);

        // When
        ClientPrivacyResponseDto dto = ClientPrivacyResponseDto.builder()
                .clientGrade(clientGrade)
                .clientEmail(clientEmail)
                .clientName(clientName)
                .clientBirth(clientBirth)
                .build();

        // Then
        assertThat(dto.getClientGrade()).isEqualTo(clientGrade);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientBirth()).isEqualTo(clientBirth);
    }

    @Test
    void testGetterAndSetter() {
        // Given
        String clientGrade = "VIP";
        String clientEmail = "test@example.com";
        String clientName = "John Doe";
        LocalDate clientBirth = LocalDate.of(1990, 1, 1);

        // When
        ClientPrivacyResponseDto dto = ClientPrivacyResponseDto.builder()
                .clientGrade(clientGrade)
                .clientEmail(clientEmail)
                .clientName(clientName)
                .clientBirth(clientBirth)
                .build();

        // Then
        assertThat(dto.getClientGrade()).isEqualTo(clientGrade);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientBirth()).isEqualTo(clientBirth);
    }

    @Test
    void testNoArgsConstructor() {
        // When
        ClientPrivacyResponseDto dto = ClientPrivacyResponseDto.builder().build();

        // Then
        assertThat(dto.getClientGrade()).isNull();
        assertThat(dto.getClientEmail()).isNull();
        assertThat(dto.getClientName()).isNull();
        assertThat(dto.getClientBirth()).isNull();
    }
}
