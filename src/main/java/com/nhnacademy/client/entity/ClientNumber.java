package com.nhnacademy.client.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientNumberId;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    private String clientPhoneNumber;
}
