package com.coder.yourwork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
}
