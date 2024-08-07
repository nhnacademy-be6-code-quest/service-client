package com.nhnacademy.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientCouponPaymentResponseDto {
    private Long clientId;
    private String clientName;
    private String clientEmail;
}
