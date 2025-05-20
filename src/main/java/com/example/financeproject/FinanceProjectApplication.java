package com.example.financeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@SpringBootApplication
@EntityScan(basePackages = "com.example.financeProject.models")
public class FinanceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceProjectApplication.class, args);
    }

}
