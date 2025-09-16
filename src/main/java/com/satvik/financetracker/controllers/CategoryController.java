package com.satvik.financetracker.controllers;

import com.satvik.financetracker.models.Category;
import com.satvik.financetracker.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category save = categoryService.save(category);
        logger.info("Category created: " + save);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        logger.info("Category deleted: " + id);
        return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Double> getTotalExpenses(@PathVariable UUID id) {
        Double totalExpenses = categoryService.getTotalExpenses(id);
        logger.info("Total expenses: " + totalExpenses);
        return new ResponseEntity<>(totalExpenses, HttpStatus.OK);
    }

}
