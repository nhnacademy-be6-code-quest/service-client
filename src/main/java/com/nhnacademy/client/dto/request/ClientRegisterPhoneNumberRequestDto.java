package com.nhnacademy.client.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientRegisterPhoneNumberRequestDto {
    private String phoneNumber;
}
