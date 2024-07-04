package com.nhnacademy.client.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientDeliveryAddressId;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    @SuppressWarnings("java:S1700") // Be sure variable name
    private String clientDeliveryAddress;
    private String clientDeliveryAddressDetail;
    private String clientDeliveryAddressNickname;
    private int clientDeliveryZipCode;
}
