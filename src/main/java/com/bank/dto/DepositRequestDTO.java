package com.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DepositRequestDTO {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Deposit amount must be greater than zero")
    private Double amount;

    public DepositRequestDTO() {
    }

    public DepositRequestDTO(String accountNumber, Double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
