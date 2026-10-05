package com.bank.repository;

import com.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByCustomerId(Integer customerId);
    boolean existsByAccountNumber(String accountNumber);
}
