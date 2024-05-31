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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculator")
@Validated
public class CalculatorController {
    CalculatorService calculatorService;
    ScoringService scoringService;
    @PostMapping("/offers")
    List<LoanOfferDto> creditOffers(@Validated @RequestBody LoanStatementRequestDto loanStatementRequestDto){
        if (scoringService.prescore(loanStatementRequestDto)){
            return calculatorService.generateOffers(loanStatementRequestDto);
        } else {
            return List.of();
        }
    }

    /*@PostMapping("/calc")
    CreditDto creditCalculation(@Validated @RequestBody ScoringDataDto scoringDataDto){

    }*/

}
