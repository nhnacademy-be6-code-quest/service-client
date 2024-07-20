package com.nhnacademy.client.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RabbitMessageConvertExceptionTest {

    @Test
    void testExceptionMessage() {
        // Given
        String message = "Failed to convert Rabbit message";

        // When
        RabbitMessageConvertException exception = new RabbitMessageConvertException(message);

        // Then
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void testExceptionThrown() {
        // Given
        String message = "Failed to convert Rabbit message";

        // When & Then
        assertThatThrownBy(() -> {
            throw new RabbitMessageConvertException(message);
        }).isInstanceOf(RabbitMessageConvertException.class)
                .hasMessage(message);
    }
}
