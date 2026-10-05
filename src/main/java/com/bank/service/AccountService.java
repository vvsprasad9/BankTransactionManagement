package com.bank.service;

import com.bank.dto.AccountDTO;
import com.bank.dto.DepositRequestDTO;
import com.bank.dto.WithdrawRequestDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountById(int accountId);
    AccountDTO getAccountByNumber(String accountNumber);
    List<AccountDTO> getAccountsByCustomerId(int customerId);
    List<AccountDTO> getAllAccounts();
    AccountDTO deposit(DepositRequestDTO depositRequest);
    AccountDTO withdraw(WithdrawRequestDTO withdrawRequest);
    void deleteAccount(int accountId);
}
