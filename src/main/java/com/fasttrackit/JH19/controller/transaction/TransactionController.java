package com.fasttrackit.JH19.controller.transaction;

import com.fasttrackit.JH19.model.transaction.Transaction;
import com.fasttrackit.JH19.model.transaction.TransactionType;
import com.fasttrackit.JH19.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    List<Transaction> getTransactionList(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount) {
        return transactionService.getTransactionList(type, minAmount, maxAmount);
    }

    @PostMapping
    void postTransaction(@RequestBody Transaction transaction) {
        transactionService.postTransaction(transaction);
    }

    @PatchMapping("{id}")
    Transaction changeProductAndAmount(@PathVariable Integer id, @RequestParam String product, @RequestParam Double amount) {
        return transactionService.changeProductAndAmount(id, product, amount);
    }

    @DeleteMapping("{id}")
    void deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
    }

    @GetMapping("{id}")
    Transaction getTransactionById(@PathVariable Integer id) {
        return transactionService.getTransactionById(id);
    }

    @PutMapping("{id}")
    Transaction replaceTransactionWithId(@PathVariable Integer id, @RequestBody Transaction transaction) {
        return transactionService.replaceTransactionWithId(id, transaction);
    }

    @GetMapping("reports/type")
    Map<TransactionType, List<Transaction>> mapTypeToListOfTransactions() {
        return transactionService.mapTypeToListOfTransactions();
    }

    @GetMapping("reports/product")
    Map<String, List<Transaction>> mapProductToListOfTransactions() {
        return transactionService.mapProductToListOfTransactions();
    }
}
