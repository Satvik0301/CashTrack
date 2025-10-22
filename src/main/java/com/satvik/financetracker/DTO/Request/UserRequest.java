package com.satvik.financetracker.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @DecimalMin(value = "0.0", inclusive = true, message = "Savings cannot be negative")
    private Double savings;

    @DecimalMin(value = "0.0", inclusive = true, message = "Salary cannot be negative")
    private Double salary;

    @Email(message = "Invalid email")
    private String email;
}