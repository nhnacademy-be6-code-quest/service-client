package com.nhnacademy.client.dto.request;

import com.nhnacademy.client.entity.ClientGrade;
import com.nhnacademy.client.entity.ClientNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientPrivacyRequestDto {
    private ClientGrade clientGrade;
    private String clientEmail;
    private String clientName;
    private LocalDateTime clientBirth;
    private List<ClientNumber> clientNumbers;
}
