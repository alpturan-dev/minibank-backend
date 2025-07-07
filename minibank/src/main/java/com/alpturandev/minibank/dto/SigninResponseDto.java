package com.alpturandev.minibank.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SigninResponseDto {
    private String token;
    private String username;
    private String email;
}
