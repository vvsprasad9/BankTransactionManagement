package com.bank.dao;

import com.bank.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    Account save(Account account);
    Optional<Account> findById(int accountId);
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerId(int customerId);
    List<Account> findAll();
    Account update(Account account);
    boolean deleteById(int accountId);
}
