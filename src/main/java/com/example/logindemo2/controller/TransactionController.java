package com.example.logindemo2.controller;

import com.example.logindemo2.dto.UserDTO;
import com.example.logindemo2.model.Transaction;
import com.example.logindemo2.service.TransactionService;
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

import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        super();
        this.transactionService = transactionService;

    }

    List<String> types = Arrays.asList("Income", "Expense");
    List<String> categories = Arrays.asList("Food", "Transportation",
            "Housing", "Utilities", "Entertainment", "Health", "Education",
            "Clothing", "Salary", "Investment", "Interests/Dividends", "Loans", "Other");


    // ========== Display =============== -----------------> DONE
    @GetMapping("/creatTransaction")
    public String createTransactionPage(Model model, HttpSession session) {
        Transaction transaction= new Transaction();
        // Retrieve the authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetail.getId();
        // Store user ID in session
        session.setAttribute("userId", userId);
        model.addAttribute("categories", categories);
        model.addAttribute("types", types);
        model.addAttribute("userId", userId);
        model.addAttribute("transaction", transaction);
        return "create-transaction";
    }

    // ========== Read All ===============
    @GetMapping("/transaction")
    public String TransactionPage(Model model) {
        // Retrieve the authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetail.getId();
        model.addAttribute("userId", userId);
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("transactions", transactionService.getTransactionsByUserId(userId));
        return "/transaction";
    }

    @PostMapping("/addTransactions")
    public String createTransaction(@ModelAttribute("transaction") Transaction transaction) {
        transactionService.createTransaction(transaction);
        return "redirect:/transaction";
    }

    // ========== Delete =============== -------------> Done
    @DeleteMapping("/transaction/{id}")
    public String deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransaction(id);
        return "redirect:/transaction";
    }


    // ========== Update ===============
    @GetMapping("/editTransaction/{id}")
    public String updateTransaction(@PathVariable("id") Long id, Model model, HttpSession session) {
        Transaction transaction = transactionService.getOneTransaction(id);
        Long userId = transaction.getUser().getId();


        model.addAttribute("categories", categories);
        model.addAttribute("types", types);
        model.addAttribute("userId", userId);
        model.addAttribute("transaction", transaction);

        return "edit-transaction";
    }

    @PutMapping("/updateTran/{id}")
    public String editTransaction(@Valid @ModelAttribute("transaction") Transaction transaction,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("transaction", transaction);
            return "edit-transaction";
        }
        transactionService.updateTransaction(transaction);

        return "redirect:/transaction";
    }


}
