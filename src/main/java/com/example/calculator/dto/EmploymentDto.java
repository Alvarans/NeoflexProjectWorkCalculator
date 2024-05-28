package com.example.calculator.dto;

import com.example.calculator.enums.EmploymentStatusEnum;
import com.example.calculator.enums.PositionsEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    private Enum<EmploymentStatusEnum> employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Enum<PositionsEnum> position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
