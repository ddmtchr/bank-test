package com.ddmtchr.banktest.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatePhoneRequest {
    @NotBlank
    private String oldPhone;
    @NotBlank
    private String newPhone;
}
