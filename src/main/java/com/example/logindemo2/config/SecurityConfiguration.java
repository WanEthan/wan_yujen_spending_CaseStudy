///////// DO NOT MODIFY THE CODE!!!!!!

package com.example.logindemo2.config;

import com.example.logindemo2.service.iml.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 * SecurityConfiguration class completely refactored
 * IMPORTANT:
 * if you are going to use for specific endpoint more than one user role
 * always use hasAnyRole(...)
 * In order to be customizable always with Thymeleaf successForwardUrl(...) method */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserServiceImpl userDetailsService;
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        auth.setUserDetailsService(userDetailsService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }
    //beans
//bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("===========>IN filterChain() ");
        http.authorizeHttpRequests().requestMatchers(
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/sign-up",
                        "/signup-process",
                        "/webjars/**").permitAll().anyRequest().authenticated().and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll();
        return http.build();
    }

//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(
//                        (auth) -> auth
//                                .requestMatchers("/", "/login*",
//                                        "/css/*", "/static/js/*", "/sign-up",
//                                        "/signup-process").permitAll()
//                                .requestMatchers("/home").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login") // should point to login page
//                                .defaultSuccessUrl("/home", true).permitAll() // must be in order thymeleaf security extras work
//                                .permitAll()
//                )
//                .logout(
//                        logout -> logout
//                                .invalidateHttpSession(true)
//                                .clearAuthentication(true)
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .logoutSuccessUrl("/login?logout").permitAll()
//                );
//        return http.build();
//    }
}