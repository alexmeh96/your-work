package com.coder.yourwork.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class ExecutorDto {
    private String name;
    private String describe;
    @NotNull(message = "Category cannot be empty")
    private List<String> categoryName;
    boolean active;

    private String email;
    private String phone;

    private MultipartFile avatar;
}
