package com.coder.yourwork;

import com.coder.yourwork.model.Role;
import com.coder.yourwork.model.User;
import com.coder.yourwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class YourWorkApplication implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public YourWorkApplication(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(YourWorkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.countUser() == 0) {
            User user = new User("admin@email.com", passwordEncoder.encode("admin"), Set.of(Role.USER, Role.ADMIN));
            userService.createFirstUser(user, "admin");

        }
    }
}
