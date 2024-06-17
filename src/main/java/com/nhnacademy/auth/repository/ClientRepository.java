package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientEmail(String clientEmail);
}
