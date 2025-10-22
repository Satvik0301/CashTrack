package com.satvik.financetracker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends BaseModel {


    private String title;

    @Override
    public String toString() {
        return "Category{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", expenses=" + expenses +
                ", user=" + user +
                '}';
    }

    private String description;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();

    @ManyToOne
    private User user;
}
