package com.example.calculator.dto;

import com.example.calculator.utils.ValidBirthDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanStatementRequestDto {
    @Min(30000)
    private BigDecimal amount;
    @Min(6)
    private Integer term;
    @Size(max = 30,min = 2)
    private String firstName;
    @Size(max = 30, min = 2)
    private String lastName;
    private String middleName;
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@ValidBirthDate  - Альтернативный способ проверки на совершеннолетие
    private LocalDate birthdate;
    @Size(min = 4, max = 4)
    private String passportSeries;
    @Size(min = 6, max = 6)
    private String passportNumber;
}
