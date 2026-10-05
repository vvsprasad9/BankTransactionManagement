package com.bank.controller;

import com.bank.dto.AccountDTO;
import com.bank.dto.DepositRequestDTO;
import com.bank.dto.WithdrawRequestDTO;
import com.bank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        AccountDTO created = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") int accountId) {
        AccountDTO account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable("accountNumber") String accountNumber) {
        AccountDTO account = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByCustomerId(@PathVariable("customerId") int customerId) {
        List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDTO> deposit(@Valid @RequestBody DepositRequestDTO depositRequest) {
        AccountDTO updatedAccount = accountService.deposit(depositRequest);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@Valid @RequestBody WithdrawRequestDTO withdrawRequest) {
        AccountDTO updatedAccount = accountService.withdraw(withdrawRequest);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") int accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
