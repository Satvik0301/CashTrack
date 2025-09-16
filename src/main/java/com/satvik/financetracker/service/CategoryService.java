package com.satvik.financetracker.service;

import com.satvik.financetracker.models.Category;

import java.util.UUID;

public interface CategoryService {
    Category save(Category category);
    void deleteCategory(UUID id);
    Double getTotalExpenses(UUID id);
}
