package com.example.calculator.service;

import com.example.calculator.dto.LoanOfferDto;
import com.example.calculator.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private final ScoringService scoringService;

    /**
     * Method generate all kind of credit offers
     * @param requestDto - request with information about client
     * @return List of offers
     */
    public List<LoanOfferDto> generateOffers(LoanStatementRequestDto requestDto) {
        return List.of(createOffer(requestDto, false, false),
                createOffer(requestDto, false, true),
                createOffer(requestDto, true, false),
                createOffer(requestDto, true, true));
    }

    /**
     * Method creating credit offer
     * @param requestDto - dto contained information about client
     * @param isInsuranceEnable - true, if client takes insurance
     * @param isSalaryClient - true, if client have a salary client status
     * @return dto with credit offer
     */
    private LoanOfferDto createOffer(LoanStatementRequestDto requestDto,
                                     boolean isInsuranceEnable,
                                     boolean isSalaryClient) {
        //Считаем предлагаемую сумму, учитывая взятие страховки и статус "зарплатного клиента"
        BigDecimal totalAmount = scoringService.calculateTotalAmount(requestDto.getAmount(), isInsuranceEnable, isSalaryClient);
        //Считаем ставку банка, учитывая взятие страховки и статус "зарплатного клиента"
        BigDecimal rate = scoringService.calculateRate(isInsuranceEnable, isSalaryClient);
        //Считаем ежемесячный платёж с учётом суммы, ставки банка и срока кредитования
        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(totalAmount, rate, requestDto.getTerm());
        //Возвращаем сформированное предложение по кредиту
        return new LoanOfferDto(
                requestDto.getAmount(),
                totalAmount,
                requestDto.getTerm(),
                monthlyPayment,
                rate,
                isInsuranceEnable,
                isSalaryClient);
    }
}
