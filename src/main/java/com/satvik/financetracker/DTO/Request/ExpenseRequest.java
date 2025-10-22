package com.satvik.financetracker.DTO.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {
    private Double amount;
    private String title;
    private String description;
    /**
     * Use java.util.Date here to match your entity. Jackson will accept ISO-8601 strings by default.
     * If clients don't send date, server will set it on save via @PrePersist.
     */
    private Date date;
    private String spentWhere;
    private UUID userId;      // required: the id of an existing User
    private UUID categoryId;  // optional: id of an existing Category
}