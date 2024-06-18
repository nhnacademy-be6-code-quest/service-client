package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.ClientGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientGradeRepository extends JpaRepository<ClientGrade, Long> {
    ClientGrade findByClientGradeName(String clientGradeName);
}
