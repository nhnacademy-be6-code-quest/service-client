package com.nhnacademy.auth.filter;

import com.nhnacademy.auth.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class ClientAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenId = CookieUtils.getCookieValue(request, "Token");

        if (Objects.nonNull(tokenId)) {

        }
        filterChain.doFilter(request, response);
    }
}
