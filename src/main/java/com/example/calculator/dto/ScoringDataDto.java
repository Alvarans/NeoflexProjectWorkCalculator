package com.example.calculator.dto;

import com.example.calculator.enums.GendersEnum;
import com.example.calculator.enums.MaritalStatusEnum;
import com.example.calculator.utils.ValidBirthDate;
import jakarta.validation.constraints.Max;
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
    @Min(2)
    @Max(30)
    private String firstName;
    @Min(2)
    @Max(30)
    private String lastName;
    private String middleName;
    private Enum<GendersEnum> gender;
    @ValidBirthDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @Size(min = 4, max = 4)
    private String passportSeries;
    @Size(min = 6, max = 6)
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
