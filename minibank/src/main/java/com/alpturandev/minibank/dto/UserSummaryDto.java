package com.alpturandev.minibank.dto;

import com.alpturandev.minibank.entity.User;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryDto {

    private UUID id;

    private String username;

    private String email;

    public static UserSummaryDto fromEntity(User user) {
        if (user == null) {
            return null;
        }

        return UserSummaryDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

}