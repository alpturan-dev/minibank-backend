package com.alpturandev.minibank.service;

import com.alpturandev.minibank.dto.AccountCreateRequestDto;
import com.alpturandev.minibank.dto.AccountResponseDto;
import com.alpturandev.minibank.dto.AccountUpdateRequestDto;

import java.util.List;

public interface AccountService {
    public AccountResponseDto getAccount(String accountId);
    public AccountResponseDto createAccount(AccountCreateRequestDto account);
    public AccountResponseDto updateAccount(AccountUpdateRequestDto account);
    public void deleteAccount(String accountId);
    public List<AccountResponseDto> getAllAccounts();
}
