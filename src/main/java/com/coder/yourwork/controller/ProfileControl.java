package com.coder.yourwork.controller;

import com.coder.yourwork.dto.UserDto;
import com.coder.yourwork.model.Executor;
import com.coder.yourwork.model.Profile;
import com.coder.yourwork.model.User;
import com.coder.yourwork.service.ExecutorService;
import com.coder.yourwork.service.UserDetailsImpl;
import com.coder.yourwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileControl {
    private final UserService userService;
    @Autowired
    private ExecutorService executorService;

    @Autowired
    public ProfileControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String profile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @RequestParam(required = false) boolean executorCreat,
                          Model model) {
        Profile profile = userService.getUserProfile(userDetails.getId());
        Executor executor = executorService.getExecutor(userDetails.getId());

        if (executor != null) {
            model.addAttribute("executor", executor);
        }

        if (executorCreat) {
            model.addAttribute("message", "Исполнитель был создан!");
        }

        if (userService.executorExist(userDetails.getId())) {
            model.addAttribute("executorExist", true);
        }

        model.addAttribute("profile", profile);
        return "profile";
    }
    @GetMapping("/setting")
    public String settings(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestParam(required = false) boolean updateEmailSuccess,
                           @RequestParam(required = false) boolean updatePasswordSuccess,
                           Model model) {

        if (updateEmailSuccess) {
            model.addAttribute("message", "Обновление email прошло успешно!");
        } else if (updatePasswordSuccess) {
            model.addAttribute("message", "Обновление пароля прошло успешно!");
        }

        User user = userService.getUser(userDetails.getId());

        model.addAttribute("user", user);
        return "setting";
    }

    @PostMapping("/setting/email")
    public String settingsUpdateEmail(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @Valid UserDto userDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "setting";
        }
        User user = userService.getUser(userDetails.getId());
        if (!userService.updateUserEmail(user, userDto)) {
            model.addAttribute("emailError", "User exists!");
            return "setting";
        }

        return "redirect:/profile/setting?updateEmailSuccess=true";
    }

    @PostMapping("/setting/password")
    public String settingsUpdatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @Valid UserDto userDto,
                                      BindingResult bindingResult,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "setting";
        }
        User user = userService.getUser(userDetails.getId());
        if (!userService.updateUserPassword(user, userDto)) {
            return "setting";
        }

        return "redirect:/profile/setting?updatePasswordSuccess=true";
    }
}
