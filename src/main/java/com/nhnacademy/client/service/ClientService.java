package com.nhnacademy.client.service;

import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.response.ClientCouponPaymentResponseDto;
import com.nhnacademy.client.dto.response.ClientLoginResponseDto;
import com.nhnacademy.client.dto.response.ClientPrivacyResponseDto;
import com.nhnacademy.client.dto.response.ClientRegisterResponseDto;
import org.springframework.data.domain.Page;

public interface ClientService {
    ClientRegisterResponseDto register(ClientRegisterRequestDto registerInfo);
    ClientLoginResponseDto login(String email);
    ClientPrivacyResponseDto privacy(String email);
    Page<ClientCouponPaymentResponseDto> couponPayment(int page, int size);
}
