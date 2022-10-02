package com.fasttrackit.JH19.service.transaction;

import com.fasttrackit.JH19.model.transaction.Transaction;
import com.fasttrackit.JH19.model.transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.type = ?1")
    List<Transaction> findByType(TransactionType type);

    @Query("SELECT t FROM Transaction t WHERE t.amount >= ?1")
    List<Transaction> findByMinAmount(Double minAmount);

    @Query("SELECT t FROM Transaction t WHERE t.amount <= ?1")
    List<Transaction> findByMaxAmount(Double maxAmount);

    @Query("SELECT t FROM Transaction t WHERE t.type = ?1 and t.amount >= ?2")
    List<Transaction> findByTypeAndMin(TransactionType type, Double minAmount);

    @Query("SELECT t FROM Transaction t WHERE t.type = ?1 and t.amount <= ?2")
    List<Transaction> findByTypeAndMax(TransactionType type, Double maxAmount);

    @Query("SELECT t FROM Transaction t WHERE t.amount >= ?1 and t.amount <= ?2")
    List<Transaction> findByMinAndMax(Double minAmount, Double maxAmount);

    @Query("SELECT t FROM Transaction t WHERE t.type = ?1 and t.amount >= ?2 and t.amount <= ?3")
    List<Transaction> findByTypeAndMinAndMax(TransactionType type, Double minAmount, Double maxAmount);
}
