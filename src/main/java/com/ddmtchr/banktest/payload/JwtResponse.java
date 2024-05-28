package com.ddmtchr.banktest.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtResponse {
    private String jwt;
    private Long id;
    private String username;
}
