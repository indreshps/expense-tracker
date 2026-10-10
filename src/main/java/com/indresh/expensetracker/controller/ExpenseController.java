package com.indresh.expensetracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.indresh.expensetracker.dto.ExpenseRequestDTO;
import com.indresh.expensetracker.dto.ExpenseResponseDTO;
import com.indresh.expensetracker.dto.TotalExpensesResponseDTO;
import com.indresh.expensetracker.service.ExpenseService;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/api/expenses")
    public List<ExpenseResponseDTO> getExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/api/expenses/total")
    public TotalExpensesResponseDTO getTotalExpenses() {

        double total = expenseService.getTotalExpenses();

        return new TotalExpensesResponseDTO(total);
    }


    @GetMapping("/api/expenses/summary/category")
public Map<String, Double> getCategoryWiseSummary() {

    return expenseService.getCategoryWiseSummary();
}


    @GetMapping("/api/expenses/{id}")
    public ExpenseResponseDTO getExpenseById(
            @PathVariable Long id) {

        return expenseService.getExpenseById(id);
    }

    @PostMapping("/api/expenses")
    public ExpenseResponseDTO addExpense(
            @Valid @RequestBody ExpenseRequestDTO requestDTO) {

        return expenseService.addExpense(requestDTO);
    }

    @PutMapping("/api/expenses/{id}")
    public ExpenseResponseDTO updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO requestDTO) {

        return expenseService.updateExpense(id, requestDTO);
    }

    @DeleteMapping("/api/expenses/{id}")
    public void deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);
    }
}