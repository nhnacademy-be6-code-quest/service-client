package com.nhnacademy.client.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientAddressOutOfRangeExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Address out of range";

        ClientAddressOutOfRangeException exception = assertThrows(ClientAddressOutOfRangeException.class, () -> {
            throw new ClientAddressOutOfRangeException(errorMessage);
        });

        assertThat(exception).isInstanceOf(ClientAddressOutOfRangeException.class);
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void testExceptionInheritance() {
        ClientAddressOutOfRangeException exception = new ClientAddressOutOfRangeException("Address out of range");

        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
