package com.nhnacademy.client.controller;

import com.nhnacademy.client.dto.request.*;
import com.nhnacademy.client.dto.response.*;
import com.nhnacademy.client.exception.*;
import com.nhnacademy.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    @PostMapping("/api/client")
    public ResponseEntity<ClientRegisterResponseDto> createClient(@Valid @RequestBody ClientRegisterRequestDto clientRegisterRequestDto) {
        log.info("Create client : {}", clientRegisterRequestDto);
        return ResponseEntity.ok(clientService.register(clientRegisterRequestDto));
    }

    @Override
    @PostMapping("/api/oauth/client")
    public ResponseEntity<String> createOauthClient(@RequestBody ClientOAuthRegisterRequestDto clientOAuthRegisterRequestDto) {
        log.info("Create OAuth client : {}", clientOAuthRegisterRequestDto);
        return ResponseEntity.ok(clientService.oauthRegister(clientOAuthRegisterRequestDto));
    }

    @Override
    @GetMapping("/api/client/login")
    public ResponseEntity<ClientLoginResponseDto> login(@RequestParam String email) {
        log.info("Login : {}", email);
        return ResponseEntity.ok(clientService.login(email));
    }

    @Override
    @GetMapping("/api/client")
    public ResponseEntity<ClientPrivacyResponseDto> getPrivacy(@RequestHeader HttpHeaders httpHeaders) {
        log.info("Get privacy : {}", httpHeaders);
        return ResponseEntity.ok(clientService.privacy(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    @GetMapping("/api/client/coupon-payment")
    public ResponseEntity<Page<ClientCouponPaymentResponseDto>> getCouponPayments(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(clientService.couponPayment(page, size));
    }

    @Override
    @GetMapping("/api/client/address")
    public ResponseEntity<List<ClientDeliveryAddressResponseDto>> getDeliveryAddresses(@RequestHeader HttpHeaders httpHeaders) {
        log.info("Get delivery addresses : {}", httpHeaders);
        return ResponseEntity.ok(clientService.deliveryAddress(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    @GetMapping("/api/client/order")
    public ResponseEntity<ClientOrderResponseDto> getOrders(@RequestHeader HttpHeaders httpHeaders) {
        log.info("Get orders : {}", httpHeaders);
        return ResponseEntity.ok(clientService.order(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    @PostMapping("/api/client/address")
    public ResponseEntity<String> registerAddress(@RequestHeader HttpHeaders httpHeaders, @RequestBody ClientRegisterAddressRequestDto clientRegisterAddressDto) {
        log.info("Register address : {}", clientRegisterAddressDto);
        return ResponseEntity.ok(clientService.registerAddress(clientRegisterAddressDto, Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    @DeleteMapping("/api/client/address")
    public ResponseEntity<String> deleteAddress(@RequestParam Long addressId) {
        log.info("Delete address");
        return ResponseEntity.ok(clientService.deleteAddress(addressId));
    }

    @Override
    @DeleteMapping("/api/client")
    public ResponseEntity<String> deleteClient(@RequestHeader HttpHeaders httpHeaders) {
        log.info("Delete client");
        return ResponseEntity.ok(clientService.deleteClient(Long.valueOf(httpHeaders.getFirst(ID_HEADER)), httpHeaders.getFirst(PASSWORD_HEADER)));
    }

    @Override
    @GetMapping("/api/client/phone")
    public ResponseEntity<List<ClientPhoneNumberResponseDto>> getPhoneNumbers(@RequestHeader HttpHeaders httpHeaders) {
        log.info("Get phone numbers");
        return ResponseEntity.ok(clientService.getPhoneNumbers(Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    @PostMapping("/api/client/phone")
    public ResponseEntity<String> registerPhoneNumber(@RequestHeader HttpHeaders httpHeaders, ClientRegisterPhoneNumberRequestDto clientRegisterPhoneNumberDto) {
        log.info("Register phone number : {}", clientRegisterPhoneNumberDto);
        return ResponseEntity.ok(clientService.registerPhoneNumber(clientRegisterPhoneNumberDto, Long.valueOf(httpHeaders.getFirst(ID_HEADER))));
    }

    @Override
    @DeleteMapping("/api/client/phone")
    public ResponseEntity<String> deletePhoneNumber(@RequestParam(name = "phoneNumberId") Long phoneNumberId) {
        log.info("Delete phone number");
        return ResponseEntity.ok(clientService.deletePhoneNumber(phoneNumberId));
    }

    @Override
    @PutMapping("/api/client")
    public ResponseEntity<String> updateClient(HttpHeaders httpHeaders, ClientUpdatePrivacyRequestDto clientUpdatePrivacyRequestDto) {
        log.info("Update client : {}", clientUpdatePrivacyRequestDto);
        return ResponseEntity.ok(clientService.updateClient(Long.valueOf(httpHeaders.getFirst(ID_HEADER)), clientUpdatePrivacyRequestDto.getClientName(), clientUpdatePrivacyRequestDto.getClientBirth()));
    }

    @Override
    @PutMapping("/api/client/change-password")
    public ResponseEntity<String> changePasswordClient(@RequestBody ClientChangePasswordRequestDto clientChangePasswordRequestDto) {
        log.info("Change password client : {}", clientChangePasswordRequestDto);
        return ResponseEntity.ok(clientService.changePasswordClient(
                clientChangePasswordRequestDto.getEmail(),
                clientChangePasswordRequestDto.getNewPassword(),
                clientChangePasswordRequestDto.getToken()
        ));
    }

    @Override
    @PutMapping("/api/client/recovery-account")
    public ResponseEntity<String> recoveryClient(@RequestBody ClientRecoveryRequestDto clientRecoveryRequestDto) {
        log.info("recovery client : {}", clientRecoveryRequestDto);
        return ResponseEntity.ok(clientService.reccveryClinet(
                clientRecoveryRequestDto.getEmail(),
                clientRecoveryRequestDto.getToken()
        ));
    }

    @Override
    @ExceptionHandler(ClientEmailDuplicatesException.class)
    public ResponseEntity<ClientRegisterResponseDto> handleException(ClientEmailDuplicatesException e) {
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @Override
    @ExceptionHandler(NotFoundClientException.class)
    public ResponseEntity<ClientRegisterResponseDto> handleException(NotFoundClientException e) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    @ExceptionHandler(ClientAuthenticationFailedException.class)
    public ResponseEntity<ClientLoginResponseDto> handleException(ClientAuthenticationFailedException e) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @Override
    @ExceptionHandler(ClientDeletedException.class)
    public ResponseEntity<ClientLoginResponseDto> handleException(ClientDeletedException e) {
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @Override
    @ExceptionHandler(ClientAddressOutOfRangeException.class)
    public ResponseEntity<String> handleException(ClientAddressOutOfRangeException e) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
