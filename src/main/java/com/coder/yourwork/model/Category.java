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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


    @ManyToMany(mappedBy = "categories")
    private List<Executor> executors = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }
}
