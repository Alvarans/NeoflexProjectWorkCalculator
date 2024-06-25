package com.example.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanOfferDto {
    @Schema(
            description = "statement id",
            name = "statementId",
            type = "UUID",
            example = "123e4567-e89b-42d3-a456-556642440000")
    private UUID statementId;
    @Schema(
            description = "amount of credit client request from bank",
            name = "requestedAmount",
            type = "BigDecimal",
            example = "300000")
    private BigDecimal requestedAmount;
    @Schema(
            description = "amount bank ready to offer",
            name = "totalAmount",
            type = "BigDecimal",
            example = "300000")
    private BigDecimal totalAmount;
    @Schema(
            description = "term of credit",
            name = "term",
            type = "Integer",
            example = "46")
    private Integer term;
    @Schema(
            description = "Monthly payment for crdit",
            name = "monthlyPayment",
            type = "BigDecimal",
            example = "4000.00")
    private BigDecimal monthlyPayment;
    @Schema(
            description = "credit rate",
            name = "rate",
            type = "BigDecimal",
            example = "16.00")
    private BigDecimal rate;
    @Schema(
            description = "client takes insurance or not",
            name = "isInsuranceEnabled",
            type = "Boolean",
            example = "true")
    private Boolean isInsuranceEnabled;
    @Schema(
            description = "client receives salary in this bank or not",
            name = "isSalaryClient",
            type = "Boolean",
            example = "false")
    private Boolean isSalaryClient;


    public LoanOfferDto(BigDecimal requestedAmount,
                        BigDecimal totalAmount,
                        Integer term,
                        BigDecimal monthlyPayment,
                        BigDecimal rate,
                        Boolean isInsuranceEnabled,
                        Boolean isSalaryClient) {

        this.statementId = UUID.randomUUID();
        this.requestedAmount = requestedAmount;
        this.totalAmount = totalAmount;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
    }

    public LoanOfferDto(){
        this.statementId = UUID.randomUUID();
    }
}
