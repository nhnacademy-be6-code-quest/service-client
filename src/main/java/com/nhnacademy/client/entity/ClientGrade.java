package com.nhnacademy.client.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientGradeId;
    private String clientGradeName;
    private long clientPolicyBoundry;
    private Long pointPolicyId;
}
