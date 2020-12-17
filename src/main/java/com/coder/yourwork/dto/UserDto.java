package com.coder.yourwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.JoinColumns;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @Email(message = "Некоректный Email")
    @NotBlank(message = "Email не должен быть пустым!")
    private String email;
    @NotBlank(message = "Пароль не должен быть пустым!")
    private String password;
    private String password2;

    @NotBlank(message = "Имя не должно быть пустым!")
    private String name;

}
