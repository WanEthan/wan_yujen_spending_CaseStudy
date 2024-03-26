package com.example.logindemo2.controller;

import com.example.logindemo2.dto.UserDTO;
import com.example.logindemo2.model.Transaction;
import com.example.logindemo2.model.User;
import com.example.logindemo2.service.CustomUserDetails;
import com.example.logindemo2.service.TransactionService;
import com.example.logindemo2.service.UserService;
import com.example.logindemo2.service.iml.Transactionlmpl;
import com.example.logindemo2.service.iml.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new
                StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    private TransactionService transactionService;
    private UserServiceImpl userDetailsService;

    @Autowired
    public UserController(UserServiceImpl userDetailsService, TransactionService transactionService) {
        this.transactionService = transactionService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    private String redirectToLogin() {

        return "redirect:/log-in";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "signup2";
    }


    @PostMapping("/signup-process")
    public String signupProcess(@ModelAttribute("userDto") @Valid UserDTO userDto, BindingResult bindingResult) {
        User existing = userDetailsService.findUserByEmail(userDto.getEmail());

        if(existing !=null) {
            log.warn("Wrong attempt");
            bindingResult.rejectValue("email", null,
                    "There is already an account registered with that email");
        }
        System.out.println("ZZZZZZZZZZZZZZZ result:"+bindingResult.toString());

        if (bindingResult.hasErrors()){
            System.out.println("result:"+ bindingResult.toString());
            return "signup2";
        }

        userDetailsService.creat(userDto);
        return"confirmation";
}

    @GetMapping("/login")
    public String getLoginPage()
    {
        log.info("Login page displayed");
        return "log-in";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal, HttpSession session){
        // Retrieve the authenticated user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetail.getId();

        // Get the first name from the user details
        String fullName = userDetail.getFullName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        System.out.println(userDetails);

        List<Transaction> allTransactions = transactionService.getAllTransactions();
        // Filter transactions by type "income"
        List<Transaction> incomeTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getType().equalsIgnoreCase("income"))
                .toList();
        List<Transaction> expenseTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getType().equalsIgnoreCase("expense"))
                .toList();

        // Calculate total amount of income transactions
        BigDecimal totalIncome = incomeTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalIncome);

        BigDecimal totalExpense = expenseTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalExpense);
        model.addAttribute("netWorth", totalIncome.add(totalExpense));
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("transactions", allTransactions);
        model.addAttribute("fullName", fullName);
        model.addAttribute("userdetail", userDetails);
        return "home";
    }

}