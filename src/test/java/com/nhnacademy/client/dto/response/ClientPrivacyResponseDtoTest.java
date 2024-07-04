package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ClientPrivacyResponseDtoTest {

    @Test
    void testBuilder() {
        String clientGrade = "VIP";
        String clientEmail = "test@example.com";
        String clientName = "John Doe";
        LocalDate clientBirth = LocalDate.of(1990, 1, 1);

        ClientPrivacyResponseDto dto = ClientPrivacyResponseDto.builder()
                .clientGrade(clientGrade)
                .clientEmail(clientEmail)
                .clientName(clientName)
                .clientBirth(clientBirth)
                .build();

        assertThat(dto.getClientGrade()).isEqualTo(clientGrade);
        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientBirth()).isEqualTo(clientBirth);
    }

    @Test
    void testSettersAndGetters() {
        ClientPrivacyResponseDto dto = ClientPrivacyResponseDto.builder().build();
        dto.setClientGrade("VIP");
        dto.setClientEmail("test@example.com");
        dto.setClientName("John Doe");
        dto.setClientBirth(LocalDate.of(1990, 1, 1));

        assertThat(dto.getClientGrade()).isEqualTo("VIP");
        assertThat(dto.getClientEmail()).isEqualTo("test@example.com");
        assertThat(dto.getClientName()).isEqualTo("John Doe");
        assertThat(dto.getClientBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testEqualsAndHashCode() {
        ClientPrivacyResponseDto dto1 = ClientPrivacyResponseDto.builder()
                .clientGrade("VIP")
                .clientEmail("test@example.com")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .build();

        ClientPrivacyResponseDto dto2 = ClientPrivacyResponseDto.builder()
                .clientGrade("VIP")
                .clientEmail("test@example.com")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void testToString() {
        ClientPrivacyResponseDto dto = ClientPrivacyResponseDto.builder()
                .clientGrade("VIP")
                .clientEmail("test@example.com")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .build();

        String expectedToString = "ClientPrivacyResponseDto(clientGrade=VIP, clientEmail=test@example.com, clientName=John Doe, clientBirth=1990-01-01)";
        assertThat(dto.toString()).isEqualTo(expectedToString);
    }
}
