package com.coder.yourwork.controller;

import com.coder.yourwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControl {

    private final UserService userService;

    @Autowired
    public MainControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String greeting() {
        return "main";
    }

//    @GetMapping("/main")
//    public String main(@AuthenticationPrincipal UserDetailsImpl userDetails, Map<String, Object> model) {
//        model.put("email", userDetails.getUsername());
//        return "main";
//    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/admin")
//    public String userList() {
//        return "admin";
//    }


}
