package com.nhnacademy.client.dto.request;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ClientUpdateGradeRequestDtoTest {

    @Test
    void testNoArgsConstructor() {
        ClientUpdateGradeRequestDto dto = new ClientUpdateGradeRequestDto();
        assertThat(dto.getClientId()).isNull();
        assertThat(dto.getPayment()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        ClientUpdateGradeRequestDto dto = new ClientUpdateGradeRequestDto(1L, 10000L);
        assertThat(dto.getClientId()).isEqualTo(1L);
        assertThat(dto.getPayment()).isEqualTo(10000L);
    }

    @Test
    void testSettersAndGetters() {
        ClientUpdateGradeRequestDto dto = new ClientUpdateGradeRequestDto();
        dto.setClientId(1L);
        dto.setPayment(10000L);

        assertThat(dto.getClientId()).isEqualTo(1L);
        assertThat(dto.getPayment()).isEqualTo(10000L);
    }
}
