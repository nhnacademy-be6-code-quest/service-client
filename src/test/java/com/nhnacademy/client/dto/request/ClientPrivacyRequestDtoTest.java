package com.nhnacademy.client.dto.request;

import com.nhnacademy.client.entity.ClientGrade;
import com.nhnacademy.client.entity.ClientNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientPrivacyRequestDtoTest {

    private ClientPrivacyRequestDto clientPrivacyRequestDto;
    private ClientGrade clientGrade;
    private String clientEmail = "test@example.com";
    private String clientName = "John Doe";
    private LocalDateTime clientBirth = LocalDateTime.of(1990, 1, 1, 0, 0);
    private List<ClientNumber> clientNumbers;

    @BeforeEach
    void setUp() {
        clientGrade = new ClientGrade(1L, "VIP", 1000L, 10L);
        clientNumbers = List.of(new ClientNumber(1L, null, "123-456-7890"));
        clientPrivacyRequestDto = new ClientPrivacyRequestDto();
        clientPrivacyRequestDto.setClientGrade(clientGrade);
        clientPrivacyRequestDto.setClientEmail(clientEmail);
        clientPrivacyRequestDto.setClientName(clientName);
        clientPrivacyRequestDto.setClientBirth(clientBirth);
        clientPrivacyRequestDto.setClientNumbers(clientNumbers);
    }

    @Test
    void testGetterAndSetter() {
        // Test getter
        assertThat(clientPrivacyRequestDto.getClientGrade()).isEqualTo(clientGrade);
        assertThat(clientPrivacyRequestDto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(clientPrivacyRequestDto.getClientName()).isEqualTo(clientName);
        assertThat(clientPrivacyRequestDto.getClientBirth()).isEqualTo(clientBirth);
        assertThat(clientPrivacyRequestDto.getClientNumbers()).isEqualTo(clientNumbers);

        // Test setter
        ClientGrade newClientGrade = new ClientGrade(2L, "Regular", 500L, 5L);
        String newClientEmail = "new@example.com";
        String newClientName = "Jane Doe";
        LocalDateTime newClientBirth = LocalDateTime.of(1995, 5, 5, 0, 0);
        List<ClientNumber> newClientNumbers = List.of(new ClientNumber(2L, null, "098-765-4321"));

        clientPrivacyRequestDto.setClientGrade(newClientGrade);
        clientPrivacyRequestDto.setClientEmail(newClientEmail);
        clientPrivacyRequestDto.setClientName(newClientName);
        clientPrivacyRequestDto.setClientBirth(newClientBirth);
        clientPrivacyRequestDto.setClientNumbers(newClientNumbers);

        assertThat(clientPrivacyRequestDto.getClientGrade()).isEqualTo(newClientGrade);
        assertThat(clientPrivacyRequestDto.getClientEmail()).isEqualTo(newClientEmail);
        assertThat(clientPrivacyRequestDto.getClientName()).isEqualTo(newClientName);
        assertThat(clientPrivacyRequestDto.getClientBirth()).isEqualTo(newClientBirth);
        assertThat(clientPrivacyRequestDto.getClientNumbers()).isEqualTo(newClientNumbers);
    }

    @Test
    void testNoArgsConstructor() {
        ClientPrivacyRequestDto dto = new ClientPrivacyRequestDto();
        assertThat(dto.getClientGrade()).isNull();
        assertThat(dto.getClientEmail()).isNull();
        assertThat(dto.getClientName()).isNull();
        assertThat(dto.getClientBirth()).isNull();
        assertThat(dto.getClientNumbers()).isNull();
    }
}
