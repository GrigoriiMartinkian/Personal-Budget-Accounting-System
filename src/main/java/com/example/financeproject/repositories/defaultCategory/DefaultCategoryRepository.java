package com.example.financeproject.repositories.defaultCategory;

import com.example.financeproject.models.DefaultCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultCategoryRepository extends JpaRepository<DefaultCategory, Long> {
}
