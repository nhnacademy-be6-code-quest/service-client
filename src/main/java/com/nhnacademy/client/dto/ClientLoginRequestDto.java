package com.nhnacademy.client.dto;

import lombok.Getter;

@Getter
public class ClientLoginRequestDto {
    private String clientEmail;
    private String clientPassword;
}
