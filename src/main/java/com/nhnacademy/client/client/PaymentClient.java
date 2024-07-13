package com.nhnacademy.client.client;

import com.nhnacademy.client.dto.response.PaymentGradeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment", url = "http://localhost:8001")
public interface PaymentClient {
    @GetMapping("/api/payment/grade/{clientId}")
    PaymentGradeResponseDto getPaymentRecordOfClient(@PathVariable Long clientId);
}
