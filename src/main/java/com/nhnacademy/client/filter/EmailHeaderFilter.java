package com.nhnacademy.client.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class EmailHeaderFilter extends OncePerRequestFilter {
    private final String requiredPath;
    private final String requiredMethod;
    private final AntPathMatcher pathMatcher;

    public EmailHeaderFilter(String requiredPath, String requiredMethod) {
        this.requiredPath = requiredPath;
        this.requiredMethod = requiredMethod;
        this.pathMatcher = new AntPathMatcher();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("header filter start");
        if (pathMatcher.match(requiredPath, request.getRequestURI()) && request.getMethod().equalsIgnoreCase(requiredMethod)) {
            String email = request.getHeader("email");

            if (email == null || email.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email header is missing or invalid");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}