package com.example.logindemo2.service;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.loader.ast.spi.Loadable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String fullName;
    private String phone;

    public CustomUserDetails(String email,
                             String password, Collection<? extends GrantedAuthority> authorities, String fullName,
                             String phone, Long id) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.fullName = fullName;
        this.phone = phone;
        this.id = id;
    }

//    public CustomUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities,
//                             String fullName, String phone) {
//        this.email = email;
//        this.password = password;
//        this.authorities = authorities;
//        this.fullName = fullName;
//        this.phone = phone;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
