package com.fasttrackit.JH19.model.transaction;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String product;
    @Column
    private TransactionType type;
    @Column
    private Double amount;

    public Transaction(String product, TransactionType type, Double amount) {
        this.product = product;
        this.type = type;
        this.amount = amount;
    }
}
