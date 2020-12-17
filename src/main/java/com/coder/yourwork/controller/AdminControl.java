package com.coder.yourwork.controller;

import com.coder.yourwork.model.User;
import com.coder.yourwork.repo.UserRepo;
import com.coder.yourwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControl {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/userList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(@RequestParam(required = false) boolean delete, Model model) {
        if (delete) {
            model.addAttribute("message", "Вы удалили пользователя");
        }
        List<User> userList = userService.allUser();
        model.addAttribute("users", userList);
        return "userList";
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userId(@PathVariable(name = "userId") User user, Model model) {
        model.addAttribute("user", user);
        return "userId";
    }

    @PostMapping("/user/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/userList?delete=true";
    }
}
