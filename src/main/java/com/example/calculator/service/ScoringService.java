package com.example.calculator.service;

import com.example.calculator.dto.LoanStatementRequestDto;
import com.example.calculator.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import static com.example.calculator.enums.EmploymentStatusEnum.*;
import static com.example.calculator.enums.GendersEnum.FEMALE;
import static com.example.calculator.enums.GendersEnum.MALE;
import static com.example.calculator.enums.MaritalStatusEnum.*;
import static com.example.calculator.enums.PositionsEnum.*;

@Service
public class ScoringService {
    final BigDecimal baseRate = new BigDecimal("16.00");

    /**
     * Method for prescoring request dto according to certain rules
     *
     * @param requestDto - requested dto. Contains information about client
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

    /**
     * Method for scoring request dto according to certain rules
     *
     * @param scoringDataDto - requested dto. Contains scoring information
     * @return adding rate for credit if all right. Otherwise, it throws Illegal Argument Exception
     */
    public BigDecimal score(ScoringDataDto scoringDataDto) {
        //Дополнительная ставка, формирующаяся на основе скоринга
        BigDecimal rate = new BigDecimal(0);
        //Проверка на превышение запроса на кредит 25 ставок клиента
        if (scoringDataDto.getEmployment().getSalary().multiply(new BigDecimal(25)).compareTo(scoringDataDto.getAmount()) < 0) {
            throw new IllegalArgumentException("You can't take more money then your 25 salaries");
        }
        //Проверка на общий и текущий стаж клиента
        if (scoringDataDto.getEmployment().getWorkExperienceTotal() < 18)
            throw new IllegalArgumentException("You need more total experience");
        if (scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3)
            throw new IllegalArgumentException("You need more experience on your current work");
        //Проверка статуса занятости клиента. Если клиент безработный - отказать в кредите. Иначе - увеличить добавочную ставку
        if (scoringDataDto.getEmployment().getEmploymentStatus().equals(UNEMPLOYED)) {
            throw new IllegalArgumentException("You can't take a credit with status \"unemployed\"");
        } else if (scoringDataDto.getEmployment().getEmploymentStatus().equals(SELF_EMPLOYEE)) {
            rate = rate.add(new BigDecimal(1));
        } else if (scoringDataDto.getEmployment().getEmploymentStatus().equals(BUSINESSOWNER)) {
            rate = rate.add(new BigDecimal(2));
        }
        //Проверка должности клиента. В зависимости от неё уменьшается добавочная ставка по кредиту
        if (scoringDataDto.getEmployment().getPosition().equals(SENIORSTAFF))
            rate = rate.subtract(new BigDecimal(1));
        else if (scoringDataDto.getEmployment().getPosition().equals(MIDDLEMANAGER))
            rate = rate.subtract(new BigDecimal(2));
        else if (scoringDataDto.getEmployment().getPosition().equals(TOPMANAGER))
            rate = rate.subtract(new BigDecimal(3));
        //Проверка семейного статуса клиента. В зависимости от него ставка как увеличивается, так и уменьшается
        if (scoringDataDto.getMaritalStatus().equals(MARRIED))
            rate = rate.subtract(new BigDecimal(3));
        else if (scoringDataDto.getMaritalStatus().equals(WIDOWED))
            rate = rate.subtract(new BigDecimal(1));
        else if (scoringDataDto.getMaritalStatus().equals(DIVORCED))
            rate = rate.add(new BigDecimal(1));
        //Возраст клиента
        int age = Period.between(scoringDataDto.getBirthdate(), LocalDate.now()).getYears();
        //Если возраст клиента не соответствует политике банка, отказать в кредите
        if (age < 20 || age > 65) {
            throw new IllegalArgumentException("Your age must be more then 20 or less then 65");
        }
        //Увеличение и уменьшение ставки на основе идентификации и возраста клиента
        if (scoringDataDto.getGender().equals(FEMALE)) {
            if (age < 32 || age > 60) {
                rate = rate.subtract(new BigDecimal(3));
            }
        } else if (scoringDataDto.getGender().equals(MALE)) {
            if (age < 30 || age > 55)
                rate = rate.subtract(new BigDecimal(3));
        } else {
            rate = rate.add(new BigDecimal(7));
        }
        //Возвращаем сформированную добавочную ставку
        return rate;
    }

    /**
     * Method for calculating amount of money, which bank ready to give a client
     *
     * @param amount             - required amount
     * @param isInsuranceEnabled - include insurance in amount or not
     * @param isSalaryClient     - if client has status "salary client", he doesn't need to pay for insurance
     * @return total amount
     */
    public BigDecimal calculateTotalAmount(BigDecimal amount,
                                           boolean isInsuranceEnabled,
                                           boolean isSalaryClient) {

        return isInsuranceEnabled & (!isSalaryClient) ? amount.add(new BigDecimal("100000")) : amount;
    }

    /**
     * Method for calculating bank rate, using based rate
     *
     * @param isInsuranceEnabled - include insurance in credit or not
     * @param isSalaryClient     - client has status "salary client" or not
     * @return current bank rate
     */
    public BigDecimal calculateRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        BigDecimal totalRate = baseRate;
        if (isInsuranceEnabled) {
            totalRate = totalRate.subtract(new BigDecimal("3.00"));
            if (isSalaryClient) {
                totalRate = totalRate.add(new BigDecimal("2.00"));
            }
        }
        return totalRate;
    }

    /**
     * Method for calculating credit montly payment
     *
     * @param amount - total amount of credit money
     * @param rate   - bank rate
     * @param term   - term of credit
     * @return monthly payment
     */
    public BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term) {
        BigDecimal monthlyPayment = amount;
        BigDecimal numerator = rate.multiply(rate.add(new BigDecimal(1).pow(term)));
        BigDecimal denominator = rate.add(new BigDecimal(1)).pow(term).subtract(new BigDecimal(1));
        monthlyPayment = monthlyPayment.multiply(numerator.divide(denominator));
        return monthlyPayment.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Method for calculating total payment amount
     *
     * @param monthlyPayment - monthly payment for credit
     * @param term           - term of credit
     * @return total payment amount
     */
    public BigDecimal calculatePSK(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(new BigDecimal(term));
    }

    /**
     * Method for checking string on letters
     *
     * @param word - checking word
     * @return true, if word contains only letters, or false, if not
     */
    private boolean isLatina(String word) {
        return word.matches("\\\\w+");
    }
}
