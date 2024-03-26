package com.example.logindemo2.service;

import com.example.logindemo2.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountService {
    List<Account> getAllAccounts();

    List<Account> getAccountsByUserId(Long userId);

    void createAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(Long accountId);

    Account getOneAccount(Long accountId);
}

