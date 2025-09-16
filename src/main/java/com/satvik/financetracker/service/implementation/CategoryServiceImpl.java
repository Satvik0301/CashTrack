package com.satvik.financetracker.service.implementation;

import com.satvik.financetracker.models.Category;
import com.satvik.financetracker.models.Expense;
import com.satvik.financetracker.repositories.CategoryRepo;
import com.satvik.financetracker.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;
    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public Category save(Category category) {
        Category save = categoryRepo.save(category);
        logger.info("Category saved");
        return save;
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("Category not found"));
        categoryRepo.deleteById(id);
        logger.info("Category deleted");
    }

    @Override
    public Double getTotalExpenses(UUID id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getExpenses()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}
