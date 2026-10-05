package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dto.TransactionDTO;
import com.bank.dto.TransferRequestDTO;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidTransactionException;
import com.bank.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO;
    private final AccountDAO accountDAO;

    public TransactionServiceImpl(TransactionDAO transactionDAO, AccountDAO accountDAO) {
        this.transactionDAO = transactionDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDTO getTransactionById(int transactionId) {
        Transaction transaction = transactionDAO.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + transactionId));
        return mapToDTO(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByAccountId(int accountId) {
        accountDAO.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));
        return transactionDAO.findByAccountId(accountId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDTO> getAllTransactions() {
        return transactionDAO.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> transferFunds(TransferRequestDTO transferRequest) {
        if (transferRequest.getFromAccountNumber().equals(transferRequest.getToAccountNumber())) {
            throw new InvalidTransactionException("Source and target account numbers cannot be the same");
        }

        Account fromAccount = accountDAO.findByAccountNumber(transferRequest.getFromAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found: " + transferRequest.getFromAccountNumber()));

        Account toAccount = accountDAO.findByAccountNumber(transferRequest.getToAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Target account not found: " + transferRequest.getToAccountNumber()));

        try {
            fromAccount.withdraw(transferRequest.getAmount());
        } catch (IllegalArgumentException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }

        toAccount.deposit(transferRequest.getAmount());

        accountDAO.update(fromAccount);
        accountDAO.update(toAccount);

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Transaction outTx = new Transaction(fromAccount.getAccountId(), "TRANSFER_OUT", transferRequest.getAmount(), now);
        Transaction inTx = new Transaction(toAccount.getAccountId(), "TRANSFER_IN", transferRequest.getAmount(), now);

        Transaction savedOutTx = transactionDAO.save(outTx);
        Transaction savedInTx = transactionDAO.save(inTx);

        return Arrays.asList(mapToDTO(savedOutTx), mapToDTO(savedInTx));
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getTransactionDate()
        );
    }
}
