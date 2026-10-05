package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dto.AccountDTO;
import com.bank.dto.DepositRequestDTO;
import com.bank.dto.WithdrawRequestDTO;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidTransactionException;
import com.bank.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;
    private final CustomerDAO customerDAO;
    private final TransactionDAO transactionDAO;

    public AccountServiceImpl(AccountDAO accountDAO, CustomerDAO customerDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.customerDAO = customerDAO;
        this.transactionDAO = transactionDAO;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        customerDAO.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + accountDTO.getCustomerId()));

        if (accountDAO.findByAccountNumber(accountDTO.getAccountNumber()).isPresent()) {
            throw new InvalidTransactionException("Account number " + accountDTO.getAccountNumber() + " already exists");
        }

        Account account = new Account(
                accountDTO.getAccountNumber(),
                accountDTO.getAccountType(),
                accountDTO.getBalance(),
                accountDTO.getCustomerId()
        );

        Account savedAccount = accountDAO.save(account);
        return mapToDTO(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountById(int accountId) {
        Account account = accountDAO.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));
        return mapToDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountByNumber(String accountNumber) {
        Account account = accountDAO.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + accountNumber));
        return mapToDTO(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> getAccountsByCustomerId(int customerId) {
        return accountDAO.findByCustomerId(customerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> getAllAccounts() {
        return accountDAO.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO deposit(DepositRequestDTO depositRequest) {
        Account account = accountDAO.findByAccountNumber(depositRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + depositRequest.getAccountNumber()));

        account.deposit(depositRequest.getAmount());
        Account updatedAccount = accountDAO.update(account);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Transaction tx = new Transaction(account.getAccountId(), "DEPOSIT", depositRequest.getAmount(), now);
        transactionDAO.save(tx);

        return mapToDTO(updatedAccount);
    }

    @Override
    public AccountDTO withdraw(WithdrawRequestDTO withdrawRequest) {
        Account account = accountDAO.findByAccountNumber(withdrawRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + withdrawRequest.getAccountNumber()));

        try {
            account.withdraw(withdrawRequest.getAmount());
        } catch (IllegalArgumentException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }

        Account updatedAccount = accountDAO.update(account);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Transaction tx = new Transaction(account.getAccountId(), "WITHDRAWAL", withdrawRequest.getAmount(), now);
        transactionDAO.save(tx);

        return mapToDTO(updatedAccount);
    }

    @Override
    public void deleteAccount(int accountId) {
        if (!accountDAO.deleteById(accountId)) {
            throw new ResourceNotFoundException("Account not found with ID: " + accountId);
        }
    }

    private AccountDTO mapToDTO(Account account) {
        return new AccountDTO(
                account.getAccountId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getCustomerId()
        );
    }
}
