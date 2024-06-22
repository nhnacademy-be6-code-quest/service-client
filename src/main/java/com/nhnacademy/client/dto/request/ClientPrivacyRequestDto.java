package com.nhnacademy.client.dto.request;

import com.nhnacademy.client.entity.ClientGrade;
import com.nhnacademy.client.entity.ClientNumber;
import com.nhnacademy.client.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@NoArgsConstructor
public class ClientPrivacyRequestDto {
    private ClientGrade clientGrade;
    private String clientEmail;
    private String clientName;
    private LocalDateTime clientBirth;
    private List<ClientNumber> clientNumbers;
}
