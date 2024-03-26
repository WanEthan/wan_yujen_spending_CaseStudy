package com.example.logindemo2.dto;
///////// DO NOT MODIFY THE CODE!!!!!!
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@FieldMatch.List( { @FieldMatch(first = "password", second = "matchingPassword",
        message = "The password fields must match")})
public class UserDTO {
    @NotEmpty
    @Pattern(regexp = "[A-Za-z ]+$", message = "Only alphabetic and space allowed")
    private String fullName;

    @Column(unique = true)
    @Email
    private String email;

    @Pattern(regexp = "[0-9]{10}$", message = "Phone wrong format")
    private String phone;

    @NotEmpty(message = "Required")
    private String password;

    @NotEmpty(message = "Required")
    private String matchingPassword;
    public UserDTO(@NotEmpty @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String fullName,
                   @Email String email,
                   @Pattern(regexp = "[0-9]{10}$", message = "Phone wrong format") String phone,
                   @NotEmpty(message = "Required") String password,
                   @NotEmpty(message = "Required") String matchingPassword) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}
