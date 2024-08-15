package com.nurd.your.places.services;

import com.nurd.your.places.exceptions.exceptions.CustomNotFoundException;
import com.nurd.your.places.models.Role;
import com.nurd.your.places.models.User;
import com.nurd.your.places.repositories.UserRepo;
import com.nurd.your.places.utils.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findUser() {
        return authService.getUserAuthenticated();
    }

    @Transactional
    public User createUser(User user) {
        user.setPoint(0);
        return userRepo.create(user);
    }

    @Transactional
    public User updateUser(User user) {
        User newUser = authService.getUserAuthenticated();
        newUser.setUsername(user.getUsername() == null ? newUser.getUsername() : user.getUsername());
        newUser.setEmail(user.getEmail() == null ? newUser.getEmail() : user.getEmail());
        newUser.setPassword(user.getPassword() == null ? newUser.getPassword() : user.getPassword());
        newUser.setPoint(user.getPoint() == null ? newUser.getPoint() : user.getPoint());

        return userRepo.update(newUser.getId(), newUser);
    }

    @Transactional
    public User updateUserRole(String id, String role) {
        User newUser = userRepo.findById(id).orElseThrow(() -> new CustomNotFoundException("User not found"));
        if (Role.isValidRole(role)) {
            newUser.setRole(Role.valueOf(role).name());
        } else {
            throw new IllegalArgumentException("Role is not valid");
        }
        return userRepo.update(id, newUser);
    }

    @Transactional
    public User changePassword(UserDto.ChangePassword dto) {
        User user = authService.getUserAuthenticated();
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("invalid credentials");
        }
        user.setPassword(dto.getNewPassword());
        return userRepo.update(user.getId(), user);
    }

    public void deleteUser(String id) {
        userRepo.delete(id);
    }
}
