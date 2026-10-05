package com.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferRequestDTO {

    @NotBlank(message = "From account number is required")
    private String fromAccountNumber;

    @NotBlank(message = "To account number is required")
    private String toAccountNumber;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Transfer amount must be greater than zero")
    private Double amount;

    public TransferRequestDTO() {
    }

    public TransferRequestDTO(String fromAccountNumber, String toAccountNumber, Double amount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
