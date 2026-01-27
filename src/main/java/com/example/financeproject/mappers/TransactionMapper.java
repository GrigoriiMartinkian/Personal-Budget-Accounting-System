package com.example.financeproject.mappers;

import com.example.financeproject.dto.dtoTransaction.TransactionDto;

import com.example.financeproject.dto.dtoTransaction.UpdateTransactionDto;
import com.example.financeproject.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring", uses = {AccountMapper.class, CategoryMapper.class} )
public interface TransactionMapper {
    //----------------------------------------------------------------------------------------
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "defaultCategory.id", target = "defaultCategoryId")
    @Mapping(target = "categoryId", ignore = true)
    TransactionDto transactionToTransactionDefDto(Transaction transaction);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "userCategory.id", target = "categoryId")
    @Mapping(target = "defaultCategoryId", ignore = true)
    TransactionDto transactionToTransactionUsCatDto(Transaction transaction);
//----------------------------------------------------------------------------------------
    @Mapping(source = "accountId", target = "account.id")
    @Mapping(source = "defaultCategoryId", target = "defaultCategory.id")
    @Mapping(target = "userCategory", ignore = true)
    Transaction toTransaction(TransactionDto transactionDto);

    @Mapping(source = "accountId", target = "account.id")
    @Mapping(source = "categoryId", target = "userCategory.id")
    @Mapping(target = "defaultCategory", ignore = true)
    Transaction usCatToTransaction(TransactionDto transactionUsCatDto);
//----------------------------------------------------------------------------------------

    UpdateTransactionDto toUpdateTransactionDto(Transaction transaction);

//----------------------------------------------------------------------------------------

   // List<TransactionDto> toTransactionDtoList(List<Transaction> transactions);
   @Mapping(source = "account.id", target = "accountId")
   @Mapping(source = "userCategory.id", target = "categoryId")
   @Mapping(target = "defaultCategoryId", ignore = true)
   TransactionDto transactionToTransactionDto (Transaction transaction);
}
