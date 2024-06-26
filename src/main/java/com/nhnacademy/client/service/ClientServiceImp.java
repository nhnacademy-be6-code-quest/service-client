package com.nhnacademy.client.service;

import com.nhnacademy.client.dto.request.ClientRegisterAddressRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterPhoneNumberRequestDto;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientDeliveryAddress;
import com.nhnacademy.client.entity.ClientNumber;
import com.nhnacademy.client.entity.Role;
import com.nhnacademy.client.exception.ClientAuthenticationFailedException;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import com.nhnacademy.client.repository.ClientDeliveryAddressRepository;
import com.nhnacademy.client.repository.ClientGradeRepository;
import com.nhnacademy.client.repository.ClientNumberRepository;
import com.nhnacademy.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        if (client == null || client.isDeleted()) {
            throw new NotFoundClientException("Not found : " + email);
        }
        return ClientLoginResponseDto.builder()
                .role(Role.ROLE_USER)
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
        client.setClientBirth(birth.atStartOfDay());
        clientRepository.save(client);
        return "Success";
    }
}
