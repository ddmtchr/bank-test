package com.ddmtchr.banktest.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeletePhoneRequest {
    @NotBlank
    private String phone;
}
