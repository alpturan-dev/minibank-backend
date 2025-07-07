package com.alpturandev.minibank.service.impl;

import com.alpturandev.minibank.dto.AccountCreateRequestDto;
import com.alpturandev.minibank.dto.AccountResponseDto;
import com.alpturandev.minibank.dto.AccountUpdateRequestDto;
import com.alpturandev.minibank.entity.Account;
import com.alpturandev.minibank.entity.User;
import com.alpturandev.minibank.exception.AccountCreationException;
import com.alpturandev.minibank.exception.UserNotFoundException;
import com.alpturandev.minibank.repository.AccountRepository;
import com.alpturandev.minibank.repository.UserRepository;
import com.alpturandev.minibank.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public AccountResponseDto getAccount(String accountId) {
        UUID uuid = UUID.fromString(accountId);
        Account account = accountRepository.findById(uuid).orElse(null);
        return AccountResponseDto.fromEntity(account);
    }

    @Override
    @Transactional
    public AccountResponseDto createAccount(AccountCreateRequestDto accountRequest) {
        log.info("Creating account for user ID: {}", accountRequest.getUserId());

        try {
            // 1. Validate and fetch user
            User user = userRepository.findById(accountRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                    "User not found with ID: " + accountRequest.getUserId()));

            // 2. Generate unique account number
            String accountNumber = generateUniqueAccountNumber();

            // 3. Create new account
            Account account = new Account();
            account.setNumber(accountNumber);
            account.setName(accountRequest.getName());
            account.setBalance(accountRequest.getBalance());
            account.setUser(user);

            // 4. Save account
            Account savedAccount = accountRepository.save(account);

            log.info("Account created successfully with ID: {} and number: {}",
                savedAccount.getId(), savedAccount.getNumber());

            return AccountResponseDto.fromEntity(savedAccount);

        } catch (UserNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error creating account: {}", e.getMessage(), e);
            throw new AccountCreationException("Failed to create account: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AccountResponseDto updateAccount(AccountUpdateRequestDto accountRequest) {
        log.info("Updating account with ID: {}", accountRequest.getId());

        // Validate that account ID is provided
        if (accountRequest.getId() == null) {
            throw new IllegalArgumentException("Account ID is required for update operation");
        }

        // Find existing account
        Account existingAccount = accountRepository.findById(accountRequest.getId())
            .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountRequest.getId()));

        // Validate and fetch user (if user is being changed)
        User user = userRepository.findById(accountRequest.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + accountRequest.getUserId()));

        // Check if the account belongs to the user or if user is being changed
        if (!existingAccount.getUser().getId().equals(accountRequest.getUserId())) {
            // Additional validation: Check if user change is allowed
            log.warn("Attempting to change account owner from {} to {}",
                existingAccount.getUser().getId(), accountRequest.getUserId());
            // You might want to restrict this operation based on business rules
        }

        // Check if account name is being changed and if new name already exists
        if (!existingAccount.getName().equals(accountRequest.getName()) &&
            accountRepository.existsByNameAndIdNot(accountRequest.getName(), accountRequest.getId())) {
            throw new RuntimeException("Account name '" + accountRequest.getName() + "' already exists");
        }

        // Update account fields
        existingAccount.setName(accountRequest.getName());
        existingAccount.setBalance(accountRequest.getBalance());
        existingAccount.setUser(user);

        // Save updated account
        Account updatedAccount = accountRepository.save(existingAccount);

        log.info("Account updated successfully with ID: {}", updatedAccount.getId());

        // Convert to DTO and return
        return AccountResponseDto.fromEntity(updatedAccount);
    }

    @Override
    public void deleteAccount(String accountId) {
        UUID uuid = UUID.fromString(accountId);
        accountRepository.deleteById(uuid);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts(String id) {
        List<AccountResponseDto> dtos = new ArrayList<>();
        UUID uuid = UUID.fromString(id);
        for (Account account : accountRepository.getAllAccountsByUserId(uuid)) {
            log.info("Account found with ID: {}", account.getId());
            dtos.add(AccountResponseDto.fromEntity(account));
        }
        return dtos;
    }

    private String generateUniqueAccountNumber() {
        // Take first 10 characters of UUID and ensure it's numeric
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        // Convert to numeric string (may not be exactly 10 digits)
        long numericValue = Math.abs(uuid.hashCode()) % 10000000000L;

        // Ensure it's 10 digits
        return String.format("%010d", numericValue);
    }

}
