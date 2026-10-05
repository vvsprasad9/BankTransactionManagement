package com.bank.service;

import com.bank.dto.TransactionDTO;
import com.bank.dto.TransferRequestDTO;

import java.util.List;

public interface TransactionService {
    TransactionDTO getTransactionById(int transactionId);
    List<TransactionDTO> getTransactionsByAccountId(int accountId);
    List<TransactionDTO> getAllTransactions();
    List<TransactionDTO> transferFunds(TransferRequestDTO transferRequest);
}
