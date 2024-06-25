package com.example.calculator.dto;

import com.example.calculator.enums.GendersEnum;
import com.example.calculator.enums.MaritalStatusEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
    private BigDecimal amount;
    @Schema(
            description = "credit term",
            name = "term",
            type = "Integer",
            example = "48")
    private Integer term;
    @Schema(
            description = "client first name",
            name = "firstName",
            type = "String",
            example = "Boris")
    @Size(max = 30, min = 2)
    private String firstName;
    @Schema(
            description = "client last name",
            name = "lastName",
            type = "String",
            example = "Britva")
    @Size(max = 30, min = 2)
    private String lastName;
    @Schema(
            description = "client middle name",
            name = "middleName",
            type = "String",
            example = "Valentinovich")
    private String middleName;
    @Schema(
            description = "client gender",
            name = "gender",
            type = "GendersEnum",
            example = "FEMALE")
    private GendersEnum gender;
    @Schema(
            description = "client birthdate",
            name = "birthdate",
            type = "LocalDate",
            example = "2001-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @Schema(
            description = "client passport series",
            name = "passportSeries",
            type = "String",
            example = "1111")
    @Size(min = 4, max = 4)
    private String passportSeries;
    @Schema(
            description = "client passport number",
            name = "passportNumber",
            type = "String",
            example = "111111")
    @Size(min = 6, max = 6)
    private String passportNumber;
    @Schema(
            description = "client passport issue date",
            name = "passportIssueDate",
            type = "LocalDate",
            example = "2014-01-01")
    private LocalDate passportIssueDate;
    @Schema(
            description = "client passport issue branch",
            name = "passportIssueBranch",
            type = "String",
            example = "MVD")
    private String passportIssueBranch;
    @Schema(
            description = "client marinal status",
            name = "maritalStatus",
            type = "MaritalStatusEnum",
            example = "VIDOWED")
    private MaritalStatusEnum maritalStatus;
    @Schema(
            description = "dependent amount",
            name = "dependentAmount",
            type = "Integer",
            example = "15000")
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
    private EmploymentDto employment;
    @Schema(
            description = "account number",
            name = "accountNumber",
            type = "String",
            example = "15235434")
    private String accountNumber;
    @Schema(
            description = "client takes insurance or not",
            name = "isInsuranceEnabled",
            type = "Boolean",
            example = "true")
    private Boolean isInsuranceEnabled;
    @Schema(
            description = "client receives salary in this bank or not",
            name = "isSalaryClient",
            type = "Boolean",
            example = "false")
    private Boolean isSalaryClient;
}
