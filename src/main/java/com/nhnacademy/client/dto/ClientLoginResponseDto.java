package com.nhnacademy.client.dto;

import com.nhnacademy.client.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientLoginResponseDto {
    private Role role;
    private String clientEmail;
    private String clientPassword;
    private String clientName;
}
