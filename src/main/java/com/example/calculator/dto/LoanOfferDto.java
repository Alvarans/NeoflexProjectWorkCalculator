package com.example.calculator.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanOfferDto {
    private UUID statementId;
    private BigDecimal requestedAmount;
    private BigDecimal totalAmount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private Boolean isInsuranceEnabled;
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
