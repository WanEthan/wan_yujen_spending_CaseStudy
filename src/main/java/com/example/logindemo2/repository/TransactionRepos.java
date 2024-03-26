package com.example.logindemo2.repository;


import com.example.logindemo2.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepos extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
}
