package com.nhnacademy.client.service;

import com.nhnacademy.client.dto.request.ClientRegisterAddressRequestDto;
import com.nhnacademy.client.dto.request.ClientRegisterRequestDto;
import com.nhnacademy.client.dto.response.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {
    ClientRegisterResponseDto register(ClientRegisterRequestDto registerInfo);
    ClientLoginResponseDto login(String email);
    ClientPrivacyResponseDto privacy(String email);
    Page<ClientCouponPaymentResponseDto> couponPayment(int page, int size);
    List<ClientDeliveryAddressResponseDto> deliveryAddress(String email);
    ClientOrderResponseDto order(String email);
    String registerAddress(ClientRegisterAddressRequestDto clientRegisterAddressDto, String email);
    String deleteAddress(Long addressId);
}
