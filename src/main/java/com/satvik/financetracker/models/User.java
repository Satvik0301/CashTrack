package com.satvik.financetracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseModel implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

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
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", savings=" + savings +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                '}';
    }

}
