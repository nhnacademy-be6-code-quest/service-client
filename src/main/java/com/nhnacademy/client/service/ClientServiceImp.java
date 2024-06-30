package com.nhnacademy.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.client.dto.message.ClientLoginMessageDto;
import com.nhnacademy.client.dto.request.ClientOAuthRegisterRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterAddressRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterPhoneNumberRequestDto;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.entity.*;
import com.nhnacademy.client.exception.ClientAuthenticationFailedException;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import com.nhnacademy.client.exception.RabbitMessageConvertException;
import com.nhnacademy.client.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService {
    private final ObjectMapper objectMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final ClientRoleRepository clientRoleRepository;
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
                .clientBirth(registerInfo.getClientBirth())
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .isDeleted(false)
                .clientGrade(clientGradeRepository.findByClientGradeName("common"))
                .build());

        clientNumberRepository.save(ClientNumber.builder()
                .client(client)
                .clientPhoneNumber(registerInfo.getClientPhoneNumber())
                .build());

        clientRoleRepository.save(ClientRole.builder()
                        .client(client)
                        .role(roleRepository.findByRoleName("ROLE_USER"))
                .build());
        return new ClientRegisterResponseDto(client.getClientEmail(), client.getClientCreatedAt());
    }

    @Override
    public String oauthRegister(ClientOAuthRegisterRequestDto clientOAuthRegisterRequestDto) {
        Client client = clientRepository.save(Client.builder()
                .clientEmail(clientOAuthRegisterRequestDto.getIdentify())
                .clientPassword(passwordEncoder.encode(UUID.randomUUID().toString()))
                .clientName(clientOAuthRegisterRequestDto.getName())
                .clientBirth(clientOAuthRegisterRequestDto.getBirth())
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .isDeleted(false)
                .clientGrade(clientGradeRepository.findByClientGradeName("common"))
                .build());

        clientRoleRepository.save(ClientRole.builder()
                .client(client)
                .role(roleRepository.findByRoleName("ROLE_OAUTH"))
                .build());
        return "Success";
    }

    @Override
    public ClientLoginResponseDto login(String email) {
        Client client = clientRepository.findByClientEmail(email);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + email);
        }
        return ClientLoginResponseDto.builder()
                .role(clientRoleRepository.findRolesByClient(client).stream()
                        .map(Role::getRoleName)
                        .toList())
                .clientId(client.getClientId())
                .clientEmail(client.getClientEmail())
                .clientPassword(client.getClientPassword())
                .clientName(client.getClientName())
                .build();
    }

    @Override
    public ClientPrivacyResponseDto privacy(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        }
        return ClientPrivacyResponseDto.builder()
                .clientEmail(client.getClientEmail())
                .clientName(client.getClientName())
                .clientGrade(client.getClientGrade().getClientGradeName())
                .clientBirth(client.getClientBirth())
                .build();
    }

    @Override
    public Page<ClientCouponPaymentResponseDto> couponPayment(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("clientId").descending());
        return clientRepository.findAll(pageRequest).map(client -> ClientCouponPaymentResponseDto.builder()
                .clientId(client.getClientId())
                .clientEmail(client.getClientEmail())
                .clientName(client.getClientName())
                .build());
    }

    @Override
    public List<ClientDeliveryAddressResponseDto> deliveryAddress(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        }
        return clientDeliveryAddressRepository.findAllByClient(client).stream()
                .map(address -> ClientDeliveryAddressResponseDto.builder()
                        .clientDeliveryAddressId(address.getClientDeliveryAddressId())
                        .clientDeliveryZipCode(address.getClientDeliveryZipCode())
                        .clientDeliveryAddress(address.getClientDeliveryAddress())
                        .clientDeliveryAddressDetail(address.getClientDeliveryAddressDetail())
                        .clientDeliveryAddressNickname(address.getClientDeliveryAddressNickname())
                        .build())
                .toList();
    }

    @Override
    public List<ClientPhoneNumberResponseDto> getPhoneNumbers(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        }
        return clientNumberRepository.findAllByClient(client).stream()
                .map(number -> ClientPhoneNumberResponseDto.builder()
                        .clientNumberId(number.getClientNumberId())
                        .clientPhoneNumber(number.getClientPhoneNumber())
                        .build())
                .toList();
    }

    @Override
    public ClientOrderResponseDto order(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        }
        return ClientOrderResponseDto.builder()
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .clientNumbers(clientNumberRepository.findAllByClient(client).stream()
                        .map(ClientNumber::getClientPhoneNumber)
                        .toList())
                .deliveryAddresses(clientDeliveryAddressRepository.findAllByClient(client).stream()
                        .map(address -> ClientDeliveryAddressResponseDto.builder()
                                .clientDeliveryAddressId(address.getClientDeliveryAddressId())
                                .clientDeliveryZipCode(address.getClientDeliveryZipCode())
                                .clientDeliveryAddress(address.getClientDeliveryAddress())
                                .clientDeliveryAddressDetail(address.getClientDeliveryAddressDetail())
                                .clientDeliveryAddressNickname(address.getClientDeliveryAddressNickname())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public String registerAddress(ClientRegisterAddressRequestDto clientRegisterAddressDto, Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        }
        clientDeliveryAddressRepository.save(ClientDeliveryAddress.builder()
                        .client(client)
                        .clientDeliveryZipCode(clientRegisterAddressDto.getClientDeliveryZipCode())
                        .clientDeliveryAddressNickname(clientRegisterAddressDto.getClientDeliveryAddressNickname())
                        .clientDeliveryAddress(clientRegisterAddressDto.getClientDeliveryAddress())
                        .clientDeliveryAddressDetail(clientRegisterAddressDto.getClientDeliveryAddressDetail())
                .build());
        return "Success";
    }

    @Override
    public String registerPhoneNumber(ClientRegisterPhoneNumberRequestDto clientPhoneNumberResponseDto, Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        }
        clientNumberRepository.save(ClientNumber.builder()
                        .client(client)
                        .clientPhoneNumber(clientPhoneNumberResponseDto.getPhoneNumber())
                .build());
        return "Success";
    }

    @Override
    public String deleteAddress(Long addressId) {
        clientDeliveryAddressRepository.deleteById(addressId);
        return "Success";
    }

    @Override
    public String deletePhoneNumber(Long phoneNumberId) {
        clientNumberRepository.deleteById(phoneNumberId);
        return "Success";
    }

    @Override
    public String deleteClient(Long id, String password) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        } else if (!passwordEncoder.matches(password, client.getClientPassword())) {
            throw new ClientAuthenticationFailedException("Client password does not match");
        }
        client.setDeleted(true);
        client.setClientDeleteDate(LocalDateTime.now());
        clientRepository.save(client);
        return "Success";
    }

    @Override
    public String updateClient(Long id, String name, LocalDate birth) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + id);
        }
        client.setClientName(name);
        client.setClientBirth(birth);
        clientRepository.save(client);
        return "Success";
    }

    @Override
    @RabbitListener(queues = "${rabbit.login.queue.name}")
    public void receiveMessage(String message) {
        log.info("consume login message: {}", message);
        ClientLoginMessageDto clientLoginMessageDto;
        try {
             clientLoginMessageDto = objectMapper.readValue(message , ClientLoginMessageDto.class);
        } catch (IOException e) {
            throw new RabbitMessageConvertException("Failed to convert message to ClientLoginMessageDto");
        }
        Client client = clientRepository.findById(clientLoginMessageDto.getClientId()).orElse(null);
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + clientLoginMessageDto.getClientId());
        }
        log.info("success update login");
        client.setLastLoginDate(clientLoginMessageDto.getLastLoginDate());
        clientRepository.save(client);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateInactiveClients() {
        int updatedRecords;
        do {
            updatedRecords = clientRepository.updateClientIsDeletedIfInactive();
            log.info("batch start: delete row({}) ", updatedRecords);
        } while (updatedRecords > 0);
    }
}
