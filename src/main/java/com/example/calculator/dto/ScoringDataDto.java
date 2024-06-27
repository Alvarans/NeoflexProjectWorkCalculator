package com.example.calculator.dto;

import com.example.calculator.enums.GendersEnum;
import com.example.calculator.enums.MaritalStatusEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoringDataDto {
    @Schema(
            description = "amount of crdit",
            name = "amount",
            type = "BigDecimal",
            example = "300000.00")
    @Min(30000)
    @NotNull(message = "amount can't be null")
    private BigDecimal amount;
    @Schema(
            description = "credit term",
            name = "term",
            type = "Integer",
            example = "48")
    @NotNull(message = "term can't be null")
    private Integer term;
    @Schema(
            description = "client first name",
            name = "firstName",
            type = "String",
            example = "Boris")
    @Size(max = 30, min = 2)
    @NotBlank(message = "First name can't be null or blank")
    private String firstName;
    @Schema(
            description = "client last name",
            name = "lastName",
            type = "String",
            example = "Britva")
    @Size(max = 30, min = 2)
    @NotBlank(message = "Last name can't be null or blank")
    private String lastName;
    @Schema(
            description = "client middle name",
            name = "middleName",
            type = "String",
            example = "Valentinovich")
    @NotBlank(message = "Middle name can't be null or blank")
    private String middleName;
    @Schema(
            description = "client gender",
            name = "gender",
            type = "GendersEnum",
            example = "FEMALE")
    @NotNull(message = "Gender can't be null")
    private GendersEnum gender;
    @Schema(
            description = "client birthdate",
            name = "birthdate",
            type = "LocalDate",
            example = "2001-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthdate can't be null")
    private LocalDate birthdate;
    @Schema(
            description = "client passport series",
            name = "passportSeries",
            type = "String",
            example = "1111")
    @Size(min = 4, max = 4)
    @NotNull(message = "passport series can't be null")
    private String passportSeries;
    @Schema(
            description = "client passport number",
            name = "passportNumber",
            type = "String",
            example = "111111")
    @Size(min = 6, max = 6)
    @NotNull(message = "passport number can't be null")
    private String passportNumber;
    @Schema(
            description = "client passport issue date",
            name = "passportIssueDate",
            type = "LocalDate",
            example = "2014-01-01")
    @NotNull(message = "passport issue date can't be null")
    private LocalDate passportIssueDate;
    @Schema(
            description = "client passport issue branch",
            name = "passportIssueBranch",
            type = "String",
            example = "MVD")
    @NotBlank(message = "passport issue branch can't be null")
    private String passportIssueBranch;
    @Schema(
            description = "client marinal status",
            name = "maritalStatus",
            type = "MaritalStatusEnum",
            example = "WIDOWED")
    @NotNull(message = "marinal status can't be null")
    private MaritalStatusEnum maritalStatus;
    @Schema(
            description = "dependent amount",
            name = "dependentAmount",
            type = "Integer",
            example = "15000")
    @NotNull(message = "dependent amount can't be null")
    private Integer dependentAmount;
    @Schema(
            description = "employment information",
            name = "employment",
            type = "EmploymentDto",
            example = """
                    {
                        "employmentStatus": "BUSINESSOWNER",
                        "employerINN": "string",
                        "salary": 30000,
                        "position": "TOPMANAGER",
                        "workExperienceTotal": 30,
                        "workExperienceCurrent": 12
                      }
                      """)
    @NotNull(message = "employment can't be null")
    private EmploymentDto employment;
    @Schema(
            description = "account number",
            name = "accountNumber",
            type = "String",
            example = "15235434")
    @NotBlank(message = "account number can't be null or blank")
    private String accountNumber;
    @Schema(
            description = "client takes insurance or not",
            name = "isInsuranceEnabled",
            type = "Boolean",
            example = "true")
    @NotNull(message = "Insurance can't be null")
    private Boolean isInsuranceEnabled;
    @Schema(
            description = "client receives salary in this bank or not",
            name = "isSalaryClient",
            type = "Boolean",
            example = "false")
    @NotNull(message = "Salary can't be null")
    private Boolean isSalaryClient;
}
