package com.nhnacademy.auth.entity;

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
public class ClientNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientNumberId;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    private String clientPhoneNumber;
}
