package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
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
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientGradeRepository clientGradeRepository;

    @BeforeEach
    void setUp() {
        ClientGrade commonGrade = clientGradeRepository.save(new ClientGrade(null, "common", 0, 0));
        Client client1 = new Client(null, commonGrade, "client1@example.com", "password1", "Client One", LocalDate.of(1990, 1, 1), LocalDateTime.now(), LocalDateTime.now().minusDays(91), false, null);
        Client client2 = new Client(null, commonGrade, "client2@example.com", "password2", "Client Two", LocalDate.of(1990, 2, 1), LocalDateTime.now(), LocalDateTime.now().minusDays(30), false, null);
        Client client3 = new Client(null, commonGrade, "client3@example.com", "password3", "Client Three", LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1), LocalDateTime.now(), LocalDateTime.now(), false, null);
        clientRepository.save(client1);
        clientRepository.save(client2);
        clientRepository.save(client3);
    }

    @Test
    void testFindByClientEmail() {
        Client client = clientRepository.findByClientEmail("client1@example.com");
        assertThat(client).isNotNull();
        assertThat(client.getClientEmail()).isEqualTo("client1@example.com");
    }

    @Test
    void testFindClientsWithBirthInCurrentMonth() {
        List<Long> clients = clientRepository.findClientsWithBirthInCurrentMonth();
        assertThat(clients).hasSize(1);
        assertThat(clients.get(0)).isNotNull();

        Client client = clientRepository.findById(clients.get(0)).orElse(null);
        assertThat(client).isNotNull();
        assertThat(client.getClientEmail()).isEqualTo("client3@example.com");
    }
}
