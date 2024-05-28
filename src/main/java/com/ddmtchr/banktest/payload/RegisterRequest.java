package com.ddmtchr.banktest.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RegisterRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotNull
    @Min(0)
    private Double money;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
    @NotNull
    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate birthDate;
    @NotBlank
    private String fullName;
}
