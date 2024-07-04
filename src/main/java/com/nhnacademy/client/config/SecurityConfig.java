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
                .addFilterBefore(new HeaderFilter(clientPath, HttpMethod.GET.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientPath, HttpMethod.DELETE.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientPath, HttpMethod.PUT.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientAddressPath, HttpMethod.GET.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientAddressPath, HttpMethod.POST.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientAddressPath, HttpMethod.DELETE.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientPhonePath, HttpMethod.GET.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientPhonePath, HttpMethod.POST.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientPhonePath, HttpMethod.DELETE.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(clientPhonePath, HttpMethod.GET.name()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter(URI.create("/api/client/coupon-payment"), HttpMethod.GET.name(), ADMIN_ROLE), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
