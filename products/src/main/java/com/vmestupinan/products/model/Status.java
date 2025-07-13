package com.vmestupinan.products.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    AVAILABLE,
    UNAVAILABLE;

    @JsonCreator
    public static Status fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Status.valueOf(value.toUpperCase());
    }
}
