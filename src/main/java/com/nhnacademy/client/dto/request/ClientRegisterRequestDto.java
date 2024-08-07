package com.nhnacademy.client.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegisterRequestDto {
    @NotNull
    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String clientEmail;

    @NotNull
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String clientPassword;

    @NotNull
    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String clientName;

    @NotNull(message = "생일은 필수 입력 항목입니다.")
    private LocalDate clientBirth;

    @NotNull
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Size(min = 10, max = 11)
    @Digits(integer = 11, fraction = 0)
    private String clientPhoneNumber;
}
