package com.nhnacademy.client.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ClientRecoveryRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        ClientRecoveryRequestDto dto = new ClientRecoveryRequestDto();
        assertThat(dto).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        ClientRecoveryRequestDto dto = new ClientRecoveryRequestDto("test@example.com", "token123");
        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getToken()).isEqualTo("token123");
    }

    @Test
    void testSettersAndGetters() {
        ClientRecoveryRequestDto dto = new ClientRecoveryRequestDto();
        dto.setEmail("test@example.com");
        dto.setToken("token123");

        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getToken()).isEqualTo("token123");
    }

    @Test
    void testValidDto() {
        ClientRecoveryRequestDto dto = new ClientRecoveryRequestDto("test@example.com", "token123");
        Set<jakarta.validation.ConstraintViolation<ClientRecoveryRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Valid DTO should not have violations");
    }
}
