package com.alpturandev.minibank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateRequestDto {
    String fromAccount;
    String toAccount;
    String amount;
}
