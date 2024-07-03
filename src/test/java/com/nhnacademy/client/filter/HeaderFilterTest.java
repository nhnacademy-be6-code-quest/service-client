package com.nhnacademy.client.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HeaderFilterTest {

    private HeaderFilter headerFilter;
    private FilterChain filterChain;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filterChain = mock(FilterChain.class);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void testDoFilterInternal_ValidIdHeader() throws ServletException, IOException {
        // Given
        headerFilter = new HeaderFilter("/api/client", "GET");
        request.setRequestURI("/api/client");
        request.setMethod("GET");
        request.addHeader("X-User-Id", "1");

        // When
        headerFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidIdHeader() throws ServletException, IOException {
        // Given
        headerFilter = new HeaderFilter("/api/client", "GET");
        request.setRequestURI("/api/client");
        request.setMethod("GET");
        request.addHeader("X-User-Id", "invalid");

        // When
        headerFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, never()).doFilter(request, response);
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    }

    @Test
    void testDoFilterInternal_MissingIdHeader() throws ServletException, IOException {
        // Given
        headerFilter = new HeaderFilter("/api/client", "GET");
        request.setRequestURI("/api/client");
        request.setMethod("GET");

        // When
        headerFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, never()).doFilter(request, response);
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    }

    @Test
    void testDoFilterInternal_ValidRoleHeader() throws ServletException, IOException {
        // Given
        headerFilter = new HeaderFilter("/api/client/coupon-payment", "GET", "ROLE_ADMIN");
        request.setRequestURI("/api/client/coupon-payment");
        request.setMethod("GET");
        request.addHeader("X-User-Id", "1");
        request.addHeader("X-User-Role", "ROLE_ADMIN");

        // When
        headerFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidRoleHeader() throws ServletException, IOException {
        // Given
        headerFilter = new HeaderFilter("/api/client/coupon-payment", "GET", "ROLE_ADMIN");
        request.setRequestURI("/api/client/coupon-payment");
        request.setMethod("GET");
        request.addHeader("X-User-Id", "1");
        request.addHeader("X-User-Role", "ROLE_USER");

        // When
        headerFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, never()).doFilter(request, response);
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    }

    @Test
    void testDoFilterInternal_MissingRoleHeader() throws ServletException, IOException {
        // Given
        headerFilter = new HeaderFilter("/api/client/coupon-payment", "GET", "ROLE_ADMIN");
        request.setRequestURI("/api/client/coupon-payment");
        request.setMethod("GET");
        request.addHeader("X-User-Id", "1");

        // When
        headerFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, never()).doFilter(request, response);
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    }
}
