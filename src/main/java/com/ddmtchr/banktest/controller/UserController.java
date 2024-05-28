package com.ddmtchr.banktest.controller;

import com.ddmtchr.banktest.payload.*;
import com.ddmtchr.banktest.security.services.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserDetailsServiceImpl userService;

    @PostMapping("/{userId}/phones")
    public ResponseEntity<?> addPhone(@PathVariable Long userId, @RequestBody @Valid AddPhoneRequest request) {
        userService.addPhone(userId, request.getPhone());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/phones")
    public ResponseEntity<?> updatePhone(@PathVariable Long userId, @RequestBody @Valid UpdatePhoneRequest request) {
        userService.updatePhone(userId, request.getOldPhone(), request.getNewPhone());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/phones")
    public ResponseEntity<Void> deletePhone(@PathVariable Long userId, @RequestBody @Valid DeletePhoneRequest request) {
        userService.deletePhone(userId, request.getPhone());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/emails")
    public ResponseEntity<?> addEmail(@PathVariable Long userId, @RequestBody @Valid AddEmailRequest request) {
        userService.addEmail(userId, request.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/emails")
    public ResponseEntity<?> updateEmail(@PathVariable Long userId, @RequestBody @Valid UpdateEmailRequest request) {
        userService.updateEmail(userId, request.getOldEmail(), request.getNewEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/emails")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long userId, @RequestBody @Valid DeleteEmailRequest request) {
        userService.deleteEmail(userId, request.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
