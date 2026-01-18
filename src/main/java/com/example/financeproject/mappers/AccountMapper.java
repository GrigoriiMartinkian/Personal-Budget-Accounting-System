package com.example.financeproject.mappers;
import com.example.financeproject.dto.dtoAccount.AccountDto;
import com.example.financeproject.dto.dtoAccount.GetAccountDto;
import com.example.financeproject.dto.dtoAccount.UpdateAccountDto;
import com.example.financeproject.models.Account;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = CurrencyMapper.class)
public interface AccountMapper {
        @Mapping(source = "user.id", target = "userId")
        @Mapping(source = "currency.code", target = "code")
        @Mapping(source = "currency.exchangeRate", target = "exchangeRate")
        AccountDto toDto(Account account);

        @Mapping(source = "userId", target = "user.id")
        @Mapping(source = "code", target = "currency.code")
        @Mapping(source = "exchangeRate", target = "currency.exchangeRate")

        Account toEntity(AccountDto dto);

        @Mapping(source = "currency.code", target = "code")
        @Mapping(source = "currency.exchangeRate", target = "exchangeRate")
        GetAccountDto toGetAccountDto(Account account);

        @Mapping(source = "code", target = "currency.code")
        @Mapping(source = "exchangeRate", target = "currency.exchangeRate")//test currencyMapper
        Account toEntity(GetAccountDto dto);


        UpdateAccountDto toUpdateAccountDto(Account account);
        Account toEntity( UpdateAccountDto dto);
}
