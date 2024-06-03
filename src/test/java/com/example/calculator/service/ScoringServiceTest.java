package com.example.calculator.service;

import com.example.calculator.dto.EmploymentDto;
import com.example.calculator.dto.LoanStatementRequestDto;
import com.example.calculator.dto.ScoringDataDto;
import com.example.calculator.enums.EmploymentStatusEnum;
import com.example.calculator.enums.GendersEnum;
import com.example.calculator.enums.MaritalStatusEnum;
import com.example.calculator.enums.PositionsEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScoringServiceTest {
    private final ScoringService scoringService = new ScoringService();

    @Test
    void prescore() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setFirstName("12345");
        loanStatementRequestDto.setLastName("12345");
        loanStatementRequestDto.setMiddleName("a");
        boolean prescoreFalseResult = scoringService.prescore(loanStatementRequestDto);
        assertFalse(prescoreFalseResult);
        loanStatementRequestDto.setFirstName("boris");
        prescoreFalseResult = scoringService.prescore(loanStatementRequestDto);
        assertFalse(prescoreFalseResult);
        loanStatementRequestDto.setLastName("britva");
        prescoreFalseResult = scoringService.prescore(loanStatementRequestDto);
        assertFalse(prescoreFalseResult);
        loanStatementRequestDto.setMiddleName("348");
        prescoreFalseResult = scoringService.prescore(loanStatementRequestDto);
        assertFalse(prescoreFalseResult);
        loanStatementRequestDto.setMiddleName(null);
        loanStatementRequestDto.setBirthdate(LocalDate.of(2017,7,30));
        prescoreFalseResult = scoringService.prescore(loanStatementRequestDto);
        assertFalse(prescoreFalseResult);
        loanStatementRequestDto.setBirthdate(LocalDate.of(2001,7,30));
        boolean rescoreCurrectResult = scoringService.prescore(loanStatementRequestDto);
        assertTrue(rescoreCurrectResult);
    }

    @Test
    void score() {
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setEmployment(new EmploymentDto());
        scoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatusEnum.UNEMPLOYED);
        scoringDataDto.getEmployment().setSalary(new BigDecimal(1000));
        scoringDataDto.setAmount(new BigDecimal(300000));
        scoringDataDto.getEmployment().setWorkExperienceTotal(1);
        scoringDataDto.getEmployment().setWorkExperienceCurrent(1);
        scoringDataDto.getEmployment().setPosition(PositionsEnum.SENIORSTAFF);
        scoringDataDto.setMaritalStatus(MaritalStatusEnum.WIDOWED);
        scoringDataDto.setGender(GendersEnum.MALE);
        scoringDataDto.setBirthdate(LocalDate.of(2001,7,30));
        assertThrows(IllegalArgumentException.class, () -> scoringService.score(scoringDataDto));
        scoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatusEnum.EMPLOYEE);
        assertThrows(IllegalArgumentException.class, () -> scoringService.score(scoringDataDto));
        scoringDataDto.getEmployment().setSalary(new BigDecimal(30000));
        assertThrows(IllegalArgumentException.class, () -> scoringService.score(scoringDataDto));
        scoringDataDto.getEmployment().setWorkExperienceCurrent(30);
        assertThrows(IllegalArgumentException.class, () -> scoringService.score(scoringDataDto));
        scoringDataDto.getEmployment().setWorkExperienceTotal(30);
        BigDecimal rate = new BigDecimal(-2);
        assertEquals(rate, scoringService.score(scoringDataDto));
    }

    @Test
    void calculateTotalAmount() {
        BigDecimal amount = new BigDecimal(300000);
        BigDecimal amountWithIns = new BigDecimal(400000);
        assertEquals(amount, scoringService.calculateTotalAmount(amount, false, false));
        assertEquals(amount, scoringService.calculateTotalAmount(amount, false, true));
        assertEquals(amount, scoringService.calculateTotalAmount(amount, true, true));
        assertEquals(amountWithIns, scoringService.calculateTotalAmount(amount, true, false));
    }

    @Test
    void calculateRate() {
        BigDecimal rate = new BigDecimal("16.00");
        assertEquals(rate, scoringService.calculateRate(false, false));
        assertEquals(rate.subtract(new BigDecimal(3)), scoringService.calculateRate(true, false));
        assertEquals(rate, scoringService.calculateRate(false, true));
        assertEquals(rate.subtract(new BigDecimal(1)), scoringService.calculateRate(true, true));
    }

    @Test
    void calculateMonthlyPayment() {
        BigDecimal amount = new BigDecimal(300000);
        BigDecimal rate = new BigDecimal(16);
        Integer term = 48;
        assertEquals(new BigDecimal("8502.08"),
                scoringService.calculateMonthlyPayment(amount, rate, term)
                        .setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void calculatePSK() {
        BigDecimal monthlyPayment = new BigDecimal("8502.08");
        Integer term = 48;
        BigDecimal currect = new BigDecimal("408099.84");
        assertEquals(currect, scoringService.calculatePSK(monthlyPayment, term));
    }
}