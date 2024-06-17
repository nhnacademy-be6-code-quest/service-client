package com.nhnacademy.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClientGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientGradeId;
    private String clientGradeName;
    private long clientPolicyBoundary;
    private Integer rate;
}
