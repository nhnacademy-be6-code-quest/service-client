package com.nhnacademy.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Principal implements UserDetails, OAuth2User {
    private Client client;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(client.getRole());
    }

    @Override
    public String getPassword() {
        return client.getClientPassword();
    }

    @Override
    public String getUsername() {
        return client.getClientEmail();
    }

    @Override
    public String getName() {
        return client.getClientName();
    }
}
