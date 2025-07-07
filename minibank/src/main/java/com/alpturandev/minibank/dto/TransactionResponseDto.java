package com.alpturandev.minibank.dto;

import com.alpturandev.minibank.entity.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {
    private UUID id;
    private String from;
    private String to;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionStatus;

    public static TransactionResponseDto fromEntity(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionResponseDto.builder()
            .id(transaction.getId())
            .from(String.valueOf(transaction.getFrom().getId()))
            .to(String.valueOf(transaction.getTo().getId()))
            .amount(transaction.getAmount())
            .transactionDate(transaction.getTransactionDate())
            .transactionStatus(transaction.getStatus().toString())
            .build();
    }
}
