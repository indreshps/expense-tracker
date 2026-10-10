package com.indresh.expensetracker.mapper;

import org.springframework.stereotype.Component;

import com.indresh.expensetracker.dto.ExpenseRequestDTO;
import com.indresh.expensetracker.dto.ExpenseResponseDTO;
import com.indresh.expensetracker.model.Expense;

@Component
public class ExpenseMapper {

    public Expense toEntity(ExpenseRequestDTO dto) {

        Expense expense = new Expense();

        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());
        expense.setDescription(dto.getDescription());
        expense.setDate(dto.getDate());
        expense.setPaymentMethod(dto.getPaymentMethod());

        return expense;
    }

    public ExpenseResponseDTO toResponseDTO(Expense expense) {

        ExpenseResponseDTO dto = new ExpenseResponseDTO();

        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setCategory(expense.getCategory());
        dto.setDescription(expense.getDescription());
        dto.setDate(expense.getDate());
        dto.setPaymentMethod(expense.getPaymentMethod());

        return dto;
    }
}