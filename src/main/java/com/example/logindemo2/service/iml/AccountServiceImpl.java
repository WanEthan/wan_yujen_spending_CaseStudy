package com.example.logindemo2.service.iml;

import com.example.logindemo2.model.Account;
import com.example.logindemo2.repository.AccountRepository;
import com.example.logindemo2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    // ========== Read One ==========================
    // Shorthand to use without Optional<Baby> optbaby = babyRepos.findById(id);

    @Override
    public Account getOneAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public void createAccount(Account account) {

        accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long accountId) {

        accountRepository.deleteById(accountId);
    }
}

