package com.example.calculator.dto;

import com.example.calculator.enums.GendersEnum;
import com.example.calculator.enums.MaritalStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoringDataDto {
    private BigDecimal amount;
    private Integer term;
    private String firstName;
    private String lastName;
    private String middleName;
    private Enum<GendersEnum> gender;
    private LocalDate birthdate;
    private String passportSeries;
    private String passportNumber;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private Enum<MaritalStatusEnum> maritalStatus;
    private Integer dependentAmount;
    private EmploymentDto employment;
    private String accountNumber;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
}
