package com.alpturandev.minibank.service.impl;

import com.alpturandev.minibank.dto.TransactionCreateRequestDto;
import com.alpturandev.minibank.dto.TransactionResponseDto;
import com.alpturandev.minibank.entity.Account;
import com.alpturandev.minibank.entity.Transaction;
import com.alpturandev.minibank.repository.AccountRepository;
import com.alpturandev.minibank.repository.TransactionRepository;
import com.alpturandev.minibank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Override
    public TransactionResponseDto createTransaction(TransactionCreateRequestDto transaction) {
        try {
            String fromId = transaction.getFromAccount();
            UUID fromAccountId = UUID.fromString(fromId);
            String toId = transaction.getToAccount();
            UUID toAccountId = UUID.fromString(toId);
            Account fromAccount = accountRepository.findById(fromAccountId).orElse(null);
            Account toAccount = accountRepository.findById(toAccountId).orElse(null);
            BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(transaction.getAmount()));
            fromAccount.setBalance(fromAccount.getBalance().subtract(transactionAmount));
            toAccount.setBalance(toAccount.getBalance().add(transactionAmount));

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            Transaction newTransaction = new Transaction();
            newTransaction.setTo(toAccount);
            newTransaction.setFrom(fromAccount);
            newTransaction.setAmount(transactionAmount);
            newTransaction.setStatus(Transaction.TransactionStatus.SUCCESS);

            transactionRepository.save(newTransaction);

            return TransactionResponseDto.fromEntity(newTransaction);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TransactionResponseDto> getAllTransactions(String accountId) {
        log.info("Fetching all transactions for account: {}", accountId);

        try {
            UUID accountUuid = UUID.fromString(accountId);

            // Find the account first to ensure it exists
            Account account = accountRepository.findById(accountUuid)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));

            // Get all transactions where this account is either sender or receiver
            List<Transaction> transactions = transactionRepository.findByFromIdOrToIdOrderByTransactionDateDesc(
                accountUuid);

            List<TransactionResponseDto> transactionResponseDtos = new ArrayList<>();

            for (Transaction transaction : transactionRepository.findAll()) {
                log.info("Account found with ID: {}", account.getId());
                transactionResponseDtos.add(TransactionResponseDto.fromEntity(transaction));
            }

            log.info("Found {} transactions for account: {}", transactions.size(), accountId);
            return transactionResponseDtos;

        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format for account ID: {}", accountId);
            throw new RuntimeException("Invalid account ID format: " + accountId);
        }
    }

}
