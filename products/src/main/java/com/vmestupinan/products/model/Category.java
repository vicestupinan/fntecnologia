package com.vmestupinan.products.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    ELECTRONICS,
    FASHION,
    HOME,
    BEAUTY,
    SPORTS;

    @JsonCreator
    public static Category fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Category.valueOf(value.toUpperCase());
    }
}
