package com.example.calculator.controller;

import com.example.calculator.dto.CreditDto;
import com.example.calculator.dto.LoanOfferDto;
import com.example.calculator.dto.LoanStatementRequestDto;
import com.example.calculator.dto.ScoringDataDto;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.service.ScoringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculator")
@Validated
public class CalculatorController {
    //Service for api logic
    private final CalculatorService calculatorService;
    //Service for prescoring and scoring data
    private final ScoringService scoringService;

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
    CreditDto creditCalculation(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        try {
            BigDecimal scoreRate = scoringService.score(scoringDataDto);
            return calculatorService.createCredit(scoringDataDto, scoreRate);
        } catch (IllegalArgumentException iae) {
            return new CreditDto();
        }
    }

    /**
     * Method, who handle ArgumentNotValidException exception
     *
     * @param ex - body of exception
     * @return map of errors, contains naming of field, failed validation, and default message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
