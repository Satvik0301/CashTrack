package com.satvik.financetracker.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private Integer age;
    private String username;
    private Double savings;
    private Double salary;
    private String email;
    private int expenseCount;
    private int categoryCount;
}