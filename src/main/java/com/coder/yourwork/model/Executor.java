package com.coder.yourwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "executor")
public class Executor {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String describe;

    private String email;
    private String phone;

    private boolean active;

    private Long avatarId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(	name = "executor_category",
            joinColumns = @JoinColumn(name = "executor_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @OneToOne(mappedBy = "executor", cascade = CascadeType.ALL)
    private User auth;

    @OneToOne(mappedBy = "executor", cascade = CascadeType.ALL)
    private Profile profile;

    @ManyToMany(mappedBy = "subscribers", cascade = CascadeType.ALL)
    private List<Order> subscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "executor", cascade = CascadeType.ALL)
    private List<Order> executions = new ArrayList<>();

    @OneToMany(mappedBy = "offerExecutor", cascade = CascadeType.ALL)
    private List<Order> offers = new ArrayList<>();

    public Executor(String name, String describe, boolean active) {
        this.name = name;
        this.describe = describe;
        this.active = active;
    }
}
