package com.bank.dto;


public class WithdrawRequestDTO {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Withdrawal amount must be greater than zero")
    private Double amount;

    public WithdrawRequestDTO() {
    }

    public WithdrawRequestDTO(String accountNumber, Double amount) {
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
