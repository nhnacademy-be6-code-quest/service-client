package com.nhnacademy.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.client.dto.message.ClientLoginMessageDto;
import com.nhnacademy.client.dto.request.ClientOAuthRegisterRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterAddressRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterPhoneNumberRequestDto;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.entity.*;
import com.nhnacademy.client.exception.*;
import com.nhnacademy.client.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final String NOT_FOUND_MESSAGE = "Not found : ";
    private final String SUCCESS_MESSAGE = "Success";

    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    private final RoleRepository roleRepository;
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
        return SUCCESS_MESSAGE;
    }

    @Override
    public ClientLoginResponseDto login(String email) {
        Client client = clientRepository.findByClientEmail(email);
        checkByEmail(client, email);
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
        checkById(client, id);
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
        checkById(client, id);
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
        checkById(client, id);
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
            throw new NotFoundClientException(NOT_FOUND_MESSAGE + id);
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
        checkById(client, id);
        if(deliveryAddress(id).size() >= 10) {
            throw new ClientAddressOutOfRangeException("Client address is too Many");
        }
        clientDeliveryAddressRepository.save(ClientDeliveryAddress.builder()
                        .client(client)
                        .clientDeliveryZipCode(clientRegisterAddressDto.getClientDeliveryZipCode())
                        .clientDeliveryAddressNickname(clientRegisterAddressDto.getClientDeliveryAddressNickname())
                        .clientDeliveryAddress(clientRegisterAddressDto.getClientDeliveryAddress())
                        .clientDeliveryAddressDetail(clientRegisterAddressDto.getClientDeliveryAddressDetail())
                .build());
        return SUCCESS_MESSAGE;
    }

    @Override
    public String registerPhoneNumber(ClientRegisterPhoneNumberRequestDto clientPhoneNumberResponseDto, Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        checkById(client, id);

        clientNumberRepository.save(ClientNumber.builder()
                        .client(client)
                        .clientPhoneNumber(clientPhoneNumberResponseDto.getPhoneNumber())
                .build());
        return SUCCESS_MESSAGE;
    }

    @Override
    public String deleteAddress(Long addressId) {
        clientDeliveryAddressRepository.deleteById(addressId);
        return SUCCESS_MESSAGE;
    }

    @Override
    public String deletePhoneNumber(Long phoneNumberId) {
        clientNumberRepository.deleteById(phoneNumberId);
        return SUCCESS_MESSAGE;
    }

    @Override
    public String deleteClient(Long id, String password) {
        Client client = clientRepository.findById(id).orElse(null);
        checkById(client, id);
        if (!passwordEncoder.matches(password, client.getClientPassword())) {
            throw new ClientAuthenticationFailedException("Client password does not match");
        }
        client.setDeleted(true);
        client.setClientDeleteDate(LocalDateTime.now());
        clientRepository.save(client);
        return SUCCESS_MESSAGE;
    }

    @Override
    public String updateClient(Long id, String name, LocalDate birth) {
        Client client = clientRepository.findById(id).orElse(null);
        checkById(client, id);
        client.setClientName(name);
        client.setClientBirth(birth);
        clientRepository.save(client);
        return SUCCESS_MESSAGE;
    }

    @Override
    public String changePasswordClient(String email, String password, String token) {
        Client client = clientRepository.findByClientEmail(email);
        checkByEmail(client, email);
        if (redisTemplate.opsForHash().get("change-password", token) == null) {
            throw new ClientAuthenticationFailedException("client token does not exist");
        }
        client.setClientPassword(passwordEncoder.encode(password));
        clientRepository.save(client);
        return SUCCESS_MESSAGE;
    }

    @Override
    public String recveryClinet(String email, String token) {
        Client client = clientRepository.findByClientEmail(email);
        if (client == null) {
            throw new NotFoundClientException(NOT_FOUND_MESSAGE + email);
        } else if (redisTemplate.opsForHash().get("recovery-account", token) == null) {
            throw new ClientAuthenticationFailedException("client token does not exist");
        }
        client.setDeleted(false);
        clientRepository.save(client);
        return SUCCESS_MESSAGE;
    }

    @Override
    public String recveryOauthClinet(String email) {
        Client client = clientRepository.findByClientEmail(email);
        if (client == null) {
            throw new NotFoundClientException(NOT_FOUND_MESSAGE + email);
        }
        client.setDeleted(false);
        clientRepository.save(client);
        return SUCCESS_MESSAGE;
    }

    @Override
    public List<Long> getThisMonthBirthClient() {
        return clientRepository.findClientsWithBirthInCurrentMonth();
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
            throw new NotFoundClientException(NOT_FOUND_MESSAGE + clientLoginMessageDto.getClientId());
        }
        log.info("success update login");
        client.setLastLoginDate(clientLoginMessageDto.getLastLoginDate());
        clientRepository.save(client);
    }

    private void checkByEmail(Client client, String email) {
        if (client == null) {
            throw new NotFoundClientException(NOT_FOUND_MESSAGE + email);
        } else if (client.isDeleted()) {
            throw new ClientDeletedException("Deleted Client : " + email);
        }
    }

    private void checkById(Client client, Long id) {
        if (client == null) {
            throw new NotFoundClientException(NOT_FOUND_MESSAGE + id);
        } else if (client.isDeleted()) {
            throw new ClientDeletedException("Deleted Client : " + id);
        }
    }
}
