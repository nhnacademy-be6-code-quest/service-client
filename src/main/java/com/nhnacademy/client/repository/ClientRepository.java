package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientEmail(String clientEmail);

    @Modifying
    @Transactional
    @Query(value = "UPDATE client SET is_deleted = true, client_delete_date = CURRENT_TIMESTAMP WHERE client_id IN (" +
            "SELECT client_id FROM (" +
            "SELECT client_id FROM client WHERE DATEDIFF(CURRENT_TIMESTAMP(), last_login_date) > 90 LIMIT 10000" +
            ") AS subquery)", nativeQuery = true)
    int updateClientIsDeletedIfInactive();
}
