package com.satvik.financetracker.service;

import com.satvik.financetracker.models.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    Expense saveExpense(Expense expense);
    Expense getExpense(UUID id);
    List<Expense> getAllExpenses();
    Expense EditExpense(Expense expense, UUID id);
    void deleteExpense(UUID id);
    Page<Expense> getAllExpenses(UUID userId ,Pageable pageable);


}
