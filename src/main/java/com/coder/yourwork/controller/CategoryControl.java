package com.coder.yourwork.controller;

import com.coder.yourwork.dto.CategoryDto;
import com.coder.yourwork.dto.OrderDto;
import com.coder.yourwork.model.Category;
import com.coder.yourwork.model.User;
import com.coder.yourwork.service.CategoryService;
import com.coder.yourwork.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryControl {

    private final CategoryService categoryService;

    @Autowired
    public CategoryControl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @GetMapping("/all")
    public String allCategory(Map<String, Object> model) {

        List<Category> categoryList = categoryService.allCategory();

        model.put("categories", categoryList);

        return "category";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/create")
    public String createCategory() {
        return "createCategory";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String addCategory(@Valid CategoryDto categoryDto, BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "createCategory";
        }

        if (categoryService.categoryExist(categoryDto)) {
            model.addAttribute("categoryError", "Category already exist!");
            return "createCategory";
        }

        if (!categoryService.createCategory(categoryDto)) {
            model.addAttribute("categoryError", "Category not create!");
            return "createCategory";
        }
        return "createCategory";
    }
}
