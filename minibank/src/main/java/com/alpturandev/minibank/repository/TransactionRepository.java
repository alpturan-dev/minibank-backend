package com.alpturandev.minibank.repository;

import com.alpturandev.minibank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    /**
     * Find all transactions where the account is either sender or receiver
     * Results are ordered by transaction date in descending order (newest first)
     */
    @Query("SELECT t FROM Transaction t WHERE t.from.id = :accountId OR t.to.id = :accountId ORDER BY t.transactionDate DESC")
    List<Transaction> findByFromIdOrToIdOrderByTransactionDateDesc(@Param("accountId") UUID accountId);
}
