package com.nhnacademy.client.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.nhnacademy.client.filter.HeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final DiscoveryClient discoveryClient;

    private String gatewayIp;
    private int gatewayPort;

    @PostConstruct
    public void init() {
        List<ServiceInstance> instances = discoveryClient.getInstances("gateway");
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance instance = instances.getFirst();
            gatewayIp = instance.getHost();
            gatewayPort = instance.getPort();
        }
        log.info("Gateway IP: " + gatewayIp);
        log.info("Gateway Port: " + gatewayPort);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(req -> req.anyRequest().access((authentication, context) -> {
                        String remoteAddr = context.getRequest().getRemoteAddr();
                        int remotePort = context.getRequest().getRemotePort();
                        boolean granted = remoteAddr.equals(gatewayIp) && gatewayPort == remotePort;

                        if (!granted) {
                            log.warn("Remote address {} not granted", remoteAddr);
                        }
                        return new AuthorizationDecision(granted);
                    })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new HeaderFilter("/api/client", "GET"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client", "DELETE"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client", "PUT"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/address", "GET"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/address", "POST"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/address", "DELETE"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/phone", "GET"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/phone", "POST"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/phone", "DELETE"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/order", "GET"), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new HeaderFilter("/api/client/coupon-payment", "GET", "ROLE_ADMIN"), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
