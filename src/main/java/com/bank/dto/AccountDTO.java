package com.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountDTO {

    private Integer accountId;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance cannot be negative")
    private Double balance;

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    public AccountDTO() {
    }

    public AccountDTO(Integer accountId, String accountNumber, String accountType, Double balance, Integer customerId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
