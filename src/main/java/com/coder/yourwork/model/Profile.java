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

    private String FirstName;
    private String LastName;

    private String PhoneNumber;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User auth;
}
