package com.example.financeproject.mappers;
import com.example.financeproject.dto.AccountDto;
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
}
