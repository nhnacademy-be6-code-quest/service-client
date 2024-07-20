package com.nhnacademy.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ClientRegisterResponseDto {
    private String clientEmail;
    private LocalDateTime clientCreatedAt;
}
