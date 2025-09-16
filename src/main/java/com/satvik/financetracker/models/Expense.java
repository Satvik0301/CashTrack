package com.satvik.financetracker.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense extends BaseModel {

    private Double amount;

    private String title;

    private String description;

    private Date date;

    private String spentWhere;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    protected void onCreate(){
        date = new Date();
    }

    @Override
    public String toString() {
        return "Expense{" +
                "amount=" + amount +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", spentWhere='" + spentWhere + '\'' +
                ", user=" + user +
                ", category=" + category +
                '}';
    }

    public void setAll(Expense expense) {
        this.amount = expense.getAmount();
        this.title = expense.getTitle();
        this.description = expense.getDescription();
        this.date = expense.getDate();
        this.spentWhere = expense.getSpentWhere();
        this.user = expense.getUser();
        this.category = expense.getCategory();
    }
}
