package com.nhnacademy.client.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
