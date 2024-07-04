package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientDeliveryAddress;
import com.nhnacademy.client.entity.ClientGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("devH2")
class ClientDeliveryAddressRepositoryTest {

    @Autowired
    private ClientDeliveryAddressRepository clientDeliveryAddressRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        ClientGrade clientGrade = new ClientGrade(1L, "common", 0, 0);
        client = new Client(1L, clientGrade, "test1@example.com", "password", "John Doe",
                LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now(), false, null);

        clientRepository.save(client);

        ClientDeliveryAddress address1 = new ClientDeliveryAddress(1L, client, "123 Main St", "Apt 4B", "Home", 12345);
        ClientDeliveryAddress address2 = new ClientDeliveryAddress(2L, client, "456 Elm St", "Suite 5C", "Work", 67890);

        clientDeliveryAddressRepository.save(address1);
        clientDeliveryAddressRepository.save(address2);
    }

    @Test
    void testFindAllByClient() {
        List<ClientDeliveryAddress> addresses = clientDeliveryAddressRepository.findAllByClient(client);

        assertThat(addresses).isNotNull();
        assertThat(addresses).hasSize(2);

        ClientDeliveryAddress address1 = addresses.get(0);
        assertThat(address1.getClientDeliveryAddress()).isEqualTo("123 Main St");
        assertThat(address1.getClientDeliveryAddressDetail()).isEqualTo("Apt 4B");
        assertThat(address1.getClientDeliveryAddressNickname()).isEqualTo("Home");
        assertThat(address1.getClientDeliveryZipCode()).isEqualTo(12345);

        ClientDeliveryAddress address2 = addresses.get(1);
        assertThat(address2.getClientDeliveryAddress()).isEqualTo("456 Elm St");
        assertThat(address2.getClientDeliveryAddressDetail()).isEqualTo("Suite 5C");
        assertThat(address2.getClientDeliveryAddressNickname()).isEqualTo("Work");
        assertThat(address2.getClientDeliveryZipCode()).isEqualTo(67890);
    }
}
