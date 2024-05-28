package com.ddmtchr.banktest.controller;

import com.ddmtchr.banktest.database.entities.User;
import com.ddmtchr.banktest.mapper.UserMapper;
import com.ddmtchr.banktest.payload.JwtResponse;
import com.ddmtchr.banktest.payload.LoginRequest;
import com.ddmtchr.banktest.payload.RegisterRequest;
import com.ddmtchr.banktest.security.jwt.JwtUtils;
import com.ddmtchr.banktest.security.services.UserDetailsImpl;
import com.ddmtchr.banktest.security.services.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserDetailsServiceImpl userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequest request) {
        if (!userService.canRegister(request)) {
            return ResponseEntity.badRequest().body("User data already exists");
        }
        User user = UserMapper.instance.toUser(request, encoder);
        userService.addUser(user);
        return new ResponseEntity<>("Registered", HttpStatus.CREATED);
    }
}
