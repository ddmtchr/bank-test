package com.ddmtchr.banktest.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteEmailRequest {
    @NotBlank
    private String email;
}
