package com.nurd.your.places.controllers;

import com.nurd.your.places.services.AuthService;
import com.nurd.your.places.utils.dtos.AuthDto;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<AuthDto.ResponseLoginDto> login(@RequestBody @Valid AuthDto.RequestLoginDto obj) {
        return ResponseEntity.ok(authService.login(obj));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthDto.ResponseRegisterDto> register(@RequestBody @Valid AuthDto.RequestRegisterDto obj) {
        return new ResponseEntity<>(authService.register(obj), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<AuthDto.ResponseRegisterDto> registerAdmin(@RequestBody @Valid AuthDto.RequestRegisterDto obj) {
        return new ResponseEntity<>(authService.registerAdmin(obj), HttpStatus.CREATED);
    }
}
