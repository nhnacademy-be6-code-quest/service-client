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
public class ClientRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientRoleId;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
}
