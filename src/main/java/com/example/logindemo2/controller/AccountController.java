package com.example.logindemo2.controller;

import com.example.logindemo2.dto.UserDTO;
import com.example.logindemo2.model.Account;
import com.example.logindemo2.service.AccountService;
import com.example.logindemo2.service.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
//@Slf4j
public class AccountController {

    private AccountService accountService;


    @Autowired
    public AccountController(AccountService accountService) {
        super();
        this.accountService = accountService;

    }

    // ========== Display =============== -----------------> DONE
    @GetMapping("/creatAccount")
    public String createAccountPage(Model model, HttpSession session) {
        Account account= new Account();
        // Retrieve the authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetail.getId();
        // Store user ID in session
        session.setAttribute("userId", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("account", account);
        return "create-account";
    }


    // ========== Read All ===============
    @GetMapping("/accounts")
    public String AccountPage(Model model) {
        // Retrieve the authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetail.getId();
        model.addAttribute("userId", userId);
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("accounts", accountService.getAccountsByUserId(userId));
        return "accounts";
    }

    @PostMapping("/addAccounts")
    public String createAccount(@ModelAttribute("account") Account account) {
        accountService.createAccount(account);
        return "redirect:/accounts";
    }

    // ========== Delete =============== -------------> Done
    @DeleteMapping("/account/{id}")
    public String deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
        return "redirect:/accounts";
    }


    // ========== Update ===============
    @GetMapping("/editAccount/{id}")
    public String updateAccount(@PathVariable("id") Long id, Model model) {
        Account account = accountService.getOneAccount(id);
        Long userId = account.getUser().getId();
        model.addAttribute("userId", userId);
        model.addAttribute("account", account);
        return "edit-account";
    }

    @PutMapping("/update/{id}")
    public String editAccount(@Valid @ModelAttribute("account") Account account,
                              BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("account", account);
            return "edit-account";
        }
        accountService.updateAccount(account);
        return "redirect:/accounts";
    }

}

