package com.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    public Account() {
    }

    public Account(Integer accountId, String accountNumber, String accountType, Double balance, Integer customerId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
    }

    public Account(String accountNumber, String accountType, Double balance, Integer customerId) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        this.balance = (this.balance == null ? 0.0 : this.balance) + amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        if (this.balance == null || amount > this.balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
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
