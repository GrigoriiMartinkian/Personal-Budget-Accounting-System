package com.example.financeproject.services.transaction;

import com.example.financeproject.dto.dtoTransaction.TransactionFilterDto;
import com.example.financeproject.models.QTransaction;
import com.querydsl.core.types.dsl.BooleanExpression;

public class TransactionPredicates {

    public static BooleanExpression buildPredicate(TransactionFilterDto filter) {
        QTransaction transaction = QTransaction.transaction;


        BooleanExpression predicate = transaction.isNotNull();

        if (filter == null) return predicate;


        if (filter.getCategoryId() != null) {
            predicate = predicate.and(transaction.userCategory.id.eq(filter.getCategoryId()));
        }


        if (filter.getDefaultCategoryId() != null) {
            predicate = predicate.and(transaction.defaultCategory.id.eq(filter.getDefaultCategoryId()));
        }


        if (filter.getDateFrom() != null) {
            predicate = predicate.and(transaction.date.goe(filter.getDateFrom().atStartOfDay()));
        }
        if (filter.getDateTo() != null) {
            predicate = predicate.and(transaction.date.loe(filter.getDateTo().atTime(23, 59, 59)));
        }


        if (filter.getMinAmount() != null) {
            predicate = predicate.and(transaction.amount.goe(filter.getMinAmount()));
        }
        if (filter.getMaxAmount() != null) {
            predicate = predicate.and(transaction.amount.loe(filter.getMaxAmount()));
        }

        return predicate;
    }
}
