package com.alpturandev.minibank.dto;

import com.alpturandev.minibank.entity.Account;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {

    private UUID id;

    private String number;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;

    private UserSummaryDto user;

    public static AccountResponseDto fromEntity(Account account) {
        if (account == null) {
            return null;
        }

        return AccountResponseDto.builder()
            .id(account.getId())
            .number(account.getNumber())
            .name(account.getName())
            .balance(account.getBalance())
            .user(UserSummaryDto.fromEntity(account.getUser()))
            .build();
    }

}