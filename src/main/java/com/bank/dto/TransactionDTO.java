package com.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionDTO {

    private Integer transactionId;

    @NotNull(message = "Account ID is required")
    private Integer accountId;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be positive")
    private Double amount;

    private String transactionDate;

    public TransactionDTO() {
    }

    public TransactionDTO(Integer transactionId, Integer accountId, String transactionType, Double amount, String transactionDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
