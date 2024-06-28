package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientRole;
import com.nhnacademy.client.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRoleRepository extends JpaRepository<ClientRole, Long> {
    @Query("SELECT cr.role FROM ClientRole cr WHERE cr.client = :client")
    List<Role> findRolesByClient(Client client);
}
