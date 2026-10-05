package com.bank.dao;

import com.bank.entity.Transaction;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

    private final TransactionRepository transactionRepository;

    public TransactionDAOImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(int transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Override
    public List<Transaction> findByAccountId(int accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
