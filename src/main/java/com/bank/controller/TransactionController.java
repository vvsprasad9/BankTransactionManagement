package com.bank.controller;

import com.bank.dto.TransactionDTO;
import com.bank.dto.TransferRequestDTO;
import com.bank.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable("id") int transactionId) {
        TransactionDTO transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable("accountId") int accountId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transfer")
    public ResponseEntity<List<TransactionDTO>> transferFunds(@Valid @RequestBody TransferRequestDTO transferRequest) {
        List<TransactionDTO> transactions = transactionService.transferFunds(transferRequest);
        return ResponseEntity.ok(transactions);
    }
}
