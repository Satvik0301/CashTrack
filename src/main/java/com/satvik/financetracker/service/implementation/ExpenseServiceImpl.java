package com.satvik.financetracker.service.implementation;

import com.satvik.financetracker.models.Expense;
import com.satvik.financetracker.repositories.ExpenseRepo;
import com.satvik.financetracker.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepo expenseRepo;
    Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Override
    public Expense saveExpense(Expense expense) {
        Expense save = expenseRepo.save(expense);
        logger.info("Expense saved to database");
        return save;
    }

    @Override
    public Expense getExpense(UUID id) {
        Expense expense = expenseRepo.findById(id).orElseThrow(() -> new RuntimeException("Expense with id " + id + " not found"));
        logger.info("Expense retrieved from database");
        return expense;
    }

    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = expenseRepo.findAll();
        logger.info("Expense retrieved from database");
        return expenses;
    }

    @Override
    public Expense EditExpense(Expense expense, UUID id) {
        Expense expense1 = expenseRepo.findById(id).orElseThrow(() -> new RuntimeException("Expense with id " + id + " not found"));
        expense1.setAll(expense);
        Expense expense2 = expenseRepo.save(expense1);
        logger.info("Expense edited to database");
        return expense2;
    }

    @Override
    public void deleteExpense(UUID id) {
        expenseRepo.findById(id).orElseThrow(() -> new RuntimeException("Expense with id " + id + " not found"));
        expenseRepo.deleteById(id);
        logger.info("Expense deleted from database");
    }

    @Override
    public Page<Expense> getAllExpenses(UUID userId,Pageable pageable) {
        Page<Expense> expenses = expenseRepo.findAllByUser_Id(userId, pageable);
        logger.info("Expense retrieved from database");
        return expenses;
    }
}
