package com.nhnacademy.client.config;

import com.nhnacademy.client.filter.HeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final URI clientPath = URI.create("/api/client");
    private final URI clientAddressPath = URI.create("/api/client/address");
    private final URI clientPhonePath = URI.create("/api/client/phone");
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Bean
    @SuppressWarnings("java:S4502") // Be sure to disable csrf
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(req -> req.anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new HeaderFilter(List.of(
                                new HeaderFilter.RouteConfig(clientPath, HttpMethod.GET.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientPath, HttpMethod.DELETE.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientPath, HttpMethod.PUT.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientAddressPath, HttpMethod.GET.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientAddressPath, HttpMethod.POST.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientAddressPath, HttpMethod.DELETE.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientPhonePath, HttpMethod.GET.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientPhonePath, HttpMethod.POST.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientPhonePath, HttpMethod.DELETE.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(clientPhonePath, HttpMethod.GET.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(URI.create("/api/client/rate"), HttpMethod.GET.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(URI.create("/api/client/role"), HttpMethod.GET.name(), Collections.emptyList()),
                                new HeaderFilter.RouteConfig(URI.create("/api/client/name"), HttpMethod.GET.name(), List.of(ADMIN_ROLE)),
                                new HeaderFilter.RouteConfig(URI.create("/api/client/privacy-page"), HttpMethod.GET.name(), List.of(ADMIN_ROLE)),
                                new HeaderFilter.RouteConfig(URI.create("/api/client/coupon-payment"), HttpMethod.GET.name(), List.of(ADMIN_ROLE))
                        )
                ), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
