package com.alpturandev.minibank.service;

import com.alpturandev.minibank.dto.TransactionCreateRequestDto;
import com.alpturandev.minibank.dto.TransactionResponseDto;
import com.alpturandev.minibank.entity.Transaction;

import java.util.List;

public interface TransactionService {
    public TransactionResponseDto createTransaction(TransactionCreateRequestDto transaction);
    public List<TransactionResponseDto> getAllTransactions(String accountId);
}
