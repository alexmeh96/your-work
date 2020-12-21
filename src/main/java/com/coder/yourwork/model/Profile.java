package com.coder.yourwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
public class Profile {
    @Id
    @GeneratedValue
    private Long id;

    private Long amountExecutionOrders;
    private Long amountExecutedOrdersSuccess;
    private Long amountExecutedOrdersWrong;
    private Long amountMakeOrders;


    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User auth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_id")
    private Executor executor;

    public Profile() {
        this.amountExecutionOrders = 0l;
        this.amountExecutedOrdersSuccess = 0l;
        this.amountExecutedOrdersWrong = 0l;
        this.amountMakeOrders = 0l;
    }
}
