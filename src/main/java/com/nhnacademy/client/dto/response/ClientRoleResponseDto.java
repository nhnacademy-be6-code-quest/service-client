package com.nhnacademy.client.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClientRoleResponseDto {
    private List<String> roles;
}
