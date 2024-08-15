package com.nurd.your.places.controllers;

import com.nurd.your.places.models.User;
import com.nurd.your.places.services.UserService;
import com.nurd.your.places.utils.dtos.UserDto;
import com.nurd.your.places.utils.dtos.formator.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    // admin

    @PutMapping("/users/role/{id}")
    public ResponseEntity<?> updateUserRole(@PathVariable String id, @RequestBody Map<String, String> role) {
        return ResponseBuilder.renderJSON(userService.updateUserRole(id, role.get("role")), "Success", HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers() {
        return ResponseBuilder.renderJSON(userService.findAll(), "Success", HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseBuilder.renderJSON(null, "Success", HttpStatus.OK);
    }

    // user

    @GetMapping("/user")
    public ResponseEntity<?> findUser() {
        return ResponseBuilder.renderJSON(userService.findUser(), "Success", HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return ResponseBuilder.renderJSON(userService.updateUser(user), "Success", HttpStatus.CREATED);
    }

    @PutMapping("/user/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserDto.ChangePassword dto) {
        return ResponseBuilder.renderJSON(userService.changePassword(dto), "Password changed", HttpStatus.CREATED);
    }

}
