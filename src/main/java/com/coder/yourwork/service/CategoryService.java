package com.coder.yourwork.service;

import com.coder.yourwork.dto.CategoryDto;
import com.coder.yourwork.model.Category;
import com.coder.yourwork.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> allCategory() {
        return categoryRepo.findAll();
    }

    public boolean categoryExist(CategoryDto categoryDto) {
        return categoryRepo.existsByName(categoryDto.getName());
    }

    public boolean createCategory(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getName());
        categoryRepo.save(category);

        return true;
    }
}
