package com.example.financeproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


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
    @JoinColumn(name = "default_category_id")
    private DefaultCategory defaultCategory; // Дефолтная категория (если выбрана)

    @ManyToOne
    @JoinColumn(name = "user_category_id")
    private Category userCategory; // Пользовательская категория (если выбрана)

    private LocalDate date;

    private String description;


}



