package com.coder.yourwork.service;

import com.coder.yourwork.dto.UserDto;
import com.coder.yourwork.model.Role;
import com.coder.yourwork.model.User;
import com.coder.yourwork.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public Boolean addUser(UserDto userDto) {
        User user = userRepo.findByEmail(userDto.getEmail()).orElse(null);

        if (user != null) {
            return false;
        }

        User newUser = new User(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                Collections.singleton(Role.USER)
        );
        userRepo.save(newUser);

        return true;
    }
}
