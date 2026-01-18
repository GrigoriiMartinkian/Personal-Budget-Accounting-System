package com.example.financeproject.services.transaction;

import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.models.Transaction;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {

    public static Specification<Transaction> withFilter(TransactionFilterDto filter) {
        return (Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            // userCategory (пользовательская категория)
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("userCategory").get("id"), filter.getCategoryId()));
            }

            // defaultCategory
            if (filter.getDefaultCategoryId() != null) {
                predicates.add(cb.equal(root.get("defaultCategory").get("id"), filter.getDefaultCategoryId()));
            }

            // date range - Transaction.date это LocalDateTime
            if (filter.getFromDate() != null) {
                LocalDateTime from = filter.getFromDate().atStartOfDay();
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), from));
            }
            if (filter.getToDate() != null) {
                LocalDateTime to = filter.getToDate().atTime(23, 59, 59);
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), to));
            }

            // amount range
            if (filter.getMinAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), filter.getMinAmount()));
            }
            if (filter.getMaxAmount() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("amount"), filter.getMaxAmount()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
