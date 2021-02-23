package br.com.matheuscirillo.cqrs.example.domain.entity;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transaction {

    private UUID id;
    private TransactionType type;
    private Double amount;

    public Transaction(UUID id, TransactionType type, Double amount) {
	this.id = id != null ? id : UUID.randomUUID();
	this.type = type;
	this.amount = amount;
    }

    public Transaction(TransactionType type, Double amount) {
	this.id = UUID.randomUUID();
	this.type = type;
	this.amount = amount;
    }
}
