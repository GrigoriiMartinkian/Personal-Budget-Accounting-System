package com.example.financeproject.mappers;
import com.example.financeproject.dto.CurrencyDto;
import com.example.financeproject.models.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
    public interface CurrencyMapper {
        @Mapping(source = "code", target = "code")
        @Mapping(source = "exchangeRate", target = "exchangeRate")
        Currency toEntity(CurrencyDto dto);

        @Mapping(source = "code", target = "code")
        @Mapping(source = "exchangeRate", target = "exchangeRate")
        CurrencyDto toDto(Currency currency);
    }


