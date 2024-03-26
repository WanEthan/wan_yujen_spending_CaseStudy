package com.example.logindemo2.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class TransactionDto {

    // ========== Member Variables ==============
    @NotNull
    private String type;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String category;

    private String description;

    public TransactionDto(String type, BigDecimal amount, String category, String description) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }
}
