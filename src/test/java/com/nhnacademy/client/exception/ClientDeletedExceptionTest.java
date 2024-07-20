package com.nhnacademy.client.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientDeletedExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Client has been deleted";

        ClientDeletedException exception = assertThrows(ClientDeletedException.class, () -> {
            throw new ClientDeletedException(errorMessage);
        });

        assertThat(exception).isInstanceOf(ClientDeletedException.class);
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void testExceptionInheritance() {
        ClientDeletedException exception = new ClientDeletedException("Client has been deleted");

        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
