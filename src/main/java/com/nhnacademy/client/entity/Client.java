package com.nhnacademy.client.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    @ManyToOne
    @JoinColumn(name = "clientGradeId")
    private ClientGrade clientGrade;
    private String clientEmail;
    private String clientPassword;
    private String clientName;
    private LocalDate clientBirth;
    private LocalDateTime clientCreatedAt;
    private LocalDateTime lastLoginDate;
    private boolean isDeleted;
    @Column(name = "clientRoleId")
    private Role role;
}
