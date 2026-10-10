package com.indresh.expensetracker.dto;

public class TotalExpensesResponseDTO {

    private double totalExpenses;

    public TotalExpensesResponseDTO(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}