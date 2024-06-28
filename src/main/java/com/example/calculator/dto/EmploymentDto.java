package com.example.calculator.dto;

import com.example.calculator.enums.EmploymentStatusEnum;
import com.example.calculator.enums.PositionsEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    @Schema(
            description = "worker status",
            name = "employmentStatus",
            type = "EmploymentStatusEnum",
            example = "BUSINESSOWNER")
    private EmploymentStatusEnum employmentStatus;
    @Schema(
            description = "number of client INN",
            name = "employerINN",
            type = "String",
            example = "1234567890")
    private String employerINN;
    @Schema(
            description = "client salary",
            name = "salary",
            type = "BigDecimal",
            example = "30000.00")
    private BigDecimal salary;
    @Schema(
            description = "client work position",
            name = "position",
            type = "PositionsEnum",
            example = "REGULARSTAFF")
    private PositionsEnum position;
    @Schema(
            description = "total client work experience",
            name = "workExperienceTotal",
            type = "Integer",
            example = "20")
    private Integer workExperienceTotal;
    @Schema(
            description = "client work experience on current work",
            name = "workExperienceCurrent",
            type = "Integer",
            example = "6")
    private Integer workExperienceCurrent;
}
