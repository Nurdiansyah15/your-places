package com.nurd.your.places.services;

import com.nurd.your.places.exceptions.exceptions.ConflictException;
import com.nurd.your.places.models.Role;
import com.nurd.your.places.models.User;
import com.nurd.your.places.repositories.UserRepo;
import com.nurd.your.places.securities.JwtService;
import com.nurd.your.places.utils.dtos.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    // register
    public AuthDto.ResponseRegisterDto register(AuthDto.RequestRegisterDto requestRegisterDto) {
        if (userRepo.findByEmail(requestRegisterDto.getEmail()).isPresent()) {
            throw new ConflictException("email already exists");
        }

        User user = new User();
        user.setUsername(requestRegisterDto.getUsername());
        user.setEmail(requestRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestRegisterDto.getPassword()));
        user.setRole(Role.ROLE_USER.toString());
        user.setPoint(0);
        User savedUser = userRepo.create(user);
        return new AuthDto.ResponseRegisterDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole());
    }

    // login
    public AuthDto.ResponseLoginDto login(AuthDto.RequestLoginDto requestLoginDto) {

        User user = userRepo.findByEmail(requestLoginDto.getEmail()).orElseThrow(() -> new IllegalStateException("invalid credentials"));

        if (!passwordEncoder.matches(requestLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new AuthDto.ResponseLoginDto(user.getId(), token);
    }

    // register admin
    public AuthDto.ResponseRegisterDto registerAdmin(AuthDto.RequestRegisterDto requestRegisterDto) {
        User user = new User();
        user.setUsername(requestRegisterDto.getUsername());
        user.setEmail(requestRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestRegisterDto.getPassword()));
        user.setRole(Role.ROLE_ADMIN.toString());
        user.setPoint(0);
        User savedUser = userRepo.create(user);
        return new AuthDto.ResponseRegisterDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole());
    }

    // userAuth
    public User getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return userRepo.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
