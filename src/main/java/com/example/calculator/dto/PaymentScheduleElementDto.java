package com.example.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentScheduleElementDto {
    @Schema(
            description = "payment number",
            name = "number",
            type = "Integer",
            example = "1")
    private Integer number;
    @Schema(
            description = "payment date",
            name = "date",
            type = "LocalDate",
            example = "2001-01-01")
    private LocalDate date;
    @Schema(
            description = "amount of total payment client need to pay",
            name = "totalPayment",
            type = "BigDecimal",
            example = "30000.00")
    private BigDecimal totalPayment;
    @Schema(
            description = "amount of interest payment",
            name = "interestPayment",
            type = "BigDecimal",
            example = "3000.00")
    private BigDecimal interestPayment;
    @Schema(
            description = "credit debt payment",
            name = "debtPayment",
            type = "BigDecimal",
            example = "3000.00")
    private BigDecimal debtPayment;
    @Schema(
            description = "remaining credit debt",
            name = "remainingDebt",
            type = "BigDecimal",
            example = "15000.00")
    private BigDecimal remainingDebt;
}
