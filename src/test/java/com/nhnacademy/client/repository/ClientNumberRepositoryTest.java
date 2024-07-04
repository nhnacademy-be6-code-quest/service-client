package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.Client;
import com.nhnacademy.client.entity.ClientGrade;
import com.nhnacademy.client.entity.ClientNumber;
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
class ClientNumberRepositoryTest {

    @Autowired
    private ClientNumberRepository clientNumberRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientGradeRepository clientGradeRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        ClientGrade clientGrade = clientGradeRepository.save(new ClientGrade(1L, "common", 0, 0));

        client = clientRepository.save(Client.builder()
                .clientEmail("test@example.com")
                .clientPassword("password")
                .clientName("John Doe")
                .clientBirth(LocalDate.of(1990, 1, 1))
                .clientCreatedAt(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .isDeleted(false)
                .clientGrade(clientGrade)
                .build());

        clientNumberRepository.save(ClientNumber.builder().client(client).clientPhoneNumber("123-456-7890").build());
        clientNumberRepository.save(ClientNumber.builder().client(client).clientPhoneNumber("098-765-4321").build());
    }

    @Test
    void testFindAllByClient() {
        List<ClientNumber> clientNumbers = clientNumberRepository.findAllByClient(client);

        assertThat(clientNumbers).isNotNull();
        assertThat(clientNumbers).hasSize(2);
        assertThat(clientNumbers).extracting("clientPhoneNumber").containsExactlyInAnyOrder("123-456-7890", "098-765-4321");
    }
}
