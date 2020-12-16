package com.coder.yourwork.controller;

import com.coder.yourwork.dto.ExecutorDto;
import com.coder.yourwork.model.*;
import com.coder.yourwork.repo.CategoryRepo;
import com.coder.yourwork.service.CategoryService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/executor")
public class ExecutorControl {

    private final CategoryService categoryService;
    private final ExecutorService executorService;
    private final UserService userService;

    @Autowired
    public ExecutorControl(CategoryService categoryService, ExecutorService executorService, UserService userService) {
        this.categoryService = categoryService;
        this.executorService = executorService;
        this.userService = userService;
    }

    @GetMapping("/{executorId}")
    public String getExecutor(@PathVariable(name = "executorId") Executor executor,
                              @RequestParam(required = false) boolean offerSuccess,
                              @RequestParam(required = false) boolean takeOffer,
                              @RequestParam(required = false) boolean rejectOffer,
                              Model model) {


        if (offerSuccess) {
            model.addAttribute("message", "задание было предложено");
        } else if (takeOffer) {
            model.addAttribute("message", "вы приняли задание");
        } else if (rejectOffer) {
            model.addAttribute("message", "вы отклонили задание");
        }
        model.addAttribute("executor", executor);

        return "executorId";
    }

    @GetMapping("/category/active")
    public String allOrder(Map<String, Object> model) {
        List<Executor> executorList = executorService.statusExecutors(Status.ACTIVE);
        model.put("executors", executorList);
        return "executorList";
    }

    @GetMapping("/category")
    public String categoryList(Model model) {
        List<Category> categoryList = categoryService.allCategory();
        model.addAttribute("categories", categoryList);
        model.addAttribute("isCategory", true);
        return "executorList";
    }

    @GetMapping("/category/{categoryId}")
    public String categoryOrder(@PathVariable(name = "categoryId") Category category, Model model) {
        model.addAttribute("category", category);
        return "executorList";
    }

    @GetMapping("/create")
    public String createExecutor( Model model) {
        List<Category> categoryList = categoryService.allCategory();
        model.addAttribute("categories", categoryList);
        model.addAttribute("isCreate", true);
        return "executor";
    }

    @PostMapping("/create")
    public String addExecutor(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid ExecutorDto executorDto, BindingResult bindingResult, Model model) {
        User user = userService.getUser(userDetails.getId());

        List<Category> categoryList = categoryService.allCategory();
        model.addAttribute("categories", categoryList);
        model.addAttribute("isCreate", true);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "executor";
        }

        if (!executorService.addExecutor(user, executorDto)) {
            model.addAttribute("executorError", "Executor not create!");
            return "executor";
        }

        return "redirect:/profile?executorCreate=true";
    }

    @GetMapping("/update")
    public String updateExecutor(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestParam(required = false) boolean success,
                                 Model model) {
        if (success) {
            model.addAttribute("message", "Executor was update");
        }
        Executor executor = executorService.getExecutor(userDetails.getId());

        List<String> categoriesName = executorService.getExecutorCategories(userDetails.getId());

        List<Category> categoryList = categoryService.allCategory();
        model.addAttribute("categories", categoryList);
        model.addAttribute("isCreate", false);
        model.addAttribute("executor", executor);
        model.addAttribute("executor_categories", categoriesName);
        return "executor";
    }

    @PostMapping("/update")
    public String editExecutor(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestParam(name = "executorId") Executor executor,
                               @Valid ExecutorDto executorDto,
                               BindingResult bindingResult,
                               Model model) {

        List<String> categoriesName = executorService.getExecutorCategories(userDetails.getId());
        List<Category> categoryList = categoryService.allCategory();
        model.addAttribute("categories", categoryList);
        model.addAttribute("isCreate", false);
        model.addAttribute("executor", executor);
        model.addAttribute("executor_categories", categoriesName);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "executor";
        }

        if (!executorService.updateExecute(executor, executorDto)) {
            model.addAttribute("executorError", "Executor not update!");
            return "executor";
        }

        return "redirect:/executor/update?success=true";
    }


}
