package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.entity.Client;
import com.nhnacademy.auth.entity.ClientNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientNumberRepository extends JpaRepository<ClientNumber, Long> {
    List<ClientNumber> findByClient(Client client);
}
