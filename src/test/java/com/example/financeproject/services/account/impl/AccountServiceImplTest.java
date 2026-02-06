package com.example.financeproject.services.account.impl;

import com.example.financeproject.dto.dtoAccount.*;
import com.example.financeproject.mappers.AccountMapper;
import com.example.financeproject.models.Account;
import com.example.financeproject.models.Currency;
import com.example.financeproject.models.User;
import com.example.financeproject.repositories.account.AccountRepository;
import com.example.financeproject.repositories.transaction.AccountTransactionRepository;
import com.example.financeproject.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.financeproject.models.AccountType.CASH;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountTransactionRepository accountTransactionRepository;

    @Test
    void getAccountById() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        AccountDto accountDto = AccountDto
                .builder()
                .id(account.getId())
                .userId(account.getUser().getId())
                .name(account.getName())
                .balance(account.getBalance())
                .type(account.getType())
                .code(account.getCurrency().getCode())
                .exchangeRate(account.getCurrency().getExchangeRate())
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getAccountById(accountId);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> accountService.getAccountById(accountId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertNotNull(result);
        assertEquals(accountDto, result);

    }

    @Test
    void getAccountById_shouldThrow_whenAccountNotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                accountService.getAccountById(accountId)
        );
    }

    //create
    @Test
    void successCreateAccount() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        AccountDto accountDto = AccountDto
                .builder()
                .id(account.getId())
                .userId(account.getUser().getId())
                .name(account.getName())
                .balance(account.getBalance())
                .type(account.getType())
                .code(account.getCurrency().getCode())
                .exchangeRate(account.getCurrency().getExchangeRate())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountMapper.toEntity(any(AccountDto.class))).thenReturn(account);
        when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDto result = accountService.createAccount(accountDto);

        verify(accountRepository).save(argThat(acc ->
                acc.getAccountNumber() != null && acc.getAccountNumber().length() == 15
        ));//

        assertNotNull(result);
        assertEquals(accountDto, result);
    }

    @Test
    void createAccountShouldThrow_whenPlnNotOne() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        AccountDto accountDto = AccountDto
                .builder()
                .id(account.getId())
                .userId(account.getUser().getId())
                .name(account.getName())
                .balance(account.getBalance())
                .type(account.getType())
                .code(account.getCurrency().getCode())
                .exchangeRate(new BigDecimal(0))
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () ->
                accountService.createAccount(accountDto));
    }

    @Test
    void createAccountShouldThrow_whenUserNotFound() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        AccountDto accountDto = AccountDto
                .builder()
                .id(account.getId())
                .userId(account.getUser().getId())
                .name(account.getName())
                .balance(account.getBalance())
                .type(account.getType())
                .code(account.getCurrency().getCode())
                .exchangeRate(account.getCurrency().getExchangeRate())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                accountService.createAccount(accountDto));

    }

    //delete
    @Test
    void successDelete() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.deleteAccount(accountId);
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void deleteAccountShouldThrow_whenAccountNotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                accountService.deleteAccount(accountId));

        verify(accountRepository, never()).delete(any());
    }

    //edit
    @Test
    void successEditAccount() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        UpdateAccountDto updateAccountDto = UpdateAccountDto
                .builder()
                .name("Dima")
                .build();
        UpdateAccountDto updateAccountDtoRet = UpdateAccountDto
                .builder()
                .name("Dima")
                .balance(account.getBalance())
                .balanceDate(account.getBalanceDate())
                .type(account.getType())
                .build();

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));
        when(accountMapper.toUpdateAccountDto(any(Account.class)))
                .thenReturn(updateAccountDtoRet);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        UpdateAccountDto UpdateDto = accountService.editAccount(accountId, updateAccountDto);

        assertEquals("Dima", account.getName());
        assertEquals(new BigDecimal("100.00"), account.getBalance());
        assertEquals(updateAccountDtoRet, UpdateDto);
        verify(accountRepository, times(1)).save(any(Account.class));

    }

    @Test
    void editAccountShouldThrow_whenAccountNotFound() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Dima")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();

        UpdateAccountDto updateAccountDto = UpdateAccountDto
                .builder()
                .name("Dima")
                .build();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                accountService.editAccount(accountId, updateAccountDto));
        verify(accountRepository, never()).save(account);
    }

    //getAllAccounts
    @Test
    void successGetAllAccounts() {
        Long accountId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        GetAccountDto getAccountDto = GetAccountDto
                .builder()
                .id(accountId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00"))
                .type(CASH)
                .build();
        List<GetAccountDto> getAccountDtosList = List.of(getAccountDto);

        when(accountRepository.findAllByUserId(userId)).thenReturn(List.of(account));
        when(accountMapper.toGetAccountDto(any(Account.class))).thenReturn(getAccountDto);

        List<GetAccountDto> getAccountDtos = accountService.getAllAccounts(userId);
        assertEquals(getAccountDtosList, getAccountDtos);
    }

    @Test
    void getAllAccountsShouldThrow_whenAccountsNotFound() {
        Long userId = 1L;

        when(accountRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () ->
                accountService.getAllAccounts(userId));
        verify(accountMapper, never()).toGetAccountDto(any(Account.class));
    }
    @Test
    void getAllAccountsShouldThrow_whenUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                accountService.getAllAccounts(userId));
        verify(accountMapper, never()).toGetAccountDto(any(Account.class));
    }

    //Transfer
    @Test
    void transferShouldThrow_whenSenderAccountNotFound() {
        Long accountSenderId = 1L;
        Long accountRecipientId = 2L;
        TransferRequestDto transferRequestDto = TransferRequestDto
                .builder()
                .amount(new BigDecimal("122"))
                .description("nqqq")
                .toAccountId(accountRecipientId)
                .fromAccountId(accountSenderId)
                .build();

        when(accountRepository.findById(accountSenderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                accountService.transfer(transferRequestDto));
    }

    @Test
    void transferShouldThrow_whenRecipientAccountNotFound() {
        Long accountSenderId = 1L;
        Long accountRecipientId = 2L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountSenderId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        TransferRequestDto transferRequestDto = TransferRequestDto
                .builder()
                .amount(new BigDecimal("122"))
                .description("nqqq")
                .toAccountId(accountRecipientId)
                .fromAccountId(accountSenderId)
                .build();

        when(accountRepository.findById(accountSenderId)).thenReturn(Optional.of(account));
        when(accountRepository.findById(accountRecipientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                accountService.transfer(transferRequestDto));
    }

    @Test
    void transferShouldThrow_whenTransferHisSelf() {
        Long accountSenderId = 1L;
        Long accountRecipientId = 1L;
        Long userId = 1L;
        User user = User
                .builder()
                .id(userId)
                .username("Grisha")
                .email("grisha@gmail.com")
                .password("qqwqwqe")
                .build();
        Account account = Account
                .builder()
                .id(accountSenderId)
                .name("qqqq")
                .accountNumber("MY1R77387874549")
                .balance(new BigDecimal("100.00")).user(user)
                .type(CASH)
                .currency(new Currency("PLN", new BigDecimal(1)))
                .categories(List.of())
                .build();
        TransferRequestDto transferRequestDto = TransferRequestDto
                .builder()
                .amount(new BigDecimal("122"))
                .description("nqqq")
                .toAccountId(accountRecipientId)
                .fromAccountId(accountSenderId)
                .build();

        when(accountRepository.findById(accountSenderId)).thenReturn(Optional.of(account));
        when(accountRepository.findById(accountRecipientId)).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () ->
                accountService.transfer(transferRequestDto));

        verify(accountRepository, never()).save(account);
    }
    @Test
    void shouldTransferMoneySuccessfully() {
        Long fromId = 1L;
        Long toId = 2L;

        Account from = Account.builder()
                .id(fromId)
                .balance(new BigDecimal("100.00"))
                .build();

        Account to = Account.builder()
                .id(toId)
                .balance(new BigDecimal("50.00"))
                .build();

        TransferRequestDto dto = TransferRequestDto.builder()
                .fromAccountId(fromId)
                .toAccountId(toId)
                .amount(new BigDecimal("30.00"))
                .description("Test transfer")
                .build();

        when(accountRepository.findById(fromId)).thenReturn(Optional.of(from));
        when(accountRepository.findById(toId)).thenReturn(Optional.of(to));

        TransferResponseDto response = accountService.transfer(dto);

        assertEquals(new BigDecimal("70.00"), from.getBalance());
        assertEquals(new BigDecimal("80.00"), to.getBalance());

        verify(accountTransactionRepository).save(any());
        verify(accountRepository).save(from);
        verify(accountRepository).save(to);

        assertEquals(new BigDecimal("30.00"), response.getAmount());
    }
}