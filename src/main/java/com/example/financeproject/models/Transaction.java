package com.example.financeproject.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDateTime;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "default_category_id", nullable = true)
    private DefaultCategory defaultCategory; // Дефолтная категория (если выбрана)

    @ManyToOne
    @JoinColumn(name = "user_category_id")
    private Category userCategory; // Пользовательская категория (если выбрана)

    @Builder.Default
    private  LocalDateTime date= LocalDateTime.now();

    private String description;

//    public Transaction (Account account, DefaultCategory defaultCategory, Category userCategory, String description) {
//        this.account = account;
//        this.defaultCategory = defaultCategory;
//        this.userCategory = userCategory;
//        this.description = description;
//
//    }
}



