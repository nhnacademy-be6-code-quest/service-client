package com.nhnacademy.client.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClientEmailDuplicatesExceptionTest {

    @Test
    void testExceptionMessage() {
        // Given
        String expectedMessage = "이미 존재하는 이메일입니다.";

        // When
        ClientEmailDuplicatesException exception = new ClientEmailDuplicatesException();

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void testExceptionThrown() {
        // Given
        String expectedMessage = "이미 존재하는 이메일입니다.";

        // When & Then
        assertThatThrownBy(() -> {
            throw new ClientEmailDuplicatesException();
        }).isInstanceOf(ClientEmailDuplicatesException.class)
                .hasMessage(expectedMessage);
    }
}
