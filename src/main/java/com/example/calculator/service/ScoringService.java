package com.example.calculator.service;

import com.example.calculator.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Service
public class ScoringService {
    final BigDecimal baseRate = new BigDecimal("16.00");

    /**
     * Function for prescoring request dto according to certain rules
     *
     * @param requestDto = requested dto. Contains information about client
     * @return true, if prescore success, or false, if one of dto fields is uncorrected
     */
    public boolean prescore(LoanStatementRequestDto requestDto) {
        //Проверка на то, состоит ли имя из латинских символов или нет
        if (!(isLatina(requestDto.getFirstName()))) {
            System.out.println("You must use letters in first name");
            return false;
        }
        //Проверка на то, состоит ли фамилия из латинских символов или нет
        if (!(isLatina(requestDto.getLastName()))) {
            System.out.println("You must use letters in last name");
            return false;
        }
        String middleName = requestDto.getMiddleName();
        //Проверка отчества на длину и содержание латинских букв при его наличии
        if (middleName != null) {
            if ((middleName.length() < 2) || (middleName.length() > 30)) {
                System.out.println("Your middlename is uncorrect");
                return false;
            }
            if (!(isLatina(requestDto.getMiddleName()))) {
                System.out.println("You must use letters in middle name");
                return false;
            }
        }
        //Проверка клиента на совершеннолетие
        if (Period.between(requestDto.getBirthdate(), LocalDate.now())
                .getYears() < 18) {
            System.out.println("Your age must be more then 18");
            return false;
        }
        System.out.println("Prescore success");
        return true;
    }

    public BigDecimal calculateTotalAmount(BigDecimal amount,
                                           boolean isInsuranceEnabled,
                                           boolean isSalaryClient){

        return isInsuranceEnabled&(!isSalaryClient) ? amount.add(new BigDecimal("100000")):amount;
    }

    public BigDecimal calculateRate(boolean isInsuranceEnabled, boolean isSalaryClient){
        BigDecimal totalRate = baseRate;
        if (isInsuranceEnabled) {
            totalRate = totalRate.subtract(new BigDecimal("3.00"));
            if (isSalaryClient) {
                totalRate = totalRate.add(new BigDecimal("2.00"));
            }
        }
        return totalRate;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term){
        BigDecimal monthlyPayment = amount;
        BigDecimal numerator = rate.multiply(rate.add(new BigDecimal(1).pow(term)));
        BigDecimal denominator = rate.add(new BigDecimal(1)).pow(term).subtract(new BigDecimal(1));
        monthlyPayment = monthlyPayment.multiply(numerator.divide(denominator));
        return monthlyPayment.setScale(2, RoundingMode.HALF_EVEN);
    }
    /**
     * Function for checking string on letters
     * @param word - checking word
     * @return true, if word contains only letters, or false, if not
     */
    private boolean isLatina(String word) {
        return word.matches("\\\\w+");
    }
}
