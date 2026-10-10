package com.indresh.expensetracker.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseRequestDTO {

    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Payment method cannot be empty")
    private String paymentMethod;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}