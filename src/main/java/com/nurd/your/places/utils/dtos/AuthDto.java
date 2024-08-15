package com.nurd.your.places.utils.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class AuthDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestLoginDto {

        @NotNull
        private String email;
        @NotNull
        private String password;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestRegisterDto {
        @NotNull
        private String username;
        @NotNull
        @Email
        private String email;
        @NotNull
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseLoginDto {
        private String id;
        private String token;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseRegisterDto {
        private String id;
        private String username;
        private String email;
        private String role;
    }
}
