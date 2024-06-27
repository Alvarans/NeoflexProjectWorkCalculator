package com.example.calculator.controller;

import com.example.calculator.dto.CreditDto;
import com.example.calculator.dto.LoanOfferDto;
import com.example.calculator.dto.LoanStatementRequestDto;
import com.example.calculator.dto.ScoringDataDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface ApiController {

    @Operation(summary = "Создание кредитных предложений",
            description = "Позволяет создать кредитные предложения с учетом страховки и статуса зарплатного клиента")
    List<LoanOfferDto> creditOffers(@Validated
            @Parameter(description = "Содержит базовую информацию по клиенту и кредиту") LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "Подсчет кредита",
            description = "Позволяет подсчитать предложение по кредиту для клиента, включая график ежемесячных платежей")
    ResponseEntity<CreditDto> creditCalculation(@Validated
            @Parameter(description = "Содержит полную информацию о клиенте и запрашиваемую сумму") ScoringDataDto scoringDataDto);
}
