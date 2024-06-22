package com.nhnacademy.client.controller;

import com.nhnacademy.client.dto.response.ClientCouponPaymentResponseDto;
import com.nhnacademy.client.dto.response.ClientLoginResponseDto;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.response.ClientPrivacyResponseDto;
import com.nhnacademy.client.dto.response.ClientRegisterResponseDto;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import com.nhnacademy.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/api/client")
    public ResponseEntity<ClientRegisterResponseDto> createClient(@Valid @RequestBody ClientRegisterRequestDto clientRegisterRequestDto) {
        log.info("Create client : {}", clientRegisterRequestDto);
        return ResponseEntity.ok(clientService.register(clientRegisterRequestDto));
    }

    @GetMapping("/api/client/login")
    public ResponseEntity<ClientLoginResponseDto> login(@RequestParam String email) {
        log.info("Login : {}", email);
        return ResponseEntity.ok(clientService.login(email));
    }

    @GetMapping("/api/client")
    public ResponseEntity<ClientPrivacyResponseDto> getPrivacy(@RequestHeader HttpHeaders httpHeaders) {
        log.info("Get privacy : {}", httpHeaders);
        return ResponseEntity.ok(clientService.privacy(httpHeaders.getFirst("email")));
    }

    @GetMapping("/api/client/coupon-payment")
    public ResponseEntity<Page<ClientCouponPaymentResponseDto>> getCouponPayments(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(clientService.couponPayment(page, size));
    }

    @ExceptionHandler(ClientEmailDuplicatesException.class)
    public ResponseEntity<ClientRegisterResponseDto> handleException(ClientEmailDuplicatesException e) {
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundClientException.class)
    public ResponseEntity<ClientRegisterResponseDto> handleException(NotFoundClientException e) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
