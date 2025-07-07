package com.alpturandev.minibank.controller;

import com.alpturandev.minibank.dto.TransactionCreateRequestDto;
import com.alpturandev.minibank.dto.TransactionResponseDto;
import com.alpturandev.minibank.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionCreateRequestDto transaction) {
        TransactionResponseDto newTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions(@PathVariable String id) {
        List<TransactionResponseDto> transactions = transactionService.getAllTransactions(id);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
}
