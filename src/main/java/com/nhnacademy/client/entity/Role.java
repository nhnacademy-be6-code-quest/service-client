package com.nhnacademy.client.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nhnacademy.client.exception.UnknownRoleException;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, NON_USER;

    private static final String prefix = "ROLE_";

    @JsonCreator
    public static Role jsonCreator(String role) {
        role = role.toUpperCase();
        if (!role.startsWith(prefix)) {
            role = prefix + role;
        }
        for (Role status : Role.values()) {
            if (status.toString().equals(role)) {
                return status;
            }
        }
        throw new UnknownRoleException("Invalid role: " + role);
    }

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
