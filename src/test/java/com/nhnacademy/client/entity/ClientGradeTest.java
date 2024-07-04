package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientGradeTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testClientGradeEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20);

        // When
        ClientGrade savedClientGrade = entityManager.persistAndFlush(clientGrade);

        // Then
        assertThat(savedClientGrade.getClientGradeId()).isNotNull();
        assertThat(savedClientGrade.getClientGradeName()).isEqualTo("VIP");
        assertThat(savedClientGrade.getClientPolicyBoundry()).isEqualTo(5000L);
        assertThat(savedClientGrade.getRate()).isEqualTo(20);
    }

    @Test
    void testUpdateClientGradeEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20);
        ClientGrade savedClientGrade = entityManager.persistAndFlush(clientGrade);

        // When
        savedClientGrade.setClientGradeName("Premium");
        savedClientGrade.setClientPolicyBoundry(10000L);
        savedClientGrade.setRate(30);
        ClientGrade updatedClientGrade = entityManager.persistAndFlush(savedClientGrade);

        // Then
        assertThat(updatedClientGrade.getClientGradeId()).isEqualTo(savedClientGrade.getClientGradeId());
        assertThat(updatedClientGrade.getClientGradeName()).isEqualTo("Premium");
        assertThat(updatedClientGrade.getClientPolicyBoundry()).isEqualTo(10000L);
        assertThat(updatedClientGrade.getRate()).isEqualTo(30);
    }

    @Test
    void testDeleteClientGradeEntity() {
        // Given
        ClientGrade clientGrade = new ClientGrade(null, "VIP", 5000L, 20);
        ClientGrade savedClientGrade = entityManager.persistAndFlush(clientGrade);

        // When
        entityManager.remove(savedClientGrade);
        entityManager.flush();
        ClientGrade foundClientGrade = entityManager.find(ClientGrade.class, savedClientGrade.getClientGradeId());

        // Then
        assertThat(foundClientGrade).isNull();
    }

    @Test
    void testClientGradeConstructorAndGetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);

        // Then
        assertThat(clientGrade.getClientGradeId()).isEqualTo(1L);
        assertThat(clientGrade.getClientGradeName()).isEqualTo("Gold");
        assertThat(clientGrade.getClientPolicyBoundry()).isEqualTo(3000L);
        assertThat(clientGrade.getRate()).isEqualTo(15);
    }

    @Test
    void testClientGradeSetters() {
        // Given
        ClientGrade clientGrade = new ClientGrade();
        clientGrade.setClientGradeId(2L);
        clientGrade.setClientGradeName("Silver");
        clientGrade.setClientPolicyBoundry(2000L);
        clientGrade.setRate(10);

        // Then
        assertThat(clientGrade.getClientGradeId()).isEqualTo(2L);
        assertThat(clientGrade.getClientGradeName()).isEqualTo("Silver");
        assertThat(clientGrade.getClientPolicyBoundry()).isEqualTo(2000L);
        assertThat(clientGrade.getRate()).isEqualTo(10);
    }

    @Test
    void testClientGradeEqualsAndHashCode() {
        // Given
        ClientGrade clientGrade1 = new ClientGrade(1L, "Gold", 3000L, 15);
        ClientGrade clientGrade2 = new ClientGrade(1L, "Gold", 3000L, 15);
        ClientGrade clientGrade3 = new ClientGrade(2L, "Silver", 2000L, 10);

        // Then
        assertThat(clientGrade1).isEqualTo(clientGrade2);
        assertThat(clientGrade1).isNotEqualTo(clientGrade3);
        assertThat(clientGrade1.hashCode()).hasSameHashCodeAs(clientGrade2.hashCode());
        assertThat(clientGrade1.hashCode()).isNotEqualTo(clientGrade3.hashCode());
    }

    @Test
    void testClientGradeToString() {
        // Given
        ClientGrade clientGrade = new ClientGrade(1L, "Gold", 3000L, 15);

        // When
        String clientGradeString = clientGrade.toString();

        // Then
        assertThat(clientGradeString).isEqualTo("ClientGrade(clientGradeId=1, clientGradeName=Gold, clientPolicyBoundry=3000, rate=15)");
    }
}