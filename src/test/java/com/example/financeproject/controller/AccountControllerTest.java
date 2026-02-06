package com.example.financeproject.controller;

import com.example.financeproject.dto.dtoAccount.AccountDto;
import com.example.financeproject.dto.dtoAccount.GetAccountDto;
import com.example.financeproject.dto.dtoAccount.TransferRequestDto;
import com.example.financeproject.dto.dtoAccount.TransferResponseDto;

import com.example.financeproject.security.JWTService;
import com.example.financeproject.services.account.AccountService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.financeproject.models.AccountType.CASH;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {
    @MockBean
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void successCreateAccount() throws Exception {
        AccountDto accountDto = AccountDto
                .builder()
                .id(1L)
                .userId(1L)
                .name("Gosha")
                .balance(new BigDecimal("100"))
                .type(CASH)
                .code("PLN")
                .exchangeRate(new BigDecimal("1"))
                .build();

        when(accountService.createAccount(any(AccountDto.class))).thenReturn(accountDto);

        String jsonRequest = objectMapper.writeValueAsString(accountDto);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.name").value("Gosha"))
                .andExpect(jsonPath("$.balance").value(100))
                .andExpect(jsonPath("$.type").value("CASH"))
                .andExpect(jsonPath("$.code").value("PLN"))
                .andExpect(jsonPath("$.exchangeRate").value(1));
    }

    @Test
    void createAccount_validationError() throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "userId": 1,
                                      "name": "Ba",
                                      "balance": 100,
                                      "type": "CASH",
                                      "code": "PLN",
                                      "exchangeRate": 1
                                    }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.name").exists());
    }

    @Test
    void successDeleteAccount() throws Exception {
        mockMvc.perform(delete("/accounts/delete_account/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account successfully deleted"));

        verify(accountService).deleteAccount(1L);
    }

    @Test
    void updateAccount() throws Exception {
        mockMvc.perform(patch("/accounts/update_account/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "name": "Bad",
                                      "type": "CASH"
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Account successfully Edited"));
        verify(accountService).editAccount(eq(1L), any());
    }

    @Test
    void getAllAccounts_success() throws Exception {

        GetAccountDto account1 = GetAccountDto.builder()
                .id(1L)
                .name("Cash")
                .balance(new BigDecimal("100"))
                .type(CASH)
                .build();

        GetAccountDto account2 = GetAccountDto.builder()
                .id(2L)
                .name("Card")
                .balance(new BigDecimal("200"))
                .type(CASH)
                .build();

        List<GetAccountDto> accounts = List.of(account1, account2);

        when(accountService.getAllAccounts(1L)).thenReturn(accounts);


        mockMvc.perform(get("/accounts/get_all/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].id")
                        .value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].name")
                        .value(containsInAnyOrder("Cash", "Card")));
    }


    @Test
    void transferMoneyBetween() throws Exception {
        TransferRequestDto transferRequestDto = TransferRequestDto
                .builder()
                .toAccountId(1L)
                .fromAccountId(2L)
                .description("")
                .amount(new BigDecimal("100"))
                .build();
        TransferResponseDto transferResponseDto = TransferResponseDto
                .builder()
                .fromBalance(new BigDecimal("900"))
                .toBalance(new BigDecimal("1100"))
                .date(LocalDateTime.now())
                .amount(new BigDecimal("100"))
                .build();
        when(accountService.transfer(transferRequestDto)).thenReturn(transferResponseDto);

        String jsonRequest = objectMapper.writeValueAsString(transferRequestDto);
        String jsonResponse = objectMapper.writeValueAsString(transferResponseDto);

        mockMvc.perform(patch("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonResponse));

    }

    @Test
    void transferMoneyBetween_ValidationError() throws Exception {
        TransferRequestDto transferRequestDto = TransferRequestDto
                .builder()
                .toAccountId(1L)
                .fromAccountId(2L)
                .description("")
                .amount(new BigDecimal("-2"))
                .build();
        TransferResponseDto transferResponseDto = TransferResponseDto
                .builder()
                .fromBalance(new BigDecimal("900"))
                .toBalance(new BigDecimal("1100"))
                .date(LocalDateTime.now())
                .amount(new BigDecimal("100"))
                .build();
        when(accountService.transfer(transferRequestDto)).thenReturn(transferResponseDto);

        String jsonRequest = objectMapper.writeValueAsString(transferRequestDto);

        mockMvc.perform(patch("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.amount").exists());


    }

    @Test
    void transferMoneyBetween_ValidationError_NotNull() throws Exception {
        TransferRequestDto transferRequestDto = TransferRequestDto
                .builder()
                .toAccountId(null)
                .fromAccountId(2L)
                .description("")
                .amount(new BigDecimal("100"))
                .build();
        TransferResponseDto transferResponseDto = TransferResponseDto
                .builder()
                .fromBalance(new BigDecimal("900"))
                .toBalance(new BigDecimal("1100"))
                .date(LocalDateTime.now())
                .amount(new BigDecimal("100"))
                .build();
        when(accountService.transfer(transferRequestDto)).thenReturn(transferResponseDto);

        String jsonRequest = objectMapper.writeValueAsString(transferRequestDto);

        mockMvc.perform(patch("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.toAccountId").exists());

    }
}