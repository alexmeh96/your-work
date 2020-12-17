package com.coder.yourwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String describe;

    private Long price;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "offer_executor_id")
    private Executor offerExecutor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders_subscribers",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "subscribe_id"))
    private List<Executor> subscribers = new ArrayList<>();
    //
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_id")
    private Executor executor;

    public Order(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }
}
