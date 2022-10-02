package com.fasttrackit.JH19.service.transaction;

import com.fasttrackit.JH19.exceptions.NullFieldException;
import com.fasttrackit.JH19.exceptions.ResourceNotFoundException;
import com.fasttrackit.JH19.model.transaction.Transaction;
import com.fasttrackit.JH19.model.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionList(TransactionType type, Double minAmount, Double maxAmount) {
        if (type != null && minAmount != null && maxAmount != null) {
            return findByTypeAndMinAndMax(type, minAmount, maxAmount);
        } else if (type != null && minAmount != null) {
            return findByTypeAndMin(type, minAmount);
        } else if (type != null && maxAmount != null) {
            return findByTypeAndMax(type, maxAmount);
        } else if (minAmount != null && maxAmount != null) {
            return findByMinAndMax(minAmount, maxAmount);
        } else if (type != null) {
            return findByType(type);
        } else if (minAmount != null) {
            return findByMinAmount(minAmount);
        } else if (maxAmount != null) {
            return findByMaxAmount(maxAmount);
        }
        return getTransactions();
    }

    private List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    private List<Transaction> findByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    private List<Transaction> findByMinAmount(Double minAmount) {
        return transactionRepository.findByMinAmount(minAmount);
    }

    private List<Transaction> findByMaxAmount(Double maxAmount) {
        return transactionRepository.findByMaxAmount(maxAmount);
    }

    private List<Transaction> findByTypeAndMin(TransactionType type, Double minAmount) {
        return transactionRepository.findByTypeAndMin(type, minAmount);
    }

    private List<Transaction> findByTypeAndMax(TransactionType type, Double maxAmount) {
        return transactionRepository.findByTypeAndMax(type, maxAmount);
    }

    private List<Transaction> findByMinAndMax(Double minAmount, Double maxAmount) {
        return transactionRepository.findByMinAndMax(minAmount, maxAmount);
    }

    private List<Transaction> findByTypeAndMinAndMax(TransactionType type, Double minAmount, Double maxAmount) {
        return transactionRepository.findByTypeAndMinAndMax(type, minAmount, maxAmount);
    }

    public void postTransaction(Transaction transaction) {
        if (transaction.getProduct() == null || transaction.getType() == null || transaction.getAmount() == null) {
            throw new NullFieldException("Field cannot be null.");
        }
        transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction changeProductAndAmount(Integer id, String product, Double amount) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found!"));
        transaction.setProduct(product);
        transaction.setAmount(amount);
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Integer id) {
        /*
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found!");
        }
        transactionRepository.deleteById(id);
         */
        transactionRepository.delete(transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found")));
    }

    public Transaction getTransactionById(Integer id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found!"));
    }

    @Transactional
    public Transaction replaceTransactionWithId(Integer id, Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found!"));
        existingTransaction.setProduct(transaction.getProduct());
        existingTransaction.setType(transaction.getType());
        existingTransaction.setAmount(transaction.getAmount());
        return transactionRepository.save(existingTransaction);
    }

    public Map<TransactionType, List<Transaction>> mapTypeToListOfTransactions() {
        return transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getType));
    }

    public Map<String, List<Transaction>> mapProductToListOfTransactions() {
        return transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getProduct));
    }
}
