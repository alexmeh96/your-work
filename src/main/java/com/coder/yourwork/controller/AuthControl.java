package com.coder.yourwork.controller;

import com.coder.yourwork.dto.UserDto;
import com.coder.yourwork.model.Role;
import com.coder.yourwork.model.User;
import com.coder.yourwork.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Map;

@Controller
public class AuthControl {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthControl(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

//    @PostMapping("/login")
//    public String signIn(UserDto userDto, Map<String, Object> model) {
//
//        return "redirect:/main";
//    }

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        model.put("message", "");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDto userDto, Map<String, Object> model) {
        User user = authService.findUser(userDto.getUsername());

        if (user != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        User newUser = new User(
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                Collections.singleton(Role.USER)
        );
        authService.addUser(newUser);

        return "redirect:/login";
    }
}
