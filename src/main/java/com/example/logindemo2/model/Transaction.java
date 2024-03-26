package com.example.logindemo2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name="transactions")
public class Transaction {
    // ========== Primary Key ===================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========== Member Variables ==============
    @NotNull
    private String type;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String category;


    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // ========== Data Creation Trackers ========
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;


    // ========== Data Creation Event ===========

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    public Transaction(String type,
                       BigDecimal amount,
                       String category,
                       String description,
                       User user) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.user = user;
    }
}
