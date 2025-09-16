package com.satvik.financetracker.controllers;

import com.satvik.financetracker.DTO.Request.PageDTO;
import com.satvik.financetracker.models.Expense;
import com.satvik.financetracker.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @PostMapping
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense) {
        Expense expense1 = expenseService.saveExpense(expense);
        logger.info("Expense saved: " + expense.toString());
        return new ResponseEntity<>(expense1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> allExpenses = expenseService.getAllExpenses();
        logger.info("All expenses: " + allExpenses.toString());
        return ResponseEntity.ok(allExpenses);
    }

    //exception handel
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable UUID id) {
        Expense expense = expenseService.getExpense(id);
        logger.info("Expense found: " + expense.toString());
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense, @PathVariable UUID id) {
        Expense expense1 = expenseService.EditExpense(expense, id);
        logger.info("Expense updated: " + expense.toString());
        return ResponseEntity.ok(expense1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
        logger.info("Expense deleted");
        return ResponseEntity.ok("Expense deleted");
    }

    //study sort
    @GetMapping("/expenses/{userId}")
    public ResponseEntity<Page<Expense>> getAllExpensesPaged(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Expense> expenses = expenseService.getAllExpenses(userId, pageable);

        return ResponseEntity.ok(expenses);
    }


}
