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

    /**
     * Method for creating credit
     *
     * @param scoringDataDto - information about client and requested credit
     * @param scoredRate     - additional rate, calculated in scoring
     * @return credit with monthly payment schedule
     */
    public CreditDto createCredit(ScoringDataDto scoringDataDto, BigDecimal scoredRate) {
        //Считаем общую сумму по кредиту с учётом взятия страховки и статуса "зарплатного клиента"
        BigDecimal totalAmount = scoringService.calculateTotalAmount(scoringDataDto.getAmount(),
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient());
        //Считаем банковскую ставку с учётом взятия страховки и статуса "зарплатного клиента"
        BigDecimal rate = scoringService.calculateRate(scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient());
        //Добавляем к ставке добавочную ставку, расчитанную в скоринге
        rate = rate.add(scoredRate);
        //Считаем ежемесячный платёж клиента по аннуитентному типу платежей
        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(totalAmount, rate, scoringDataDto.getTerm());
        //Считаем полную стоимость кредита
        BigDecimal psk = scoringService.calculatePSK(monthlyPayment, scoringDataDto.getTerm());
        //Формируем кредитное предложение
        return new CreditDto(totalAmount,
                scoringDataDto.getTerm(),
                monthlyPayment,
                rate,
                psk,
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient(),
                createPaymentSchedule(1, totalAmount, rate, monthlyPayment)); //Формируем и добавляем график ежемесячных платежей
    }

    /**
     * Method for creating monthly payment schedule
     *
     * @param number         - number of payment in schedule
     * @param amount         - remaining payment amount
     * @param rate           - credit rate
     * @param monthlyPayment - monthly payment
     * @return payment schedule
     */
    private List<PaymentScheduleElementDto> createPaymentSchedule(Integer number, BigDecimal amount,
                                                                  BigDecimal rate,
                                                                  BigDecimal monthlyPayment) {
        //Создаём объект платежа
        PaymentScheduleElementDto paymentScheduleElementDto = new PaymentScheduleElementDto();
        //Устанавливаем значение номера и суммы остатка
        paymentScheduleElementDto.setNumber(number);
        paymentScheduleElementDto.setTotalPayment(amount);
        //Считаем ежемесячную ставку
        BigDecimal monthlyRate = rate.divide(new BigDecimal(12), 4, RoundingMode.HALF_EVEN).
                divide(new BigDecimal(100), 4, RoundingMode.HALF_EVEN);
        //Считаем платёж к банку
        BigDecimal interestPayment = amount.multiply(monthlyRate);
        //Считаем платёж по долгу
        BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
        //Считаем оставшийся долг
        BigDecimal remainingDebt = amount.subtract(debtPayment);
        //Заносим данные в наш объект
        paymentScheduleElementDto.setInterestPayment(interestPayment.setScale(2, RoundingMode.HALF_EVEN));
        paymentScheduleElementDto.setDebtPayment(debtPayment.setScale(2, RoundingMode.HALF_EVEN));
        paymentScheduleElementDto.setRemainingDebt(remainingDebt.setScale(2, RoundingMode.HALF_EVEN));
        //Создаём лист платежей для записи в него
        List<PaymentScheduleElementDto> paymentSchedule;
        //Пока у нас есть долг по кредиту - уходим в рекурсию с остатком по платежу. Иначе - формируем список с последним платежом
        if (remainingDebt.compareTo(BigDecimal.ZERO) != 0) {
            paymentSchedule = createPaymentSchedule(++number, remainingDebt, rate, monthlyPayment);
            paymentSchedule.add(--number, paymentScheduleElementDto);
        } else {
            return List.of(paymentScheduleElementDto);
        }
        //Возвращаем сформированный график платежей
        return paymentSchedule;
    }
}
