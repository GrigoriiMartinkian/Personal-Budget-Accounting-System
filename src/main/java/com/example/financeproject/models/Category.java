package com.example.financeproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type; // Указываем, доход это или расход

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public Category(String name, CategoryType type, Account account) {
        this.name = name;
        this.type = type;
        this.account = account;
    }





}
