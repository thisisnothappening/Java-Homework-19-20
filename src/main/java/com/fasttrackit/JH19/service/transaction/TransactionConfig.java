package com.fasttrackit.JH19.service.transaction;

import com.fasttrackit.JH19.model.transaction.Transaction;
import com.fasttrackit.JH19.model.transaction.TransactionType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TransactionConfig {

    @Bean
    List<Transaction> multipleTransactionPost(TransactionRepository transactionRepository) {
        return transactionRepository.saveAll(List.of(
                new Transaction("shoes", TransactionType.BUY, 50.25),
                new Transaction("pants", TransactionType.BUY, 95.70),
                new Transaction("gloves", TransactionType.BUY, 30.1),
                new Transaction("shoes", TransactionType.SELL, 38.16),
                new Transaction("pants", TransactionType.SELL, 39.52),
                new Transaction("gloves", TransactionType.SELL, 15.63)
        ));
    }
}
