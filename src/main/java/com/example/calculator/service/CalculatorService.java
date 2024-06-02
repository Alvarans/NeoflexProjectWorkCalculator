package com.example.calculator.service;

import com.example.calculator.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private final ScoringService scoringService;

    /**
     * Method generate all kind of credit offers
     *
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
     *
     * @param requestDto        - dto contained information about client
     * @param isInsuranceEnable - true, if client takes insurance
     * @param isSalaryClient    - true, if client have a salary client status
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

    public CreditDto createCredit(ScoringDataDto scoringDataDto, BigDecimal scoredRate) {
        BigDecimal totalAmount = scoringService.calculateTotalAmount(scoringDataDto.getAmount(),
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient());
        BigDecimal rate = scoringService.calculateRate(scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient());
        rate = rate.add(scoredRate);
        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(totalAmount, rate, scoringDataDto.getTerm());
        BigDecimal psk = scoringService.calculatePSK(monthlyPayment, scoringDataDto.getTerm());
        return new CreditDto(totalAmount,
                scoringDataDto.getTerm(),
                monthlyPayment,
                rate,
                psk,
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient(),
                createPaymentSchedule(1, totalAmount, rate, monthlyPayment));
    }

    private List<PaymentScheduleElementDto> createPaymentSchedule(Integer number, BigDecimal amount,
                                                                  BigDecimal rate,
                                                                  BigDecimal monthlyPayment) {
        PaymentScheduleElementDto paymentScheduleElementDto = new PaymentScheduleElementDto();
        paymentScheduleElementDto.setNumber(number);
        paymentScheduleElementDto.setTotalPayment(amount);
        BigDecimal monthlyRate = rate.divide(new BigDecimal(12)).
                divide(new BigDecimal(100));
        BigDecimal interestPayment = amount.multiply(monthlyRate);
        BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
        BigDecimal remainingDebt = amount.subtract(debtPayment);
        BigDecimal newAmount = amount.subtract(debtPayment);
        paymentScheduleElementDto.setInterestPayment(interestPayment.setScale(2, RoundingMode.HALF_EVEN));
        paymentScheduleElementDto.setDebtPayment(debtPayment.setScale(2, RoundingMode.HALF_EVEN));
        paymentScheduleElementDto.setRemainingDebt(remainingDebt.setScale(2, RoundingMode.HALF_EVEN));
        List<PaymentScheduleElementDto> paymentSchedule;
        if (newAmount.compareTo(BigDecimal.ZERO) != 0) {
            paymentSchedule = createPaymentSchedule(++number, newAmount, rate, monthlyPayment);
            paymentSchedule.add(--number, paymentScheduleElementDto);
        } else {
            return List.of(paymentScheduleElementDto);
        }
        return paymentSchedule;
    }
}
