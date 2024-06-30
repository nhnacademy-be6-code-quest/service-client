package com.nhnacademy.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientOAuthRegisterRequestDto {
    private String identify;
    private String name;
    private LocalDate birth;
}
