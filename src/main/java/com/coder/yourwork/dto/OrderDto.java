package com.coder.yourwork.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class OrderDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Describe cannot be empty")
    private String describe;
    @NotBlank(message = "Category cannot be empty")
    private String categoryName;

}
