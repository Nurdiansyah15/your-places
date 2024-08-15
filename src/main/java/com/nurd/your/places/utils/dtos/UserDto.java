package com.nurd.your.places.utils.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangePassword{
        @NotNull
        private String oldPassword;
        @NotNull
        private String newPassword;
    }
}
