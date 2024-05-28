package com.ddmtchr.banktest.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateEmailRequest {
    @NotBlank
    private String oldEmail;
    @NotBlank
    private String newEmail;
}
