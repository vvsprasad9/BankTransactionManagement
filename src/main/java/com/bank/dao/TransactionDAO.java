package com.bank.dao;

import com.bank.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDAO {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(int transactionId);
    List<Transaction> findByAccountId(int accountId);
    List<Transaction> findAll();
}
