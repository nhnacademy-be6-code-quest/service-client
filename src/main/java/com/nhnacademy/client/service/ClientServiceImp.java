package com.nhnacademy.client.service;

import com.nhnacademy.client.dto.response.ClientDeliveryAddressDto;
import com.nhnacademy.client.dto.response.ClientLoginResponseDto;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.response.ClientPrivacyResponseDto;
import com.nhnacademy.client.dto.response.ClientRegisterResponseDto;
import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientNumber;
import com.nhnacademy.client.entity.Role;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import com.nhnacademy.client.repository.ClientDeliveryAddressRepository;
import com.nhnacademy.client.repository.ClientGradeRepository;
import com.nhnacademy.client.repository.ClientNumberRepository;
import com.nhnacademy.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService {
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final ClientGradeRepository clientGradeRepository;
    private final ClientNumberRepository clientNumberRepository;
    private final ClientDeliveryAddressRepository clientDeliveryAddressRepository;

    @Override
    public ClientRegisterResponseDto register(ClientRegisterRequestDto registerInfo) {
        if (clientRepository.findByClientEmail(registerInfo.getClientEmail()) != null) {
            throw new ClientEmailDuplicatesException();
        }

        Client client = clientRepository.save(Client.builder()
                .clientEmail(registerInfo.getClientEmail())
                .clientPassword(passwordEncoder.encode(registerInfo.getClientPassword()))
                .clientName(registerInfo.getClientName())
                .clientBirth(registerInfo.getClientBirth().atStartOfDay())
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
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

    @Override
    public ClientLoginResponseDto login(String email) {
        Client client = clientRepository.findByClientEmail(email);
        if (client == null) {
            throw new NotFoundClientException("Not found : " + email);
        }
        return ClientLoginResponseDto.builder()
                .role(Role.ROLE_USER)
                .clientEmail(client.getClientEmail())
                .clientPassword(client.getClientPassword())
                .clientName(client.getClientName())
                .build();
    }

    @Override
    public ClientPrivacyResponseDto privacy(String email) {
        Client client = clientRepository.findByClientEmail(email);
        if (client == null) {
            throw new NotFoundClientException("Not found : " + email);
        }

        return ClientPrivacyResponseDto.builder()
                .clientEmail(client.getClientEmail())
                .clientName(client.getClientName())
                .clientGrade(client.getClientGrade().getClientGradeName())
                .clientBirth(client.getClientBirth())
                .clientNumbers(clientNumberRepository.findAllByClient(client).stream()
                        .map(clientNumber -> clientNumber.getClientPhoneNumber())
                        .toList())
                .clientDeliveryAddresses(clientDeliveryAddressRepository.findAllByClient(client).stream()
                        .map(address -> ClientDeliveryAddressDto.builder()
                                .clientDeliveryZipCode(address.getClientDeliveryZipCode())
                                .clientDeliveryAddress(address.getClientDeliveryAddress())
                                .clientDeliveryAddressDetail(address.getClientDeliveryAddressDetail())
                                .clientDeliveryAddressNickname(address.getClientDeliveryAddressNickname())
                                .build())
                        .toList())
                .build();
    }
}
