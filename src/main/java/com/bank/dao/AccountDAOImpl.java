package com.bank.dao;

import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountDAOImpl implements AccountDAO {

    private final AccountRepository accountRepository;

    public AccountDAOImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(int accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> findByCustomerId(int customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean deleteById(int accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }
}
