package com.example.calculator.enums;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum EmploymentStatusEnum {
    @JsonProperty BUSINESSOWNER,
    @JsonProperty EMPLOYEE,
    @JsonProperty UNEMPLOYED,
    @JsonProperty SELF_EMPLOYEE;

    EmploymentStatusEnum() {
    }
}
