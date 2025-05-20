package com.example.financeproject.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;               // имя аккаунта
    private String accountNumber;      // номер счета
    private BigDecimal balance;        // баланс
    private LocalDate balanceDate;     // дата баланса

    @Enumerated(EnumType.STRING)
    private AccountType type;          // CASH, DEPOSIT, CREDIT_CARD

    @Embedded
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();


    public Account(String name, String accountNumber, BigDecimal balance,
                   LocalDate balanceDate, AccountType type,
                   Currency currency, User user) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.balanceDate = balanceDate;
        this.type = type;
        this.currency = currency;
        this.user = user;
    }








}

