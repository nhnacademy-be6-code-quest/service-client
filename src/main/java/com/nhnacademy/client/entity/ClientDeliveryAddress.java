package com.nhnacademy.client.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ClientDeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientDeliveryAddressId;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    private String clientDeliveryAddress;
    private String clientDeliveryAddressDetail;
    private String clientDeliveryAddressNickname;
    private int clientDeliveryZipCode;
}
