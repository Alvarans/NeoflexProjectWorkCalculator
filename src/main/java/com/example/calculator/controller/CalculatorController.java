package com.example.calculator.controller;

import com.example.calculator.dto.CreditDto;
import com.example.calculator.dto.LoanOfferDto;
import com.example.calculator.dto.LoanStatementRequestDto;
import com.example.calculator.dto.ScoringDataDto;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.service.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculator")
@Validated
public class CalculatorController {
    //Service for api logic
    CalculatorService calculatorService;
    //Service for prescoring and scoring data
    ScoringService scoringService;

    /**
     * End-point for creation credit offers. Information about client must pass prescore
     *
     * @param loanStatementRequestDto - information about client
     * @return list of credit offers. If information about client can't pass prescore - return empty list
     */
    @PostMapping("/offers")
    List<LoanOfferDto> creditOffers(@Validated @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        if (scoringService.prescore(loanStatementRequestDto)) {
            return calculatorService.generateOffers(loanStatementRequestDto);
        } else {
            return List.of();
        }
    }

    /**
     * End-point for creation credit for client. Request information must pass validation and score
     *
     * @param scoringDataDto - information about client and requested credit
     * @return filled credit offer with monthly payment schedule. If request information can't pass score - return empty credit offer
     */
    @PostMapping("/calc")
    CreditDto creditCalculation(@Validated @RequestBody ScoringDataDto scoringDataDto) {
        try {
            BigDecimal scoreRate = scoringService.score(scoringDataDto);
            return calculatorService.createCredit(scoringDataDto, scoreRate);
        } catch (IllegalArgumentException iae) {
            return new CreditDto();
        }
    }

}
