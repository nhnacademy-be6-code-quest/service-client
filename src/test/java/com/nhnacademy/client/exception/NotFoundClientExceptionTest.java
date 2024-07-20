package com.nhnacademy.client.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotFoundClientExceptionTest {

    @Test
    void testExceptionMessage() {
        // Given
        String message = "Client not found";

        // When
        NotFoundClientException exception = new NotFoundClientException(message);

        // Then
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void testExceptionThrown() {
        // Given
        String message = "Client not found";

        // When & Then
        assertThatThrownBy(() -> {
            throw new NotFoundClientException(message);
        }).isInstanceOf(NotFoundClientException.class)
                .hasMessage(message);
    }
}
