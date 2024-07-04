package com.nhnacademy.client.repository;

import com.nhnacademy.client.entity.ClientGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("devH2")
class ClientGradeRepositoryTest {

    @Autowired
    private ClientGradeRepository clientGradeRepository;

    @BeforeEach
    void setUp() {
        clientGradeRepository.save(new ClientGrade(1L, "common", 0, 0));
        clientGradeRepository.save(new ClientGrade(2L, "VIP", 10, 100));
    }

    @Test
    void testFindByClientGradeName_Common() {
        ClientGrade clientGrade = clientGradeRepository.findByClientGradeName("common");

        assertThat(clientGrade).isNotNull();
        assertThat(clientGrade.getClientGradeName()).isEqualTo("common");
        assertThat(clientGrade.getClientPolicyBoundry()).isEqualTo(0);
        assertThat(clientGrade.getRate()).isEqualTo(0);
    }

    @Test
    void testFindByClientGradeName_VIP() {
        ClientGrade clientGrade = clientGradeRepository.findByClientGradeName("VIP");

        assertThat(clientGrade).isNotNull();
        assertThat(clientGrade.getClientGradeName()).isEqualTo("VIP");
        assertThat(clientGrade.getClientPolicyBoundry()).isEqualTo(10);
        assertThat(clientGrade.getRate()).isEqualTo(100);
    }

    @Test
    void testFindByClientGradeName_NotFound() {
        ClientGrade clientGrade = clientGradeRepository.findByClientGradeName("nonexistent");

        assertThat(clientGrade).isNull();
    }
}
