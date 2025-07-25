package com.alpturandev.minibank.repository;

import com.alpturandev.minibank.dto.AccountResponseDto;
import com.alpturandev.minibank.entity.Account;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByNameAndIdNot(String name, @NotNull(message = "Account ID is required for update") UUID attr0);

    @Query("SELECT a FROM Account a WHERE a.user.id = :id")
    List<Account> getAllAccountsByUserId(UUID id);

    @Query("SELECT a FROM Account a WHERE a.number = :number")
    Account getAccountByNumber(String number);
}
