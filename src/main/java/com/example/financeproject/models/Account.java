package com.example.financeproject.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String name;               // имя счета
    private String accountNumber;      // номер счета
    private BigDecimal balance;        // баланс
    private LocalDateTime balanceDate;     // дата баланса

    @Enumerated(EnumType.STRING)
    private AccountType type;          // CASH, DEPOSIT, CREDIT_CARD

    @Embedded
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonIgnore
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();


    public Account(String name, String accountNumber, BigDecimal balance,
                   AccountType type,
                   Currency currency, User user) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;

        this.type = type;
        this.currency = currency;
        this.user = user;

    }

    @PrePersist
    public void onCreate(){
        this.balanceDate = LocalDateTime.now();
    }
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Not enough money");
        }
        balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance = balance.add(amount);
    }








}

