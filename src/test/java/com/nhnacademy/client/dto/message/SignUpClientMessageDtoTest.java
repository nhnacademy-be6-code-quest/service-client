package com.nhnacademy.client.dto.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SignUpClientMessageDtoTest {

    private SignUpClientMessageDto signUpClientMessageDto;
    private Long clientId = 1L;

    @BeforeEach
    void setUp() {
        signUpClientMessageDto = new SignUpClientMessageDto(clientId);
    }

    @Test
    void testGetterAndSetter() {
        // Test getter
        assertThat(signUpClientMessageDto.getClientId()).isEqualTo(clientId);

        // Test setter
        Long newClientId = 2L;
        signUpClientMessageDto.setClientId(newClientId);

        assertThat(signUpClientMessageDto.getClientId()).isEqualTo(newClientId);
    }

    @Test
    void testNoArgsConstructor() {
        SignUpClientMessageDto dto = new SignUpClientMessageDto();
        assertThat(dto.getClientId()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        SignUpClientMessageDto dto = new SignUpClientMessageDto(clientId);
        assertThat(dto.getClientId()).isEqualTo(clientId);
    }
}