package com.coder.yourwork.controller;

import com.coder.yourwork.model.Profile;
import com.coder.yourwork.service.UserDetailsImpl;
import com.coder.yourwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileControl {
    private final UserService userService;

    @Autowired
    public ProfileControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String profile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @RequestParam(required = false) boolean executorCreat,
                          Model model) {
        Profile profile = userService.getUserProfile(userDetails.getId());

        if (executorCreat) {
            model.addAttribute("message", "Исполнитель был создан!");
        }

        if (userService.executorExist(userDetails.getId())) {
            model.addAttribute("executorExist", true);
        }

        model.addAttribute("profile", profile);
        return "profile";
    }
}
