package com.satvik.financetracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {

    @Column(nullable = false)
    private String name;


    private int age;


    private String username;

    @JsonIgnore
    private String password;


    private Double savings;


    private Double salary;


    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    public void setAll(User user) {
        setName(user.getName());
        setAge(user.getAge());
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setSavings(user.getSavings());
        setSalary(user.getSalary());
        setEmail(user.getEmail());
        setCategories(user.getCategories());
        setExpenses(user.getExpenses());
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", savings=" + savings +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", expenses=" + expenses +
                ", categories=" + categories +
                '}';
    }
}
