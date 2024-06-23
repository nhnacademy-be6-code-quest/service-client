package com.nhnacademy.client.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientOrderResponseDto {
    private Long clientId;
    private String clientName;
    private List<String> clientNumbers;
    private List<ClientDeliveryAddressResponseDto> deliveryAddresses;
}
