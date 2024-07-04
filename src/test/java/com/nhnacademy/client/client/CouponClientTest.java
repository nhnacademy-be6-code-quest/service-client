package com.nhnacademy.client.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CouponClientTest {

    @Mock
    private CouponClient couponClient;

    @InjectMocks
    private CouponClientTest couponClientTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPayWelcomeCoupon() {
        // Given
        String expectedResponse = "Welcome coupon paid!";
        when(couponClient.payWelcomeCoupon()).thenReturn(ResponseEntity.ok(expectedResponse));

        // When
        ResponseEntity<String> responseEntity = couponClient.payWelcomeCoupon();

        // Then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
    }
}
