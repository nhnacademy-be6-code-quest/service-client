package com.nhnacademy.auth.service;

import com.nhnacademy.auth.dto.ClientRegisterRequestDto;
import com.nhnacademy.auth.dto.ClientRegisterResponseDto;
import com.nhnacademy.auth.entity.Client;
import com.nhnacademy.auth.entity.ClientNumber;
import com.nhnacademy.auth.entity.Role;
import com.nhnacademy.auth.exception.ClientEmailDuplicatesException;
import com.nhnacademy.auth.repository.ClientGradeRepository;
import com.nhnacademy.auth.repository.ClientNumberRepository;
import com.nhnacademy.auth.repository.ClientRepository;
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
}
