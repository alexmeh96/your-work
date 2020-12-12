package com.coder.yourwork.controller;

import com.coder.yourwork.model.Profile;
import com.coder.yourwork.service.UserDetailsImpl;
import com.coder.yourwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainControl {

    private final UserService userService;

    @Autowired
    public MainControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal UserDetailsImpl userDetails, Map<String, Object> model) {
        model.put("email", userDetails.getUsername());
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String userList() {
        return "admin";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetailsImpl userDetails,  Model model) {
        Profile profile = userService.getUserProfile(userDetails.getId());
        model.addAttribute("profile", profile);
        return "profile";
    }
}
