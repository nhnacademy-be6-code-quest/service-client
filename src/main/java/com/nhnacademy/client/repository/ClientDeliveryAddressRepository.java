package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientDeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientDeliveryAddressRepository extends JpaRepository<ClientDeliveryAddress, Long> {
    List<ClientDeliveryAddress> findAllByClient(Client client);
}
