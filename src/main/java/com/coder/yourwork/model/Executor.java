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

    private String firstName;
    private String lastName;
    private String describe;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(	name = "executor_category",
            joinColumns = @JoinColumn(name = "executor_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(mappedBy = "executors")
    private List<Order> orders = new ArrayList<>();

//    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinColumn(name = "category_id")
//    private Category category;

    @OneToOne(mappedBy = "executor", cascade = CascadeType.ALL)
    private User auth;

    public Executor(String firstName, String lastName, String describe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.describe = describe;
    }
}
