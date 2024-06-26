package com.nhnacademy.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "coupon", url = "http://localhost:8001")
public interface CouponClient {
    @PostMapping("/api/client/welcome")
    ResponseEntity<String> payWelcomeCoupon();
}
