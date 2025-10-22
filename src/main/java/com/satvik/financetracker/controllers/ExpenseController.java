package com.satvik.financetracker.controllers;

import com.satvik.financetracker.DTO.Request.ExpenseRequest;
import com.satvik.financetracker.DTO.Request.PageDTO;
import com.satvik.financetracker.models.Category;
import com.satvik.financetracker.models.Expense;
import com.satvik.financetracker.models.User;
import com.satvik.financetracker.repositories.CategoryRepo;
import com.satvik.financetracker.repositories.UserRepo;
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

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @PostMapping
    public ResponseEntity<Expense> saveExpense(@RequestBody ExpenseRequest req) {
        // map DTO -> entity
        Expense expense = new Expense();
        expense.setAmount(req.getAmount());
        expense.setTitle(req.getTitle());
        expense.setDescription(req.getDescription());
        // If client sends date it will be used; otherwise @PrePersist on entity will set it.
        expense.setDate(req.getDate());
        expense.setSpentWhere(req.getSpentWhere());

        // Validate and attach User
        if (req.getUserId() == null) {
            throw new RuntimeException("userId is required in ExpenseRequest");
        }
        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + req.getUserId()));
        expense.setUser(user);

        // Attach category if provided
        if (req.getCategoryId() != null) {
            Category category = categoryRepo.findById(req.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + req.getCategoryId()));
            expense.setCategory(category);
        } else {
            expense.setCategory(null);
        }

        Expense saved = expenseService.saveExpense(expense);
        logger.info("Expense saved: id={}, title={}, userId={}", saved.getId(), saved.getTitle(), user.getId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
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
        // If client sends user/category inside expense, normalize them as in saveExpenseRaw
        if (expense.getUser() != null && expense.getUser().getId() != null) {
            User u = userRepo.findById(expense.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + expense.getUser().getId()));
            expense.setUser(u);
        }

        if (expense.getCategory() != null && expense.getCategory().getId() != null) {
            Category c = categoryRepo.findById(expense.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + expense.getCategory().getId()));
            expense.setCategory(c);
        }

        Expense expense1 = expenseService.EditExpense(expense, id);
        logger.info("Expense updated: id={}", expense1.getId());
        return ResponseEntity.ok(expense1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
        logger.info("Expense deleted");
        return ResponseEntity.ok("Expense deleted");
    }

    //study sort
    @GetMapping("/by-user/{userId}")
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
