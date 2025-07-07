package com.alpturandev.minibank.controller;

import com.alpturandev.minibank.dto.AccountCreateRequestDto;
import com.alpturandev.minibank.dto.AccountResponseDto;
import com.alpturandev.minibank.dto.AccountUpdateRequestDto;
import com.alpturandev.minibank.security.CustomUserDetails;
import com.alpturandev.minibank.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getId();
        List<AccountResponseDto> accounts = accountService.getAllAccounts(String.valueOf(userId));
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountCreateRequestDto accountRequest) {
        AccountResponseDto createdAccount = accountService.createAccount(accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping
    public ResponseEntity<AccountResponseDto> updateAccount(@RequestBody AccountUpdateRequestDto account) {
        AccountResponseDto createdAccount = accountService.updateAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);
    }

    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable String id) {
        return accountService.getAccount(id);
    }

}
