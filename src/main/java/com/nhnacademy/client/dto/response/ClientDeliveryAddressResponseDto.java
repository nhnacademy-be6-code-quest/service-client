package com.nhnacademy.client.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDeliveryAddressResponseDto {
    private Long clientDeliveryAddressId;
    private String clientDeliveryAddress;
    private String clientDeliveryAddressDetail;
    private String clientDeliveryAddressNickname;
    private int clientDeliveryZipCode;
}
