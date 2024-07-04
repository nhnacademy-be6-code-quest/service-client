package com.nhnacademy.client.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ClientChangePasswordRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        ClientChangePasswordRequestDto dto = new ClientChangePasswordRequestDto();
        assertThat(dto).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        ClientChangePasswordRequestDto dto = new ClientChangePasswordRequestDto("test@example.com", "token", "Password1!");
        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getToken()).isEqualTo("token");
        assertThat(dto.getNewPassword()).isEqualTo("Password1!");
    }

    @Test
    void testSettersAndGetters() {
        ClientChangePasswordRequestDto dto = new ClientChangePasswordRequestDto();
        dto.setEmail("test@example.com");
        dto.setToken("token");
        dto.setNewPassword("Password1!");

        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getToken()).isEqualTo("token");
        assertThat(dto.getNewPassword()).isEqualTo("Password1!");
    }

    @Test
    void testValidPassword() {
        ClientChangePasswordRequestDto dto = new ClientChangePasswordRequestDto("test@example.com", "token", "Password1!");
        Set<jakarta.validation.ConstraintViolation<ClientChangePasswordRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Valid password should not have violations");
    }

    @Test
    void testInvalidPasswordNull() {
        ClientChangePasswordRequestDto dto = new ClientChangePasswordRequestDto("test@example.com", "token", null);
        Set<jakarta.validation.ConstraintViolation<ClientChangePasswordRequestDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("널이어서는 안됩니다"));
    }

    @Test
    void testInvalidPasswordBlank() {
        ClientChangePasswordRequestDto dto = new ClientChangePasswordRequestDto("test@example.com", "token", "");
        Set<jakarta.validation.ConstraintViolation<ClientChangePasswordRequestDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("비밀번호는 필수 입력 값입니다."));
    }

    @Test
    void testInvalidPasswordPattern() {
        ClientChangePasswordRequestDto dto = new ClientChangePasswordRequestDto("test@example.com", "token", "password");
        Set<jakarta.validation.ConstraintViolation<ClientChangePasswordRequestDto>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
    }
}
