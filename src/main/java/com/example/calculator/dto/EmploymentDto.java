package com.example.calculator.dto;

import com.example.calculator.enums.EmploymentStatusEnum;
import com.example.calculator.enums.PositionsEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    @JsonValue
    private EmploymentStatusEnum employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    @JsonValue
    private PositionsEnum position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
