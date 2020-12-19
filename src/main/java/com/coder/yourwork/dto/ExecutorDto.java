package com.coder.yourwork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class ExecutorDto {
    @NotBlank(message = "firstName cannot be empty")
    private String firstName;
    @NotBlank(message = "lastName cannot be empty")
    private String lastName;
    @NotBlank(message = "Describe cannot be empty")
    private String describe;
    @NotNull(message = "Category cannot be empty")
    private List<String> categoryName;
    boolean active;
}
