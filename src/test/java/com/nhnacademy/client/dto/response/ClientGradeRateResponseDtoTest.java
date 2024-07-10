package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientGradeRateResponseDtoTest {
    @Test
    void testNoArgsConstructor() {
        ClientGradeRateResponseDto dto = new ClientGradeRateResponseDto();

        assertThat(dto.getRate()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        Integer rate = 1;

        ClientGradeRateResponseDto dto = new ClientGradeRateResponseDto();
        dto.setRate(rate);

        assertThat(dto.getRate()).isEqualTo(rate);
    }

    @Test
    void testAllArgsConstructor() {
        Integer rate = 1;

        ClientGradeRateResponseDto dto = new ClientGradeRateResponseDto(rate);

        assertThat(dto.getRate()).isEqualTo(rate);
    }
}