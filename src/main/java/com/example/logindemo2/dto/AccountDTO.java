package com.example.logindemo2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {


    @NotEmpty
    private String name;
    @NotEmpty
    private String type;

    @NotEmpty
    @Pattern(regexp = "[0-9]{12}$", message = "Account number wrong format")
    private String number;

    public AccountDTO(String name, String type, String number) {
        this.name = name;
        this.type = type;
        this.number = number;
    }
    // Getters and setters
}
