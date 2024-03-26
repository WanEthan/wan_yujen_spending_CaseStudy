package com.example.logindemo2.service.iml;

import com.example.logindemo2.model.Transaction;
import com.example.logindemo2.repository.TransactionRepos;
import com.example.logindemo2.repository.TransactionRepos;
import com.example.logindemo2.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Transactionlmpl implements TransactionService {
    private final TransactionRepos transactionRepos;

    public Transactionlmpl(TransactionRepos transactionRepos) {

        this.transactionRepos = transactionRepos;
    }

    @Override
    public List<Transaction> getAllTransactions() {

        return transactionRepos.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepos.findByUserId(userId);
    }

    // ========== Read One ==========================
    // Shorthand to use without Optional<Baby> optbaby = babyRepos.findById(id);
    @Override
    public Transaction getOneTransaction(Long id) {
        return transactionRepos.findById(id).orElse(null);
    }

    @Override
    public void createTransaction(Transaction account) {

        transactionRepos.save(account);
    }

    @Override
    public Transaction updateTransaction(Transaction account) {
        return transactionRepos.save(account);
    }

    @Override
    public void deleteTransaction(Long accountId) {

        transactionRepos.deleteById(accountId);
    }
    
}
