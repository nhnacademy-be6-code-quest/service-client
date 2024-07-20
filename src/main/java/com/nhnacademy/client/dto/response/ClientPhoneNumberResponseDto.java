package com.nhnacademy.client.dto.response;

import lombok.*;

@Data
@Builder
public class ClientPhoneNumberResponseDto {
    private Long clientNumberId;
    private String clientPhoneNumber;
}
