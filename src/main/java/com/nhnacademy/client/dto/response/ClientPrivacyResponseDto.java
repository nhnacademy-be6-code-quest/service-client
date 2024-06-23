package com.nhnacademy.client.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClientPrivacyResponseDto {
    private String clientGrade;
    private String clientEmail;
    private String clientName;
    private LocalDateTime clientBirth;
    private List<String> clientNumbers;
}
