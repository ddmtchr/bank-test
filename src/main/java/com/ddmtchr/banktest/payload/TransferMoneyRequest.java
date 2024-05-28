package com.ddmtchr.banktest.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferMoneyRequest {
    @NotNull
    private Long toId;
    @NotNull
    @Positive
    private Double amount;
}
