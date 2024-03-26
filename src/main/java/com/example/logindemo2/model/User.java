///////// DO NOT MODIFY THE CODE!!!!!!

package com.example.logindemo2.model;

import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Igor Adulyan
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fullName;
    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public User(long id, String fullName, String email, String phone, String password, List<Account> accounts) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.accounts = accounts;
    }

    public User(String fullName, String email, String phone, String password ) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

}