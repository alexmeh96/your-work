package com.coder.yourwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long amountExecutionOrders;
    private Long amountExecutedOrdersSuccess;
    private Long amountExecutedOrdersWrong;
    private Long amountMakeOrders;


    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User auth;

    public Profile(String name) {
        this.name = name;
        this.amountExecutionOrders = 0l;
        this.amountExecutedOrdersSuccess = 0l;
        this.amountExecutedOrdersWrong = 0l;
        this.amountMakeOrders = 0l;
    }
}
