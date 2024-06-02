package com.example.calculator.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MaritalStatusEnum {
    @JsonProperty SINGLE,
    @JsonProperty MARRIED,
    @JsonProperty WIDOWED,
    @JsonProperty DIVORCED,
    @JsonProperty SEPARATED;

    MaritalStatusEnum() {
    }
}
