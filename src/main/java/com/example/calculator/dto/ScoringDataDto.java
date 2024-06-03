package com.example.calculator.dto;

import com.example.calculator.enums.GendersEnum;
import com.example.calculator.enums.MaritalStatusEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoringDataDto {
    @Min(30000)
    private BigDecimal amount;
    private Integer term;
    @Size(max = 30, min = 2)
    private String firstName;
    @Size(max = 30, min = 2)
    private String lastName;
    private String middleName;
    @JsonValue
    private GendersEnum gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @Size(min = 4, max = 4)
    private String passportSeries;
    @Size(min = 6, max = 6)
    private String passportNumber;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    @JsonValue
    private MaritalStatusEnum maritalStatus;
    private Integer dependentAmount;
    private EmploymentDto employment;
    private String accountNumber;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

    public ScoringDataDto(BigDecimal amount, Integer term, String firstName, String lastName, String middleName, GendersEnum gender, LocalDate birthdate, String passportSeries, String passportNumber, LocalDate passportIssueDate, String passportIssueBranch, MaritalStatusEnum maritalStatus, Integer dependentAmount, EmploymentDto employment, String accountNumber, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        this.amount = amount;
        this.term = term;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.passportIssueDate = passportIssueDate;
        this.passportIssueBranch = passportIssueBranch;
        this.maritalStatus = maritalStatus;
        this.dependentAmount = dependentAmount;
        this.employment = employment;
        this.accountNumber = accountNumber;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
    }

    public ScoringDataDto() {
    }
}
