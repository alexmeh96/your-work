package com.coder.yourwork.controller;

import com.coder.yourwork.dto.UserDto;
import com.coder.yourwork.model.Role;
import com.coder.yourwork.model.User;
import com.coder.yourwork.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.http.HttpRequest;
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

    @GetMapping("/loginError")
    public String loginPage(Map<String, Object> model) {
        model.put("messageError", "No correct email or password!");
        return "login";
    }

//    @PostMapping("/login")
//    public String signIn(UserDto userDto, Map<String, Object> model) {
//
//        return "redirect:/main";
//    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid UserDto userDto, BindingResult bindingResult, Model model) {

        if (userDto.getPassword() != null && !userDto.getPassword().equals(userDto.getPassword2())) {
            model.addAttribute("passwordError", "Password are different!");
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";
        }

        if (!authService.addUser(userDto)) {
            model.addAttribute("emailError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }
}
