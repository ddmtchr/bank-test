package com.ddmtchr.banktest.controller;

import com.ddmtchr.banktest.payload.TransferMoneyRequest;
import com.ddmtchr.banktest.security.jwt.JwtUtils;
import com.ddmtchr.banktest.security.services.UserDetailsServiceImpl;
import com.ddmtchr.banktest.services.MoneyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/money")
public class MoneyController {
    private final UserDetailsServiceImpl userService;
    private final MoneyService moneyService;
    private final JwtUtils jwtUtils;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody @Valid TransferMoneyRequest request) {
        Long fromId = userService.findUserByLogin(jwtUtils.getCurrentUser().getUsername()).getId();
        moneyService.transferMoney(fromId, request.getToId(), request.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
