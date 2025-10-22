package com.satvik.financetracker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Expense extends BaseModel {

    private Double amount;

    private String title;

    private String description;

    private Date date;

    private String spentWhere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"expenses", "categories"}) // prevents infinite loop when serializing user
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"expenses", "user"}) // prevent recursion
    private Category category;

    @PrePersist
    protected void onCreate(){
        date = new Date();
    }

    @Override
    public String toString() {
        UUID userId = (user != null) ? user.getId() : null;
        UUID categoryId = (category != null) ? category.getId() : null;

        return "Expense{" +
                "id=" + getId() +
                ", amount=" + amount +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", spentWhere='" + spentWhere + '\'' +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
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
