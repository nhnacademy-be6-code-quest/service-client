package com.nhnacademy.client.config;

import com.nhnacademy.client.filter.EmailHeaderFilter;
import com.nhnacademy.client.filter.RoleHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(req -> req.anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new EmailHeaderFilter("/api/client", "GET"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new EmailHeaderFilter("/api/client/address", "GET"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new EmailHeaderFilter("/api/client/address", "POST"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new EmailHeaderFilter("/api/client/address", "DELETE"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new EmailHeaderFilter("/api/client/order", "GET"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new RoleHeaderFilter("/api/client/coupon-payment", "GET", "ROLE_ADMIN"), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
