package com.nhnacademy.client.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRegisterRequestDtoTest {

    private Validator validator;
    private ClientRegisterRequestDto clientRegisterRequestDto;

    private String clientEmail = "test@example.com";
    private String clientPassword = "Password1!";
    private String clientName = "JohnDoe";
    private LocalDate clientBirth = LocalDate.of(1990, 1, 1);
    private String clientPhoneNumber = "01012345678";

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        clientRegisterRequestDto = new ClientRegisterRequestDto(
                clientEmail,
                clientPassword,
                clientName,
                clientBirth,
                clientPhoneNumber
        );
    }

    @Test
    void testGetterAndSetter() {
        // Test getter
        assertThat(clientRegisterRequestDto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(clientRegisterRequestDto.getClientPassword()).isEqualTo(clientPassword);
        assertThat(clientRegisterRequestDto.getClientName()).isEqualTo(clientName);
        assertThat(clientRegisterRequestDto.getClientBirth()).isEqualTo(clientBirth);
        assertThat(clientRegisterRequestDto.getClientPhoneNumber()).isEqualTo(clientPhoneNumber);

        // Test setter
        String newClientEmail = "new@example.com";
        String newClientPassword = "NewPassword1!";
        String newClientName = "JaneDoe";
        LocalDate newClientBirth = LocalDate.of(1995, 5, 5);
        String newClientPhoneNumber = "01198765432";

        clientRegisterRequestDto.setClientEmail(newClientEmail);
        clientRegisterRequestDto.setClientPassword(newClientPassword);
        clientRegisterRequestDto.setClientName(newClientName);
        clientRegisterRequestDto.setClientBirth(newClientBirth);
        clientRegisterRequestDto.setClientPhoneNumber(newClientPhoneNumber);

        assertThat(clientRegisterRequestDto.getClientEmail()).isEqualTo(newClientEmail);
        assertThat(clientRegisterRequestDto.getClientPassword()).isEqualTo(newClientPassword);
        assertThat(clientRegisterRequestDto.getClientName()).isEqualTo(newClientName);
        assertThat(clientRegisterRequestDto.getClientBirth()).isEqualTo(newClientBirth);
        assertThat(clientRegisterRequestDto.getClientPhoneNumber()).isEqualTo(newClientPhoneNumber);
    }

    @Test
    void testAllArgsConstructor() {
        ClientRegisterRequestDto dto = new ClientRegisterRequestDto(
                clientEmail,
                clientPassword,
                clientName,
                clientBirth,
                clientPhoneNumber
        );

        assertThat(dto.getClientEmail()).isEqualTo(clientEmail);
        assertThat(dto.getClientPassword()).isEqualTo(clientPassword);
        assertThat(dto.getClientName()).isEqualTo(clientName);
        assertThat(dto.getClientBirth()).isEqualTo(clientBirth);
        assertThat(dto.getClientPhoneNumber()).isEqualTo(clientPhoneNumber);
    }

    @Test
    void testNoArgsConstructor() {
        ClientRegisterRequestDto dto = new ClientRegisterRequestDto();
        assertThat(dto.getClientEmail()).isNull();
        assertThat(dto.getClientPassword()).isNull();
        assertThat(dto.getClientName()).isNull();
        assertThat(dto.getClientBirth()).isNull();
        assertThat(dto.getClientPhoneNumber()).isNull();
    }

    @Test
    void testInvalidEmail() {
        clientRegisterRequestDto.setClientEmail("invalid-email");

        Set<ConstraintViolation<ClientRegisterRequestDto>> violations = validator.validate(clientRegisterRequestDto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testInvalidPassword() {
        clientRegisterRequestDto.setClientPassword("password");

        Set<ConstraintViolation<ClientRegisterRequestDto>> violations = validator.validate(clientRegisterRequestDto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testInvalidName() {
        clientRegisterRequestDto.setClientName("Invalid@Name");

        Set<ConstraintViolation<ClientRegisterRequestDto>> violations = validator.validate(clientRegisterRequestDto);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testInvalidPhoneNumber() {
        clientRegisterRequestDto.setClientPhoneNumber("123");

        Set<ConstraintViolation<ClientRegisterRequestDto>> violations = validator.validate(clientRegisterRequestDto);
        assertThat(violations).isNotEmpty();
    }
}
