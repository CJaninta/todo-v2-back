package com.macky.todo.v2.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TodoStep {
    OPEN,
    IN_PROGRESS,
    FAILED,
    COMPLETED;

    @JsonCreator
    public static TodoStep getValue(String value) {
        String strVal = value.toUpperCase();
        switch (strVal) {
            case "OPEN":
                return OPEN;
            case "COMPLETED":
                return COMPLETED;
            case "IN_PROGRESS":
                return IN_PROGRESS;
            case "FAILED":
                return FAILED;
            default:
                throw new IllegalArgumentException("Unsupported value");
        }

    }
}
