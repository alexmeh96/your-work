package com.coder.yourwork.service;

import com.coder.yourwork.dto.UserDto;
import com.coder.yourwork.model.User;
import com.coder.yourwork.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;

    @Autowired
    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findUser(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public User addUser(User user) {

        return userRepo.save(user);
    }
}
