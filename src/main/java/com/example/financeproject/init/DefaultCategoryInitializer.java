package com.example.financeproject.init;

import com.example.financeproject.models.CategoryType;
import com.example.financeproject.models.DefaultCategory;
import com.example.financeproject.repositories.defaultCategory.DefaultCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.boot.CommandLineRunner;

@Component
@RequiredArgsConstructor
public class DefaultCategoryInitializer implements CommandLineRunner {

    private final DefaultCategoryRepository defaultCategoryRepository;

    @Override
    public void run(String... args) {
        if (defaultCategoryRepository.count() == 0) {
            List<DefaultCategory> defaultCategories = List.of(
                    new DefaultCategory(null, "Salary", CategoryType.INCOME),
                    new DefaultCategory(null, "Bonus", CategoryType.INCOME),
                    new DefaultCategory(null, "Savings", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Bills", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Clothes", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Communications", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Eating out", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Entertainment", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Food", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Gifts", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Health", CategoryType.EXPENSE),
                    new DefaultCategory(null, "House", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Pets", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Sports", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Taxi", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Toiletry", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Transport", CategoryType.EXPENSE),
                    new DefaultCategory(null, "Save up", CategoryType.EXPENSE)
            );

            defaultCategoryRepository.saveAll(defaultCategories);
            System.out.println(" Дефолтные категории успешно добавлены в базу данных.");
        } else {
            System.out.println(" Дефолтные категории уже существуют. Пропуск инициализации.");
        }
    }
}
