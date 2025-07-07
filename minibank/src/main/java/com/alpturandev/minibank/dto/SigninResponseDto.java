package com.alpturandev.minibank.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SigninResponseDto {
    private UUID id;
    private String token;
    private String username;
    private String email;
}
