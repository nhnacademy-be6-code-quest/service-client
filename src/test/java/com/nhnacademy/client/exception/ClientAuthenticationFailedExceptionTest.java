package com.nhnacademy.client.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClientAuthenticationFailedExceptionTest {

    @Test
    void testExceptionMessage() {
        // Given
        String message = "Authentication failed";

        // When
        ClientAuthenticationFailedException exception = new ClientAuthenticationFailedException(message);

        // Then
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void testExceptionThrown() {
        // Given
        String message = "Authentication failed";

        // When & Then
        assertThatThrownBy(() -> {
            throw new ClientAuthenticationFailedException(message);
        }).isInstanceOf(ClientAuthenticationFailedException.class)
                .hasMessage(message);
    }
}
