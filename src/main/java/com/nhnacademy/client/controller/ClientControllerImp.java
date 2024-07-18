package com.nhnacademy.client.controller;

import com.nhnacademy.client.dto.request.*;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.exception.*;
import com.nhnacademy.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClientControllerImp implements ClientController {
    private static final String ID_HEADER = "X-User-Id";
    private static final String PASSWORD_HEADER = "password";

    private final ClientService clientService;

    @Override
    public ResponseEntity<ClientRegisterResponseDto> createClient(ClientRegisterRequestDto clientRegisterRequestDto) {
        log.info("Create client : {}", clientRegisterRequestDto);
        return ResponseEntity.ok(clientService.register(clientRegisterRequestDto));
    }

    @Override
    public ResponseEntity<String> createOauthClient(ClientOAuthRegisterRequestDto clientOAuthRegisterRequestDto) {
        log.info("Create OAuth client : {}", clientOAuthRegisterRequestDto);
        return ResponseEntity.ok(clientService.oauthRegister(clientOAuthRegisterRequestDto));
    }

    @Override
    public ResponseEntity<ClientLoginResponseDto> login(String email) {
        log.info("Login : {}", email);
        return ResponseEntity.ok(clientService.login(email));
    }

    @Override
    public ResponseEntity<ClientPrivacyResponseDto> getPrivacy(HttpHeaders httpHeaders) {
        log.info("Get privacy : {}", httpHeaders);
        return ResponseEntity.ok(clientService.privacy(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    public ResponseEntity<Page<ClientCouponPaymentResponseDto>> getCouponPayments(int page, int size) {
        return ResponseEntity.ok(clientService.couponPayment(page, size));
    }

    @Override
    public ResponseEntity<List<ClientDeliveryAddressResponseDto>> getDeliveryAddresses(HttpHeaders httpHeaders) {
        log.info("Get delivery addresses : {}", httpHeaders);
        return ResponseEntity.ok(clientService.deliveryAddress(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    public ResponseEntity<ClientOrderResponseDto> getOrders(HttpHeaders httpHeaders) {
        log.info("Get orders : {}", httpHeaders);
        return ResponseEntity.ok(clientService.order(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    public ResponseEntity<String> registerAddress(HttpHeaders httpHeaders, ClientRegisterAddressRequestDto clientRegisterAddressDto) {
        log.info("Register address : {}", clientRegisterAddressDto);
        return ResponseEntity.ok(clientService.registerAddress(clientRegisterAddressDto, Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    public ResponseEntity<String> deleteAddress(Long addressId) {
        log.info("Delete address");
        return ResponseEntity.ok(clientService.deleteAddress(addressId));
    }

    @Override
    public ResponseEntity<String> deleteClient(HttpHeaders httpHeaders) {
        log.info("Delete client");
        return ResponseEntity.ok(clientService.deleteClient(Long.valueOf(httpHeaders.getFirst(ID_HEADER)), httpHeaders.getFirst(PASSWORD_HEADER)));
    }

    @Override
    public ResponseEntity<List<ClientPhoneNumberResponseDto>> getPhoneNumbers(HttpHeaders httpHeaders) {
        log.info("Get phone numbers");
        return ResponseEntity.ok(clientService.getPhoneNumbers(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    public ResponseEntity<String> registerPhoneNumber(HttpHeaders httpHeaders, ClientRegisterPhoneNumberRequestDto clientRegisterPhoneNumberDto) {
        log.info("Register phone number : {}", clientRegisterPhoneNumberDto);
        return ResponseEntity.ok(clientService.registerPhoneNumber(clientRegisterPhoneNumberDto, Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    public ResponseEntity<String> deletePhoneNumber(Long phoneNumberId) {
        log.info("Delete phone number");
        return ResponseEntity.ok(clientService.deletePhoneNumber(phoneNumberId));
    }

    @Override
    public ResponseEntity<String> updateClient(HttpHeaders httpHeaders, ClientUpdatePrivacyRequestDto clientUpdatePrivacyRequestDto) {
        log.info("Update client : {}", clientUpdatePrivacyRequestDto);
        return ResponseEntity.ok(clientService.updateClient(Long.valueOf(httpHeaders.getFirst(ID_HEADER)), clientUpdatePrivacyRequestDto.getClientName(), clientUpdatePrivacyRequestDto.getClientBirth()));
    }

    @Override
    public ResponseEntity<String> changePasswordClient(ClientChangePasswordRequestDto clientChangePasswordRequestDto) {
        log.info("Change password client : {}", clientChangePasswordRequestDto);
        return ResponseEntity.ok(clientService.changePasswordClient(
                clientChangePasswordRequestDto.getEmail(),
                clientChangePasswordRequestDto.getNewPassword(),
                clientChangePasswordRequestDto.getToken()
        ));
    }

    @Override
    public ResponseEntity<String> recoveryClient(ClientRecoveryRequestDto clientRecoveryRequestDto) {
        log.info("recovery client : {}", clientRecoveryRequestDto);
        return ResponseEntity.ok(clientService.recoveryClient(
                clientRecoveryRequestDto.getEmail(),
                clientRecoveryRequestDto.getToken()
        ));
    }

    @Override
    public ResponseEntity<String> recoveryOauthClient(String email) {
        log.info("recovery client : {}", email);
        return ResponseEntity.ok(clientService.recoveryOauthClient(email));
    }

    @Override
    public ResponseEntity<List<Long>> getThisMonthBirthClient() {
        log.info("Get this month birth coupon");
        return ResponseEntity.ok(clientService.getThisMonthBirthClient());
    }

    @Override
    public ResponseEntity<ClientNameResponseDto> getClientName(Long clientId) {
        log.info("Get client name : {}", clientId);
        return ResponseEntity.ok(clientService.getClientName(clientId));
    }

    @Override
    public ResponseEntity<ClientGradeRateResponseDto> getClientGradeRate(Long clientId) {
        log.info("Get client grade rate : {}", clientId);
        return ResponseEntity.ok(clientService.getClientGradeRate(clientId));
    }

    @Override
    public ResponseEntity<ClientRoleResponseDto> getClientRole(HttpHeaders httpHeaders) {
        log.info("Get client role : {}", httpHeaders.get("X-User-Role"));
        return ResponseEntity.ok(ClientRoleResponseDto.builder()
                .roles(httpHeaders.get("X-User-Role"))
                .build());
    }

    @Override
    public ResponseEntity<Page<ClientPrivacyResponseDto>> getClientPrivacyPage(int page, int size, String sort, boolean desc) {
        log.info("Get client privacy page : {}", page);
        return ResponseEntity.ok(clientService.getClientPrivacyPage(page, size, sort, desc));
    }

    @Override
    public ResponseEntity<String> updateClientGrade(ClientUpdateGradeRequestDto clientUpdateGradeRequestDto) {
        log.info("Update client grade : {}", clientUpdateGradeRequestDto.getClientId());
        return ResponseEntity.ok(clientService.updateClientGrade(clientUpdateGradeRequestDto.getClientId(), clientUpdateGradeRequestDto.getPayment()));
    }

    @Override
    public ResponseEntity<ClientRegisterResponseDto> handleException(ClientEmailDuplicatesException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Override
    public ResponseEntity<ClientRegisterResponseDto> handleException(NotFoundClientException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<ClientLoginResponseDto> handleException(ClientAuthenticationFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    public ResponseEntity<ClientLoginResponseDto> handleException(ClientDeletedException e) {
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @Override
    public ResponseEntity<String> handleException(ClientAddressOutOfRangeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
