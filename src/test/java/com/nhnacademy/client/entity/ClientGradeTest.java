package com.nhnacademy.client.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientGradeTest {

    @Test
    void testNoArgsConstructor() {
        ClientGrade clientGrade = new ClientGrade();
        assertNotNull(clientGrade);
    }

    @Test
    void testAllArgsConstructor() {
        ClientGrade clientGrade = new ClientGrade(
                1L,
                "Gold",
                1000L,
                2L
        );

        assertAll(
                () -> assertEquals(1L, clientGrade.getClientGradeId()),
                () -> assertEquals("Gold", clientGrade.getClientGradeName()),
                () -> assertEquals(1000L, clientGrade.getClientPolicyBoundry()),
                () -> assertEquals(2L, clientGrade.getPointPolicyId())
        );
    }

    @Test
    void testBuilder() {
        ClientGrade clientGrade = ClientGrade.builder()
                .clientGradeId(1L)
                .clientGradeName("Gold")
                .clientPolicyBoundry(1000L)
                .pointPolicyId(2L)
                .build();

        assertAll(
                () -> assertEquals(1L, clientGrade.getClientGradeId()),
                () -> assertEquals("Gold", clientGrade.getClientGradeName()),
                () -> assertEquals(1000L, clientGrade.getClientPolicyBoundry()),
                () -> assertEquals(2L, clientGrade.getPointPolicyId())
        );
    }

    @Test
    void testSettersAndGetters() {
        ClientGrade clientGrade = new ClientGrade();

        clientGrade.setClientGradeId(1L);
        clientGrade.setClientGradeName("Gold");
        clientGrade.setClientPolicyBoundry(1000L);
        clientGrade.setPointPolicyId(2L);

        assertAll(
                () -> assertEquals(1L, clientGrade.getClientGradeId()),
                () -> assertEquals("Gold", clientGrade.getClientGradeName()),
                () -> assertEquals(1000L, clientGrade.getClientPolicyBoundry()),
                () -> assertEquals(2L, clientGrade.getPointPolicyId())
        );
    }
}
