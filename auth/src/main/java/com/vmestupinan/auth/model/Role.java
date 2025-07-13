package com.vmestupinan.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    USER,
    ADMIN;

    @JsonCreator
    public static Role fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Role.valueOf(value.toUpperCase());
    }
}
