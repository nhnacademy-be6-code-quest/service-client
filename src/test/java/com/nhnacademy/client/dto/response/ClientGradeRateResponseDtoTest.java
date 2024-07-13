package com.nhnacademy.client.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientGradeRateResponseDtoTest {
    @Test
    void testNoArgsConstructor() {
        ClientGradeRateResponseDto dto = new ClientGradeRateResponseDto();

        assertThat(dto.getRatePolicyId()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        Long rate = 1L;

        ClientGradeRateResponseDto dto = new ClientGradeRateResponseDto();
        dto.setRatePolicyId(rate);

        assertThat(dto.getRatePolicyId()).isEqualTo(rate);
    }

    @Test
    void testAllArgsConstructor() {
        Long rate = 1L;

        ClientGradeRateResponseDto dto = new ClientGradeRateResponseDto(rate);

        assertThat(dto.getRatePolicyId()).isEqualTo(rate);
    }
}