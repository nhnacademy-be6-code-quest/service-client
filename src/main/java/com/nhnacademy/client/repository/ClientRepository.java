package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientEmail(String clientEmail);

    @Query(value = "SELECT c.clientId FROM Client c WHERE MONTH(c.clientBirth) = MONTH(CURRENT_DATE)")
    List<Long> findClientsWithBirthInCurrentMonth();

    @Query(value = "SELECT c FROM Client c",
            countQuery = "SELECT COUNT(c) FROM Client c")
    Page<Client> findAllLazy(Pageable pageable);
}
