package com.nhnacademy.client.service;

import com.nhnacademy.client.dto.ClientLoginRequestDto;
import com.nhnacademy.client.dto.ClientLoginResponseDto;
import com.nhnacademy.client.dto.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.ClientRegisterResponseDto;
import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientNumber;
import com.nhnacademy.client.entity.Role;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import com.nhnacademy.client.repository.ClientGradeRepository;
import com.nhnacademy.client.repository.ClientNumberRepository;
import com.nhnacademy.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final ClientGradeRepository clientGradeRepository;
    private final ClientNumberRepository clientNumberRepository;

    public ClientRegisterResponseDto register(ClientRegisterRequestDto registerInfo) {
        if (clientRepository.findByClientEmail(registerInfo.getClientEmail()) != null) {
            throw new ClientEmailDuplicatesException();
        }

        Client client = clientRepository.save(Client.builder()
                .clientEmail(registerInfo.getClientEmail())
                .clientPassword(passwordEncoder.encode(registerInfo.getClientPassword()))
                .clientName(registerInfo.getClientName())
                .clientBirth(registerInfo.getClientBirth())
                .clientCreatedAt(LocalDateTime.now())
                .clientLoginAt(LocalDateTime.now())
                .isDeleted(false)
                .role(Role.ROLE_USER)
                .clientGrade(clientGradeRepository.findByClientGradeName("common"))
                .build());

        clientNumberRepository.save(ClientNumber.builder()
                .client(client)
                .clientPhoneNumber(registerInfo.getClientPhoneNumber())
                .build());

        return new ClientRegisterResponseDto(client.getClientEmail(), client.getClientCreatedAt());
    }

    public ClientLoginResponseDto login(ClientLoginRequestDto loginInfo) {
        Client client = clientRepository.findByClientEmail(loginInfo.getClientEmail());
        if (client == null) {
            throw new NotFoundClientException("Not found : " + loginInfo.getClientEmail());
        }
        return ClientLoginResponseDto.builder()
                .role(Role.ROLE_USER)
                .clientEmail(client.getClientEmail())
                .clientPassword(client.getClientPassword())
                .clientName(client.getClientName())
                .build();
    }
}
