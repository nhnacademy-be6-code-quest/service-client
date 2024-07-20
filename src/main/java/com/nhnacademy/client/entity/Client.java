package com.nhnacademy.client.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private LocalDateTime clientDeleteDate;
}
