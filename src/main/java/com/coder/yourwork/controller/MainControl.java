package com.coder.yourwork.controller;

import com.coder.yourwork.service.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainControl {

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal UserDetailsImpl userDetails, Map<String, Object> model) {
        model.put("email", userDetails.getUsername());
        return "main";
    }


}
