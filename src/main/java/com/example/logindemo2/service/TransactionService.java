package com.example.logindemo2.service;

import com.example.logindemo2.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TransactionService {
    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByUserId(Long userId);

    void createTransaction(Transaction transaction);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(Long transactionId);

    Transaction getOneTransaction(Long transactionId);
}
