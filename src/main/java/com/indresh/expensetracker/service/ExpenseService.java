package com.indresh.expensetracker.service;

import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.indresh.expensetracker.dto.ExpenseRequestDTO;
import com.indresh.expensetracker.dto.ExpenseResponseDTO;
import com.indresh.expensetracker.mapper.ExpenseMapper;
import com.indresh.expensetracker.model.Expense;
import com.indresh.expensetracker.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            ExpenseMapper expenseMapper) {

        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }

    public List<ExpenseResponseDTO> getAllExpenses() {

        return expenseRepository.findAll()
                .stream()
                .map(expenseMapper::toResponseDTO)
                .toList();
    }

    public ExpenseResponseDTO getExpenseById(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Expense not found with ID: " + id));

        return expenseMapper.toResponseDTO(expense);
    }

    public ExpenseResponseDTO addExpense(ExpenseRequestDTO requestDTO) {

        Expense expense = expenseMapper.toEntity(requestDTO);

        Expense savedExpense = expenseRepository.save(expense);

        return expenseMapper.toResponseDTO(savedExpense);
    }

    public ExpenseResponseDTO updateExpense(
            Long id,
            ExpenseRequestDTO requestDTO) {

        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Expense not found with ID: " + id));

        existingExpense.setAmount(requestDTO.getAmount());
        existingExpense.setCategory(requestDTO.getCategory());
        existingExpense.setDescription(requestDTO.getDescription());
        existingExpense.setDate(requestDTO.getDate());
        existingExpense.setPaymentMethod(requestDTO.getPaymentMethod());

        Expense updatedExpense = expenseRepository.save(existingExpense);

        return expenseMapper.toResponseDTO(updatedExpense);
    }

    public void deleteExpense(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Expense not found with ID: " + id));

        expenseRepository.delete(expense);
    }

    public double getTotalExpenses() {

        return expenseRepository.findAll()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }



    public Map<String, Double> getCategoryWiseSummary() {

    return expenseRepository.findAll()
            .stream()
            .collect(Collectors.groupingBy(
                    Expense::getCategory,
                    Collectors.summingDouble(Expense::getAmount)
            ));
}

}