package com.alpturandev.minibank.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequestDto {
    private String username;
    private String password;
}
