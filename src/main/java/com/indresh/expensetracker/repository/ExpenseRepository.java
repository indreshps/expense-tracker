
package com.indresh.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indresh.expensetracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}