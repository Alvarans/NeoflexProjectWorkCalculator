package com.example.calculator.dto;

import com.example.calculator.utils.ValidBirthDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanStatementRequestDto {
    @Schema(
            description = "amount of credit",
            name = "amount",
            type = "BigDecimal",
            example = "300000.00")
    @Min(30000)
    private BigDecimal amount;
    @Schema(
            description = "term of credit",
            name = "term",
            type = "Integer",
            example = "48")
    @Min(6)
    private Integer term;
    @Schema(
            description = "client first name",
            name = "firstName",
            type = "String",
            example = "Boris")
    @Size(max = 30,min = 2)
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
            description = "client email",
            name = "email",
            type = "String",
            example = "email@mail.ru")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @Schema(
            description = "client birthdate",
            name = "birthdate",
            type = "LocalDate",
            example = "2001-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ValidBirthDate  - Альтернативный способ проверки на совершеннолетие
    private LocalDate birthdate;
    @Schema(
            description = "client passport series",
            name = "passportSeries",
            type = "String",
            example = "1234")
    @Size(min = 4, max = 4)
    private String passportSeries;
    @Schema(
            description = "client passport number",
            name = "passportNumber",
            type = "String",
            example = "true")
    @Size(min = 6, max = 6)
    private String passportNumber;
}
