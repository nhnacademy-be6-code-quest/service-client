package com.nhnacademy.client.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientPrivacyResponseDto {
    private String clientGrade;
    private String clientEmail;
    private String clientName;
    private LocalDate clientBirth;
}
