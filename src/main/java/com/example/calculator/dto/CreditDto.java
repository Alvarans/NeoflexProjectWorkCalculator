package com.example.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDto {
    @Schema(
            description = "amount of credit",
            name = "amount",
            type = "BigDecimal",
            example = "300000")
    private BigDecimal amount;
    @Schema(
            description = "term of credit in months",
            name = "term",
            type = "Integer",
            example = "24")
    private Integer term;
    @Schema(
            description = "amount of monthly payment",
            name = "monthlyPayment",
            type = "BigDecimal",
            example = "3000.00")
    private BigDecimal monthlyPayment;
    @Schema(
            description = "rate for credit",
            name = "rate",
            type = "BigDecimal",
            example = "16.00")
    private BigDecimal rate;
    @Schema(
            description = "full amount of payment",
            name = "psk",
            type = "BigDecimal",
            example = "310000")
    private BigDecimal psk;
    @Schema(
            description = "client takes insurance or not",
            name = "isInsuranceEnabled",
            type = "Boolean",
            example = "true")
    private Boolean isInsuranceEnabled;
    @Schema(
            description = "client has salary in this bank or not",
            name = "isSalaryClient",
            type = "Boolean",
            example = "false")
    private Boolean isSalaryClient;
    @Schema(
            description = "client payment schedule",
            name = "paymentSchedule",
            type = "List<PaymentScheduleElementDto>",
            example = """
                     {
                           "number": 1,
                           "date": "2024-06-25",
                           "totalPayment": 8197.942948514074,
                           "interestPayment": 3500,
                           "debtPayment": 4697.94,
                           "remainingDebt": 295302.06
                         }\
                    """)
    private List<PaymentScheduleElementDto> paymentSchedule;

}
