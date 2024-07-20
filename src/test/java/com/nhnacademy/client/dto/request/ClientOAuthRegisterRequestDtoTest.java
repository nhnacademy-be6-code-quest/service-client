package com.nhnacademy.client.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ClientOAuthRegisterRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        ClientOAuthRegisterRequestDto dto = new ClientOAuthRegisterRequestDto();
        assertThat(dto).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        ClientOAuthRegisterRequestDto dto = new ClientOAuthRegisterRequestDto("identifier123", "John Doe", LocalDate.of(1990, 1, 1));
        assertThat(dto.getIdentify()).isEqualTo("identifier123");
        assertThat(dto.getName()).isEqualTo("John Doe");
        assertThat(dto.getBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testSettersAndGetters() {
        ClientOAuthRegisterRequestDto dto = new ClientOAuthRegisterRequestDto();
        dto.setIdentify("identifier123");
        dto.setName("John Doe");
        dto.setBirth(LocalDate.of(1990, 1, 1));

        assertThat(dto.getIdentify()).isEqualTo("identifier123");
        assertThat(dto.getName()).isEqualTo("John Doe");
        assertThat(dto.getBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testValidDto() {
        ClientOAuthRegisterRequestDto dto = new ClientOAuthRegisterRequestDto("identifier123", "John Doe", LocalDate.of(1990, 1, 1));
        Set<jakarta.validation.ConstraintViolation<ClientOAuthRegisterRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Valid DTO should not have violations");
    }
}
