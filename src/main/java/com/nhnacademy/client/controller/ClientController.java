package com.nhnacademy.client.controller;

import com.nhnacademy.client.dto.ClientLoginRequestDto;
import com.nhnacademy.client.dto.ClientLoginResponseDto;
import com.nhnacademy.client.dto.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.ClientRegisterResponseDto;
import com.nhnacademy.client.exception.ClientEmailDuplicatesException;
import com.nhnacademy.client.exception.NotFoundClientException;
import com.nhnacademy.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/api/client")
    public ResponseEntity<ClientRegisterResponseDto> createClient(@Valid @RequestBody ClientRegisterRequestDto clientRegisterRequestDto) {
        return ResponseEntity.ok(clientService.register(clientRegisterRequestDto));
    }

    @GetMapping("/api/client/login")
    public ResponseEntity<ClientLoginResponseDto> login(@RequestBody ClientLoginRequestDto clientLoginRequestDto) {
        return ResponseEntity.ok(clientService.login(clientLoginRequestDto));
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
