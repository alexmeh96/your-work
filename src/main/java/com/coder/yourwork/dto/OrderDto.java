package com.coder.yourwork.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class OrderDto {

    private String name;
    private String describe;
    private String categoryName;
    private Long price;
    private String nameOwner;
    private String emailOwner;
    private String phoneOwner;

}
